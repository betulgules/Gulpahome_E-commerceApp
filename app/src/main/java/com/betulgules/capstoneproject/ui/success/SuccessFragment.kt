package com.betulgules.capstoneproject.ui.success
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.betulgules.capstoneproject.R

class SuccessFragment : Fragment(R.layout.fragment_success){



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_success, container, false)

        /*val lottieAnimationView = view.findViewById<LottieAnimationView>(R.id.lottieAnimationView)
        lottieAnimationView.playAnimation()

        val btnHome = view.findViewById<Button>(R.id.butonHome)
        btnHome.setOnClickListener {
            // Home sayfasına gitmek için yönlendirme yap
            findNavController().navigate(R.id.action_success_to_home)
        }



        return view*/



    return view
    }
}