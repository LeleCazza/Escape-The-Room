package escape.room;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Switch;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ImpostazioniActivity extends AppCompatActivity {

    public static boolean AR = false;
    public static boolean videoIntro = true;
    public static long tempo = 20 * 60 * 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impostazioni);
    }

    @Override
    protected void onStart() {
        super.onStart();
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchAR = findViewById(R.id.switch_ar);
        switchAR.setOnCheckedChangeListener((buttonView, isChecked) -> AR = isChecked);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchVideoIntro = findViewById(R.id.switch_video_intro);
        switchVideoIntro.setOnCheckedChangeListener((buttonView, isChecked) -> videoIntro = isChecked);
        SeekBar seekBarTempo = findViewById(R.id.seekBar_tempo);
        seekBarTempo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tempo = (long) (progress+1) * 60 * 1000;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar){}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar){}
        });
    }
}