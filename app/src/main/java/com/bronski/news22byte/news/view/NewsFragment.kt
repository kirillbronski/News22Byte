package com.bronski.news22byte.news.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bronski.news22byte.MainActivity
import com.bronski.news22byte.R
import com.bronski.news22byte.article.view.ArticleFragment
import com.bronski.news22byte.core.state.ViewState
import com.bronski.news22byte.databinding.FragmentNewsBinding
import com.bronski.news22byte.news.view.adapter.NewsAdapter
import com.bronski.news22byte.news.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private val viewModel by viewModel<NewsViewModel>()
    private lateinit var newsAdapter: NewsAdapter

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

    private fun checkViewState() {
        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect {
                when (it) {
                    is ViewState.SuccessState -> {
                        hideProgressIndicator()
                        newsAdapter.newsListDiffer.submitList(it.newsResponse.articles)
                    }
                    is ViewState.LoadingState -> {
                        showProgressIndicator()
                    }
                    is ViewState.ErrorState -> {
                        hideProgressIndicator()
                        displayToastMessage(it.message.toString())
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
        }
    }

    private fun showProgressIndicator() {
        binding.progressBar.isVisible = true
    }

    private fun hideProgressIndicator() {
        binding.progressBar.isVisible = false
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