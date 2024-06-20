package escape.room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ComputerDesktopActivity extends AppCompatActivity {
    private static boolean videoPensieriPhishing = true;
    private String logTo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_desktop);
        Bundle extras = getIntent().getExtras();
        logTo = extras.getString("logTo");
    }

    @Override
    protected void onStart() {
        super.onStart();
        VideoView pensieriPhishing = findViewById(R.id.videoView_desktop);
        if(logTo.equals("profAutoritario") && videoPensieriPhishing){
            pensieriPhishing.setVisibility(View.VISIBLE);
            findViewById(R.id.computer_mail).setVisibility(View.INVISIBLE);
            findViewById(R.id.computer_registro).setVisibility(View.INVISIBLE);
            pensieriPhishing.setVideoPath("android.resource://" + getPackageName()+ "/" + R.raw.pensieri_phishing);
            pensieriPhishing.start();
            pensieriPhishing.setOnCompletionListener(mp -> {
                pensieriPhishing.setVisibility(View.INVISIBLE);
                findViewById(R.id.computer_mail).setVisibility(View.VISIBLE);
                findViewById(R.id.computer_registro).setVisibility(View.VISIBLE);
                videoPensieriPhishing = false;
            });
        }
        else
            pensieriPhishing.setVisibility(View.INVISIBLE);
        findViewById(R.id.computer_mail).setOnClickListener(v -> {
                switch (logTo) {
                    case "profDistratto":
                        startActivity(new Intent(this, ComputerLoginActivity.class)
                                .putExtra("icona", "mail"));
                        break;
                    case "profAutoritario":
                        startActivity(new Intent(this, ComputerMailActivity.class)
                                .putExtra("logTo", "profAutoritario"));
                        break;
                }
        });
        findViewById(R.id.computer_registro).setOnClickListener(v -> {
            startActivity(new Intent(this,ComputerLoginActivity.class)
                    .putExtra("icona","registro"));
        });
    }
}