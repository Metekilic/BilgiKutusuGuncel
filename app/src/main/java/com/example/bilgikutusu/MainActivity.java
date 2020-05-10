package com.example.bilgikutusu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Intent i = new Intent(MainActivity.this, StartActivity.class);

        Thread timer = new Thread(){

            public  void run(){
                try {
                    sleep(3000);
                } catch (InterruptedException e ){

                    e.printStackTrace();

                }
                finally {
                    startActivity(i);
                    finish();
                }

            }

        };
        timer.start();



    }
}
