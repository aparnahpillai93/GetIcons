package com.geticons.UI.DetailPages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.geticons.R
import com.geticons.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    lateinit var binding : ActivityWebViewBinding
    var url=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_web_view)
        if (intent.hasExtra("URL")){
            url=intent.getStringExtra("URL").toString()
        }
        if (url.isEmpty()){
            url="http://www.iconfinder.com/"
        }
Log.e("URL",url)
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(url)
        val webSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true
    }
}