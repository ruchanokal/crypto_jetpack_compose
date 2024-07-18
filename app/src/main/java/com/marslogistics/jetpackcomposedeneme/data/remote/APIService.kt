package com.marslogistics.jetpackcomposedeneme.data.remote

import com.marslogistics.jetpackcomposedeneme.data.model.CryptoModel
import retrofit2.http.GET

interface APIService {

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    suspend fun getCryptoData(): CryptoModel

}