package com.damir.stipancic.newsappv3.ui.fragments.article_detail_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.damir.stipancic.newsappv3.R
import com.damir.stipancic.newsappv3.data.database.ArticleDatabase
import com.damir.stipancic.newsappv3.databinding.FragmentArticleDetailsBinding
import com.damir.stipancic.newsappv3.repository.NewsRepository
import com.google.android.material.snackbar.Snackbar

class ArticleDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = FragmentArticleDetailsBinding.inflate(inflater)
        val article = ArticleDetailsFragmentArgs.fromBundle(requireArguments()).selectedArticle
        val repository = NewsRepository(ArticleDatabase.getInstance(requireContext()))
        val viewModelFactory = ArticleDetailsViewModelFactory(article, repository)
        val articleDetailsViewModel = ViewModelProvider(this, viewModelFactory)[ArticleDetailViewModel::class.java]

        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.apply {
            lifecycleOwner = this@ArticleDetailsFragment.viewLifecycleOwner
            viewModel = articleDetailsViewModel

            saveFab.setOnClickListener {
                val previousFragment = findNavController().previousBackStackEntry?.destination?.id
                if(previousFragment == R.id.searchNewsFragment) {
                    article.saved = true
                    article.createdAt = 0L
                    articleDetailsViewModel.insertArticle(article)
                }
                else
                    articleDetailsViewModel.onSaveClicked()
                articleDetailsViewModel.onSaveClicked()
                Snackbar.make(requireView(), "Article saved successfully!", Snackbar.LENGTH_SHORT).show()
                it.visibility = View.GONE
            }

            if(article.saved)
                saveFab.visibility = View.GONE
        }

        return binding.root
    }



}

