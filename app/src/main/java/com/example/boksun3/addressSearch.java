package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class addressSearch extends AppCompatActivity {

    private WebView daum_webview;
    private TextView daum_result;
    private Handler handler;
    private String address;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_search);

        daum_result = findViewById(R.id.daum_result);

        init_webView();

        handler = new Handler();

    }

    public void init_webView(){

        daum_webview = findViewById(R.id.daum_webview);

        daum_webview.getSettings().setJavaScriptEnabled(true);

        daum_webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        daum_webview.addJavascriptInterface(new AndroidBridge(), "TestApp");

        daum_webview.setWebChromeClient(new WebChromeClient());

        daum_webview.loadUrl("http://211.227.224.230:8090/Project_bok/search2.html");

    }


    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3){
            handler.post(new Runnable() {
                @Override
                public void run() {


                    daum_result.setText(String.format("(%s) %s %s", arg1,arg2,arg3));

                    address = daum_result.getText().toString();
                    Intent intent = new Intent(addressSearch.this, adminHandiJoin.class);
                    intent.putExtra("address",address);
                    startActivity(intent);

                    init_webView();

                    finish();
                }
            });
        }

    }

}
