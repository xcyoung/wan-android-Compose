package me.xcyoung.wan.android.http

/**
 * @author ChorYeung
 * @since 2022/1/13
 */
data class WanResponse<T>(
    val errorCode: Int,
    val errorMsg: String,
    val data: T?
)