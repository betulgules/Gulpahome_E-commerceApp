package com.betulgules.capstoneproject.ui.payment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.betulgules.capstoneproject.R
import com.betulgules.capstoneproject.common.viewBinding
import com.betulgules.capstoneproject.databinding.FragmentPaymentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private val binding by viewBinding(FragmentPaymentBinding::bind)
    private val viewModel by viewModels<PaymentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val etCardholderName = etCardholderName
            val etCardNumber = etCardNumber
            val etExpiryMonth = etExpiryMonth
            val etExpiryYear = etExpiryYear
            val etCVC = etCVC
            val etAddress = etAddress
            val btnPay = btnPay

            btnPay.setOnClickListener {
                val cardholderName = etCardholderName.text.toString()
                val cardNumber = etCardNumber.text.toString()
                val expiryMonth = etExpiryMonth.text.toString()
                val expiryYear = etExpiryYear.text.toString()
                val cvc = etCVC.text.toString()
                val address = etAddress.text.toString()

                viewModel.makePayment(cardholderName, cardNumber, expiryMonth, expiryYear, cvc, address)
            }
        }

        observeData()
    }

    private fun observeData() {
        viewModel.paymentState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is PaymentState.Loading -> {
                    // Loading durumunu görselleştirebilirsiniz, örneğin bir progress bar göstererek
                }
                is PaymentState.SuccessState -> {
                    findNavController().navigate(R.id.paymentToSuccess)
                }
                is PaymentState.ShowPopUp -> {
                    // Hata durumu, kullanıcıya bir hata mesajı gösterebilirsiniz
                    Snackbar.make(requireView(), state.errorMessage, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}


