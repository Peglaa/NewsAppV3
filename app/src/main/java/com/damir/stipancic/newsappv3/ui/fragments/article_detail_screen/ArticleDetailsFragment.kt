package com.damir.stipancic.newsappv3.ui.fragments.article_detail_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.damir.stipancic.newsappv3.data.database.ArticleDatabase
import com.damir.stipancic.newsappv3.databinding.FragmentArticleDetailsBinding
import com.damir.stipancic.newsappv3.repository.NewsRepository
import com.damir.stipancic.newsappv3.ui.ArticleDetailsRecyclerAdapter

class ArticleDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = FragmentArticleDetailsBinding.inflate(inflater)
        val repository = NewsRepository(ArticleDatabase.getInstance(requireContext()))
        val viewModelFactory = ArticleDetailsViewModelFactory(repository)
        val articleDetailsViewModel: ArticleDetailViewModel by viewModels(factoryProducer = { viewModelFactory })
        val articleList = ArticleDetailsFragmentArgs.fromBundle(requireArguments()).articleList
        val clickedPosition = ArticleDetailsFragmentArgs.fromBundle(requireArguments()).position
        val articleAdapter = ArticleDetailsRecyclerAdapter(this, articleDetailsViewModel)

        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.apply {
            lifecycleOwner = this@ArticleDetailsFragment.viewLifecycleOwner
            viewPagerArticleDetails.adapter = articleAdapter
        }

        articleAdapter.submitList(articleList.toMutableList())
        binding.viewPagerArticleDetails.currentItem = clickedPosition

        return binding.root
    }

}

