package com.betulgules.capstoneproject.ui.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.betulgules.capstoneproject.R
import com.betulgules.capstoneproject.common.gone
import com.betulgules.capstoneproject.common.viewBinding
import com.betulgules.capstoneproject.common.visible
import com.betulgules.capstoneproject.databinding.FragmentSignUpBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)

    private val viewModel by viewModels<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnKayit.setOnClickListener {
                val email = etEmail2.text.toString()
                val password = etPassword2.text.toString()

                viewModel.checkFields(email,password)
            }

            btnGirisYap.setOnClickListener {
                findNavController().navigate(R.id.signUpToSignIn)
            }
        }

        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.signUpState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SignUpState.Loading -> progressBar.visible()

                is SignUpState.GoToHome -> {
                    progressBar.gone()
                    findNavController().navigate(R.id.signUpToMainGraph)
                }

                is SignUpState.ShowPopUp -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }

                else -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), "Bir hata oluştu.", 1000).show()}
            }
        }
    }
}