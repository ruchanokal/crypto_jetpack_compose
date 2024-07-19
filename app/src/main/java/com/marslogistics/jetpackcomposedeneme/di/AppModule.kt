package com.marslogistics.jetpackcomposedeneme.di

import com.marslogistics.jetpackcomposedeneme.data.remote.APIService
import com.marslogistics.jetpackcomposedeneme.data.repo.CryptoRepoImpl
import com.marslogistics.jetpackcomposedeneme.domain.repo.CryptoRepo
import com.marslogistics.jetpackcomposedeneme.utils.Constants.API_KEY
import com.marslogistics.jetpackcomposedeneme.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
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
            .client(provideOkHttpClient())
            .build()
            .create(APIService::class.java)
    }

    @Provides
    @Singleton
    fun provideCryptoRepository(api : APIService) :  CryptoRepo {
        return CryptoRepoImpl(api)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("X-CMC_PRO_API_KEY", API_KEY)
                    .addHeader("Accept", "application/json")
                    .build()
                chain.proceed(request)
            }
            .build()
    }


}