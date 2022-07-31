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
import com.damir.stipancic.newsappv3.data.models.Article
import com.damir.stipancic.newsappv3.databinding.FragmentSearchNewsBinding
import com.damir.stipancic.newsappv3.repository.NewsRepository
import com.damir.stipancic.newsappv3.ui.NewsRecyclerAdapter
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
        val adapter = NewsRecyclerAdapter(NewsRecyclerAdapter.OnClickListener { article, position ->
            val arguments = mutableListOf<Pair<List<Article>, Int>>()
            arguments.add(Pair(article, position))
            searchNewsViewModel.displayArticleDetails(arguments)
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
                    SearchNewsFragmentDirections.actionSearchNewsFragmentToArticleDetailsFragment(
                        it[0].first.toTypedArray(),
                        it[0].second )
                )
                searchNewsViewModel.displayArticleDetailsComplete()

            }
        }

        return binding.root
    }
}