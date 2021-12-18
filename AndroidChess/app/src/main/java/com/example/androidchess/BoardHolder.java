package com.example.androidchess;

import com.example.androidchess.piece.Piece;

import java.io.Serializable;

public class BoardHolder implements Serializable {

    Piece[][] board;

    public BoardHolder(Piece[][] board) {
        this.board = board;
    }

}