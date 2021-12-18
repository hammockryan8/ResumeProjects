package com.example.androidchess.piece;

import java.io.Serializable;
import java.util.ArrayList;

public class Rook extends Piece implements Serializable {

    public boolean canCastle = true;

    public Rook(Piece[][] board, boolean color, int file, int rank) {
        this.board = board;
        this.color = color;
        this.file = file;
        this.rank = rank;
    }

    public void updateMoves() {
        moves = new ArrayList<>();
        for (int i = rank + 1; i <= 7; i++) {
            if (board[file][i] == null) {
                moves.add(new Move(file, i, REGULAR_MOVE));
            } else {
                if (board[file][i].color != color) {
                    moves.add(new Move(file, i, CAPTURE));
                }
                break;
            }
        }
        for (int i = rank - 1; i >= 0; i--) {
            if (board[file][i] == null) {
                moves.add(new Move(file, i, REGULAR_MOVE));
            } else {
                if (board[file][i].color != color) {
                    moves.add(new Move(file, i, CAPTURE));
                }
                break;
            }
        }
        for (int i = file + 1; i <= 7; i++) {
            if (board[i][rank] == null) {
                moves.add(new Move(i, rank, REGULAR_MOVE));
            } else {
                if (board[i][rank].color != color) {
                    moves.add(new Move(i, rank, CAPTURE));
                }
                break;
            }
        }
        for (int i = file - 1; i >= 0; i--) {
            if (board[i][rank] == null) {
                moves.add(new Move(i, rank, REGULAR_MOVE));
            } else {
                if (board[i][rank].color != color) {
                    moves.add(new Move(i, rank, CAPTURE));
                }
                break;
            }
        }
    }

    public void copyToBoard(Piece[][] board) {
        Rook clone = new Rook(board, color, file, rank);
        clone.moves = moves;
        clone.canCastle = canCastle;
        board[file][rank] = clone;
    }

}
