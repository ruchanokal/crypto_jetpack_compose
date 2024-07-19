package com.marslogistics.jetpackcomposedeneme.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marslogistics.jetpackcomposedeneme.data.model.CryptoModel
import com.marslogistics.jetpackcomposedeneme.data.model.Data
import com.marslogistics.jetpackcomposedeneme.data.model.Status
import com.marslogistics.jetpackcomposedeneme.domain.use_case.GetCryptoUseCase
import com.marslogistics.jetpackcomposedeneme.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(private val getCryptoUseCase: GetCryptoUseCase) : ViewModel() {


    private var _state = mutableStateOf(CryptoState())
    val state : State<CryptoState> = _state
    private var job : Job? = null

    private var initialCryptoList = listOf<Data>()
    private var isSearchStarted = true

    init {
        getCryptoDatas()
    }

    private fun getCryptoDatas() {

//        _state.value = CryptoState()

        job?.cancel()
        job = getCryptoUseCase.executeCryptoData().onEach {

            when (it) {
                is Resource.Success -> {
                    _state.value = CryptoState(cryptoList = it.data!!.data )
                }

                is Resource.Error -> {
                    _state.value = CryptoState(errorMessage = it.data!!.status.error_message )
                }

                is Resource.Loading -> {
                    _state.value = CryptoState(loading = true)
                }
            }

        }.launchIn(viewModelScope)

    }


    private fun search(searchString: String) {

        val listToSearch = if (isSearchStarted){
            _state.value.cryptoList
        } else {
            initialCryptoList
        }

        viewModelScope.launch(Dispatchers.Default) {

            if (searchString.isEmpty()){
                _state.value.cryptoList = initialCryptoList
                isSearchStarted = true
                return@launch
            }

            val results = listToSearch.filter {  it.name.contains(searchString,true)}

            if (isSearchStarted){
                initialCryptoList = _state.value.cryptoList
                isSearchStarted = false
            }

            _state.value.cryptoList = results

        }

    }


    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }





}