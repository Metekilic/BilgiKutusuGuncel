package com.example.bilgikutusu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

public class StartActivity extends AppCompatActivity {

    private Button startTekoyunculu;
    private Button startAyarlar;
    private Button cikisbuton;
    private Button  btndeneme;
    private  Button btnHesap;





    private MediaPlayer btnAyarlar;
    private MediaPlayer btnPlay;
    private MediaPlayer btnCokoyunculu;
    private MediaPlayer btnCikis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);





        btnAyarlar = MediaPlayer.create(StartActivity.this, R.raw.song);
        Button btnayarlarses = (Button) this.findViewById(R.id.startAyarlar);

        btnayarlarses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnCokoyunculu = MediaPlayer.create(StartActivity.this, R.raw.song);
        Button btnCokoyunculuses = (Button) this.findViewById(R.id.btnCokoyunculu);

        btnCokoyunculuses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCokoyunculu.start();
            }
        });

        btnPlay = MediaPlayer.create(StartActivity.this, R.raw.song);
        Button btnTekoyunculuses = (Button) this.findViewById(R.id.btnTekoyunculu);

        btnTekoyunculuses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnCikis = MediaPlayer.create(StartActivity.this, R.raw.song);
        Button btnCikisses = (Button) this.findViewById(R.id.btnCikis);

        btnCikisses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCikis.start();
                finish();


            }
        });


        startTekoyunculu = findViewById(R.id.btnTekoyunculu);
        startTekoyunculu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(StartActivity.this, CategoriesActivity.class);
                startActivity(categoryIntent);
                btnPlay.start();
            }
        });


        startAyarlar = findViewById(R.id.startAyarlar);
        startAyarlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ayarlarIntent = new Intent(StartActivity.this, AyarlarActivity.class);
                startActivity(ayarlarIntent);
                btnAyarlar.start();
            }
        });



    }


}











