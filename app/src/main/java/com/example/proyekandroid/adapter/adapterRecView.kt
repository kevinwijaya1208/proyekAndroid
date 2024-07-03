package com.example.proyekandroid.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyekandroid.R
import com.example.proyekandroid.detailJurnal
import com.example.proyekandroid.jurnalTravel
import com.example.proyekandroid.tambahJurnal
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class adapterRecView(
    private var listJurnal: MutableList<jurnalTravel>,
    private val db: FirebaseFirestore
) : RecyclerView.Adapter<adapterRecView.ListViewHolder>() {

    private var loveStates: MutableList<Boolean> = MutableList(listJurnal.size) { false }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvJudul: TextView = itemView.findViewById(R.id.tvJudulcard)
        var _tvDeskripsi: TextView = itemView.findViewById(R.id.tvDeskcard)
        var _tvTanggal: TextView = itemView.findViewById(R.id.tvTanggal)
        var _ivPicture: ImageView = itemView.findViewById(R.id.ivPiccard)
        var loveIcon: ImageView = itemView.findViewById(R.id.ivLoveIcon)
        var editIcon: ImageView = itemView.findViewById(R.id.ivEdit)
        var deleteIcon: ImageView = itemView.findViewById(R.id.ivDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_viewcard, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listJurnal.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        try {
            val currentItem = listJurnal[position]
            holder._tvJudul.text = currentItem.judul
            holder._tvDeskripsi.text = currentItem.deskripsi
            holder._tvTanggal.text = currentItem.tanggal
            Picasso.get().load(currentItem.gambar).resize(600,0).into(holder._ivPicture)

            holder.loveIcon.isSelected = currentItem.favorite == "true"
            updateLoveIcon(holder.loveIcon, currentItem.favorite == "true")

            holder.loveIcon.setOnClickListener {
                loveStates[position] = !loveStates[position]
                holder.loveIcon.isSelected = loveStates[position]
                updateLoveIcon(holder.loveIcon, loveStates[position])
                if (loveStates[position]) {
                    favoriteData(currentItem, position)
                } else {
                    unfavoriteData(currentItem, position)
                }
            }

            holder.editIcon.setOnClickListener {
                val intent = Intent(it.context, tambahJurnal::class.java)
                intent.putExtra("judul", currentItem.judul)
                intent.putExtra("deskripsi", currentItem.deskripsi)
                intent.putExtra("tanggal", currentItem.tanggal)
                intent.putExtra("favorite", currentItem.favorite)
                intent.putExtra("gambar", currentItem.gambar)
                intent.putExtra("addEdit", 1)
                it.context.startActivity(intent)
            }

            holder.deleteIcon.setOnClickListener {
                deleteCard(currentItem, position)
            }

        } catch (e: Exception) {
            Log.e("adapterRecView", "Error in onBindViewHolder: ", e)
        }
    }

    private fun deleteCard(item: jurnalTravel, position: Int) {
        db.collection("listJurnal")
            .document(item.judul)
            .delete()
            .addOnSuccessListener {
                listJurnal.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, listJurnal.size)
                Log.d("Firebase", "berhasil di delete" + position)
            }
            .addOnFailureListener { e ->
                Log.d("Firebase", "error delete")
            }
    }

    private fun updateLoveIcon(loveIcon: ImageView, isLoved: Boolean) {
        if (isLoved) {
            loveIcon.setImageResource(R.drawable.heart_red) // Your filled heart drawable
        } else {
            loveIcon.setImageResource(R.drawable.heart_outlined) // Your outline heart drawable
        }
    }

    private fun favoriteData(item: jurnalTravel, position: Int){
        db.collection("listJurnal")
            .document(item.judul)
            .update(mapOf(
                "favorite" to "true"
            ))
            .addOnSuccessListener {
                Log.d("Firebase", "berhasil favorite")
            }
            .addOnFailureListener { e ->
                Log.d("Firebase", "Failed to update document: ${e.message}")
            }

    }

    private fun unfavoriteData(item: jurnalTravel, position: Int){
        db.collection("listJurnal")
            .document(item.judul)
            .update(mapOf(
                "favorite" to "false"
            ))
            .addOnSuccessListener {
                Log.d("Firebase", "berhasil favorite")
            }
            .addOnFailureListener { e ->
                Log.d("Firebase", "Failed to update document: ${e.message}")
            }

    }

    fun updateData(newListJurnal: MutableList<jurnalTravel>) {
        listJurnal = newListJurnal
        loveStates = MutableList(listJurnal.size) { newListJurnal[it].favorite == "true" }
        notifyDataSetChanged()
    }


}
