package com.example.submission1.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission1.core.R
import com.example.submission1.core.databinding.ItemRowFilmBinding
import com.example.submission1.core.domain.model.Film

class FilmAdapter : RecyclerView.Adapter<FilmAdapter.ViewHolder>() {

    private var listData = ArrayList<Film>()
    var onItemClick: ((Film) -> Unit)? = null

    fun setData(newListData: List<Film>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowFilmBinding.bind(itemView)
        fun bind(data: Film) {
            with(binding) {
                binding.tvItemName.text = data.judul
                Glide.with(itemView.context)
                    .load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/${data.image}")
                    .apply(RequestOptions().override(140, 100))
                    .into(binding.imgItemPhoto)

            }
        }

        init {
            binding.root.setOnClickListener { onItemClick?.invoke(listData[bindingAdapterPosition]) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_row_film, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}