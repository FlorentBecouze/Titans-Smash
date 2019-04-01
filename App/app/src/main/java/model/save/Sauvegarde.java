package model.save;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

import model.Monstre;
import model.Partie;

public class Sauvegarde {

    private FileOutputStream outputStream;


    public Sauvegarde(FileOutputStream file){

        outputStream = file;

    }


    public void save(Partie p){
        try{
            ObjectOutputStream objectOut = new ObjectOutputStream(outputStream);
            objectOut.writeObject(p);
            objectOut.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
