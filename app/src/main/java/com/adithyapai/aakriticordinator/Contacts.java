package com.adithyapai.aakriticordinator;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class Contacts extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        WebView webView = findViewById(R.id.webview);
        // load the contacts page from R.raw.contacts.html
        webView.loadUrl("file:///android_asset/contacts.html");

    }


}