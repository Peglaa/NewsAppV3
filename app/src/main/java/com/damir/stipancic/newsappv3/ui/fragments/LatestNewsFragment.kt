package com.damir.stipancic.newsappv3.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.damir.stipancic.newsappv3.R
import com.damir.stipancic.newsappv3.databinding.FragmentLatestNewsBinding

class LatestNewsFragment : Fragment(R.layout.fragment_latest_news) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = FragmentLatestNewsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        return binding.root
    }
}