package iut.android.titansmash;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import model.Hero;
import model.Util.SoundBox;

public class CreerJoueur extends AppCompatActivity {

    private int cptErreurs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Vue

        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getSupportActionBar().hide();

        setContentView(R.layout.activity_creer_joueur);

    }


    public void clickCreerJoueur(View view){

        Intent intent = new Intent(this, AfficherPartie.class);

        EditText editText = findViewById(R.id.editTextJoueur);

        if (editText.getText().length() < 3){
            if(cptErreurs > 0) {
                return;
            }
            LinearLayout linearLayout = findViewById(R.id.linearCreerHero);

            TextView text = new TextView(getApplicationContext());
            text.setText(R.string.erreurNomJoueur);
            text.setTextSize(10);
            text.setTextColor(getResources().getColor(R.color.rouge));
            text.setPadding(40,20,0,0);

            linearLayout.addView(text);
            cptErreurs++;
            return;
        }

        Hero h = new Hero(editText.getText().toString(), 1, R.drawable.hero);

        intent.putExtra("Hero", h);

        startActivity(intent);
    }
}
