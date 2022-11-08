package sk.stuba.fei.i_mobv_projekt.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import sk.stuba.fei.i_mobv_projekt.databinding.FragmentItemListBinding
import sk.stuba.fei.i_mobv_projekt.databinding.FragmentPubInfoBinding

/**
 * A simple [Fragment] subclass.
 * Use the [PubInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PubInfoFragment : Fragment() {
    private lateinit var binding: FragmentPubInfoBinding
    private val args : PubInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPubInfoBinding.inflate(inflater, container, false)
        binding.buttonMap.setOnClickListener { showMap() }
        binding.buttonDelete.setOnClickListener {
            val viewModel = FragmentItemListBinding.inflate(inflater, container, false)
            deleteItem(viewModel)
        }
        setText()
        return binding.root
    }

    private fun setText()
    {
        binding.apply {
            pubNameText.text = args.name
            phone.text = args.phone ?: "no phone"
            website.text = args.website ?: "no website"
            amenity.text = args.amenity ?: "none"
            hours.text = args.openingHours?.replace(';','\n') ?: "none"
            phone.text = args.phone
        }
    }

    private fun showMap()
    {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:${args.latitude},${args.longitude}"))
        startActivity(intent)
    }

    private fun deleteItem(viewModel : FragmentItemListBinding)
    {
        //ItemAdapter.removeById(args.itemID)
        viewModel.itemViewModel?.deleteByID(args.itemID)
        val fragmentDirections = PubInfoFragmentDirections.actionPubInfoFragmentToItemFragment()
        binding.root.findNavController().navigate(fragmentDirections)
    }
}