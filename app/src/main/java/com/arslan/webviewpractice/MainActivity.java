package com.arslan.webviewpractice;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //MUST ADD INTERNET PERMISSION IN MANIFEST FOR WEB VIEW !!!!!!!!!!!!!
    WebView webView;
    WebSettings webSettings;
    Toolbar toolbar;
    ImageView imageView;
    TextView titleText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageView = (ImageView) findViewById(R.id.iconImageView);

        titleText = (TextView) findViewById(R.id.txtTitle);

        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("https://www.google.com");

        //Setting javascript in browser
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                titleText.setText(title);
            }


            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);

                imageView.setImageBitmap(icon);


                //Drawable drawable = new BitmapDrawable(MainActivity.this.getResources(), icon);
                //getSupportActionBar().setIcon(drawable);

            }
        });

    }

    @Override
    public void onBackPressed() {


        if(webView.canGoBack()){
            webView.goBack();
        }
        else{
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        MenuItem menuItem = menu.findItem(R.id.m_search);
        SearchView searchView = (SearchView)menuItem.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                webView.loadUrl("https://www.google.com/search?q="+query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){


            case R.id.m_backward:
                onBackPressed();
                break;

            case R.id.m_forward:

                if(webView.canGoForward()){
                    webView.goForward();
                }
                break;


        }
        return super.onOptionsItemSelected(item);

    }
}
