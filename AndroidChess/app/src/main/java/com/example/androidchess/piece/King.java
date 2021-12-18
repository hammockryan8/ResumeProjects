package com.example.androidchess.piece;

import java.io.Serializable;
import java.util.ArrayList;

public class King extends Piece implements Serializable {

    public boolean canCastle = true;

    public King (Piece[][] board, boolean color, int file, int rank) {
        this.board = board;
        this.color = color;
        this.file = file;
        this.rank = rank;
    }

    public void updateMoves() {
        moves = new ArrayList<>();
        for (int i = file - 1; i <= file + 1; i++) {
            for (int j = rank - 1; j <= rank + 1; j++) {
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    continue;
                }
                if (board[i][j] == null) {
                    moves.add(new Move(i, j, REGULAR_MOVE));
                } else if (board[i][j].color != color) {
                    moves.add(new Move(i, j, CAPTURE));
                }
            }
        }
        if (canCastle) {
            if (board[0][rank] instanceof Rook) {
                if (board[0][rank].color == color && ((Rook) board[0][rank]).canCastle) {
                    if (board[1][rank] == null && board[2][rank] == null && board[3][rank] == null) {
                        moves.add(new Move(2, rank, QUEENSIDE_CASTLE));
                    }
                }
            }
            if (board[7][rank] instanceof Rook) {
                if (board[7][rank].color == color && ((Rook) board[7][rank]).canCastle) {
                    if (board[6][rank] == null && board[5][rank] == null) {
                        moves.add(new Move(6, rank, KINGSIDE_CASTLE));
                    }
                }
            }
        }
        System.out.print(color + " king moves: ");
        for (Move m : moves) {
            System.out.print("[" + m.file + "," + m.rank + "] ");
        }
        System.out.println();
    }

    public void copyToBoard(Piece[][] board) {
        King clone = new King(board, color, file, rank);
        clone.moves = moves;
        clone.canCastle = canCastle;
        board[file][rank] = clone;
    }

}
