package com.damir.stipancic.newsappv3.ui.fragments.article_detail_screen

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.damir.stipancic.newsappv3.data.database.ArticleDatabase
import com.damir.stipancic.newsappv3.databinding.FragmentArticleDetailsBinding
import com.damir.stipancic.newsappv3.repository.NewsRepository
import com.google.android.material.snackbar.Snackbar

class ArticleDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = FragmentArticleDetailsBinding.inflate(inflater)

        val article = ArticleDetailsFragmentArgs.fromBundle(arguments!!).selectedArticle
        val repository = NewsRepository(ArticleDatabase.getInstance(requireContext()))

        val viewModelFactory = ArticleDetailsViewModelFactory(article, repository)
        val viewModel = ViewModelProvider(this, viewModelFactory)[ArticleDetailViewModel::class.java]

        binding.lifecycleOwner = this.viewLifecycleOwner

        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if(article.saved)
            binding.saveFab.visibility = View.GONE

        binding.saveFab.setOnClickListener {
            viewModel.onSaveClicked()
            Snackbar.make(requireView(), "Article saved successfully!", Snackbar.LENGTH_SHORT).show()
            it.visibility = View.GONE
        }

        binding.viewModel = viewModel

        return binding.root
    }



}

