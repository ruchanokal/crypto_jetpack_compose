package com.marslogistics.jetpackcomposedeneme.domain.repo

import com.marslogistics.jetpackcomposedeneme.data.model.CryptoModel

interface CryptoRepo {

    suspend fun getCryptoDatas() : CryptoModel
}