package sk.stuba.fei.i_mobv_projekt.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import sk.stuba.fei.i_mobv_projekt.R
import sk.stuba.fei.i_mobv_projekt.databinding.FragmentContactAddBinding
import sk.stuba.fei.i_mobv_projekt.helpers.Injection
import sk.stuba.fei.i_mobv_projekt.ui.viewmodels.ContactViewModel

class ContactAddFragment : Fragment() {
    private lateinit var binding: FragmentContactAddBinding
    private lateinit var viewmodel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewmodel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory(requireContext())
        ).get(ContactViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewmodel
        }.also { bnd ->

            bnd.contactSubmit.setOnClickListener {
                if (bnd.contactName.text.toString().isNotBlank()) {
                    viewmodel.addFriend(bnd.contactName.text.toString())
                }else {
                    viewmodel.show("Fill in friend's name")
                }
            }

            bnd.back.setOnClickListener { it.findNavController().popBackStack() }
        }

        viewmodel.message.observe(viewLifecycleOwner) {
            // Navigate to bar
        }
    }
}