package com.damir.stipancic.newsappv3.ui.fragments.latest_news_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.damir.stipancic.newsappv3.data.database.ArticleDatabase
import com.damir.stipancic.newsappv3.databinding.FragmentLatestNewsBinding
import com.damir.stipancic.newsappv3.repository.NewsRepository
import com.damir.stipancic.newsappv3.ui.NewsRecyclerAdapter

class LatestNewsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val repository = NewsRepository(ArticleDatabase.getInstance(requireContext()))
        val binding = FragmentLatestNewsBinding.inflate(inflater)
        val viewModelFactory = LatestNewsViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory)[LatestNewsViewModel::class.java]

        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = NewsRecyclerAdapter(NewsRecyclerAdapter.OnClickListener{ article ->
            viewModel.displayArticleDetails(article)
        })
        binding.latestNewsRecycler.adapter = adapter

        viewModel.getLatestNewsFromDB().observe(viewLifecycleOwner) { articles ->
            adapter.submitList(articles)
        }

        viewModel.navigateToClickedArticle.observe(viewLifecycleOwner) {
            if(null != it) {
                this.findNavController()
                    .navigate(LatestNewsFragmentDirections.showArticleDetails(it))

                viewModel.displayArticleDetailsComplete()

            }
        }

        return binding.root
    }
}