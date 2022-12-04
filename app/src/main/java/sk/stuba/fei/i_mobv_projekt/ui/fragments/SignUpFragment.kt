package sk.stuba.fei.i_mobv_projekt.ui.fragments

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import sk.stuba.fei.i_mobv_projekt.R
import sk.stuba.fei.i_mobv_projekt.databinding.FragmentSignUpBinding
import sk.stuba.fei.i_mobv_projekt.helpers.Injection
import sk.stuba.fei.i_mobv_projekt.helpers.PreferenceData
import sk.stuba.fei.i_mobv_projekt.ui.viewmodels.AuthViewModel

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory(requireContext())
        ).get(AuthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val x = PreferenceData.getInstance().getUserItem(requireContext())
        if ((x?.uid ?: "").isNotBlank()) {
            Navigation.findNavController(view).navigate(R.id.action_to_bars)
            return
        }

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = authViewModel
        }

        binding.welcome.addAnimatorListener(object: Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) { }

            override fun onAnimationEnd(animation: Animator?) {
                Navigation.findNavController(requireView()).navigate(R.id.action_to_bars)
            }

            override fun onAnimationCancel(animation: Animator?) {
                Navigation.findNavController(requireView()).navigate(R.id.action_to_bars)
            }

            override fun onAnimationStart(animation: Animator?) {}
        })

        binding.signup.setOnClickListener {
            if (binding.username.text.toString().isNotBlank() && binding.password.text.toString().isNotBlank()
                && binding.password.text.toString().compareTo(binding.repeatPassword.text.toString())==0) {
                authViewModel.signup(
                    binding.username.text.toString(),
                    binding.password.text.toString()
                )
            } else if (binding.username.text.toString().isBlank() || binding.password.text.toString().isBlank()){
                authViewModel.show("Fill in name and password")
            } else {
                authViewModel.show("Passwords must be same")
            }
        }

        binding.login.setOnClickListener {
            it.findNavController().navigate(R.id.action_to_login)
        }

        authViewModel.user.observe(viewLifecycleOwner){
            it?.let {
                PreferenceData.getInstance().putUserItem(requireContext(),it)
                binding.welcome.visibility = View.VISIBLE
                binding.welcome.playAnimation()
            }
        }

    }
}