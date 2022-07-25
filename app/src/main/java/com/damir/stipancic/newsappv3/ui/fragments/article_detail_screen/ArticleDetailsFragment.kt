package com.damir.stipancic.newsappv3.ui.fragments.article_detail_screen

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.damir.stipancic.newsappv3.data.database.ArticleDatabase
import com.damir.stipancic.newsappv3.databinding.FragmentArticleDetailsBinding
import com.damir.stipancic.newsappv3.repository.NewsRepository

class ArticleDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = FragmentArticleDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val article = ArticleDetailsFragmentArgs.fromBundle(arguments!!).selectedArticle
        val repository = NewsRepository(ArticleDatabase.getInstance(requireContext()))

        val viewModelFactory = ArticleDetailsViewModelFactory(article, repository)

        binding.viewModel = ViewModelProvider(this, viewModelFactory)[ArticleDetailViewModel::class.java]

        return binding.root
    }
}

