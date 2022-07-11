package com.damir.stipancic.newsappv3.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.damir.stipancic.newsappv3.R
import com.damir.stipancic.newsappv3.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityNewsBinding>(this, R.layout.activity_news)
        val navController = binding.newsNavHostFragment.getFragment<NavHostFragment>().navController

        binding.bottomNavigationView.setupWithNavController(navController)
    }
}