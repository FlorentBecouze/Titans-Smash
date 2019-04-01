package iut.android.titansmash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.Monstre;
import model.Partie;
import model.STUB.StubMonstres;
import model.Util.SoundBox;
import model.save.Chargement;

public class MainActivity extends AppCompatActivity {

    private boolean boolMusique;
    private File file;
    public static String fileString = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        file = new File(getFilesDir(),"sauvegarde.bin");
        fileString = file.toString().split("/", 7)[6];

        // Vue
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getSupportActionBar().hide();

        setContentView(R.layout.activity_main);


        SharedPreferences sharedPref = getSharedPreferences("userOptions",MODE_PRIVATE);
        boolMusique = sharedPref.getBoolean("boolMusique",true);

        if(boolMusique){
            SoundBox.playMusicBackground(this);
        }

    }

    // Explications des différences entre les méthodes "onClick" (dans la vue) et
    // "setOnClickListener" (dans le onCreate), sur 1 page Wiki de GitLab

    public void clickPlay(View view){
        // Si le fichier de sauvegarde existe
        if(getBaseContext().getFileStreamPath(fileString).exists()){
            Intent intent = new Intent(this, AfficherPartie.class);
            try {
                FileInputStream inputStream = openFileInput(fileString);
                Chargement charger = new Chargement(inputStream);
                Partie partie = charger.chargement();

                intent.putExtra("partie", partie);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, CreerJoueur.class);
            startActivity(intent);
        }

    }

    public void clickRules(View view) {
        Intent intent = new Intent(this, Regles.class);
        startActivity(intent);
    }

    public void clickOptions(View view) {
        Intent intent = new Intent(this, Options.class);
        startActivity(intent);
    }

}
