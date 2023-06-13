package escape.room;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
        ImageView imageView = findViewById(R.id.imageView_cestino);
        VideoView videoView = findViewById(R.id.videoView_cestino);
        if(videoCestino){
            imageView.setVisibility(View.INVISIBLE);
            videoView.setVideoPath("android.resource://" + getPackageName()+ "/" + R.raw.cestino);
            videoView.start();
            videoCestino = false;
        }
        else{
            videoView.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
        }
    }
}
