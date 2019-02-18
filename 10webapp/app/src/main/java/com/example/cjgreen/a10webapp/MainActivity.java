package com.example.cjgreen.a10webapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    // 변수 선언
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //from here
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        web = new WebView(this);
        WebSettings webSet = web.getSettings();
        webSet.setJavaScriptEnabled(true);
        webSet.setUseWideViewPort(true);
        webSet.setBuiltInZoomControls(false);
        webSet.setAllowUniversalAccessFromFileURLs(true);
        webSet.setJavaScriptCanOpenWindowsAutomatically(true);
        webSet.setSupportMultipleWindows(true);
        webSet.setSaveFormData(false);
        webSet.setSavePassword(false);
        webSet.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        web.setWebChromeClient(new WebChromeClient());
        web.setWebViewClient(new WebViewClient());
        //web.loadUrl("file:///android_asset/index.html");
        //내부적으로 android_asset은 우리가 생성한 assets폴더를 의미함. 첫페이지를 index.html페이지로 물고감
        web.loadUrl("https://m.daum.net"); //우리가 만든 어플리케이션의 첫페이지를 다음으로 물고감
        layout.addView(web);
        setContentView(layout);
        //until here
        //setContentView(R.layout.activity_main);
    }// end of onCreate

    public void onBackPressed(){
        if(web.canGoBack()) web.goBack(); //뒤로 갈수 있는 상황이면 뒤로가주고 아니면 끝내기
        else finish();
    }
}
