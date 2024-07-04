package com.example.proyekandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyekandroid.FlightDetail
import com.example.proyekandroid.FlightsData
import com.example.proyekandroid.R
import com.example.proyekandroid.adapter.adapterRecViewFlights.OnItemClickCallback

class adapterRecViewFlightDetail (private val listFlights: List<FlightDetail>):
    RecyclerView.Adapter<adapterRecViewFlightDetail.ListViewHolder> () {

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var _tvRoute = itemView.findViewById<TextView>(R.id.tvRoute)
        var _flightDate = itemView.findViewById<TextView>(R.id.flightDate)
        var _price = itemView.findViewById<TextView>(R.id.flightPrice)
        var _cvBudget = itemView.findViewById<CardView>(R.id.cvBudget)
    }

    interface OnItemClickCallback{
        fun chooseFlight(position: Int, item: FlightsData)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.fd_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listFlights.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = listFlights[position]
        val result = item.skycode.joinToString(separator = " -> ")
        holder._tvRoute.setText(result)
        holder._flightDate.setText(item.departureDate)
        holder._price.setText(item.price)
        holder._cvBudget.setOnClickListener {
            Toast.makeText(it.context, item.direct.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}
