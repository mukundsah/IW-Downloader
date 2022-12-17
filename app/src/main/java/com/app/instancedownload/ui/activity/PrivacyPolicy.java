package com.app.instancedownload.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.instancedownload.R;
import com.app.instancedownload.util.Method;
import com.google.android.material.appbar.MaterialToolbar;

import java.io.IOException;
import java.io.InputStream;


public class PrivacyPolicy extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        Method method = new Method(PrivacyPolicy.this);

        MaterialToolbar toolbar = findViewById(R.id.toolbar_privacy_policy);
        toolbar.setTitle(getResources().getString(R.string.privacy_policy));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        WebView webView = findViewById(R.id.webView_privacy_policy);

        String str;
        try {
            InputStream is = getAssets().open("privarcypolicy.txt");
            int size = is.available();
            byte[] buffer = new byte[size];// Read the entire asset into a local byte buffer.
            is.read(buffer);
            is.close();
            str = new String(buffer);// Convert the buffer into a string.
        } catch (IOException e) {
            throw new RuntimeException(e); // Should never happen!
        }

        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setFocusableInTouchMode(false);
        webView.setFocusable(false);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setJavaScriptEnabled(true);
        String mimeType = "text/html";
        String encoding = "utf-8";

        String text = "<html><head>"
                + "<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/font/latoregular.ttf\")}body{font-family: MyFont;color: " + method.webViewText() + "}"
                + "</style></head>"
                + "<body>"
                + str
                + "</body></html>";

        webView.loadDataWithBaseURL(null, text, mimeType, encoding, null);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
