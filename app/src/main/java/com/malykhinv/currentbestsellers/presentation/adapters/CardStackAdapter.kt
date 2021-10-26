package com.malykhinv.currentbestsellers.presentation.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.malykhinv.currentbestsellers.databinding.ItemCardBinding
import com.malykhinv.currentbestsellers.domain.model.books.BooksApiResponse

class CardStackAdapter : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    private var responseBooks: BooksApiResponse? = null
    private var covers: Map<Int, Drawable?> = emptyMap()

    fun setListOfBooks(responseBooks: BooksApiResponse) {
        this.responseBooks = responseBooks
        notifyDataSetChanged()
    }

    fun setCovers(covers: Map<Int, Drawable?>) {
        this.covers = covers
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.binding.ivBookCover.setImageDrawable(covers[position])

            if (holder.binding.ivBookCover.drawable != null) holder.binding.ivBookCoverShimmer.hideShimmer()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return responseBooks?.numResults ?: 15
    }

    inner class ViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root)
}