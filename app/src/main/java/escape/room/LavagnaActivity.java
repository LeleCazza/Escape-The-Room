package escape.room;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LavagnaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lavagna);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(MainActivity.getLockLavagna())
            findViewById(R.id.cifrario).setVisibility(View.INVISIBLE);
        else
            findViewById(R.id.cifrario).setVisibility(View.VISIBLE);
    }
}
