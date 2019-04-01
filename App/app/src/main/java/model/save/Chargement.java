package model.save;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;

import model.Hero;
import model.Partie;

public class Chargement {

    private FileInputStream inputStream;


    public Chargement(FileInputStream file){

        inputStream = file;

    }


    public Partie chargement(){
        try{
            ObjectInputStream objectIn = new ObjectInputStream(inputStream);
            Partie p = (Partie) objectIn.readObject();
            objectIn.close();
            return p;
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }

}
