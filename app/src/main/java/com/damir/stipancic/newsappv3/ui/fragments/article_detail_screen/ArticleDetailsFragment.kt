package com.damir.stipancic.newsappv3.ui.fragments.article_detail_screen

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.damir.stipancic.newsappv3.data.database.ArticleDatabase
import com.damir.stipancic.newsappv3.databinding.FragmentArticleDetailsBinding
import com.damir.stipancic.newsappv3.repository.NewsRepository
import com.damir.stipancic.newsappv3.setBackStackData

class ArticleDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = FragmentArticleDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner

        val article = ArticleDetailsFragmentArgs.fromBundle(arguments!!).selectedArticle
        val repository = NewsRepository(ArticleDatabase.getInstance(requireContext()))

        if(article.saved)
            binding.saveFab.visibility = View.GONE

        val viewModelFactory = ArticleDetailsViewModelFactory(article, repository)
        val viewModel = ViewModelProvider(this, viewModelFactory)[ArticleDetailViewModel::class.java]

        binding.viewModel = viewModel

        viewModel.updateRecyclerOnBack.observe(viewLifecycleOwner){
            if(it) {
                val updateRecyclerOnBack = true
                setBackStackData("updateRecycler", updateRecyclerOnBack)
            }
        }

        return binding.root
    }


}

