package com.example.androidchess.Model;

public class Pawn extends Piece {

    public boolean color;
    public int file;
    public int rank;

    public int move = 0;
    public int moveNumberWhenItDoubleJumped = -1;
    public boolean ifItDoubleJumped = false;

    public Pawn(boolean color, int file, int rank, int moveNumberWhenItDoubleJumped, boolean ifItDoubleJumped) {
        this.color = color;
        this.file = file;
        this.rank = rank;
        this.moveNumberWhenItDoubleJumped = moveNumberWhenItDoubleJumped;
        this.ifItDoubleJumped = ifItDoubleJumped;
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

    public int getMove() {
        return moveNumberWhenItDoubleJumped;
    }

    public boolean getDoubleJump() {
        return ifItDoubleJumped;
    }

    public int getPawnMove() {
        return this.move;
    }

    public void subtractForIllegalMove() {
        this.move--;
    }

    public void setFile(int newFile) {
        this.file = newFile;
    }

    public void setRank(int newRank) {
        this.rank = newRank;
    }


    // given that (newFile == file && newRank == rank) = false
    public boolean isPossibleMove(Piece[][] board, int newFile, int newRank, int moveNumber) {
        // increases counter for en passant situation;
        this.move++;

        if (!color) {
            // white piece
            if (newFile == this.file) {
                // pawn attempts to move within same file
                if (newRank == this.rank - 1) {
                    // pawn attempts to move one space forward
                    if (board[newRank][newFile] == null) {
                        // pawn can move into an unoccupied position
                        return true;
                    }
                    // pawn cannot move into an occupied position
                    return false;
                }
                else if (newRank == this.rank - 2) {
                    //a pawn can only go two spaces on its first move
                    if(move != 1) {
                        return false;
                    }
                    // pawn attempts to move two spaces forward
                    if (board[this.rank - 1][this.file] == null && board[newRank][this.file] == null) {
                        // pawn can move into an unoccupied position
                        this.moveNumberWhenItDoubleJumped = moveNumber;
                        this.ifItDoubleJumped = true;
                        return true;
                    }
                    // pawn cannot move into or past an occupied position
                    return false;
                }
                // pawn cannot move backward or more than two spaces forward
                return false;
            }
            else if ((newFile == this.file - 1 || newFile == this.file + 1) && newRank == this.rank - 1) {
                // pawn attempts to take on the left or right
                //en passant condition
                if(board[newRank][newFile] == null) {
                    //can not enpassant if there's no adjacent Piece
                    if(board[this.rank][newFile] == null) {
                        return false;
                    }
                    else {
                        //can not en passant if the adjacent isnt a Pawn
                        if(!(board[this.rank][newFile] instanceof Pawn)) {
                            return false;
                        }
                        else {
                            //can not en passant if the pawn is the same color
                            if(!(board[this.rank][newFile].getColor())) {
                                return false;
                            }
                            else {
                                //can not en passant if capturing pawn is not on rank 5
                                if(this.rank != 3) {
                                    return false;
                                }
                                else {
                                    Pawn adjacent = (Pawn) board[3][newFile];
                                    //can not en passant if the captured pawn never double jumped
                                    if(!(adjacent.getDoubleJump())){
                                        return false;
                                    }
                                    else {
                                        //can not en passant if the captured pawn did not make its double jump on the last move
                                        if(moveNumber - (adjacent.getMove()) != 1) {
                                            return false;
                                        }
                                        else {
                                            board[3][newFile] = null;
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else if (board[newRank][newFile] != null) {
                    if(!(board[newRank][newFile].getColor())) {
                        return false;
                    }
                    else {
                        return true;
                    }
                }
                // pawn cannot take either an unoccupied position or piece of same color
                return false;
            }
            // pawn cannot move to a file other than its original file or an adjacent file
            return false;
        } else {
            // black piece
            if (newFile == this.file) {
                // pawn attempts to move within same file
                if (newRank == this.rank + 1) {
                    // pawn attempts to move one space forward
                    if (board[newRank][newFile] == null) {
                        // pawn can move into an unoccupied position
                        return true;
                    }
                    // pawn cannot move into an occupied position
                    return false;
                } else if (newRank == this.rank + 2) {
                    // pawn attempts to move two spaces forward
                    if(move != 1) {
                        return false;
                    }
                    if (board[this.rank + 1][this.file] == null && board[newRank][this.file] == null) {
                        // pawn can move into an unoccupied position
                        this.moveNumberWhenItDoubleJumped = moveNumber;
                        this.ifItDoubleJumped = true;
                        return true;
                    }
                    // pawn cannot move into or past an occupied position
                    return false;
                }
                // pawn cannot move backward or more than two spaces forward
                return false;
            }
            else if ((newFile == this.file - 1 || newFile == this.file + 1) && newRank == this.rank + 1) {
                // pawn attempts to take on the left or right
                //en passant condition
                if(board[newRank][newFile] == null) {
                    if(board[this.rank][newFile] == null) {
                        return false;
                    }
                    else {
                        //can not en passant if the adjacent isnt a Pawn
                        if(!(board[this.rank][newFile] instanceof Pawn)) {
                            return false;
                        }
                        else {
                            //can not en passant if the pawn is the same color
                            if(board[this.rank][newFile].getColor()) {
                                return false;
                            }
                            else {
                                //can not en passant if capturing pawn is not on rank 3
                                if(this.rank != 4) {
                                    return false;
                                }
                                else {
                                    Pawn adjacent = (Pawn) board[4][newFile];
                                    //can not en passant if the captured pawn never double jumped
                                    if(!(adjacent.getDoubleJump())) {
                                        return false;
                                    }
                                    else {
                                        //can not en passant if captured pawn did not make its double jump on the last move
                                        if(moveNumber - (adjacent.getMove()) != 1) {
                                            return false;
                                        }
                                        else {
                                            board[4][newFile] = null;
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else if (board[newRank][newFile] != null) {
                    if((board[newRank][newFile].getColor())) {
                        return false;
                    }
                    else {
                        return true;
                    }
                }
                // pawn cannot take either an unoccupied position or piece of same color
                return false;
            }
            // pawn cannot move to a file other than its original file or an adjacent file
            return false;
        }
    }

}