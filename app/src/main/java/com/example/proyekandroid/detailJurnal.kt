package com.example.proyekandroid

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class detailJurnal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_jurnal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val intent = intent
        val judul = intent.getStringExtra("judul")
        val deskripsi = intent.getStringExtra("deskripsi")
        val tanggal = intent.getStringExtra("tanggal")
        val gambar = intent.getStringExtra("gambar")

        val etJudul: TextView = findViewById(R.id.tvJudulcard)
        val etDeskripsi: TextView = findViewById(R.id.tvDeskcard)
        val etTanggal: TextView = findViewById(R.id.tvTanggal)
        val ivGambar: ImageView = findViewById(R.id.ivPiccard)

        etJudul.setText(judul)
        etDeskripsi.setText(deskripsi)
        etTanggal.setText(tanggal)
        Picasso.get().load(gambar).fit().centerCrop().into(ivGambar)
    }
}