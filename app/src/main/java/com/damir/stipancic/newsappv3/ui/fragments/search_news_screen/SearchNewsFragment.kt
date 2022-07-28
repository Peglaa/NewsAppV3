package com.damir.stipancic.newsappv3.ui.fragments.search_news_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.damir.stipancic.newsappv3.data.database.ArticleDatabase
import com.damir.stipancic.newsappv3.databinding.FragmentSearchNewsBinding
import com.damir.stipancic.newsappv3.repository.NewsRepository
import com.damir.stipancic.newsappv3.ui.NewsRecyclerAdapter
import com.damir.stipancic.newsappv3.ui.fragments.saved_news_screen.SavedNewsFragmentDirections
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false) //remove up arrow cause we are in bottom nav fragment
        val binding = FragmentSearchNewsBinding.inflate(inflater)
        val repository = NewsRepository(ArticleDatabase.getInstance(requireContext()))
        val viewModelFactory = SearchNewsViewModelFactory(repository)
        val searchNewsViewModel =
            ViewModelProvider(this, viewModelFactory)[SearchNewsViewModel::class.java]
        val adapter = NewsRecyclerAdapter(NewsRecyclerAdapter.OnClickListener {
            searchNewsViewModel.displayArticleDetails(it)
        })
        var job: Job? = null

        binding.apply {
            lifecycleOwner = this@SearchNewsFragment
            searchNewsRecycler.adapter = adapter
        }

        binding.etSearch.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                it?.let {
                    if (it.toString().isNotEmpty())
                        searchNewsViewModel.getSearchedNews(it.toString())
                }
            }
        }

        searchNewsViewModel.searchResponse.observe(viewLifecycleOwner)
        {
            adapter.submitList(it.body()?.articles)
        }

        searchNewsViewModel.navigateToClickedArticle.observe(viewLifecycleOwner)
        {
            it?.let {
                this@SearchNewsFragment.findNavController().navigate(
                    SearchNewsFragmentDirections.actionSearchNewsFragmentToArticleDetailsFragment(it)
                )
                searchNewsViewModel.displayArticleDetailsComplete()

            }
        }

        return binding.root
    }
}