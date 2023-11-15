package com.betulgules.capstoneproject.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.betulgules.capstoneproject.common.Resource
import com.betulgules.capstoneproject.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private var _signInState = MutableLiveData<SignInState>()
    val signInState: LiveData<SignInState> get() = _signInState

    fun signIn(email: String, password: String) = viewModelScope.launch {
        _signInState.value = SignInState.Loading

        _signInState.value = when (val result = authRepository.signIn(email, password)) {
            is Resource.Success -> SignInState.GoToHome
            is Resource.Fail -> SignInState.ShowPopUp(result.failMessage)
            is Resource.Error -> SignInState.ShowPopUp(result.errorMessage)

        }
    }

    fun checkFields(email: String, password: String){
        when {
            email.isEmpty() -> {
                _signInState.value = SignInState.ShowPopUp("Email alanı boş bırakılamaz")
            }

            password.isEmpty() -> {
                _signInState.value = SignInState.ShowPopUp("Şifre alanı boş bırakılamaz")
                false
            }

            password.length < 6 -> {
                _signInState.value = SignInState.ShowPopUp("Şifre alanı 6 karakterden kısa olamaz")
                false
            }

            else -> {
                signIn(email, password)
            }
        }
    }
}

sealed interface SignInState {
    object Loading : SignInState
    object GoToHome: SignInState
    data class ShowPopUp(val errorMessage: String) : SignInState
}