package com.damir.stipancic.newsappv3.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.damir.stipancic.newsappv3.databinding.NewsListItemBinding
import com.damir.stipancic.newsappv3.data.models.Article

class NewsRecyclerAdapter (private val onClickListener: OnClickListener) :
    ListAdapter<Article, NewsRecyclerAdapter.NewsViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(NewsListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(currentList, position)
        }
        if(article.saved)
            holder.setIsRecyclable(false)

        holder.bind(article)
    }

    class NewsViewHolder(private var binding: NewsListItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.article = article
            binding.executePendingBindings()
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

    class OnClickListener(val clickListener: (articleList: List<Article>, position: Int) -> Unit) {
        fun onClick(articleList: List<Article>, position: Int) = clickListener(articleList, position)
    }
}