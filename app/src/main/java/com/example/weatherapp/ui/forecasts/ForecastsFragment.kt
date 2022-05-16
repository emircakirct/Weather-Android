package com.example.weatherapp.ui.forecasts

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.weatherapp.databinding.FragmentForecastsBinding
import com.example.weatherapp.ui.MainViewModel

class ForecastsFragment : Fragment() {
    private var binding: FragmentForecastsBinding? = null
    private val mainViewModel: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentForecastsBinding.inflate(inflater, container, false)
        mainViewModel.forecastsResponse.observe(viewLifecycleOwner) {
            binding!!.recycleView.adapter = ForecastsAdapter(it, mainViewModel.tempUnit)
        }

        return binding!!.root
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.getWeatherForecast()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}