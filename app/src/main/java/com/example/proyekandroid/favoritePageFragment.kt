package com.example.proyekandroid

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyekandroid.adapter.adapterRecView
import com.example.proyekandroid.adapter.adapterRecViewFavorite
import com.google.firebase.firestore.FirebaseFirestore

class favoritePageFragment : Fragment(R.layout.fragment_favorite_page) {

    private lateinit var adapter: adapterRecViewFavorite
    private val DataJurnal = mutableListOf<jurnalTravel>()

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvJurnal)
        val db = FirebaseFirestore.getInstance()
        adapter = adapterRecViewFavorite(DataJurnal, db)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        readData(db)
    }

    override fun onResume() {
        super.onResume()
        val db = FirebaseFirestore.getInstance()
        readData(db)
    }

    private fun readData(db: FirebaseFirestore) {
        db.collection("listJurnal").get()
            .addOnSuccessListener { result ->
                DataJurnal.clear()
                for (document in result) {
                    val readData = jurnalTravel(
                        document.data["judul"].toString(),
                        document.data["deskripsi"].toString(),
                        document.data["tanggal"].toString(),
                        document.data["gambar"].toString(),
                        document.data["favorite"].toString()
                    )
                    if (readData.favorite == "true") {
                        DataJurnal.add(readData)
                    }
                }
                adapter.notifyDataSetChanged()
                adapter.updateData(DataJurnal)
            }
            .addOnFailureListener {
                Log.d("Firebase", it.message.toString())
            }
    }
}
