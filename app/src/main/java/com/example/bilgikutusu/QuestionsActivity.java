package com.example.bilgikutusu;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private TextView sorular,no_indicator;
    private FloatingActionButton archive;
    private LinearLayout secenekbolumu;
    private int puan;
    private Button paylas,ilerle;
    private int count = 0;
    private List<SoruModel> list;
    private int position = 0;
    private String category;
    private int setNo;
    private Dialog loadingDialog;
    TextView text;
    CountDownTimer countDownTimer;
    int saniye = 17; //15 + 2 (animasyon için geçen süre)//****************************************************

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        text = (TextView) findViewById(R.id.textViewsecond);
        countDownTimer = new CountDownTimer(10000000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //****************************************************
                saniye--;
                //2 saniye animasyon için geçen süreyide ekleyerek 17 den saymaya başlıyor
                //ancak 17 ve 16 yerine ekranda 15 görünmesi için aşağıdaki (....) içindeki kısmı ekledim.
                //eğer saniye 15 ten büyük ise ekrana 15 yaz, 15den küçük ise saniye değerini yaz anlamındadır.

                text.setText("Kalan Süre: " + (saniye > 15 ? 15 : saniye));

                if (saniye == 0) {

                    text.setText("Süreniz Bitmiştir!");
                    countDownTimer.cancel();

                    //puan yeri
                    Intent scoreIntent = new Intent(QuestionsActivity.this, PuanActivity.class);
                    scoreIntent.putExtra("score", puan);
                    scoreIntent.putExtra("total", list.size());
                    startActivity(scoreIntent);
                    finish();
                }
                //****************************************************
            }

            @Override
            public void onFinish() {
                //text.setText("Süreniz Bitmiştir!");
            }
        };

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sorular = findViewById(R.id.sorular);
        no_indicator = findViewById(R.id.no_indicator);

        secenekbolumu = findViewById(R.id.secenek_bolumu);
        paylas = findViewById(R.id.btn_paylas);
        ilerle = findViewById(R.id.btn_ilerle);

        category = getIntent().getStringExtra("category");
        setNo = getIntent().getIntExtra("setNo", 1);


        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.yukleme);
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.kenarlik));


        list = new ArrayList<>();

        loadingDialog.show();

        myRef.child("SETS").child(category).child("questions").orderByChild("setNo").equalTo(setNo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    list.add(snapshot.getValue(SoruModel.class));
                }

                if (list.size() > 0) {
                    for (int i = 0; i < 4; i++) {
                        secenekbolumu.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onClick(View v) {
                                checkAnswer((Button) v);
                            }
                        });
                    }
                    playAnim(sorular, 0, list.get(position).getSoru());
                    countDownTimer.start();//****************************************************

                } else {
                    finish();
                    Toast.makeText(QuestionsActivity.this, "no questions", Toast.LENGTH_SHORT).show();

                }
                loadingDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(QuestionsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
                finish();

            }
        });

        //****************************************************
        //Butonların kodlarını firebase kodları dışına aldım.
        ilerle.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                ilerle.setEnabled(false);
                ilerle.setAlpha(0.7f);
                enableOption(true);
                position++;

                if (position == list.size()) {
                    //puan yeri
                    Intent scoreIntent = new Intent(QuestionsActivity.this, PuanActivity.class);
                    scoreIntent.putExtra("score", puan);
                    scoreIntent.putExtra("total", list.size());
                    startActivity(scoreIntent);
                    finish();
                    return;
                }

                count = 0;
                playAnim(sorular, 0, list.get(position).getSoru());

                saniye = 17;//15+2(animasyon süresi)****************************************************
                countDownTimer.start();//Cevap seçildiğinde sayma duruyor. Burada yeniden saymaya başlıyor****************************************************
            }
        });

        //****************************************************
        //Butonların kodlarını firebase kodları dışına aldım.
        paylas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String body = list.get(position).getSoru() + list.get(position).getSecenek1() + "  " +
                        list.get(position).getSecenek2() + "  " + list.get(position).getSecenek3() + "  " +
                        list.get(position).getSecenek4();

                Intent paylasIntent = new Intent(Intent.ACTION_SEND);
                paylasIntent.setType(" text/plain");
                paylasIntent.putExtra(Intent.EXTRA_SUBJECT, "Bilgi");
                paylasIntent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(paylasIntent, "Paylas"));
            }
        });
    }

    private void playAnim(final View view, final int value, final String data){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (value == 0 && count < 4){
                    String secenek = "";
                    if (count == 0){
                        secenek = list.get(position).getSecenek1();
                    }
                    else if (count == 1){
                        secenek = list.get(position).getSecenek2();
                    }
                    else if (count == 2){
                        secenek = list.get(position).getSecenek3();
                    }
                    else if (count == 3){
                        secenek = list.get(position).getSecenek4();
                    }
                    playAnim(secenekbolumu.getChildAt(count),0, secenek);
                    count++;

                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ((TextView)view).setText(data);
                if (value == 0){
                    try {
                        ((TextView) view).setText(data);
                        no_indicator.setText(position+1+"/"+list.size());
                    }
                    catch (ClassCastException ex){

                        ((Button) view).setText(data);

                    }
                    view.setTag(data);

                    playAnim(view,1,data);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkAnswer(Button selectedOption){

        countDownTimer.cancel();//Cevap verildiğinde saymayı duraklat. Yeni soruda saymayı başlat.****************************************************

        enableOption(false);
        ilerle.setEnabled(true);
        ilerle.setAlpha(1);
        if (selectedOption.getText().toString().equals(list.get(position).getDogrucvp())){
            puan++;
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        }
        else{
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
            Button correctoption = (Button) secenekbolumu.findViewWithTag(list.get(position).getDogrucvp());
            correctoption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void enableOption(boolean enable){
        for (int i = 0;i<4;i++){
            secenekbolumu.getChildAt(i).setEnabled(enable);
            if (enable){
                secenekbolumu.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#31b0d5")));
            }
        }
    }

    //****************************************************
    //Timer'ın activity kapanışında durdurulması için onDestroy metodunu ekledim.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }
}
