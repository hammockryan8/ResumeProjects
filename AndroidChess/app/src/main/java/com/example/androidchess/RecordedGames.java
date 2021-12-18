package com.example.androidchess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class RecordedGames implements Serializable {

    public static String storeDir = "";
    public static final String storeFile = "RecordedGames.dat";
    static final long serialVersionUID = 1L;
    public ArrayList<ChessGame> rg;

    public RecordedGames() {
        rg = new ArrayList<>();
    }

    public static void writeList(RecordedGames rg) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(storeDir + File.separator + storeFile));
        oos.writeObject(rg);
    }

    public static RecordedGames readList() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(storeDir + File.separator + storeFile));
        return (RecordedGames) ois.readObject();
    }

    public static void main(String[] args) throws IOException {
        RecordedGames rg = new RecordedGames();
        writeList(rg);
    }
}
