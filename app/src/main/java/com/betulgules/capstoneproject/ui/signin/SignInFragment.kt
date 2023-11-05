package com.betulgules.capstoneproject.ui.signin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.betulgules.capstoneproject.R
import com.betulgules.capstoneproject.common.viewBinding
import com.betulgules.capstoneproject.databinding.FragmentSignInBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        auth.currentUser?.let {
            findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
        }

        with(binding) {
            btnSignIn.setOnClickListener {

                val et_email1 = etEmail1.text.toString()
                val et_password1 = etPassword1.text.toString()

                if (CheckFields(et_email1, et_password1)) {
                    loginToFirebase(et_email1, et_password1)
                }
            }
            textView.setOnClickListener {
                val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
                findNavController().navigate(action)
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

    fun loginToFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
        }.addOnFailureListener {
            Snackbar.make(requireView(), it.message.orEmpty(), 1000).show()
        }
    }
}
