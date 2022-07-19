package com.damir.stipancic.newsappv3.ui.fragments.saved_news_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.damir.stipancic.newsappv3.databinding.FragmentSavedNewsBinding

class SavedNewsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = FragmentSavedNewsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        return binding.root
    }
}