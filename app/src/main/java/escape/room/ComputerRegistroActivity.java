package escape.room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ComputerRegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_registro);
    }

    @Override
    protected void onStart() {
        super.onStart();
        VideoView videoFinale = findViewById(R.id.videoView_finale);
        videoFinale.setVisibility(View.INVISIBLE);
        Button eliminaColloquio = findViewById(R.id.elimina_colloquio);
        eliminaColloquio.setOnClickListener(v -> {
            eliminaColloquio.setVisibility(View.INVISIBLE);
            videoFinale.setVideoPath("android.resource://" + getPackageName()+ "/" + R.raw.video_finale);
            videoFinale.setVisibility(View.VISIBLE);
            videoFinale.start();
        });
    }

    @Override
    public void onBackPressed() {
        //tasto indietro disabilitato
    }
}