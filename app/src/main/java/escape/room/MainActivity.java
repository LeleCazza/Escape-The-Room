package escape.room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import escape.room.util.Typewriter;

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

        set_pusanti(View.INVISIBLE);
        if(videoIntro){
            VideoView videoView = findViewById(R.id.videoView_intro);
            videoView.setVideoPath("android.resource://" + getPackageName()+ "/" + R.raw.prof_entra);
            videoView.start();
            videoView.setOnCompletionListener(mp -> {
                TextView textView = findViewById(R.id.testo);
                if(videoIntro){
                    textView.setBackgroundResource(R.drawable.back_text);
                    textView.setVisibility(View.VISIBLE);
                    Typewriter writer = new Typewriter(textView);
                    writer.setCharacterDelay(50);
                    writer.animateText(getDialogo());
                    videoView.setVideoPath("android.resource://" + getPackageName()+ "/" + R.raw.prof_esce);
                    videoView.start();
                    videoView.pause();
                    videoView.postDelayed((Runnable) videoView::start,8000);
                    videoIntro = false;
                }else{
                    textView.setVisibility(View.INVISIBLE);
                    videoView.setVisibility(View.INVISIBLE);
                    set_pusanti(View.VISIBLE);
                }
            });
        }
        else
            set_pusanti(View.VISIBLE);
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

    private CharSequence getDialogo(){
        //inserire il carattere " %" per aggiornare la textview
        CharSequence dialogo = "";
        dialogo = "Ci vediamo domani bello di Mamma!";
        dialogo += " %";
        dialogo += "MI RACCOMANDO!";
        dialogo += " %";
        dialogo += "Unico risultato possibile: VITTORIA.";
        dialogo += " %";
        dialogo += "Mo vado.";
        return dialogo;
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