package escape.room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        findViewById(R.id.start).setOnClickListener(c -> {
            if(ImpostazioniActivity.AR && !hasCameraPermission())
                requestPermission();
            else if(ImpostazioniActivity.videoIntro)
                startVideoIntro();
            else
                startGame();
        });
        findViewById(R.id.imageView_settings).setOnClickListener(s -> {
            startActivity(new Intent(this,ImpostazioniActivity.class));
        });
        findViewById(R.id.storia).setOnClickListener(s -> {
            startActivity(new Intent(this,StoriaActivity.class));
        });
    }

    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, CAMERA_PERMISSION, CAMERA_REQUEST_CODE);
    }

    private void startVideoIntro(){
        nascondiView();
        VideoView videoView = findViewById(R.id.videoView_intro);
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.video_introduzione);
        videoView.start();
        videoView.setOnCompletionListener( v -> {
            startGame();
        });
    }

    private void nascondiView(){
        findViewById(R.id.start).setVisibility(View.INVISIBLE);
        findViewById(R.id.imageView_settings).setVisibility(View.INVISIBLE);
        findViewById(R.id.imageView_sfondo).setVisibility(View.INVISIBLE);
        findViewById(R.id.storia).setVisibility(View.INVISIBLE);
    }

    private void startGame(){
        if(ImpostazioniActivity.AR)
            startActivity(new Intent(this,CameraActivity.class));
        else
            startActivity(new Intent(this,TestActivity.class));
    }
}