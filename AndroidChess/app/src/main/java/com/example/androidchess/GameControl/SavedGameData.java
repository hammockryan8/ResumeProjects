package com.example.androidchess.GameControl;
import com.example.androidchess.Model.*;

import java.util.ArrayList;

public class SavedGameData {

    String date;
    String title;
    ArrayList<Piece[][]>boardStates = new ArrayList<Piece[][]>();

    public SavedGameData(String date, String title, ArrayList<Piece[][]>boardStates){
        this.date = date;
        this.title = title;
        this.boardStates = boardStates;

    }

}
