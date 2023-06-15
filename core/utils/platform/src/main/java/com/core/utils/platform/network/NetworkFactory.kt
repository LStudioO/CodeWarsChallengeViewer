package com.core.utils.platform.network

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val NETWORK_TIMEOUT = 30L

inline fun <reified T> createApiService(
    moshi: Moshi = Moshi.Builder().build(),
    client: OkHttpClient.Builder = createOkHttpBuilder(),
    baseUrl: String,
): T = Retrofit.Builder()
    .client(client.build())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(EitherCallAdapterFactory())
    .baseUrl(baseUrl)
    .build()
    .create(T::class.java)

fun createOkHttpBuilder(): OkHttpClient.Builder {
    return OkHttpClient.Builder()
        .readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
}
