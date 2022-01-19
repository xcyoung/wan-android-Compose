package me.xcyoung.wan.android.module.browser

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import me.xcyoung.wan.android.ui.theme.WanAndroidTheme


class BrowserActivity : ComponentActivity() {
    companion object {
        fun start(context: Context, url: String) {
            val intent = Intent(context, BrowserActivity::class.java)
            intent.putExtra("url", url)
            context.startActivity(intent)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url = intent.getStringExtra("url") ?: ""
        setContent {
            WanAndroidTheme {
                Scaffold {
                    AndroidView(
                        factory = {
                            WebView(it)
                        },
                        modifier = Modifier
                            .fillMaxSize(),
                        update = {
                            it.webViewClient = object : WebViewClient() {
                                override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                                    return try {
                                        if (url.startsWith("http:") || url.startsWith("https:")) {
                                            view!!.loadUrl(url)
                                            true
                                        } else {
                                            false
                                        }
                                    } catch (e: Exception) {
                                        false
                                    }
                                }
                            }
                            val settings: WebSettings = it.settings
                            settings.mixedContentMode =
                                WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                            settings.javaScriptEnabled = true //启用js
                            settings.blockNetworkImage = false //解决图片不显示
                            it.loadUrl(url)
                        }
                    )
                }
            }
        }
    }
}