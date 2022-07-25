package com.damir.stipancic.newsappv3.ui.fragments.latest_news_screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.damir.stipancic.newsappv3.data.database.ArticleDatabase
import com.damir.stipancic.newsappv3.databinding.FragmentLatestNewsBinding
import com.damir.stipancic.newsappv3.getBackStackData
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

        binding.latestNewsRecycler.adapter = NewsRecyclerAdapter(NewsRecyclerAdapter.OnClickListener{ article ->
            viewModel.displayArticleDetails(article)
        })

        binding.latestNewsRecycler.addItemDecoration(DividerItemDecoration(binding.latestNewsRecycler.context, DividerItemDecoration.VERTICAL))

        viewModel.navigateToClickedArticle.observe(viewLifecycleOwner) {
            if(null != it) {
                Log.d("latest_news_fragment", "onCreateView: AFTER_NAV")
                this.findNavController()
                    .navigate(LatestNewsFragmentDirections.showArticleDetails(it))

                viewModel.displayArticleDetailsComplete()

            }
        }

        getBackStackData<Boolean?>("updateRecycler"){
            it?.let {
                if(it)
                    viewModel.updateRecyclerItems()
            }
        }

        return binding.root
    }
}