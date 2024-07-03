package com.example.proyekandroid.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyekandroid.R
import com.example.proyekandroid.newsTravel
import com.squareup.picasso.Picasso

class adapterRecViewNews(private val listNews: List<newsTravel>):
    RecyclerView.Adapter<adapterRecViewNews.ListViewHolder> () {

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var image = itemView.findViewById<ImageView>(R.id.ivNews)
        var title = itemView.findViewById<TextView>(R.id.tvTitle)
        var preview = itemView.findViewById<TextView>(R.id.ivPreview)
        var readMore = itemView.findViewById<TextView>(R.id.tvReadMore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listNews.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val currentItem = listNews[position]
        holder.title.setText(currentItem.title)
        holder.preview.setText(currentItem.preview)
        Picasso.get().load(currentItem.image).resize(600, 0) // Resize to a width of 600px, maintaining aspect ratio
            .onlyScaleDown().into(holder.image)

        holder.readMore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(currentItem.link)
            }
            holder.itemView.context.startActivity(intent)
        }
    }
}