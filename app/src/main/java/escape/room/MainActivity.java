package escape.room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;
    private static boolean lockLavagna = true;
    private static boolean videoIntro = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        set_pusanti(View.INVISIBLE);
        /*
        setContentView(R.layout.activity_main);
        Button enableCamera = findViewById(R.id.enableCamera);
        enableCamera.setOnClickListener(c -> {
            if (hasCameraPermission()) {
                enableCamera();
            } else {
                requestPermission();
            }
        });
        */
    }

    @Override
    protected void onStart() {
        super.onStart();
        VideoView videoView = findViewById(R.id.videoView);
        if(videoIntro){
            videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.intro);
            videoView.start();
            videoView.setOnCompletionListener(mp -> {
                videoView.setVisibility(View.INVISIBLE);
                set_pusanti(View.VISIBLE);
            });
        }
        else{
            videoView.setVisibility(View.INVISIBLE);
            set_pusanti(View.VISIBLE);
        }
        videoIntro = false;
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

    public static boolean getLockLavagna() {
        return lockLavagna;
    }

    public static void setLockLavagna(boolean lockLavagna) {
        MainActivity.lockLavagna = lockLavagna;
    }

    private void set_pusanti(int visibilita){
        findViewById(R.id.cestino).setVisibility(visibilita);
        findViewById(R.id.cassetto).setVisibility(visibilita);
        findViewById(R.id.computer).setVisibility(visibilita);
        findViewById(R.id.lavagna).setVisibility(visibilita);
        findViewById(R.id.banco).setVisibility(visibilita);
    }

    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                CAMERA_PERMISSION,
                CAMERA_REQUEST_CODE
        );
    }

    private void enableCamera() {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }
}