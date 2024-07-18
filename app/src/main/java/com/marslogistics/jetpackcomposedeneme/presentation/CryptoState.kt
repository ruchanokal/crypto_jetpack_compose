package com.marslogistics.jetpackcomposedeneme.presentation

import com.marslogistics.jetpackcomposedeneme.data.model.CryptoModel
import com.marslogistics.jetpackcomposedeneme.data.model.CryptoModelItem

data class CryptoState(
    val loading : Boolean = false,
    val errorMessage : String = "",
    val cryptoList: CryptoModel = CryptoModel()
) {

}