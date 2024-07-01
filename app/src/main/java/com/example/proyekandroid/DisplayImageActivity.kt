package com.example.proyekandroid

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class DisplayImageActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var textViewTitle: TextView
    private lateinit var textViewDescription: TextView
    private lateinit var textViewDate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_image)

        imageView = findViewById(R.id.imageView2)
//        textViewTitle = findViewById(R.id.textViewTitle)
//        textViewDescription = findViewById(R.id.textViewDescription)
//        textViewDate = findViewById(R.id.textViewDate)

        // Get document ID from intent
//        val documentId = intent.getStringExtra("document_id")

        val documentId = "1"
        if (documentId != null) {
            loadImageFromFirestore(documentId)
        }
    }

    private fun loadImageFromFirestore(documentId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("listJurnal").document(documentId).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val imageUrl = document.getString("gambar")
//                    val title = document.getString("judul")
//                    val description = document.getString("deskripsi")
//                    val date = document.getString("tanggal")

//                    textViewTitle.text = title
//                    textViewDescription.text = description
//                    textViewDate.text = date

                    if (imageUrl != null) {
                        Picasso.get().load(imageUrl).into(imageView)
                    }
                } else {
                    Log.d("Firestore", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Firestore", "get failed with ", exception)
            }
    }
}
