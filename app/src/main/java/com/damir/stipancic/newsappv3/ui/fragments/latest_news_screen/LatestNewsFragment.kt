package com.damir.stipancic.newsappv3.ui.fragments.latest_news_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.damir.stipancic.newsappv3.data.database.ArticleDatabase
import com.damir.stipancic.newsappv3.data.models.Article
import com.damir.stipancic.newsappv3.databinding.FragmentLatestNewsBinding
import com.damir.stipancic.newsappv3.repository.NewsRepository
import com.damir.stipancic.newsappv3.ui.NewsRecyclerAdapter

class LatestNewsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {


        val binding = FragmentLatestNewsBinding.inflate(inflater)
        val repository = NewsRepository(ArticleDatabase.getInstance(requireActivity()))
        val viewModelFactory = LatestNewsViewModelFactory(repository)
        val latestNewsViewModel: LatestNewsViewModel by viewModels(factoryProducer = { viewModelFactory })
        val adapter = NewsRecyclerAdapter(NewsRecyclerAdapter.OnClickListener{ article, position ->
            val arguments = mutableListOf<Pair<List<Article>, Int>>()
            arguments.add(Pair(article, position))
            latestNewsViewModel.displayArticleDetails(arguments)
        }).apply {
            registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver(){
                override fun onChanged() {
                    super.onChanged()
                    binding.latestNewsRecycler.scrollToPosition(0)
                }
            })
        }

        binding.apply {
            lifecycleOwner = this@LatestNewsFragment.viewLifecycleOwner
            viewModel = latestNewsViewModel
            latestNewsRecycler.adapter = adapter
        }

        latestNewsViewModel.apply {
            //-----------------------------------
            getLatestNewsFromDB().observe(viewLifecycleOwner) { articles ->
                adapter.submitList(articles){
                    adapter.notifyDataSetChanged()
                }
            }

            //-----------------------------------
            navigateToClickedArticle.observe(viewLifecycleOwner) {
                if(null != it) {
                    this@LatestNewsFragment.findNavController()
                        .navigate(LatestNewsFragmentDirections.showArticleDetails(
                            it[0].first.toTypedArray(),
                            it[0].second ))

                    latestNewsViewModel.displayArticleDetailsComplete()

                }
            }
        }

        return binding.root
    }
}