package com.example.proyekandroid

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Viewcard : AppCompatActivity() {
    private lateinit var loveIcon: ImageView
    private var isLoved = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_viewcard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loveIcon = findViewById(R.id.ivLoveIcon)
        loveIcon.setOnClickListener {
            toggleLoveIcon()
        }
    }
    private fun toggleLoveIcon() {
        isLoved = !isLoved
        loveIcon.isSelected = isLoved
    }
}