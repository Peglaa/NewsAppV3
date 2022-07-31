package com.damir.stipancic.newsappv3.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.damir.stipancic.newsappv3.R
import com.damir.stipancic.newsappv3.data.models.Article
import com.damir.stipancic.newsappv3.databinding.ArticleDetailsPagerItemBinding
import com.damir.stipancic.newsappv3.ui.fragments.article_detail_screen.ArticleDetailViewModel
import com.google.android.material.snackbar.Snackbar

class ArticleDetailsRecyclerAdapter (private val fragment: Fragment, val viewModel: ArticleDetailViewModel) :
    ListAdapter<Article, ArticleDetailsRecyclerAdapter.ArticleViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(ArticleDetailsPagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), fragment, viewModel)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    class ArticleViewHolder(private var binding: ArticleDetailsPagerItemBinding, val fragment: Fragment, val viewModel: ArticleDetailViewModel):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.article = article

            if (article.saved)
                binding.saveFab.visibility = View.GONE

            else {
                binding.saveFab.setOnClickListener {
                    val previousFragment =
                        fragment.findNavController().previousBackStackEntry?.destination?.id
                    if (previousFragment == R.id.searchNewsFragment) {
                        article.saved = true
                        article.createdAt = 0L
                        viewModel.insertArticle(article)
                    } else
                        viewModel.onSaveClicked(article)
                    viewModel.onSaveClicked(article)
                    Snackbar.make(
                        fragment.requireView(),
                        "Article saved successfully!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    it.visibility = View.GONE
                }
                binding.executePendingBindings()
            }
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }
    }
}