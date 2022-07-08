package com.bronski.news22byte.news.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bronski.news22byte.R
import com.bronski.news22byte.core.api.ArticleEntity
import com.bronski.news22byte.databinding.ItemNewsBinding
import com.bumptech.glide.Glide

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var onItemClickListener: ((ArticleEntity) -> Unit)? = null

    private val diffUtilCallback = object : DiffUtil.ItemCallback<ArticleEntity>() {
        override fun areItemsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity): Boolean {
            return oldItem == newItem
        }
    }

    val newsListDiffer = AsyncListDiffer(this, diffUtilCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsBinding.inflate(inflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = newsListDiffer.currentList[position]
        with(holder.binding) {
            titleTextView.text = article.title
            descriptionTextView.text = article.description
            sourceTextView.text = article.source.name

            Glide.with(photoImageView.context)
                .load(article.urlToImage)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_terrain_24)
                .error(R.drawable.ic_baseline_terrain_24)
                .into(photoImageView)
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(article)
            }
        }
    }

    override fun getItemCount(): Int = newsListDiffer.currentList.size

    fun setOnItemClickListener(Listener: (ArticleEntity) -> Unit) {
        onItemClickListener = Listener
    }

    class NewsViewHolder(
        val binding: ItemNewsBinding,
    ) : RecyclerView.ViewHolder(binding.root)
}