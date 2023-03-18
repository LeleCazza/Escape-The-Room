package escape.room;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ComputerLoginActivity extends AppCompatActivity {

    private String icona = "login";
    private static final String username  = "superprof";
    private static final String password  = "vialavello90mila";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_login);
        Bundle extras = getIntent().getExtras();
        if(extras != null)
            icona = extras.getString("icona");
    }

    @Override
    protected void onStart() {
        super.onStart();
        findViewById(R.id.computer_login).setOnClickListener(v -> {
            TextView username = findViewById(R.id.computer_username);
            TextView password = findViewById(R.id.computer_password);
            switch (icona){
                case "login":
                    if(login(username,password)){
                        Toast.makeText(this, "LOGIN EFFETTUATO CON SUCCESSO", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this,ComputerDesktopActivity.class));
                    }
                    else
                        Toast.makeText(this,"USERNAME O PASSWORD ERRATI", Toast.LENGTH_SHORT).show();
                    break;
                case "registro":
                    Toast.makeText(this,"USERNAME O PASSWORD ERRATI", Toast.LENGTH_SHORT).show();
                    break;
                case "mail":
                    if(login(username, password)){
                        Toast.makeText(this, "LOGIN EFFETTUATO CON SUCCESSO", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this,ComputerMailActivity.class));
                    }
                    else
                        Toast.makeText(this,"USERNAME O PASSWORD ERRATI", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    private boolean login(TextView username, TextView password){
        return username.getText().toString().trim().equals(ComputerLoginActivity.username)
                && password.getText().toString().trim().equals(ComputerLoginActivity.password);
    }
}