package model;

import android.media.Image;

import java.io.Serializable;

public class Monstre implements Serializable {

    private String nomM;
    private int vie;
    private int imageM;
    private int argentDrop;


    public Monstre(String nomM, int vie, int imageM, int argentDrop){
        this.nomM = nomM;
        this.vie = vie;
        this.imageM = imageM;
        this.argentDrop = argentDrop;
    }

    // Nom
    public String getNomM() {
        return nomM;
    }


    // Vie
    public int getVie() {
        return vie;
    }

    public void setVie(int vie) {
        this.vie = vie;
    }

    public void suppVie(int vie) {
        this.vie -= vie;
    }


    // Image
    public int getLienImageM() {
        return imageM;
    }


    // Argent
    public int getArgentDrop() {
        return argentDrop;
    }

    public void setArgentDrop(int argentDrop) {
        this.argentDrop = argentDrop;
    }

}
