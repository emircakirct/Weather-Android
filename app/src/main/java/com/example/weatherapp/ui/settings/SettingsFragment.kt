package com.example.weatherapp.ui.settings

import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentSettingsBinding
import com.example.weatherapp.ui.MainViewModel
import java.util.*


class SettingsFragment : Fragment(), View.OnClickListener{
    private var binding: FragmentSettingsBinding? = null
    private val mainViewModel: MainViewModel by activityViewModels()
    private val sharedPreferences by lazy { requireActivity().getSharedPreferences(
        getString(R.string.preference_file_key), Context.MODE_PRIVATE) }

    override fun onCreateView( inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding!!.root

        binding!!.btnUpdate.setOnClickListener(this)

        //load saved settings
        loadSettings()

        return root
    }

    private fun loadSettings() {
        binding!!.edtCity.setText(sharedPreferences.getString("city", "New York"))
        binding!!.switchUnit.isChecked = sharedPreferences.getBoolean("fahrenheit", true)
        binding!!.spinnerDays.setSelection(sharedPreferences.getInt("days", 1))
    }

    private fun inValidCity(cityName: String): Boolean {
        val gcd = Geocoder(requireContext(), Locale.getDefault())
        return gcd.getFromLocationName(cityName, 1).isEmpty()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onClick(p0: View?) {
        if (binding!!.spinnerDays.selectedItemPosition == 0) {
            showMessage("Select days from 7 to 16")
            return
        } else if (inValidCity(binding!!.edtCity.text.toString())) {
            showMessage("Please enter a valid city name")
            return
        }

        mainViewModel.updateSettings(binding!!.edtCity.text.toString(),
            binding!!.spinnerDays.selectedItemPosition + 6,
            binding!!.switchUnit.isChecked)

        sharedPreferences.edit().apply() {
            putBoolean("fahrenheit", binding!!.switchUnit.isChecked)
            putString("city",binding!!.edtCity.text.toString())
            putInt("days", binding!!.spinnerDays.selectedItemPosition)
            showMessage("Settings are updated successfully")
        }.apply()
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}