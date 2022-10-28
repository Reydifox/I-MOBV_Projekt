package sk.stuba.fei.i_mobv_projekt.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import sk.stuba.fei.i_mobv_projekt.R
import sk.stuba.fei.i_mobv_projekt.databinding.FragmentSettingsBinding

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        binding.buttonSubmit.setOnClickListener { submitSettings() }
        return binding.root
    }

    private fun submitSettings() {
        val drinkNameText = binding.drinkNameText.text.toString()
        val pubNameText = binding.pubNameText.text.toString()
        val latitudeText = binding.latitudeText.text.toString()
        val longitudeText = binding.longitudeText.text.toString()

        val fragmentDirections = SettingsFragmentDirections.actionSettingsFragmentToFragmentPub(drinkNameText, pubNameText, latitudeText, longitudeText)
        binding.root.findNavController().navigate(fragmentDirections)
    }
}