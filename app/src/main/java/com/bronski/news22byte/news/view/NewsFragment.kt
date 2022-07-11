package com.bronski.news22byte.news.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bronski.news22byte.MainActivity
import com.bronski.news22byte.R
import com.bronski.news22byte.article.view.ArticleFragment
import com.bronski.news22byte.core.state.ViewState
import com.bronski.news22byte.core.utils.Constants.Companion.QUERY_PAGE_SIZE
import com.bronski.news22byte.databinding.FragmentNewsBinding
import com.bronski.news22byte.news.view.adapter.NewsAdapter
import com.bronski.news22byte.news.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private val viewModel by viewModel<NewsViewModel>()
    private lateinit var newsAdapter: NewsAdapter

    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (setupScrollListener(recyclerView)) {
                viewModel.getNews()
                isScrolling = false
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        checkViewState()
        newsAdapter.setOnItemClickListener {
            displayFragment(ArticleFragment.newInstance(it.url))
        }
    }

    private fun setupScrollListener(recyclerView: RecyclerView) : Boolean {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount

        val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
        val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
        val isNotAtBeginning = firstVisibleItemPosition >= 0
        val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE

        return isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                isTotalMoreThanVisible && isScrolling
    }

    private fun checkViewState() {
        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect {
                when (it) {
                    is ViewState.SuccessState -> {
                        newsAdapter.newsListDiffer.submitList(it.newsResponse.articles)
                        val totalPages = it.newsResponse.totalResults / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.getPage() == totalPages
                        Log.d("TAG", "checkViewState: $totalPages")
                        hideProgressIndicator()
                    }
                    is ViewState.LoadingState -> {
                        showProgressIndicator()
                    }
                    is ViewState.ErrorState -> {
                        displayToastMessage(it.message.toString())
                        hideProgressIndicator()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setUpAdapter() {
        newsAdapter = NewsAdapter()
        binding.recyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addOnScrollListener(this@NewsFragment.scrollListener)
        }
    }

    private fun showProgressIndicator() {
        binding.progressBar.isVisible = true
        isLoading = true
    }

    private fun hideProgressIndicator() {
        binding.progressBar.isVisible = false
        isLoading = false
    }

    private fun displayToastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = NewsFragment()
    }

    private fun displayFragment(fragment: Fragment) {
        (requireActivity() as MainActivity).displayFragment(fragment) {
            replace(R.id.fragment_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
        }
    }
}