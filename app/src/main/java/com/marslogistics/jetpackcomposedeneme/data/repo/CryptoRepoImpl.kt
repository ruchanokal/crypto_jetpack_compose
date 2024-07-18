package com.marslogistics.jetpackcomposedeneme.data.repo

import com.marslogistics.jetpackcomposedeneme.data.model.CryptoModel
import com.marslogistics.jetpackcomposedeneme.data.remote.APIService
import com.marslogistics.jetpackcomposedeneme.domain.repo.CryptoRepo
import javax.inject.Inject

class CryptoRepoImpl @Inject constructor(val apiService: APIService) : CryptoRepo {
    override suspend fun getCryptoDatas(): CryptoModel {
        return apiService.getCryptoData()
    }
}