package com.damir.stipancic.newsappv3.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
        navController.addOnDestinationChangedListener{_, destination, _ ->
            if (destination == navController.findDestination(R.id.articleDetailsFragment))
                binding.bottomNavigationView.visibility = View.GONE
            else
                binding.bottomNavigationView.visibility = View.VISIBLE
        }

        binding.bottomNavigationView.setupWithNavController(navController)

    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount

        if (count == 0) {
            super.onBackPressed()
            //additional code
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}