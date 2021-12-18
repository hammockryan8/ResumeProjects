package com.example.androidchess.piece;

import java.io.Serializable;
import java.util.ArrayList;

public class Bishop extends Piece implements Serializable {

    public Bishop(Piece[][] board, boolean color, int file, int rank) {
        this.board = board;
        this.color = color;
        this.file = file;
        this.rank = rank;
    }

    public void updateMoves() {
        moves = new ArrayList<>();
        for (int i = file + 1, j = rank + 1; i <= 7 && j <= 7; i++, j++) {
            if (board[i][j] == null) {
                moves.add(new Move(i, j, REGULAR_MOVE));
            } else {
                if (board[i][j].color != color) {
                    moves.add(new Move(i, j, CAPTURE));
                }
                break;
            }
        }
        for (int i = file + 1, j = rank - 1; i <= 7 && j >= 0; i++, j--) {
            if (board[i][j] == null) {
                moves.add(new Move(i, j, REGULAR_MOVE));
            } else {
                if (board[i][j].color != color) {
                    moves.add(new Move(i, j, CAPTURE));
                }
                break;
            }
        }
        for (int i = file - 1, j = rank + 1; i >= 0 && j <= 7; i--, j++) {
            if (board[i][j] == null) {
                moves.add(new Move(i, j, REGULAR_MOVE));
            } else {
                if (board[i][j].color != color) {
                    moves.add(new Move(i, j, CAPTURE));
                }
                break;
            }
        }
        for (int i = file - 1, j = rank - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == null) {
                moves.add(new Move(i, j, REGULAR_MOVE));
            } else {
                if (board[i][j].color != color) {
                    moves.add(new Move(i, j, CAPTURE));
                }
                break;
            }
        }
    }

    public void copyToBoard(Piece[][] board) {
        Bishop clone = new Bishop(board, color, file, rank);
        clone.moves = moves;
        board[file][rank] = clone;
    }

}
