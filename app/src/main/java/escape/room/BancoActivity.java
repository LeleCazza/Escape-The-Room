package escape.room;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import escape.room.util.Abilita;

public class BancoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banco);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Abilita.CERCATORE_DI_INFORMAZIONI_AVANZATO_2 = true;
    }
}
