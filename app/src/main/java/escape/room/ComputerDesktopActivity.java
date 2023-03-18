package escape.room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ComputerDesktopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_desktop);
    }

    @Override
    protected void onStart() {
        super.onStart();
        findViewById(R.id.computer_registro).setOnClickListener(v -> {
                startActivity(new Intent(this,ComputerLoginActivity.class)
                        .putExtra("icona","registro"));
        });
        findViewById(R.id.computer_mail).setOnClickListener(v -> {
            startActivity(new Intent(this,ComputerLoginActivity.class)
                    .putExtra("icona","mail"));
        });
    }
}