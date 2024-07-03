package com.example.proyekandroid

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyekandroid.adapter.adapterRecView
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [homePageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class homePageFragment : Fragment(R.layout.fragment_home_page) {
    private lateinit var adapter: adapterRecView
    private val DataJurnal = mutableListOf<jurnalTravel>()

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        view.enableEdgeToEdge()
//        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvJurnal)
        val db = FirebaseFirestore.getInstance()
        adapter = adapterRecView(DataJurnal, db)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        readData(db)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_page, container, false)
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