package com.betulgules.capstoneproject.ui.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor() : ViewModel() {

    private var _paymentState = MutableLiveData<PaymentState>()
    val paymentState: LiveData<PaymentState> get() = _paymentState

    fun makePayment(
        cardholderName: String,
        cardNumber: String,
        expiryMonth: String,
        expiryYear: String,
        cvc: String,
        address: String
    ) = viewModelScope.launch {
        _paymentState.value = PaymentState.Loading

        if (validatePaymentInputs(cardholderName, cardNumber, expiryMonth, expiryYear, cvc, address)) {
            // Ödeme işlemini simüle et, burada sadece bir bekleme süresi ekliyoruz.
            _paymentState.value = PaymentState.SuccessState("Ödeme başarılı")
        } else {
            _paymentState.value = PaymentState.ShowPopUp("Lütfen tüm bilgileri eksiksiz ve doğru girin.")
        }
    }


    private fun validatePaymentInputs(
        cardholderName: String,
        cardNumber: String,
        expiryMonth: String,
        expiryYear: String,
        cvc: String,
        address: String
    ): Boolean {
        return cardholderName.isNotEmpty() &&
                cardNumber.length == 16 &&
                expiryMonth.isNotEmpty() &&
                expiryYear.isNotEmpty() &&
                cvc.length == 3 &&
                address.isNotEmpty()
    }
}

sealed interface PaymentState {
    object Loading : PaymentState
    data class SuccessState(val successMessage: String) : PaymentState
    data class ShowPopUp(val errorMessage: String) : PaymentState
}
