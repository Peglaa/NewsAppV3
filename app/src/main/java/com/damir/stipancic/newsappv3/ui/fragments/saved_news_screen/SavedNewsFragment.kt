package com.damir.stipancic.newsappv3.ui.fragments.saved_news_screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.damir.stipancic.newsappv3.data.database.ArticleDatabase
import com.damir.stipancic.newsappv3.data.models.Article
import com.damir.stipancic.newsappv3.databinding.FragmentSavedNewsBinding
import com.damir.stipancic.newsappv3.repository.NewsRepository
import com.damir.stipancic.newsappv3.ui.NewsRecyclerAdapter
import com.google.android.material.snackbar.Snackbar

class SavedNewsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false) //remove up arrow cause we are in bottom nav fragment
        val binding = FragmentSavedNewsBinding.inflate(inflater)
        val repository = NewsRepository(ArticleDatabase.getInstance(requireActivity()))
        val viewModelFactory = SavedNewsViewModelFactory(repository)
        val savedNewsViewModel: SavedNewsViewModel by viewModels(factoryProducer = { viewModelFactory })
        val adapter = NewsRecyclerAdapter(NewsRecyclerAdapter.OnClickListener { article, position ->
            val arguments = mutableListOf<Pair<List<Article>, Int>>()
            arguments.add(Pair(article, position))
            savedNewsViewModel.displayArticleDetails(arguments)
        })

        binding.apply {
            lifecycleOwner = this@SavedNewsFragment.viewLifecycleOwner
            savedNewsRecycler.adapter = adapter
        }

        savedNewsViewModel.apply {
            //------------------------------
            getSavedArticles().observe(viewLifecycleOwner){ savedArticles ->
                adapter.submitList(savedArticles) {
                    adapter.notifyDataSetChanged()

                }

                if(adapter.currentList.isEmpty())
                    binding.emptyListImage.visibility = View.VISIBLE
                else
                    binding.emptyListImage.visibility = View.INVISIBLE
            }

            //------------------------------
            navigateToClickedArticle.observe(viewLifecycleOwner){
                it?.let {
                    this@SavedNewsFragment.findNavController()
                        .navigate(SavedNewsFragmentDirections.actionSavedNewsFragmentToArticleDetailsFragment(
                            it[0].first.toTypedArray(),
                            it[0].second))
                    savedNewsViewModel.displayArticleDetailsComplete()

                }
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
                Log.d("savedNewsFragment", "onSwiped_BEFORE: ${adapter.currentList.size}")
                savedNewsViewModel.unSaveArticle(article)
                Log.d("savedNewsFragment", "onSwiped_AFTER: ${adapter.currentList.size}")
                Snackbar.make(requireView(), "Successfully deleted article!", Snackbar.LENGTH_LONG).apply {
                    setAction("UNDO"){
                        savedNewsViewModel.saveArticle(article)
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