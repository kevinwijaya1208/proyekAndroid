package com.example.proyekandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyekandroid.FlightsData
import com.example.proyekandroid.R

class adapterRecViewFlights(private val listDeparture: List<FlightsData>):
    RecyclerView.Adapter<adapterRecViewFlights.ListViewHolder> () {

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var _tvSrc = itemView.findViewById<TextView>(R.id.tvSrc)
        var _tvSkyID = itemView.findViewById<TextView>(R.id.tvSkyID)
        var _tvDesc = itemView.findViewById<TextView>(R.id.tvDesc)
        var _flightcv = itemView.findViewById<CardView>(R.id.flightcv)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback{
        fun choose(position: Int, item: FlightsData)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterRecViewFlights.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.flight_item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapterRecViewFlights.ListViewHolder, position: Int) {
        val currentItem = listDeparture[position]
        holder._tvSrc.setText(currentItem.src)
        holder._tvSkyID.setText(currentItem.skyID)
        holder._tvDesc.setText(currentItem.desc)
        holder._flightcv.setOnClickListener {
            onItemClickCallback.choose(position, currentItem)
        }
    }



    override fun getItemCount(): Int {
        return listDeparture.size
    }


}