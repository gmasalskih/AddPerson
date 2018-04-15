package Util.WebHelper

import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import Util.Const.*

object RestApi {
    private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor {
                var newUrl = SingURL.signingUrl(it.request().url())
                println(newUrl)
                it.proceed(it.request().newBuilder().url(newUrl).build())
            }.build()
    private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    val webApi = retrofit.create(Api::class.java)
}