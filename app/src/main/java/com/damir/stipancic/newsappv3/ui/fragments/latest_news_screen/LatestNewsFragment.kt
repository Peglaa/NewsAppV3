package com.damir.stipancic.newsappv3.ui.fragments.latest_news_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.damir.stipancic.newsappv3.databinding.FragmentLatestNewsBinding
import com.damir.stipancic.newsappv3.ui.NewsRecyclerAdapter

class LatestNewsFragment : Fragment() {

    private val viewModel: LatestNewsViewModel by lazy {
        ViewModelProvider(this)[LatestNewsViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = FragmentLatestNewsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.latestNewsRecycler.adapter = NewsRecyclerAdapter(NewsRecyclerAdapter.OnClickListener{
            viewModel.displayArticleDetails(it)
        })

        binding.latestNewsRecycler.addItemDecoration(DividerItemDecoration(binding.latestNewsRecycler.context, DividerItemDecoration.VERTICAL))

        viewModel.navigateToClickedArticle.observe(viewLifecycleOwner) {
            if (null != it) {
                this.findNavController().navigate(LatestNewsFragmentDirections.showArticleDetails(it))

                viewModel.displayArticleDetailsComplete()
            }
        }



        return binding.root
    }
}