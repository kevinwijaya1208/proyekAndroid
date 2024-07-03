package com.example.proyekandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.proyekandroid.R

class ImageAdapter(private val imagelist: ArrayList<Int>, private val viewPager2: ViewPager2)
    : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val imageView : ImageView = itemView.findViewById(R.id.carrimageView2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_container, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imagelist.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.imageView.setImageResource(imagelist[position])
        if (position == imagelist.size - 1){
            viewPager2.post(runnable)
        }
    }

    private val runnable = Runnable{
        imagelist.addAll(imagelist)
        notifyDataSetChanged()
    }
}