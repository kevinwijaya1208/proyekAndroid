package com.example.proyekandroid

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.proyekandroid.API.ApiServices
import com.example.proyekandroid.API.ResponseFlightID
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        getData("Jakarta")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_travel_page, container, false)
    }

    object ApiClient{
        private const val BASE_URL = "https://sky-scanner3.p.rapidapi.com/flights/"

        fun create(): ApiServices {
            val retrofit : Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
            return retrofit.create(ApiServices::class.java)
        }
    }


    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()
        return dateFormat.format(date)
    }

    fun getData(query: String, placeTypes:String = "COUNTRY",
                outboundDate: String = getCurrentDate(), market: String = "US",
                locale: String = "en-US")
    {
        var call = ApiClient.create().getLocationId(query, placeTypes, outboundDate, market, locale)
        call.enqueue(object : Callback<ResponseFlightID>{
            override fun onResponse(
                call: Call<ResponseFlightID>,
                response: Response<ResponseFlightID>
            )
            {
                if(response.isSuccessful){
                    val data = response.body()!!
                    Log.d("SUCCESS RESPONSE", data.toString())
                }
            }

            override fun onFailure(call: Call<ResponseFlightID>, t: Throwable) {
                Log.d("ERROR RESPONSE", t.message.toString())
            }
        })
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