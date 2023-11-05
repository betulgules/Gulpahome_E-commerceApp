package com.betulgules.capstoneproject

import android.app.Application
import android.content.Context
import com.betulgules.capstoneproject.data.source.remote.ProductService
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.hilt.android.HiltAndroidApp
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@HiltAndroidApp
class MainApplication : Application()
