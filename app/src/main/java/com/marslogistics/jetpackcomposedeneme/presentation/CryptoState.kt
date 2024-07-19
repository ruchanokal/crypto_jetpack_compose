package com.marslogistics.jetpackcomposedeneme.presentation

import com.marslogistics.jetpackcomposedeneme.data.model.Data
import com.marslogistics.jetpackcomposedeneme.data.model.Status


data class CryptoState(
    var loading : Boolean = false,
    var cryptoList: List<Data> = emptyList(),
    var errorMessage: String = ""
) {

}