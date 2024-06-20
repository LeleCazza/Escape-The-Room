package escape.room;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import escape.room.util.Abilita;

public class ComputerLoginActivity extends AppCompatActivity {

    private String icona = "login";
    private static final String username  = "superprof";
    private static final String password  = "pwd24dacambiare";
    private static final String usernameAutoritario  = "coordinatrice";
    private static final String passwordAutoritario  = "bimba";
    private static final String usernameDirigente  = "megadirettore";
    private static final String passwordDirigente  = "GLULJHQWH";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if(extras != null)
            icona = extras.getString("icona");
        if(Abilita.CERCATORE_DI_INFORMAZIONI)
            setContentView(R.layout.activity_computer_login);
        else
            setContentView(R.layout.activity_error);
        if ("mail".equals(icona)) {
            findViewById(R.id.sfondo_login).setBackgroundColor(Color.GRAY);
            ImageView icona = findViewById(R.id.icona_login);
            icona.setImageResource(R.drawable.icona_mail);
        }
        if ("registro".equals(icona)) {
            findViewById(R.id.sfondo_login).setBackgroundColor(Color.GRAY);
            ImageView icona = findViewById(R.id.icona_login);
            icona.setImageResource(R.drawable.icona_registro);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(Abilita.CERCATORE_DI_INFORMAZIONI) {
            findViewById(R.id.computer_login).setOnClickListener(v -> {
                TextView username = findViewById(R.id.computer_username);
                TextView password = findViewById(R.id.computer_password);
                switch (icona) {
                    case "login":
                        if (login(username, password)) {
                            startActivity(new Intent(this, ComputerDesktopActivity.class)
                                    .putExtra("logTo", "profDistratto"));
                        } else if (login_autoritario(username, password)) {
                            startActivity(new Intent(this, ComputerDesktopActivity.class)
                                    .putExtra("logTo", "profAutoritario"));
                        }
                        else
                            Toast.makeText(this, "USERNAME O PASSWORD ERRATI", Toast.LENGTH_SHORT).show();
                        break;
                    case "registro":
                        if (login_dirigente(username, password)) {
                            startActivity(new Intent(this, ComputerRegistroActivity.class));
                        } else
                            Toast.makeText(this, "USERNAME O PASSWORD ERRATI", Toast.LENGTH_SHORT).show();
                        break;
                    case "mail":
                        if (login(username, password)) {
                            startActivity(new Intent(this, ComputerMailActivity.class)
                                    .putExtra("logTo", "profDistratto"));
                        }
                        else
                            Toast.makeText(this, "USERNAME O PASSWORD ERRATI", Toast.LENGTH_SHORT).show();
                        }
            });
        }
        else{
            TextView textView = findViewById(R.id.abilita_mancante);
            textView.setText("PER POTER ACCEDERE AL PC DEVI POSSEDERE L'ABILITÀ CERCATORE DI INFORMAZIONI");
        }
    }

    private boolean login(TextView username, TextView password){
        return username.getText().toString().trim().equals(ComputerLoginActivity.username)
                && password.getText().toString().trim().equals(ComputerLoginActivity.password);
    }

    private boolean login_autoritario(TextView username, TextView password){
        return username.getText().toString().trim().equals(ComputerLoginActivity.usernameAutoritario)
                && password.getText().toString().trim().equalsIgnoreCase(ComputerLoginActivity.passwordAutoritario);
    }

    private boolean login_dirigente(TextView username, TextView password){
        return username.getText().toString().trim().equals(ComputerLoginActivity.usernameDirigente)
                && password.getText().toString().trim().equals(ComputerLoginActivity.passwordDirigente);
    }
}