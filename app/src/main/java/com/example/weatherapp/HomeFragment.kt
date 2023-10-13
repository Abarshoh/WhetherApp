package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.weatherapp.databinding.FragmentHomeBinding
import okhttp3.internal.concurrent.Task
import org.json.JSONArray

class HomeFragment : Fragment() {
    private val apiUrl = "https://api.weatherapi.com/v1/forecast.json?key=11b9394e7e024a2588a44954230610&q=Tashkent&days=8&aqi=no&alerts=no"
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val requestQue = Volley.newRequestQueue(requireContext())
        var forecastAdapter = ForecastAdapter(JSONArray())
        binding.forecastRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.todayRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val request = JsonObjectRequest(apiUrl,
            { response ->
                val current = response.getJSONObject("current")
                val tempC = current.getDouble("temp_c")
                val windKph = current.getDouble("wind_kph")
                val humidity = current.getInt("humidity")


                binding.temp.text = "${tempC}°"

                forecastAdapter = ForecastAdapter(response.getJSONObject("forecast").getJSONArray("forecastday"))
                binding.forecastRv.adapter = forecastAdapter
                binding.todayRv.adapter = TodayAdapter(response.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(0).getJSONArray("hour"))

                binding.icon.load("https:" + current.getJSONObject("condition").getString("icon"))
                forecastAdapter.notifyDataSetChanged()
                Log.d("TAG", "$response")
            }
        ) { error -> Log.d("TAG", "onErrorResponse: $error") }
        requestQue.add(request)




        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}