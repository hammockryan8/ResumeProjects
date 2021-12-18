package com.example.androidchess.Model;

public class Queen extends Piece {

    public boolean color;
    public int file;
    public int rank;

    public Queen (boolean color, int file, int rank) {
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
            // queen attempts to move diagonally
            if (newFile > this.file && newRank < this.rank) {
                // queen attempts to move right and up
                for (int i = this.rank - 1, j = this.file + 1; i > newRank && j < newFile; i--, j++) {
                    if (board[i][j] != null) {
                        // queen cannot move past an occupied position
                        return false;
                    }
                }
            }
            else if (newFile > this.file && newRank > this.rank) {
                // queen attempts to move right and down
                for (int i = this.rank + 1, j = this.file + 1; i < newRank && j < newFile; i++, j++) {
                    if (board[i][j] != null) {
                        // queen cannot move past an occupied position
                        return false;
                    }
                }
            }
            else if (newFile < this.file && newRank < this.rank) {
                // queen attempts to move left and up
                for (int i = this.rank - 1, j = this.file - 1; i > newRank && j > newFile; i--, j--) {
                    if (board[i][j] != null) {
                        // queen cannot move past an occupied position
                        return false;
                    }
                }
            }
            else {
                // queen attempts to move left and down
                for (int i = this.rank + 1, j = this.file - 1; i < newRank && j > newFile; i++, j--) {
                    if (board[i][j] != null) {
                        // queen cannot move past an occupied position
                        return false;
                    }
                }
            }
            if(board[newRank][newFile] != null) {
                //queen cannot take piece of same color
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
        else if (newFile == this.file && newRank != this.rank) {
            // queen attempts to move within same file
            if (this.rank > newRank) {
                // queen attempts to move up
                for(int i = this.rank - 1; i > newRank; i--) {
                    if(board[i][this.file] != null) {
                        //queen cannot move past an occupied position
                        return false;
                    }
                }
            }
            else {
                // queen attempts to move down
                for(int i = this.rank + 1; i < newRank; i++) {
                    if(board[i][this.file] != null) {
                        //queen cannot move past an occupied position
                        return false;
                    }
                }
            }
            if(board[newRank][newFile] != null) {
                //queen cannot take piece of same color
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
        else if (newFile != this.file && newRank == this.rank) {
            // queen moves within same rank
            if (this.file < newFile) {
                // queen attempts to move right
                for(int i = this.file + 1; i < newFile; i++) {
                    if(board[this.rank][i] != null) {
                        //queen cannot move past an occupied position
                        return false;
                    }
                }
            }
            else {
                // queen attempts to move left
                for(int i = this.file - 1; i > newFile; i--) {
                    if(board[this.rank][i] != null) {
                        //queen cannot move past an occupied position
                        return false;
                    }
                }
            }
            if(board[newRank][newFile] != null) {
                //queen cannot take piece of same color
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
        // queen cannot move to a non-orthogonal and non-diagonal position
        return false;
    }

}