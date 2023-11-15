package com.betulgules.capstoneproject.ui.success
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.betulgules.capstoneproject.R
import com.betulgules.capstoneproject.common.viewBinding
import com.betulgules.capstoneproject.databinding.FragmentSuccessBinding

class SuccessFragment : Fragment(R.layout.fragment_success){

    private val binding by viewBinding(FragmentSuccessBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.butonHome.setOnClickListener {

            findNavController().navigate(R.id.action_success_to_home)
        }


}
}