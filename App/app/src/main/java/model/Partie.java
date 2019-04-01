package model;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import iut.android.titansmash.R;
import model.STUB.StubMonstres;

public class Partie extends AppCompatActivity implements Serializable {

    private List<Monstre> listeMonstres;
    private Hero hero;

    public Partie(Hero hero, Resources resources){
        listeMonstres = new StubMonstres().creationListeMonstres(resources);
        this.hero = hero;
    }

    public Hero getHero() {
        return hero;
    }

    public List<Monstre> getListeMonstres() {
        return listeMonstres;
    }


    public void setListeMonstres(List<Monstre> listeMonstres) {
        this.listeMonstres = listeMonstres;
    }

    public List<Monstre> ameliorerMonstres(){
        for(Monstre m : listeMonstres){
            m.setVie(m.getVie()*2);
            m.setArgentDrop(m.getArgentDrop()*2);
        }
        return listeMonstres;
    }

    public void upgradeHero(int valueBouton) {

        hero.suppArgent(valueBouton);
        hero.addDegats(1);
    }


}
