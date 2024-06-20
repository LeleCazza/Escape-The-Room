package escape.room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import escape.room.util.Abilita;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        startCountDown();
    }

    @Override
    protected void onStart() {
        super.onStart();
        controlloAbilitaOttenute();
        findViewById(R.id.cestino).setOnClickListener(v -> {
            startActivity(new Intent(this,CestinoActivity.class));
        });
        findViewById(R.id.cassetto).setOnClickListener(v -> {
            startActivity(new Intent(this,CassettoActivity.class));
        });
        findViewById(R.id.computer).setOnClickListener(v -> {
            startActivity(new Intent(this, ComputerLoginActivity.class));
        });
        findViewById(R.id.lavagna).setOnClickListener(v -> {
            startActivity(new Intent(this,LavagnaActivity.class));
        });
        findViewById(R.id.banco).setOnClickListener(v -> {
            startActivity(new Intent(this,BancoActivity.class));
        });
    }

    private void startCountDown(){
        String attivaCountdown = "si";
        Bundle extras = getIntent().getExtras();
        if(extras != null)
            attivaCountdown = extras.getString("countdown");
        if(attivaCountdown.equals("si")){
            TextView countdownTextView = findViewById(R.id.countdown_textview_test);
            countdownTextView.setTextColor(Color.RED);
            new CountDownTimer(ImpostazioniActivity.tempo, 1000) {
                @SuppressLint("DefaultLocale")
                @Override
                public void onTick(long millisUntilFinished) {
                    long minutes = millisUntilFinished / 60000;
                    long seconds = (millisUntilFinished % 60000) / 1000;
                    countdownTextView.setText(String.format("%02d:%02d", minutes, seconds));
                }
                @Override
                public void onFinish() {
                    countdownTextView.setText("");
                    startActivity(new Intent(TestActivity.this,PausaActivity.class));
                    finish();
                }
            }.start();
        }
        else
            findViewById(R.id.countdown_textview_test).setVisibility(View.INVISIBLE);
    }

    private void controlloAbilitaOttenute() {
        if(Abilita.CERCATORE_DI_INFORMAZIONI_AVANZATO_1 && Abilita.CERCATORE_DI_INFORMAZIONI_AVANZATO_2){
            Toast.makeText(this,
                    "HAI OTTENUTO L'ABILITÀ CERCATORE DI INFORMAZIONI AVANZATO!",
                    Toast.LENGTH_LONG).show();
            Abilita.CERCATORE_DI_INFORMAZIONI_AVANZATO = true;
            Abilita.CERCATORE_DI_INFORMAZIONI_AVANZATO_1 = false;
            Abilita.CERCATORE_DI_INFORMAZIONI_AVANZATO_2 = false;
        }
    }

    @Override
    public void onBackPressed() {
        //tasto indietro disabilitato
    }
}