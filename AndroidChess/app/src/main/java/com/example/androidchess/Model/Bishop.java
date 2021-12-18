package com.example.androidchess.Model;

public class Bishop extends Piece {

    public boolean color;
    public int file;
    public int rank;

    public Bishop(boolean color, int file, int rank) {
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

    public void setFile(int newFile) {
        this.file = newFile;
    }

    public void setRank(int newRank) {
        this.rank = newRank;
    }

    // given that (newFile == file && newRank == rank) = false
    public boolean isPossibleMove(Piece[][] board, int newFile, int newRank, int moveNumber) {
        int absDiffFile = Math.abs(newFile - this.file);
        int absDiffRank = Math.abs(newRank - this.rank);
        if (absDiffFile == absDiffRank) {
            // bishop attempts to move diagonally
            if (newFile > this.file && newRank < this.rank) {
                // bishop attempts to move right and up
                for (int i = this.rank - 1, j = this.file + 1; i > newRank && j < newFile; i--, j++) {
                    if (board[i][j] != null) {
                        // bishop cannot move past an occupied position
                        return false;
                    }
                }
            }
            else if (newFile > this.file && newRank > this.rank) {
                // bishop attempts to move right and down
                for (int i = this.rank + 1, j = this.file + 1; i < newRank && j < newFile; i++, j++) {
                    if (board[i][j] != null) {
                        // bishop cannot move past an occupied position
                        return false;
                    }
                }
            }
            else if (newFile < this.file && newRank < this.rank) {
                // bishop attempts to move left and up
                for (int i = this.rank - 1, j = this.file - 1; i > newRank && j > newFile; i--, j--) {
                    if (board[i][j] != null) {
                        // bishop cannot move past an occupied position
                        return false;
                    }
                }
            }
            else {
                // bishop attempts to move left and down
                for (int i = this.rank + 1, j = this.file - 1; i < newRank && j > newFile; i++, j--) {
                    if (board[i][j] != null) {
                        // bishop cannot move past an occupied position
                        return false;
                    }
                }
            }
            if(board[newRank][newFile] != null) {
                //bishop cannot take piece of same color
                if(board[newRank][newFile].getColor() == this.color) {
                    return false;
                }
                else {
                    //except when its a piece of the opposite color
                    return true;
                }
            }
            else {
                return true;
            }

        }
        // bishop cannot move to a non-diagonal position
        return false;
    }

}