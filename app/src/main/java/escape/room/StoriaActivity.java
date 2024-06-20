package escape.room;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StoriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storia);
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView storia = findViewById(R.id.textView_storia);
        storia.setText(
                "Ti trovi nella tua aula di scuola e sì, ti è andata male. Non solo sei stato punito " +
                        "per quello che hai fatto e ora ti tocca " +
                        "pulire l'aula, ma è stato fissato pure un colloquio con i tuoi genitori! " +
                        "Hai un'unica via d'uscita, cerca di approfittare dei momenti di distrazione del " +
                        "prof. che ti sta sorvegliando e tenta in tutti i modi di accedere all'account della Coordinatrice Autoritaria. " +
                        "So che sembra una pessima idea, ma ascolta, come sai il Dirigente Scolastico è molto influenzato " +
                        "dall'autorità della coordinatrice. Ti basterà far finta di essere lei e inviargli una " +
                        "mail di phishing per ottenere le credenziali del registro ed eliminare il colloquio! " +
                        "Certo, l'aula ti toccherà pulirla lo stesso, ma in questo modo sarai salvo. " +
                        "Appena puoi datti da fare, il Professore Distratto avrà sicuramente qualche informazione che potrai " +
                        "sfruttare per completare il piano. Buona fortuna.");
    }

}
