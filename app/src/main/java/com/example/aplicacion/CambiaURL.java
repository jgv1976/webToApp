package com.example.aplicacion;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CambiaURL extends AppCompatActivity {
    EditText et_nuevaUrl;
    TextView tv_actualURL;
    CheckBox ck_muestraURL;
    String actualURL;
    String nuevaUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambia_url);
        ck_muestraURL = (CheckBox) findViewById(R.id.checkBox);
        //ck_muestraURL.setOnCheckedChangeListener(myChangeListener);
        leeMemoria();

    }

    public void guardar(View v) {
        et_nuevaUrl = (EditText) findViewById(R.id.et_url);
        nuevaUrl = et_nuevaUrl.getText().toString().trim().toLowerCase();
        SharedPreferences memoria = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = memoria.edit();
        editor.putString("url", "http://" + nuevaUrl);
        editor.apply();
        leeMemoria();
    }

    public void leeMemoria() {
        esconderTeclado();
        SharedPreferences memoria = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        actualURL = memoria.getString("url", "http://www.google.es");
        Boolean checked = memoria.getBoolean("muestraURL", true);
        if(checked) ck_muestraURL.setChecked(true);
        else ck_muestraURL.setChecked(false);
        et_nuevaUrl = (EditText) findViewById(R.id.et_url);
        et_nuevaUrl.setText(actualURL);
    }

    public void volver(View v) {
        finish();
    }

    public void esconderTeclado() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private CompoundButton.OnCheckedChangeListener myChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SharedPreferences memoria = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = memoria.edit();
            if(isChecked) {
                editor.putBoolean("mostrarURL", true);
                editor.apply();
                //Toast.makeText(getApplicationContext(), "Se mostrará la URL cada vez que se inicie la aplicación", Toast.LENGTH_SHORT).show();
            }else{
                editor.putBoolean("mostrarURL", false);
                editor.apply();
                //Toast.makeText(getApplicationContext(), "NO se mostrará la URL cada vez que se inicie la aplicación", Toast.LENGTH_SHORT).show();
            }
        }
    };




}
