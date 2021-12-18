package com.example.androidchess.piece;

import java.io.Serializable;
import java.util.ArrayList;

public class Knight extends Piece implements Serializable {

    public Knight(Piece[][] board, boolean color, int file, int rank) {
        this.board = board;
        this.color = color;
        this.file = file;
        this.rank = rank;
    }

    public void updateMoves() {
        moves = new ArrayList<>();
        int i, j;
        i = file + 1;
        if (i <= 7) {
            j = rank + 2;
            if (j <= 7) {
                if (board[i][j] == null) {
                    moves.add(new Move(i, j, REGULAR_MOVE));
                } else {
                    if (board[i][j].color != color) {
                        moves.add(new Move(i, j, CAPTURE));
                    }
                }
            }
            j = rank - 2;
            if (j >= 0) {
                if (board[i][j] == null) {
                    moves.add(new Move(i, j, REGULAR_MOVE));
                } else {
                    if (board[i][j].color != color) {
                        moves.add(new Move(i, j, CAPTURE));
                    }
                }
            }
            i = file + 2;
            if (i <= 7) {
                j = rank + 1;
                if (j <= 7) {
                    if (board[i][j] == null) {
                        moves.add(new Move(i, j, REGULAR_MOVE));
                    } else {
                        if (board[i][j].color != color) {
                            moves.add(new Move(i, j, CAPTURE));
                        }
                    }
                }
                j = rank - 1;
                if (j >= 0) {
                    if (board[i][j] == null) {
                        moves.add(new Move(i, j, REGULAR_MOVE));
                    } else {
                        if (board[i][j].color != color) {
                            moves.add(new Move(i, j, CAPTURE));
                        }
                    }
                }
            }
        }
        i = file - 1;
        if (i >= 0) {
            j = rank + 2;
            if (j <= 7) {
                if (board[i][j] == null) {
                    moves.add(new Move(i, j, REGULAR_MOVE));
                } else {
                    if (board[i][j].color != color) {
                        moves.add(new Move(i, j, CAPTURE));
                    }
                }
            }
            j = rank - 2;
            if (j >= 0) {
                if (board[i][j] == null) {
                    moves.add(new Move(i, j, REGULAR_MOVE));
                } else {
                    if (board[i][j].color != color) {
                        moves.add(new Move(i, j, CAPTURE));
                    }
                }
            }
            i = file - 2;
            if (i >= 0) {
                j = rank + 1;
                if (j <= 7) {
                    if (board[i][j] == null) {
                        moves.add(new Move(i, j, REGULAR_MOVE));
                    } else {
                        if (board[i][j].color != color) {
                            moves.add(new Move(i, j, CAPTURE));
                        }
                    }
                }
                j = rank - 1;
                if (j >= 0) {
                    if (board[i][j] == null) {
                        moves.add(new Move(i, j, REGULAR_MOVE));
                    } else {
                        if (board[i][j].color != color) {
                            moves.add(new Move(i, j, CAPTURE));
                        }
                    }
                }
            }
        }
    }

    public void copyToBoard(Piece[][] board) {
        Knight clone = new Knight(board, color, file, rank);
        clone.moves = moves;
        board[file][rank] = clone;
    }
    
}
