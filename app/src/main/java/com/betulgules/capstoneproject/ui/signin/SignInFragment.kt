package com.betulgules.capstoneproject.ui.signin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.betulgules.capstoneproject.R
import com.betulgules.capstoneproject.common.gone
import com.betulgules.capstoneproject.common.viewBinding
import com.betulgules.capstoneproject.common.visible
import com.betulgules.capstoneproject.databinding.FragmentSignInBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)

    private val viewModel by viewModels<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            btnSignIn.setOnClickListener {
                val email = etEmail1.text.toString()
                val password = etPassword1.text.toString()

                viewModel.checkFields(email,password)
            }

            textView.setOnClickListener {
                findNavController().navigate(R.id.signInToSignUp)
            }
        }

        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.signInState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SignInState.Loading -> progressBar.visible()

                is SignInState.GoToHome -> {
                    progressBar.gone()
                    findNavController().navigate(R.id.signInToMainGraph)
                }

                is SignInState.ShowPopUp -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }
    }
}