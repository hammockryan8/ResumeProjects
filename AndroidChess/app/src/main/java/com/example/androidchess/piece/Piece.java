package com.example.androidchess.piece;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Piece implements Serializable {

    static final int REGULAR_MOVE = 0;
    static final int CAPTURE = 1;
    static final int QUEENSIDE_CASTLE = 2;
    static final int KINGSIDE_CASTLE = 3;
    static final int PROMOTION = 4;
    static final int PROMOTION_CAPTURE = 5;
    static final int PAWN_FIRST_MOVE = 6;
    static final int EN_PASSANT = 7;

    public Piece[][] board;
    public boolean color;
    public int file;
    public int rank;
    public ArrayList<Move> moves;

    public abstract void updateMoves();
    public abstract void copyToBoard(Piece[][] board);

}
