package com.malykhinv.readdd.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.malykhinv.readdd.databinding.ItemCardBinding
import com.malykhinv.readdd.model.data.ApiResponse

class CardQueueAdapter : RecyclerView.Adapter<CardQueueAdapter.ViewHolder>() {

    var response: ApiResponse? = null

    fun setListOfBooks(response: ApiResponse) {
        this.response = response
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            var book = response!!.results.books[position]
            holder.binding.tvBookTitle.text = book.title
            holder.binding.tvBookAuthor.text = book.author
            Glide
                .with(holder.itemView)
                .load(book.bookImage)
                .into(holder.binding.ivBookCover)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return response?.numResults ?: 0
    }

    inner class ViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root)
}