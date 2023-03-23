package escape.room;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ComputerRegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_registro);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MainActivity.setLockLavagna(false);
        findViewById(R.id.elimina_colloquio).setOnClickListener(v -> {
            TextView colloquio = findViewById(R.id.colloquio);
            colloquio.setText("COLLOQUIO ELIMINATO CON SUCCESSO, HAI VINTO!");
        });
    }
}