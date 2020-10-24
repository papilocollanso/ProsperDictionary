package com.example.prosperdictionary;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prosperdictionary.ui.dickwiki.WikiFragment;

public class WikiActivity extends AppCompatActivity {
  private WebView webView;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"SetJavaScriptEnabled", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki);

         setTitle("Wikipedia");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Wikipedia");


        webView =findViewById(R.id.web_content);

        webView.setWebViewClient(new CustomWebViewClient());
        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setAllowContentAccess(true);
if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O) {
    settings.setSafeBrowsingEnabled(true);

}
 String word=getIntent().getStringExtra("key");
        loadSearchWordOnWiki(word);
        Toast.makeText(this, R.string.joy, Toast.LENGTH_SHORT).show();



}

    class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.startsWith("https:") && url.contains("wikipedia")){
                webView.loadUrl(url);
            }
            return  true;
        }
    }
    private void loadSearchWordOnWiki(String dicWord){
        String searchQuery="https://en.m.wikipedia.org/wiki/" + dicWord;
        webView.loadUrl(searchQuery);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}