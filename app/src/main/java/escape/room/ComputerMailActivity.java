package escape.room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ComputerMailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_mail);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MainActivity.setLockLavagna(false);
        TextView testoMail = findViewById(R.id.mail);
        testoMail.setText(
                "PROFESSORE DISTRATTO E SUPERFICIALE:\n"
                +"Ma hai visto che trash che è il coordinatore autoritario?!\n"
                +"\nCOLLEGA:\n"
                +"Mamma mia! Hai proprio ragione! E grazie per avermi lasciato il suo account instagram "
                +"scritto sulla LAVAGNA!\n"
                +"\nPROFESSORE DISTRATTO E SUPERFICIALE:\n"
                +"Modestamente usare il CIFRARIO DI CESARE è stata una genialata! "
                +"Che poi è facilissimo da usare, basta modificare le lettere del proprio messaggio "
                +"sostituendole con le lettere che nell'alfabeto si trovano ad una certa distanza...\n"
                +"\nCOLLEGA:\n"
                +"Cesare era davvero uno stolto, ma come si fa ad utilizzare la chiave 3?!\n"
                +"Con la distanza 3 è troppo facile decifrare i messaggi: la I diventa F, la S diventa P... "
                +"Menomale che noi siamo geniali!"
        );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
    }
}