package com.zaim.microzaim


import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private val URL = "https://microzaim.github.io/#/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                println("Fetching FCM registration token failed")
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result



            println(token);
            Toast.makeText(baseContext, "your token - $token", Toast.LENGTH_SHORT).show()
        })
        webView = findViewById(R.id.MywebView)
        webView.apply {
            loadUrl(URL)
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }

    }

}