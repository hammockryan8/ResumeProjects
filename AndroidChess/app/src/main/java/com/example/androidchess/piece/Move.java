package com.example.androidchess.piece;

import java.io.Serializable;

public class Move implements Serializable {

    public int file;
    public int rank;
    public int type;

    public Move(int file, int rank, int type) {
        this.file = file;
        this.rank = rank;
        this.type = type;
    }

}
