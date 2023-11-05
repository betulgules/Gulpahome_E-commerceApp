package com.betulgules.capstoneproject.ui.payment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.betulgules.capstoneproject.R
import com.betulgules.capstoneproject.common.viewBinding
import com.betulgules.capstoneproject.databinding.FragmentPaymentBinding
import com.google.android.material.snackbar.Snackbar

class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private val binding by viewBinding(FragmentPaymentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etCardholderName = view.findViewById<EditText>(R.id.etCardholderName)
        val etCardNumber = view.findViewById<EditText>(R.id.etCardNumber)
        val etExpiryMonth = view.findViewById<EditText>(R.id.etExpiryMonth)
        val etExpiryYear = view.findViewById<EditText>(R.id.etExpiryYear)
        val etCVC = view.findViewById<EditText>(R.id.etCVC)
        val etAddress = view.findViewById<EditText>(R.id.etAddress)
        val btnPay = view.findViewById<Button>(R.id.btnPay)

        btnPay.setOnClickListener {
            val cardholderName = etCardholderName.text.toString()
            val cardNumber = etCardNumber.text.toString()
            val expiryMonth = etExpiryMonth.text.toString()
            val expiryYear = etExpiryYear.text.toString()
            val cvc = etCVC.text.toString()
            val address = etAddress.text.toString()

            if (cardholderName.isEmpty() || cardNumber.length != 16 || expiryMonth.isEmpty() || expiryYear.isEmpty() || cvc.length != 3 || address.isEmpty()) {
                Snackbar.make(view, "Lütfen tüm bilgileri eksiksiz ve doğru girin.", Snackbar.LENGTH_SHORT).show()
            } else {
                // Ödeme işlemini gerçekleştir
                if (btnPay.isPressed.equals(true)){
                    //navigateToSuccess()
                }
            }
        }
        binding.btnPay.setOnClickListener {
            findNavController().navigate(R.id.paymentToSuccess) }

    }
}


