package escape.room;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import escape.room.util.Abilita;

public class CestinoActivity extends AppCompatActivity {
    private static boolean videoCestino = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cestino);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Abilita.CERCATORE_DI_INFORMAZIONI = true;
        ImageView imageView = findViewById(R.id.imageView_cestino);
        VideoView videoView = findViewById(R.id.videoView_cestino);
        if(videoCestino){
            imageView.setVisibility(View.INVISIBLE);
            videoView.setVideoPath("android.resource://" + getPackageName()+ "/" + R.raw.cestino);
            videoView.start();
            videoView.setOnCompletionListener(mp -> {
                Toast.makeText(videoView.getContext(),"HAI OTTENUTO L'ABILITÀ CERCATORE DI INFORMAZIONI!",Toast.LENGTH_LONG).show();
            });
            videoCestino = false;
        }
        else{
            videoView.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
        }
    }
}
