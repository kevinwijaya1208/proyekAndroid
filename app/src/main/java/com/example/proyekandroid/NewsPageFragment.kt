package com.example.proyekandroid

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyekandroid.API.ApiServices
import com.example.proyekandroid.API.NewsApiServices
import com.example.proyekandroid.API.ResponseFlightID
import com.example.proyekandroid.API.ResponseNews
import com.example.proyekandroid.adapter.adapterRecViewNews
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
 * Use the [NewsPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsPageFragment : Fragment() {
    private lateinit var _lvData: RecyclerView
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _lvData = view.findViewById(R.id.rvNews)
        _lvData.layoutManager = LinearLayoutManager(context)
        getData("travel")
    }

    object ApiClient{
        private const val BASE_URL = "https://newsapi90.p.rapidapi.com/"

        fun create(): NewsApiServices {
            val retrofit : Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
            return retrofit.create(NewsApiServices::class.java)
        }
    }

    fun getData(query: String, language: String = "en-US", region: String = "US") {
        val call = ApiClient.create().getNews(query, language, region)
        call.enqueue(object : Callback<List<ResponseNews>> {
            override fun onResponse(
                call: Call<List<ResponseNews>>,
                response: Response<List<ResponseNews>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body() ?: emptyList()
                    var counter = 0
                    for (news in data) {
                        val newsList = data.take(10).map { item ->
                            newsTravel(
                                title = item.title ?: "No Description",
                                preview = item.preview ?: "No Description",
                                image = item.image ?: "No Description",
                                link = item.link ?: "No Description"
                            )
                        }
                        val adapterNews =adapterRecViewNews(newsList)
                        _lvData.adapter = adapterNews
//                        Log.d("SUCCESS RESPONSE", "Title: ${news.title}, Publisher: ${news.publisher}")
//                        Log.d("SUCCESS RESPONSE", "${news}")
                        // Example: Accessing related articles
//                        news.relatedArticles?.let { articles ->
//                            for (article in articles) {
//                                Log.d("RELATED ARTICLE", "Title: ${article.title}, Publisher: ${article.publisher}")
//                            }
//                        }
                    }
                } else {
                    Log.d("ERROR RESPONSE", "Response not successful")
                }
            }

            override fun onFailure(call: Call<List<ResponseNews>>, t: Throwable) {
                Log.d("ERROR RESPONSE", t.message ?: "Unknown error")
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_page, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewsPageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewsPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}