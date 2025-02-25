package com.example.proyekandroid

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.proyekandroid.API.ApiServices
import com.example.proyekandroid.API.ResponseFlightID
import com.example.proyekandroid.adapter.ImageAdapter
import retrofit2.Call
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import kotlin.math.abs

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TravelPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TravelPageFragment : Fragment(R.layout.fragment_travel_page) {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var _etDest : EditText
    private lateinit var _etDept : EditText
    private lateinit var _btnCalcBudget : Button
    private lateinit var viewPager2: ViewPager2
    private lateinit var handler : Handler
    private lateinit var imageList: ArrayList<Int>
    private lateinit var adapter : ImageAdapter
    private val delayMillis: Long = 5000

    private val runnable = Runnable {
        viewPager2.currentItem += 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }



//        getData("Jakarta")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager2 = view.findViewById(R.id.travelViewPager)
        _etDest = view.findViewById(R.id.etDest)
        _etDept = view.findViewById(R.id.etDept)
        _btnCalcBudget = view.findViewById(R.id.btnCalcBudget)
        handler = Handler(Looper.myLooper()!!)

        prepareImageList()

        adapter = ImageAdapter(imageList, viewPager2, _etDest, _etDept)
        viewPager2.adapter = adapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false

        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        setupTransformer()

        val colors = listOf(
            R.color.bali,
            R.color.kl,
            R.color.tokyo,
            R.color.paris,
            R.color.barca,
            R.color.venice,
            R.color.la,
            R.color.athens
        )
        var currentColor = ContextCompat.getColor(requireContext(), colors[viewPager2.currentItem % colors.size])


        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, delayMillis)
                // Get the new color
                val newColor = ContextCompat.getColor(requireContext(), colors[position % colors.size])

                // Animate the background color change
                val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), currentColor, newColor)
                colorAnimation.duration = 600 // Duration of the transition in milliseconds
                colorAnimation.addUpdateListener { animator ->
                    view.setBackgroundColor(animator.animatedValue as Int)

                }
                colorAnimation.start()

                // Update the current color
                currentColor = newColor

            }
        })

        _btnCalcBudget.setOnClickListener{
            if(check()){
                val chooseFlightFragment = ChooseFlight.newInstance(_etDept.text.toString(), _etDest.text.toString())
                (activity as? homePage)?.openFragment(chooseFlightFragment)
            }
        }
    }

    private fun check() : Boolean{
        if(_etDest.text.toString() == _etDept.text.toString()){
            Toast.makeText(requireContext(), "Departure dan Tujuan tidak boleh sama", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun prepareImageList(){
        imageList = ArrayList()


        imageList.add(R.drawable.bali)
        imageList.add(R.drawable.kualalumpur)
        imageList.add(R.drawable.tokyo)
        imageList.add(R.drawable.france)
        imageList.add(R.drawable.barcelona)
        imageList.add(R.drawable.venice)
        imageList.add(R.drawable.losangeles)
        imageList.add(R.drawable.athens)
    }



    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()

        handler.postDelayed(runnable, delayMillis)
    }



    private fun setupTransformer(){
        val transformer = CompositePageTransformer()
        // margin item tengah
        transformer.addTransformer(MarginPageTransformer(30))
        transformer.addTransformer { page, position ->
//            val scale = 0.9f + (1 - abs(position)) * 0.1f // Adjust the scaling factor to almost fit the screen width
//            page.scaleY = scale
//            page.scaleX = scale
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
            page.alpha = 0.5f + r * 0.5f
        }

        viewPager2.setPageTransformer(transformer)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_travel_page, container, false)
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TravelPageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TravelPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}