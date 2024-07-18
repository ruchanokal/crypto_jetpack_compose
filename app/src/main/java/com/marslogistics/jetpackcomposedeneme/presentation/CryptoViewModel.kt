package com.marslogistics.jetpackcomposedeneme.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marslogistics.jetpackcomposedeneme.data.model.CryptoModel
import com.marslogistics.jetpackcomposedeneme.domain.use_case.GetCryptoUseCase
import com.marslogistics.jetpackcomposedeneme.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(private val getCryptoUseCase: GetCryptoUseCase) : ViewModel() {


    private val _state = mutableStateOf(CryptoState())
    val state : State<CryptoState> = _state
    private var job : Job? = null

    init {
        getCryptoDatas()
    }

    private fun getCryptoDatas() {

        job?.cancel()
        job = getCryptoUseCase.executeCryptoData().onEach {

            when (it) {
                is Resource.Success -> {
                    _state.value = CryptoState(cryptoList = it.data ?: CryptoModel())
                }

                is Resource.Error -> {
                    _state.value = CryptoState(errorMessage = it.message ?: "Error!")
                }

                is Resource.Loading -> {
                    _state.value = CryptoState(loading = true)
                }
            }

        }.launchIn(viewModelScope)

    }






}