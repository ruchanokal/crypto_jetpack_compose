package com.marslogistics.jetpackcomposedeneme.domain.use_case

import com.marslogistics.jetpackcomposedeneme.data.model.CryptoModel
import com.marslogistics.jetpackcomposedeneme.domain.repo.CryptoRepo
import com.marslogistics.jetpackcomposedeneme.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

class GetCryptoUseCase @Inject constructor(val cryptoRepo : CryptoRepo) {

    fun executeCryptoData() : Flow<Resource<CryptoModel>>  = flow {

        try {
            emit(Resource.Loading())
            val cryptoList = cryptoRepo.getCryptoDatas()
            emit(Resource.Success(cryptoList))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(Resource.Error(message = "Could not reach internet"))
        }

    }

}