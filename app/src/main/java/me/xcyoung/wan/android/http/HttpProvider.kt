package me.xcyoung.wan.android.http

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author ChorYeung
 * @since 2022/1/13
 */
class HttpProvider {
    private val retrofit: Retrofit

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        this.retrofit = retrofit
    }

    fun <T> createService(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }
}