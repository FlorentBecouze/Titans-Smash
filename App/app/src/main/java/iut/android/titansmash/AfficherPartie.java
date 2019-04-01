package iut.android.titansmash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

import model.Capteur;
import model.CapteurListener;
import model.Hero;
import model.Monstre;
import model.Partie;
import model.Util.SoundBox;
import model.save.Sauvegarde;

public class AfficherPartie extends AppCompatActivity {

    private Hero hero;
    private Partie partie;
    private ConstraintLayout layoutAffJoueur;
    private TextView textViewNomHero;
    private TextView textViewCptMonstre;
    private ProgressBar progressVie;
    private TextView textViewVieMonstre;
    private ImageView imageMonstre;
    private ImageView imageHero;
    private TextView textViewNomMonstre;
    private Button boutonUpgradeHero;
    private Monstre monstreActuel;
    private Random r = new Random();
    private int vieTotalM;
    private Capteur accelerometre;
    private boolean boolMusique;
    private boolean boolBruit;
    private Animation shake;
    private String fileString = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Vue
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getSupportActionBar().hide();

        // Affichage de la vue
        setContentView(R.layout.activity_partie);



        // Création de la Partie de jeu
        Intent intent = getIntent();

        // Si le fichier de sauvegarde existe
        if(getBaseContext().getFileStreamPath(MainActivity.fileString).exists()){

            partie = (Partie) intent.getSerializableExtra("partie");
            fileString = MainActivity.fileString;
        }
        else{
            // Récupération du Hero passé depuis l'activity CreerJoueur
            hero = (Hero) intent.getSerializableExtra("Hero");
            partie = new Partie(hero, getResources());

            // Il faut donner un fichier de sauvegarde quand celui ci n'existe pas
            // car sinon dans le onPause, on essayera de sauvegarder dans null
            fileString = MainActivity.fileString;
        }


        recuperationVue();

        // Le chargement de l'image du Hero ne se fait qu'une seule fois,
        // donc ne pas mettre le setImageResource dans miseAJour()
        // On doit passer par la 'partie' car si on prend 'hero' (celui récupérer via le Intent),
        // on risque de ne pas l'avoir si le fichier de sauvegarde existe
        imageHero.setImageResource(partie.getHero().getLienImageH());


        // Récupération aléatoire d'un Monstre dans la liste de Monstres
        monstreActuel = partie.getListeMonstres().get(r.nextInt(partie.getListeMonstres().size()));
        vieTotalM = monstreActuel.getVie();
        miseAJourVue();



        // Code qui initialise l'accéléromètre (ne s'affiche pas dans le TextView)
        accelerometre = new Capteur();
        accelerometre.setListener(new CapteurListener() {
            @Override
            public void onValueChanged(float x, float y, float z, int sensi) {
                if(x > sensi | y > sensi | z > sensi){
                    infligerDegats();
                }
            }
        });
        SensorManager m = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        m.registerListener(accelerometre,SensorManager.SENSOR_ACCELEROMETER, SensorManager.SENSOR_DELAY_UI);


        // Pour permettre le clique sur tout l'écran on ajoute un setOnTouchListener sur le layout principal (activity_partie)
        layoutAffJoueur.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                            infligerDegats();
                        break;

                    default:
                }
                return true;
            }
        });


        // Récupération des options
        SharedPreferences sharedPref = getSharedPreferences("userOptions",MODE_PRIVATE);
        boolMusique = sharedPref.getBoolean("boolMusique",true);
        boolBruit = sharedPref.getBoolean("boolBruit",true);

        if(boolMusique){
            SoundBox.playMusicBackground(this);
        }
        else{
            SoundBox.stopMusicBackground();
        }


        // Création de l'animation pour la mort des Monstre
        shake = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
        setAnimationMortMonstre();

    }


    // Méthode permettant la récupération de tous les éléments de la vue
    private void recuperationVue() {
        // Layout globale de la vue
        layoutAffJoueur = findViewById(R.id.layoutAffJoueur);

        // Nom donné par le joueur
        textViewNomHero = findViewById(R.id.nomHero);

        // Le TextView contenant le nombre de monstre tués
        textViewCptMonstre = findViewById(R.id.cptMonstre);

        // La ProgressBar représentant la vie du Monstre
        progressVie = findViewById(R.id.progressVie);

        // Le TextView contenant la vie du Monstre
        textViewVieMonstre = findViewById(R.id.vieMonstre);

        // L'ImageView contenant l'image du Monstre
        imageMonstre = findViewById(R.id.imageMonstre);

        // L'ImageView contenant l'image du Hero
        imageHero = findViewById(R.id.imageHero);

        // Le TextView contenant le nom du Monstre
        textViewNomMonstre = findViewById(R.id.nomMonstre);

        // Le Button permettant l'amélioration du Hero
        boutonUpgradeHero = findViewById(R.id.boutonUpgradeHero);

        boutonUpgradeHero.setText(getResources().getString(R.string.upgradeHero) + "" + partie.getHero().getValueBoutonUpgradeHero() + " " + getResources().getString(R.string.pièces));

        // Utile lors du rechargement de la vue
        if(partie.getHero().getArgent() < partie.getHero().getValueBoutonUpgradeHero()) {
            boutonUpgradeHero.setEnabled(false);
        }
    }


    // Méthode permettant de gérer le clique sur l'écran (mise à jour de la vie du Monstre, et si
    // besoin incrémentation de l'argent, des DPS et changement de Monstre)

    public void infligerDegats() {
        if(boolBruit){
            SoundBox.playBruitageSabre(this);
        }

        System.out.println(partie.getHero().getNomH() + " / " + monstreActuel.getNomM() + " / " + monstreActuel.getVie() + " / " + textViewNomHero.getText());

        // Décrémentation de la vie du Monstre, des dégats actuels du Hero
        monstreActuel.suppVie(partie.getHero().getDegats());

        if(monstreActuel.getVie() < 0){
            monstreActuel.setVie(0);
        }
        progressVie.setProgress(monstreActuel.getVie());
        textViewVieMonstre.setText(monstreActuel.getVie() + " / " + vieTotalM);

        // Si le Monstre est mort
        if(monstreActuel.getVie() <= 0){
            if(boolBruit){
                SoundBox.playBruitageGrognement(this);
            }

            imageMonstre.startAnimation(shake);

        }
    }


    // Méthode permettant la mise à jour de la vue
    // (affichage des changements pour le Hero et le nombre de Monstres tués
    // récupération d'un nouveau Monstre, ProgressBar pleine, changement du Monstre sur la vue)
    public void miseAJourVue() {
        // Affichage des éléments du Hero récupéré
        textViewNomHero.setText(partie.getHero().getNomH() + " : " + partie.getHero().getArgent() + " " + getResources().getString(R.string.pièces) + "  DPS : " + partie.getHero().getDegats());

        // Affichage du nombre de monstres tués
        textViewCptMonstre.setText(partie.getHero().getMonstresTues() + " " + getResources().getString(R.string.cptMonstre));


        // Affichage de la vie du Monstre (ProgressBar + TextView)
        progressVie.setMax(vieTotalM);
        progressVie.setProgress(monstreActuel.getVie());
        textViewVieMonstre.setText(monstreActuel.getVie() + " / " + vieTotalM);

        // Affichage de l'image du Monstre
        imageMonstre.setImageResource(monstreActuel.getLienImageM());

        // Affichage du nom du Monstre
        textViewNomMonstre.setText(monstreActuel.getNomM());
    }





    // Méthode permettant l'amélioration du Hero lors d'un clique sur le bouton
    public void cliqueUpgradeHero(View view) {
        if(boolBruit) {
            SoundBox.playBruitagePiece(this);
        }

        partie.upgradeHero(partie.getHero().getValueBoutonUpgradeHero());
        textViewNomHero.setText(partie.getHero().getNomH() + " : " + partie.getHero().getArgent() + " " + getResources().getString(R.string.pièces) + "  DPS : " + partie.getHero().getDegats());
        partie.getHero().addValueBoutonUpgradeHero(100);
        boutonUpgradeHero.setText(getResources().getString(R.string.upgradeHero) + "" + partie.getHero().getValueBoutonUpgradeHero() + " " + getResources().getString(R.string.pièces));

        if(partie.getHero().getArgent() < partie.getHero().getValueBoutonUpgradeHero()) {
            boutonUpgradeHero.setEnabled(false);
        }
    }




    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putSerializable("partie", partie);
        savedInstanceState.putInt("valueBoutonUpgradeHero", partie.getHero().getValueBoutonUpgradeHero());
        savedInstanceState.putSerializable("monstreActuel", monstreActuel);
        savedInstanceState.putSerializable("listeMonstres", (Serializable) partie.getListeMonstres());
        savedInstanceState.putInt("vieTotalM", vieTotalM);
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        partie = (Partie) savedInstanceState.getSerializable("partie");
        partie.getHero().setValueBoutonUpgradeHero(savedInstanceState.getInt("valueBoutonUpgradeHero"));
        monstreActuel = (Monstre) savedInstanceState.getSerializable("monstreActuel");
        partie.setListeMonstres((List<Monstre>) savedInstanceState.getSerializable("listeMonstres"));
        vieTotalM = savedInstanceState.getInt("vieTotalM");

        miseAJourVue();
    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            if(fileString != null){
                deleteFile(fileString);
            }

            FileOutputStream outputStream = openFileOutput(fileString, MODE_PRIVATE);

            Sauvegarde sauvegarde = new Sauvegarde(outputStream);
            sauvegarde.save(partie);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }




    private void setAnimationMortMonstre() {
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                monstreActuel.setVie(vieTotalM); // On remet la vie du monstre actuel à la normale pour pouvoir le réutiliser
                partie.getHero().addArgent(monstreActuel.getArgentDrop());
                partie.getHero().addMonstresTues(1);

                // If permettant de vérifier si le joueur a l'argent pour améliorer le Hero ou pas
                if(partie.getHero().getArgent() >= partie.getHero().getValueBoutonUpgradeHero()) {
                    boutonUpgradeHero.setEnabled(true);
                }


                // Condition : si le nombre de monstres tués est un multiple de la taille de la liste et si monstreTues different de 0
                // pour que la première condition ne plante pas à cause de l'opération 0%0 = 0
                // If permettant l'amélioration des Monstres
                if((partie.getHero().getMonstresTues() % partie.getListeMonstres().size()) == 0 && partie.getHero().getMonstresTues()!=0) {
                    partie.setListeMonstres(partie.ameliorerMonstres());
                }
                // Récupération aléatoire d'un Monstre dans la liste de Monstres
                monstreActuel = partie.getListeMonstres().get(r.nextInt(partie.getListeMonstres().size()));
                vieTotalM = monstreActuel.getVie();

                miseAJourVue();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
