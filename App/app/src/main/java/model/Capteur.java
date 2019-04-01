package model;

import android.content.res.Resources;
import android.hardware.SensorListener;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import iut.android.titansmash.AfficherPartie;
import iut.android.titansmash.R;

public class Capteur extends AppCompatActivity implements SensorListener {

    private CapteurListener listener;
    private final int sensi = 10;


    public Capteur() {
    }


    public void setListener(CapteurListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSensorChanged(int sensor, float[] values) {

        float x = values[0];
        float y = values[1];
        float z = values[2];
        listener.onValueChanged(x,y,z,sensi);
    }

    @Override
    public void onAccuracyChanged(int sensor, int accuracy) {

    }

}
