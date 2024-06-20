package escape.room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RansomwareActivity extends AppCompatActivity {

    public static String chiaveSegreta ="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ransomware);
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView ransomText = findViewById(R.id.textview_ransomware);
        VideoView matrix = findViewById(R.id.videoView_matrix);
        matrix.setVisibility(View.INVISIBLE);
        ransomText.setText(
            "Grazie all'abilità:" + "\n\n" + "CREATORE DI RANSOMWARE" + "\n\n" +
            "Ti è ora possibile eseguire un attacco al PC di casa" +
            " criptando tutti i dati presenti e impostando una password segreta" +
            " per ottenere il ripristino dei dati." + "\n\n" +
            "LA MAMMA NON RIUSCIRÀ MAI AD ACCEDERE AL REGISTRO ELETTRONICO SENZA " +
            "CONOSCERE LA CHIAVE DI DECRIPTAZIONE!");
        TextView chiaveSegreta = findViewById(R.id.chiave_segreta);
        Button confermaChiave = findViewById(R.id.conferma_chiave);
        confermaChiave.setOnClickListener(v -> {
            if(chiaveSegreta.getText() != null){
                String chiave = chiaveSegreta.getText().toString();
                if(!chiave.trim().equals("")){
                    RansomwareActivity.chiaveSegreta = chiave;
                    ransomText.setVisibility(View.INVISIBLE);
                    chiaveSegreta.setVisibility(View.INVISIBLE);
                    confermaChiave.setVisibility(View.INVISIBLE);
                    matrix.setVisibility(View.VISIBLE);
                    matrix.setVideoPath("android.resource://" + getPackageName()+ "/" + R.raw.matrix);
                    matrix.start();
                    matrix.setOnCompletionListener(vm -> {
                        Toast.makeText(matrix.getContext(),"ATTACCO RANSOMWARE ESEGUITO!",Toast.LENGTH_LONG).show();
                        matrix.setVideoPath("android.resource://" + getPackageName()+ "/" + R.raw.pensieri_dopo_ransomware);
                        matrix.start();
                        matrix.setOnCompletionListener( in -> {
                            matrix.setVideoPath("android.resource://" + getPackageName()+ "/" + R.raw.video_intermendio);
                            matrix.start();
                            matrix.setOnCompletionListener( f -> {
                                if(ImpostazioniActivity.AR)
                                    startActivity(new Intent(RansomwareActivity.this,CameraActivity.class)
                                            .putExtra("countdown", "no"));
                                else
                                    startActivity(new Intent(RansomwareActivity.this,TestActivity.class)
                                            .putExtra("countdown", "no"));
                            });
                        });
                    });
                }
                else
                    Toast.makeText(RansomwareActivity.this, "CREA UNA CHIAVE SEGRETA!", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(RansomwareActivity.this, "CREA UNA CHIAVE SEGRETA!", Toast.LENGTH_SHORT).show();
        });
    }
}
