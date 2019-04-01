package iut.android.titansmash;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;

import model.Util.SoundBox;


public class Options extends AppCompatActivity {

    private Switch switchMusique;
    private Switch switchBruitage;
    private boolean boolMusique;
    private boolean boolBruit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Vue
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getSupportActionBar().hide();

        setContentView(R.layout.activity_options);

        // Récupération des Switch de la vue
        switchMusique = findViewById(R.id.switcMusique);
        switchBruitage = findViewById(R.id.switchBruitage);

        SharedPreferences sharedPref = getSharedPreferences("userOptions",MODE_PRIVATE);
        boolMusique = sharedPref.getBoolean("boolMusique",true);
        boolBruit = sharedPref.getBoolean("boolBruit",true);

        switchMusique.setChecked(boolMusique);
        switchBruitage.setChecked(boolBruit);

        switchMusique.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    boolMusique = true;
                }
                else{
                    boolMusique = false;
                }
            }
        });
        switchBruitage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    boolBruit = true;
                }
                else{
                    boolBruit = false;
                }
            }
        });


    }

    @Override
    protected void onPause(){
        super.onPause();
        if(!boolMusique){
            SoundBox.stopMusicBackground();
        }
        else{
            SoundBox.playMusicBackground(this);
        }

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("userOptions",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("boolMusique",boolMusique);
        editor.putBoolean("boolBruit",boolBruit);

        editor.apply();
    }


}
