package escape.room;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import escape.room.util.Abilita;

public class ComputerMailActivity extends AppCompatActivity {

    private String logTo;
    private static boolean videoMail = true;
    private static boolean videoRispostaDirigente = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_mail);
        Bundle extras = getIntent().getExtras();
        logTo = extras.getString("logTo");
    }

    @Override
    protected void onStart() {
        super.onStart();
        VideoView videoView = findViewById(R.id.videoView_mail);
        ImageView imageView = findViewById(R.id.imageView_mail);
        ImageView imageViewInviaMail = findViewById(R.id.imageView_invia_mail);
        imageViewInviaMail.setVisibility(View.INVISIBLE);
        switch(logTo) {
            case "profDistratto":
                if (videoMail) {
                    imageView.setVisibility(View.INVISIBLE);
                    videoView.setVisibility(View.VISIBLE);
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.scambio_mail);
                    videoView.start();
                    videoView.setOnCompletionListener(mp -> {
                        Toast.makeText(this, "HAI OTTENUTO L'ABILITÀ MENTE BRILLANTE: PROBLEM SOLVING!", Toast.LENGTH_LONG).show();
                    });
                    videoMail = false;
                } else {
                    videoView.setVisibility(View.INVISIBLE);
                    imageView.setBackgroundResource(R.drawable.scambio_mail);
                    imageView.setVisibility(View.VISIBLE);
                }
                Abilita.MENTE_BRILLANTE_PROBLEM_SOLVING = true;
                break;
            case "profAutoritario":
                if (videoRispostaDirigente) {
                    imageView.setVisibility(View.INVISIBLE);
                    videoView.setVisibility(View.VISIBLE);
                    videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.mail_phishing);
                    videoView.start();
                    videoView.setOnCompletionListener(mc -> {
                        if(videoRispostaDirigente)
                            imageViewInviaMail.setVisibility(View.VISIBLE)
                    ;});
                    imageViewInviaMail.setOnClickListener(v -> {
                        imageViewInviaMail.setVisibility(View.INVISIBLE);
                        videoRispostaDirigente = false;
                        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.risposta_dirigente);
                        videoView.start();
                    });
                }else{
                    videoView.setVisibility(View.INVISIBLE);
                    imageViewInviaMail.setVisibility(View.INVISIBLE);
                    imageView.setBackgroundResource(R.drawable.mail_dirigente);
                    imageView.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}