package com.example.proyekandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyekandroid.adapter.adapterRecView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore

class homePage : AppCompatActivity() {
    private lateinit var adapter: adapterRecView
    private val DataJurnal= mutableListOf<jurnalTravel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val fbAdd = findViewById<FloatingActionButton>(R.id.fbAdd)
        fbAdd.setOnClickListener {
            startActivity(Intent(this, tambahJurnal::class.java))
        }

        val recyclerView = findViewById<RecyclerView>(R.id.rvJurnal)
        val db = FirebaseFirestore.getInstance()
        adapter = adapterRecView(DataJurnal, db)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


        readData(db)
    }
    override fun onResume() {
        super.onResume()
        val db = FirebaseFirestore.getInstance()
        readData(db)
    }
    fun readData(db: FirebaseFirestore){
        db.collection("listJurnal").get()
            .addOnSuccessListener {
                    result->
                DataJurnal.clear()
                for (document in result){
                    val readData = jurnalTravel(
                        document.data.get("judul").toString(),
                        document.data.get("deskripsi").toString(),
                        document.data.get("tanggal").toString(),
                        document.data.get("gambar").toString(),
                        document.data.get("favorite").toString()
                    )
                    DataJurnal.add(readData)
                }
                adapter.notifyDataSetChanged()
                adapter.updateData(DataJurnal)
            }
            .addOnFailureListener {
                Log.d("Firebase", it.message.toString())
            }
    }
}