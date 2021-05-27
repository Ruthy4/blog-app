package com.decagon.android.sq007.services

import com.decagon.android.sq007.util.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var firstInstance: Retrofit? = null

    val instance: Retrofit
        // create a retrofit instance to call the end point and retrieve information
        // set the default converter
        // build the retrofit object
        get() {
            if (firstInstance == null)
                firstInstance = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build() // get a Retrofit object that can be reuseable

            return firstInstance!!
        }

    val api: ApiEndPointInterface by lazy {
        instance.create(ApiEndPointInterface::class.java)
    }
}


// .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
