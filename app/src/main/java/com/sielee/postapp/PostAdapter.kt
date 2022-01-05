package com.sielee.postapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sielee.postapp.databinding.PostItemBinding

class PostAdapter:ListAdapter<Post,PostAdapter.PostViewHolder>(DiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
       val binding = PostItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
       holder.bind(getItem(position))
    }

    class PostViewHolder(private val binding: PostItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post?) {
            binding.tvUserId.text = post?.userId.toString()
            binding.tvtittle.text = post?.title
            binding.tvbody.text = post?.body
        }

    }

    class DiffItemCallback:DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
           return oldItem == newItem
        }
    }
}