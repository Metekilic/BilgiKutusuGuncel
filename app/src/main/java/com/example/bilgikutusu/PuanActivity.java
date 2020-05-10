package com.example.bilgikutusu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PuanActivity extends AppCompatActivity {

    private TextView scored,total;
    private Button btn_bitir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puan);


        scored = findViewById(R.id.scored);
        total = findViewById(R.id.total);

        btn_bitir = findViewById(R.id.btn_Bitir);

        scored.setText(String.valueOf(getIntent().getIntExtra("score",0)));
        total.setText("OUT OF"+String.valueOf(getIntent().getIntExtra("total",0)));

        btn_bitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
