package com.damir.stipancic.newsappv3.ui.fragments.saved_news_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.damir.stipancic.newsappv3.data.database.ArticleDatabase
import com.damir.stipancic.newsappv3.databinding.FragmentSavedNewsBinding
import com.damir.stipancic.newsappv3.repository.NewsRepository
import com.damir.stipancic.newsappv3.ui.NewsRecyclerAdapter

class SavedNewsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = FragmentSavedNewsBinding.inflate(inflater)
        val repository = NewsRepository(ArticleDatabase.getInstance(requireContext()))
        val viewModelFactory = SavedNewsViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory)[SavedNewsViewModel::class.java]

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.savedNewsRecycler.adapter = NewsRecyclerAdapter(NewsRecyclerAdapter.OnClickListener {
            viewModel.displayArticleDetails(it)
        })

        binding.savedNewsRecycler.addItemDecoration(DividerItemDecoration(binding.savedNewsRecycler.context, DividerItemDecoration.VERTICAL))
        binding.savedNewsRecycler.setHasFixedSize(true)

        viewModel.navigateToClickedArticle.observe(viewLifecycleOwner){
            it?.let {
                this.findNavController().navigate(SavedNewsFragmentDirections.actionSavedNewsFragmentToArticleDetailsFragment(it))
                viewModel.displayArticleDetailsComplete()
            }
        }

        return binding.root
    }
}