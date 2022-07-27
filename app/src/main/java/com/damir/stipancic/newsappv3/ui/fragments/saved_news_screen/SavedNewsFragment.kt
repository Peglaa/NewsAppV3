package com.damir.stipancic.newsappv3.ui.fragments.saved_news_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.damir.stipancic.newsappv3.data.database.ArticleDatabase
import com.damir.stipancic.newsappv3.databinding.FragmentSavedNewsBinding
import com.damir.stipancic.newsappv3.repository.NewsRepository
import com.damir.stipancic.newsappv3.ui.NewsRecyclerAdapter
import com.google.android.material.snackbar.Snackbar


class SavedNewsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false) //remove up arrow cause we are in bottom nav fragment
        val binding = FragmentSavedNewsBinding.inflate(inflater)
        val repository = NewsRepository(ArticleDatabase.getInstance(requireContext()))
        val viewModelFactory = SavedNewsViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory)[SavedNewsViewModel::class.java]

        binding.lifecycleOwner = this

        val adapter = NewsRecyclerAdapter(NewsRecyclerAdapter.OnClickListener {
            viewModel.displayArticleDetails(it)
        })
        binding.savedNewsRecycler.adapter = adapter

        binding.savedNewsRecycler.addItemDecoration(DividerItemDecoration(binding.savedNewsRecycler.context, DividerItemDecoration.VERTICAL))
        //binding.savedNewsRecycler.setHasFixedSize(true)

        viewModel.getSavedArticles().observe(viewLifecycleOwner){ savedArticles ->
            adapter.submitList(savedArticles)
        }

        viewModel.navigateToClickedArticle.observe(viewLifecycleOwner){
            it?.let {
                this.findNavController().navigate(SavedNewsFragmentDirections.actionSavedNewsFragmentToArticleDetailsFragment(it))
                viewModel.displayArticleDetailsComplete()
            }
        }

        val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                val article = adapter.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(requireView(), "Successfully deleted article!", Snackbar.LENGTH_LONG).apply {
                    setAction("UNDO"){
                        viewModel.insertArticle(article)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.savedNewsRecycler)
        }

        return binding.root
    }
}