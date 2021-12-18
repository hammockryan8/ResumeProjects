package com.example.androidchess.Model;

public class Rook extends Piece {

    public boolean color;
    public int file;
    public int rank;

    public boolean castle = true;

    public Rook(boolean color, int file, int rank) {
        this.color = color;
        this.file = file;
        this.rank = rank;
    }

    public boolean getColor() {
        return color;
    }

    public int getFile() {
        return file;
    }

    public int getRank() {
        return rank;
    }

    public boolean getCastle() {
        return castle;
    }

    public void setFile(int newFile) {
        this.file = newFile;
    }

    public void setRank(int newRank) {
        this.rank = newRank;
    }

    // given that (newFile == file && newRank == rank) = false
    public boolean isPossibleMove(Piece[][] board, int newFile, int newRank, int moveNumber) {
        castle = false;

        if (newFile == this.file && newRank != this.rank) {
            // rook attempts to move within same file
            if (this.rank > newRank) {
                // rook attempts to move up
                for(int i = this.rank - 1; i > newRank; i--) {
                    if(board[i][this.file] != null) {
                        //rook cannot move past an occupied position
                        return false;
                    }
                }
            }
            else {
                // rook attempts to move down
                for(int i = this.rank + 1; i < newRank; i++) {
                    if(board[i][this.file] != null) {
                        //rook cannot move past an occupied position
                        return false;
                    }
                }
            }
            if(board[newRank][newFile] != null) {
                //rook cannot take piece of same color
                if(board[newRank][newFile].getColor() == this.color) {
                    return false;
                }
                else {
                    //except when its a piece of the opposite color
                    castle = false;
                    return true;
                }
            }
            else {
                castle = false;
                return true;
            }
        } else if (newFile != this.file && newRank == this.rank) {
            // rook attempts to move within same rank
            if (this.file < newFile) {
                // rook attempts to move right
                for(int i = this.file + 1; i < newFile; i++) {
                    if(board[this.rank][i] != null) {
                        //queen cannot move past an occupied position
                        return false;
                    }
                }
            }
            else {
                // rook attempts to move left
                for(int i = this.file - 1; i > newFile; i--) {
                    if(board[this.rank][i] != null) {
                        //rook cannot move past an occupied position
                        return false;
                    }
                }
            }
            if(board[newRank][newFile] != null) {
                //rook cannot take piece of same color
                if(board[newRank][newFile].getColor() == this.color) {
                    return false;
                }
                else {
                    //except when its a piece of the opposite color
                    castle = false;
                    return true;
                }
            }
            else {
                castle = false;
                return true;
            }
        }
        // rook cannot move to a non-orthogonal position
        return false;
    }

}