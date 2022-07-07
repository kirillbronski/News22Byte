package com.bronski.news22byte.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.bronski.news22byte.databinding.FragmentArticleBinding
import com.bronski.news22byte.utils.Constants.Companion.URL

class ArticleFragment : Fragment() {

    private lateinit var binding: FragmentArticleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString(URL)
        binding.webView.apply {
            webViewClient = WebViewClient()
            if (url != null) {
                loadUrl(url)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(url: String) =
            ArticleFragment().apply {
                arguments = Bundle().apply {
                    putString(URL, url)
                }
            }
    }
}