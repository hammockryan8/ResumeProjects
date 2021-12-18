package com.example.androidchess.Model;

public class Knight extends Piece {

    public boolean color;
    public int file;
    public int rank;

    public Knight(boolean color, int file, int rank) {
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
        if ((absDiffFile == 1 && absDiffRank == 2) || (absDiffFile == 2 && absDiffRank == 1)) {
            // knight attempts to move two squares horizontally and one square vertically or vice versa
            if(board[newRank][newFile] != null) {
                if (board[newRank][newFile].getColor() != this.color) {
                    // knight can take piece of opposite color
                    return true;
                }
                else {
                    // knight cannot take piece of same color
                    return false;
                }
            }
            else {
                return true;
            }
        }
        // knight cannot make move other than two squares horizontally and one square vertically or vice versa
        return false;
    }

}
