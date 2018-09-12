package com.uautogo.qidian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.uautogo.qidian.R;

public class ProblemActivity extends AppCompatActivity {
    private WebView webView;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);
        Intent intent = getIntent();
        String web = intent.getStringExtra("web");
        if(web.equals("etc")){
            url = "https://www.uautogo.com/question/etc_problem.html";
        }else if("sign".equals(web)){
            url = "https://www.uautogo.com/question/activation.html";
        }
        else{
            url = "https://www.uautogo.com/question/cke_proble.html";
        }

        webView = findViewById(R.id.webView);
        webView.loadUrl(url);
    }
    public void onPause() {
        super.onPause();
        webView.onPause();
    }
    public void onResume() {
        super.onResume();
        webView.onResume();
    }

}
