package sk.stuba.fei.i_mobv_projekt.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import sk.stuba.fei.i_mobv_projekt.databinding.FragmentPubBinding

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentPub.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentPub : Fragment() {
    private lateinit var binding: FragmentPubBinding
    private val args : FragmentPubArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPubBinding.inflate(inflater, container, false)
        binding.buttonMap.setOnClickListener { showMap() }
        setText()
        return binding.root
    }

    private fun setText()
    {
        binding.apply {
            pubNameText.text = args.pubName
            drinkNameText.text = args.drinkName
        }
    }

    private fun showMap()
    {
        val intent : Intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:${args.latitude},${args.longitude}"))
        startActivity(intent)
    }
}