package com.betulgules.capstoneproject.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.betulgules.capstoneproject.R

class SplashFragment : Fragment() {
    private val splashDelay: Long = 3000 // 3 saniye
    private fun navigateToNextScreen() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        // Logo görüntüsünü 3 saniye boyunca görüntüle
        val logoImageView = view.findViewById<ImageView>(R.id.ic_logo)
        logoImageView.visibility = View.VISIBLE

        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
        }, splashDelay)

        return view
    }


}
