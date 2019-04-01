package model;

import java.io.Serializable;

public class Hero implements Serializable {

    private String nomH;
    private int degats;
    private int argent;
    private int lienImageH;
    private int monstresTues;
    private int valueBoutonUpgradeHero = 100;

    public Hero(String nomH, int degats, int imageH){
        this.nomH = nomH;
        this.degats = degats;
        this.argent = 0;
        this.lienImageH = imageH;
        this.monstresTues = 0;
    }


    // Nom
    public String getNomH() {
        return nomH;
    }



    // Dégats
    public int getDegats() {
        return degats;
    }

    public void addDegats(int degats) {
        this.degats += degats;
    }


    // Argent
    public int getArgent() {
        return argent;
    }

    public void addArgent(int argent) {
        this.argent += argent;
    }

    public void suppArgent(int argent) {
        this.argent -= argent;
    }


    // Image
    public int getLienImageH() {
        return lienImageH;
    }


    // Monstres tués

    public int getMonstresTues() {
        return monstresTues;
    }

    public void addMonstresTues(int monstresTues) {
        this.monstresTues += monstresTues;
    }



    // Valeur du bouton d'amélioration
    public int getValueBoutonUpgradeHero() {
        return valueBoutonUpgradeHero;
    }

    public void setValueBoutonUpgradeHero(int value) {
        this.valueBoutonUpgradeHero = value;
    }

    public void addValueBoutonUpgradeHero(int value) {
        this.valueBoutonUpgradeHero += value;
    }
}

