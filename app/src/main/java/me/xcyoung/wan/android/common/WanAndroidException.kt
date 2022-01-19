package me.xcyoung.wan.android.common

/**
 * @author ChorYeung
 * @since 2022/1/13
 */
data class WanAndroidException(val code: Int, override val message: String) : Exception(message) {
    companion object {
        fun create(e: Throwable): WanAndroidException {
            return WanAndroidException(-1, e.message ?: "no error message")
        }
    }

    override fun getLocalizedMessage(): String {
        return "[$code]$message"
    }
}