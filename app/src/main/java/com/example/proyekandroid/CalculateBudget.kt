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
import com.example.proyekandroid.API.BudgetResponse
import com.example.proyekandroid.API.ResponseFlightID
import com.example.proyekandroid.ChooseFlight.ApiClient
import com.example.proyekandroid.adapter.adapterRecViewFlightDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CalculateBudget.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalculateBudget : Fragment() {
    private var param1: FlightsData? = null
    private var param2: FlightsData? = null

    private lateinit var _rvListChoice : RecyclerView
    private lateinit var _tvBudgetDesc : TextView
    private lateinit var flightArr: ArrayList<FlightDetail>
    private lateinit var flightDetailAdapter : adapterRecViewFlightDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelable(ARG_PARAM1)
            param2 = it.getParcelable(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _tvBudgetDesc = view.findViewById(R.id.tvBudgetDesc)
        _rvListChoice = view.findViewById(R.id.rvListChoice)

        _tvBudgetDesc.text = ("from " + param1!!.src + "(" + param1!!.skyID + ") to " + param2!!.src
                + "(" + param2!!.skyID + ")")

        Log.d("param", param1!!.toString())

        flightArr = ArrayList()

        flightDetailAdapter = adapterRecViewFlightDetail(flightArr)
        _rvListChoice.layoutManager = LinearLayoutManager(context)
        _rvListChoice.adapter = flightDetailAdapter

        getResult(param1!!.skyID, param2!!.skyID)




    }

    object ApiClient {
        private const val BASE_URL = "https://sky-scanner3.p.rapidapi.com/"

        fun create(): ApiServices {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiServices::class.java)
        }
    }

    fun getResult(fromEntityID: String, toEntityID:String)
    {
        var call = ApiClient.create().getFlightDetail("NYCA", "KULM", "oneway")
        call.enqueue(object : Callback<BudgetResponse> {
            override fun onResponse(
                call: Call<BudgetResponse>,
                response: Response<BudgetResponse>
            ) {
                if(response.isSuccessful){
                    var result = response.body()!!
                    Log.d("SUCCESS RESPONSE2 ", result.toString())
                }else{
                    Log.d("ERROR RESPONSE", "Response code: ${response.code()}, message: ${response.message()}")
                    Log.d("ERROR RESPONSE BODY", response.errorBody()?.string() ?: "No error body")
                }
            }

            override fun onFailure(call: Call<BudgetResponse>, t: Throwable) {
                Log.d("ERROR RESPONSE 2", t.message.toString())
                Log.d("testingerr", "masuk")
                Toast.makeText(requireContext(), "problem di response getdata budget", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculate_budget, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CalculateBudget.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: FlightsData, param2: FlightsData) =
            CalculateBudget().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, param1)
                    putParcelable(ARG_PARAM2, param2)
                }
            }
    }
}