package com.betulgules.capstoneproject.ui.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.betulgules.capstoneproject.R
import com.betulgules.capstoneproject.common.viewBinding
import com.betulgules.capstoneproject.databinding.FragmentSignUpBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        with(binding){

            buttonkayit.setOnClickListener {
                val et_email2 = etEmail2.text.toString()
                val et_password2 = etPassword2.text.toString()
                if(CheckFields(et_email2, et_password2)){
                    signUp(et_email2, et_password2 )
                }
            }
        }
    }

    fun CheckFields(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> false
            password.isEmpty() -> false
            else -> true
        }
    }

    private fun signUp(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }.addOnFailureListener {
            Snackbar.make(requireView(), it.message.orEmpty(), 1000).show()
         }
    }
}