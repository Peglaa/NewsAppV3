package com.damir.stipancic.newsappv3.ui.fragments.search_news_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.damir.stipancic.newsappv3.databinding.FragmentSearchNewsBinding


class SearchNewsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false) //remove up arrow cause we are in bottom nav fragment
        val binding = FragmentSearchNewsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        return binding.root
    }
}