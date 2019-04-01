package model.STUB;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

import iut.android.titansmash.R;
import model.Monstre;

public class StubMonstres {

    public StubMonstres() {

    }

    public List<Monstre> creationListeMonstres(Resources resources) {
        List<Monstre> listeMonstres = new ArrayList<>();

        listeMonstres.add(new Monstre(resources.getString(R.string.Smeagol), 15, R.drawable.smeagol, 15));
        listeMonstres.add(new Monstre(resources.getString(R.string.EyeToad), 10, R.drawable.eye_toad, 10));
        listeMonstres.add(new Monstre(resources.getString(R.string.MonsterMud), 5, R.drawable.monster_mud, 5));
        listeMonstres.add(new Monstre(resources.getString(R.string.Knight), 20, R.drawable.knight, 20));
        listeMonstres.add(new Monstre(resources.getString(R.string.BadStone),20,R.drawable.bad_stone, 20));
        listeMonstres.add(new Monstre(resources.getString(R.string.Cerbere_de_feu), 30, R.drawable.cerbere_de_feu, 30));
        listeMonstres.add(new Monstre(resources.getString(R.string.Cerbere_des_flammes), 40, R.drawable.cerbere_des_flammes, 40));
        listeMonstres.add(new Monstre(resources.getString(R.string.DinoColossal), 60, R.drawable.dino_colossal, 60));
        listeMonstres.add(new Monstre(resources.getString(R.string.Esprit_des_tenebres), 20, R.drawable.esprit_des_tenebres, 20));
        listeMonstres.add(new Monstre(resources.getString(R.string.GiantFrost), 50, R.drawable.giant_frost, 50));
        listeMonstres.add(new Monstre(resources.getString(R.string.Hyene_des_enfers), 20, R.drawable.hyena_of_the_hell, 20));
        listeMonstres.add(new Monstre(resources.getString(R.string.Roi_de_la_foret), 60, R.drawable.roi_de_la_foret, 60));
        listeMonstres.add(new Monstre(resources.getString(R.string.Lezard_de_venin), 25, R.drawable.venom_lizard, 25));
        listeMonstres.add(new Monstre(resources.getString(R.string.Wendigo), 10, R.drawable.wendigo, 10));

        return listeMonstres;
    }




}
