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
import com.example.proyekandroid.API.ResponseFlightID
import com.example.proyekandroid.ChooseFlight.ApiClient
import com.example.proyekandroid.adapter.adapterRecViewFlights
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChooseDestination.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChooseDestination : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: FlightsData? = null

    private lateinit var _tvDeptSkyID : TextView
    private lateinit var _rvFlightDest : RecyclerView
    private lateinit var flightDest : ArrayList<FlightsData>
    private lateinit var destFlightAdapter : adapterRecViewFlights


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getParcelable(ARG_PARAM2)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _tvDeptSkyID = view.findViewById(R.id.tvDeptSkyID)
        _rvFlightDest = view.findViewById(R.id.rvFlightDest)

        _tvDeptSkyID.setText("Departure from: " + param2!!.skyID.toString())
        flightDest = ArrayList()

        destFlightAdapter = adapterRecViewFlights(flightDest)

        _rvFlightDest.layoutManager = LinearLayoutManager(context)
        _rvFlightDest.adapter = destFlightAdapter
        getData(param1!!)


        destFlightAdapter.setOnItemClickCallback(object : adapterRecViewFlights.OnItemClickCallback{
            override fun choose(position: Int, item: FlightsData) {
                val chooseDestinationFragment = CalculateBudget.newInstance(param2!!, item)
                (activity as? homePage)?.openFragment(chooseDestinationFragment)

            }

        })
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

                    for (item in data.data!!){

                        val navigation = item!!.navigation

                        //  Navigation
                        val localizedName = navigation!!.localizedName
                        val entityType = navigation.entityType
                        val flightParams = navigation.relevantFlightParams

                        // Flight Params
                        val skyID = flightParams!!.skyId

                        flightDest.add(FlightsData(localizedName.toString(), entityType.toString(), skyID.toString()))
                    }
                    destFlightAdapter.notifyDataSetChanged()
                    Log.d("SUCCESS RESPONSE", flightDest.toString())
                }
            }

            override fun onFailure(call: Call<ResponseFlightID>, t: Throwable) {
                Log.d("ERROR RESPONSE", t.message.toString())

                Toast.makeText(requireContext(), "problem di response getdata destination", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_destination, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChooseDestination.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: FlightsData) =
            ChooseDestination().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putParcelable(ARG_PARAM2, param2)

                }
            }
    }
}