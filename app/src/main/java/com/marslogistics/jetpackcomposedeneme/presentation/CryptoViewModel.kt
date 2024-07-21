package com.marslogistics.jetpackcomposedeneme.presentation

import android.util.Log
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

    private val TAG = "CryptoViewModel"

    //private var _state = mutableStateOf(CryptoState())
    //val state : State<CryptoState> = _state

    var cryptoList = mutableStateOf<List<Data>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)


    private var job : Job? = null

    private var initialCryptoList = listOf<Data>()
    private var isSearchStarted = true

    init {
        getCryptoDatas()
    }

    fun getCryptoDatas() {

//        _state.value = CryptoState()

        job?.cancel()
        job = getCryptoUseCase.executeCryptoData().onEach {

            when (it) {
                is Resource.Success -> {
                    //_state.value = CryptoState(cryptoList = it.data!!.data )
                    val cryptoItems = it.data!!.data

                    errorMessage.value = ""
                    isLoading.value = false
                    cryptoList.value += cryptoItems
                }

                is Resource.Error -> {
                    //_state.value = CryptoState(errorMessage = it.data!!.status.error_message )
                    errorMessage.value = it.message!!
                    isLoading.value = false
                }

                is Resource.Loading -> {
                    //_state.value = CryptoState(loading = true)
                    errorMessage.value = ""
                }
            }

        }.launchIn(viewModelScope)

    }


    fun search(searchString: String) {

        val listToSearch = if (isSearchStarted){
            //_state.value.cryptoList
            cryptoList.value
        } else {
            initialCryptoList
        }

        viewModelScope.launch(Dispatchers.Default) {

            if (searchString.isEmpty()){
                //_state.value.cryptoList = initialCryptoList
                cryptoList.value = initialCryptoList
                isSearchStarted = true
                return@launch
            }

            Log.i(TAG,"listToSearch: ${listToSearch}")

            val results = listToSearch.filter {  it.name.contains(searchString,true)}

            if (isSearchStarted){
                //initialCryptoList = _state.value.cryptoList
                initialCryptoList = cryptoList.value
                isSearchStarted = false
            }

            Log.i(TAG,"results: ${results.size}")
            //_state.value.cryptoList = results
            cryptoList.value = results


        }

    }


    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }





}