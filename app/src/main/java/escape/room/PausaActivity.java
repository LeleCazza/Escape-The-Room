package escape.room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PausaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pausa);
    }

    @Override
    protected void onStart() {
        super.onStart();
        VideoView dialogoMamma = findViewById(R.id.videoView_dialogoMamma);
        dialogoMamma.setVisibility(View.INVISIBLE);
        TextView text = findViewById(R.id.textview_stop);
        text.setText("STOP! \n IL PROF. E' TORNATO! \n NON CI VOLEVA! PECCATO, NON PUOI CONTINUARE...");
        Button riprendi = findViewById(R.id.button_riprendi);
        riprendi.setOnClickListener(c -> {
            text.setVisibility(View.INVISIBLE);
            riprendi.setVisibility(View.INVISIBLE);
            dialogoMamma.setVisibility(View.VISIBLE);
            dialogoMamma.setVideoPath("android.resource://" + getPackageName()+ "/" + R.raw.dialogo_mamma);
            dialogoMamma.start();
            dialogoMamma.setOnCompletionListener(v -> {
                startActivity(new Intent(PausaActivity.this,RansomwareActivity.class));
            });
        });
    }

    @Override
    public void onBackPressed() {
        //tasto indietro disabilitato
    }
}
