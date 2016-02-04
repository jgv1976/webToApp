package com.example.aplicacion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class Principal extends AppCompatActivity {

    String url = "";
    WebView navegador;
    Boolean flag = false;
    Boolean muestraURL = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        url = leerURL();
        if(muestraURL) Toast.makeText(this, "Abriendo " + url, Toast.LENGTH_LONG).show();
        navegador = (WebView) findViewById(R.id.webView);
        navegador.getSettings().setJavaScriptEnabled(true);
        navegador.getSettings().setBuiltInZoomControls(false);
        navegador.loadUrl(url);
        navegador.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cambio:
                cambiaURL();
                break;
            case R.id.salir:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void cambiaURL() {
        flag = true;
        Intent i = new Intent(getApplicationContext(),CambiaURL.class);
        startActivity(i);
      }

    public String leerURL() {
        SharedPreferences memoria = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String res =  memoria.getString("url", "");
        if (res=="") res = "www.google.es";
        muestraURL = memoria.getBoolean("muestraURL", true);
        return res;
    }

    public void refrescaWeb(){
        if (url != leerURL()) {
            url = leerURL();
            if(muestraURL) Toast.makeText(this, "Abriendo " + url, Toast.LENGTH_LONG).show();
            navegador.loadUrl(url);
        }
    }

    public void onResume() {
        super.onResume();
        if (flag) {
            refrescaWeb();
            flag = false;
        }
    }
}
