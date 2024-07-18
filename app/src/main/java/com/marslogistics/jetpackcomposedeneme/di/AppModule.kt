package com.marslogistics.jetpackcomposedeneme.di

import com.marslogistics.jetpackcomposedeneme.data.remote.APIService
import com.marslogistics.jetpackcomposedeneme.data.repo.CryptoRepoImpl
import com.marslogistics.jetpackcomposedeneme.domain.repo.CryptoRepo
import com.marslogistics.jetpackcomposedeneme.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideRetrofitService() : APIService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(APIService::class.java)

    }

    @Provides
    @Singleton
    fun provideCryptoRepository(api : APIService) :  CryptoRepo {
        return CryptoRepoImpl(api)
    }


}