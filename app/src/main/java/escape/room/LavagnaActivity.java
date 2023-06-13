package escape.room;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LavagnaActivity extends AppCompatActivity {
    private static boolean videoLavagna = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lavagna);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ImageView imageView = findViewById(R.id.imageView_lavagna);
        VideoView videoView = findViewById(R.id.videoView_lavagna);
        if(MainActivity.getLockLavagna()){
            imageView.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.INVISIBLE);
        }
        else if(videoLavagna){
            videoView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.INVISIBLE);
            videoView.setVideoPath("android.resource://" + getPackageName()+ "/" + R.raw.lavagna);
            videoView.start();
            videoLavagna = false;
        }
        else{
            imageView.setImageDrawable(getDrawable(R.drawable.lavagna_cripto));
            videoView.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
        }
    }
}
