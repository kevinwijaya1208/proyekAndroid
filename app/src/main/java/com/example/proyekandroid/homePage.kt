package com.example.proyekandroid

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class homePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainHome)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
    bottomNavigationView.setOnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                openFragment(homePageFragment())
                true
            }
            R.id.navigation_favorite -> {
                openFragment(favoritePageFragment())
                true
            }
            R.id.navigation_add -> {
                startActivity(Intent(this, tambahJurnal::class.java))
                true
            }
            R.id.navigation_travel -> {
                // Add your travel fragment here
                openFragment(TravelPageFragment())
                true
            }
            R.id.navigation_news-> {
                // Add your profile fragment here
                openFragment(NewsPageFragment())
                true
            }
            else -> false
        }
    }

    // Open the home fragment by default
    if (savedInstanceState == null) {
        bottomNavigationView.selectedItemId = R.id.navigation_home
    }
}

    public fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}