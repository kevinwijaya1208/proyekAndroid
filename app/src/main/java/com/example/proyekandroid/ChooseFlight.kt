package com.example.proyekandroid

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyekandroid.API.ApiServices
import com.example.proyekandroid.API.ResponseFlightID
import com.example.proyekandroid.adapter.adapterRecViewFlights
import com.example.proyekandroid.databinding.ActivityMainBinding

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChooseFlight.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChooseFlight : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var _tvJudulFlight : TextView
    private lateinit var _listFlightRv : RecyclerView
    private lateinit var viewFlightAdapter : adapterRecViewFlights
    var myresponse : ResponseFlightID? = null
    lateinit var flightData : ArrayList<FlightsData>

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _tvJudulFlight = view.findViewById(R.id.tvJudulFlight)
        _tvJudulFlight.setText("Select Flight from: " + param1)
        _listFlightRv = view.findViewById(R.id.listFlightRv)

        flightData = ArrayList()


        viewFlightAdapter = adapterRecViewFlights(flightData)

        _listFlightRv.layoutManager = LinearLayoutManager(context)
        _listFlightRv.adapter = viewFlightAdapter
        getData(param1!!)


        viewFlightAdapter.setOnItemClickCallback(object : adapterRecViewFlights.OnItemClickCallback{
            override fun choose(position: Int, item: FlightsData) {

                val chooseDestinationFragment = ChooseDestination.newInstance(param2.toString(), item)
                (activity as? homePage)?.openFragment(chooseDestinationFragment)
            }

        })

//        Log.d("param1", param1.toString())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_flight, container, false)
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

    fun getData(query: String, placeTypes:String = "CITY, AIRPORT",
                outboundDate: String = getCurrentDate(), market: String = "US",
                locale: String = "en-US")
    {
        var call = ApiClient.create().getLocationId(query, placeTypes, outboundDate, market, locale)
        call.enqueue(object : Callback<ResponseFlightID> {
            override fun onResponse(
                call: Call<ResponseFlightID>,
                response: Response<ResponseFlightID>
            )
            {
                if(response.isSuccessful){
                    val data = response.body()!!
                    myresponse = data

                    for (item in data.data!!){

                        val navigation = item!!.navigation

                        //  Navigation
                        val localizedName = navigation!!.localizedName
                        val entityType = navigation.entityType
                        val flightParams = navigation.relevantFlightParams

                        // Flight Params
                        val skyID = flightParams!!.skyId

                        flightData.add(FlightsData(localizedName.toString(), entityType.toString(), skyID.toString()))
                    }
                    viewFlightAdapter.notifyDataSetChanged()
                    Log.d("SUCCESS RESPONSE", flightData.toString())
                }else{
                    Log.d("ERROR RESPONSE", "error")
                }
            }

            override fun onFailure(call: Call<ResponseFlightID>, t: Throwable) {
                Log.d("ERROR RESPONSE", t.message.toString())

                Toast.makeText(requireContext(), "problem di response getdata", Toast.LENGTH_SHORT).show()
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
         * @return A new instance of fragment ChooseFlight.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChooseFlight().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}