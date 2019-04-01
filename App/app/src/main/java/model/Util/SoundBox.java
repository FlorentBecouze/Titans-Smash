package model.Util;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.MediaStore;

import java.io.File;

import iut.android.titansmash.R;

public class SoundBox {

    private static MediaPlayer mediaMusique;
    private static MediaPlayer mediaBruitage;

    // Musique
    private static void playBackground(Context context, int music) {

        // "If" utile pour empêcher de relancer la musique à chaque fois que l'on tourne le téléphone
        if(mediaMusique == null) {
            mediaMusique = MediaPlayer.create(context, music);
            mediaMusique.setLooping(true);
        }
        mediaMusique.start();
    }


    public static void playMusicBackground(Context context) {
        playBackground(context, R.raw.music_tap_titans);
    }


    public static void stopMusicBackground() {
        // Pour ne pas arrêter un MediaPlayer qui n'a jamais été lancé.
        if(mediaMusique != null) {
            mediaMusique.stop();
            mediaMusique.release();
            mediaMusique = null;
        }
    }




    // Bruitages
    private static void playBruitage(Context context, int bruit) {

        if(mediaBruitage == null) {
            mediaBruitage = MediaPlayer.create(context, bruit);
        }

        mediaBruitage.start();

        mediaBruitage = null;
    }


    public static void playBruitageSabre(Context context) {
        playBruitage(context, R.raw.sabre);
    }

    public static void playBruitageGrognement(Context context) {
        playBruitage(context, R.raw.grognement);
    }

    public static void playBruitagePiece(Context context) {
        playBruitage(context, R.raw.son_piece);
    }


}
