package escape.room;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import escape.room.util.Abilita;

public class LavagnaActivity extends AppCompatActivity {
    private static boolean videoLavagna = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lavagna);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onStart() {
        super.onStart();
        ImageView imageView = findViewById(R.id.imageView_lavagna);
        VideoView videoView = findViewById(R.id.videoView_lavagna);
        if(Abilita.MENTE_BRILLANTE_PROBLEM_SOLVING) {
            if (videoLavagna) {
                videoView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.INVISIBLE);
                videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.lavagna);
                videoView.start();
                videoView.setOnCompletionListener(mp -> {
                    Toast.makeText(videoView.getContext(),"HAI OTTENUTO L'ABILITÀ CREATORE DI RANDSOMWARE!",Toast.LENGTH_LONG).show();
                });
                videoLavagna = false;
            } else {
                imageView.setImageDrawable(getDrawable(R.drawable.lavagna_cripto));
                videoView.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.VISIBLE);
            }
        }
        else{
            videoView.setVisibility(View.INVISIBLE);
        }
    }
}
