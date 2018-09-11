package com.uautogo.qidian.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.uautogo.qidian.R;

public class BankWebActivity extends AppCompatActivity {
    WebView web_wv;
    String url = "https://www.baidu.com";
    ValueCallback<Uri> mUploadMessage;
    ValueCallback<Uri[]> mUploadMessage5;
    private static final int FILECHOOSER_RESULTCODE = 101;
    private static final int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 105;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_web);
        web_wv = (WebView) findViewById(R.id.web_wv);
        WebSettings settings = web_wv.getSettings();
        settings.setJavaScriptEnabled(true);
        web_wv.addJavascriptInterface(new JsCall(), "callNative");
        web_wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        web_wv.setWebChromeClient(new MyWebChromeClient());

        web_wv.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!web_wv.getUrl().contains("Welcome") && web_wv.canGoBack()) {
                web_wv.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage) {
                return;
            }
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5) {
            if (null == mUploadMessage5) {
                return;
            }
            mUploadMessage5.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
            mUploadMessage5 = null;
        }
    }

    private class JsCall {
        @JavascriptInterface
        public void finishNative() {
            BankWebActivity.this.finish();
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        public void openFileChooser(ValueCallback uploadMsg) {
            mUploadMessage = uploadMsg;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "File Chooser"), FILECHOOSER_RESULTCODE);
        }

        //Api >= 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType(acceptType);
            startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);
        }

        //Api 4.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType(acceptType);
            startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);
        }

        //Api 5.0+
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            if (mUploadMessage5 != null) {
                mUploadMessage5.onReceiveValue(null);
                mUploadMessage5 = null;
            }
            mUploadMessage5 = filePathCallback;
            Intent intent = fileChooserParams.createIntent();
            try {
                startActivityForResult(intent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
            } catch (ActivityNotFoundException e) {
                mUploadMessage5 = null;
                return false;
            }
            return true;
        }
    }
}
