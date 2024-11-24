package com.atg.network.di

import com.atg.common.InfoProvider
import com.atg.network.market.MarketService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideJson() }
    single { httpClient(get()) }
    single { provideRetrofit(get(), get(), get()) }
    single<MarketService> { get<Retrofit>().create() }
}

private fun provideJson(): Json = Json {
    explicitNulls = false
    ignoreUnknownKeys = true
    isLenient = true
    coerceInputValues = true
}

private fun httpClient(info: InfoProvider): OkHttpClient {
    val timeout = 2L
    val okHttpBuilder = OkHttpClient.Builder()
        .connectTimeout(timeout, TimeUnit.MINUTES)
        .readTimeout(timeout, TimeUnit.MINUTES)
        .writeTimeout(timeout, TimeUnit.MINUTES)
    if (info.isDebug.not()) return okHttpBuilder.build()

    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    okHttpBuilder.addInterceptor(loggingInterceptor)
    return okHttpBuilder.build()
}

private fun provideRetrofit(info: InfoProvider, json: Json, client: OkHttpClient) =
    Retrofit.Builder()
        .baseUrl(info.baseUrl)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .client(client)
        .build()