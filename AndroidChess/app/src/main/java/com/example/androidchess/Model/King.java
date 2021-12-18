package com.example.androidchess.Model;

public class King extends Piece {

    public boolean color;
    public int file;
    public int rank;

    public int move = 0;

    public King (boolean color, int file, int rank) {
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

    public int getKingMove() {
        return move;
    }

    public void setFile(int newFile) {
        this.file = newFile;
    }

    public void setRank(int newRank) {
        this.rank = newRank;
    }

    // given that (newFile == file && newRank == rank) = false
    public boolean isPossibleMove(Piece[][] board, int newFile, int newRank, int moveNumber) {
        // increases counter for castling situation
        this.move++;

        int absDiffFile = Math.abs(newFile - this.file);
        int absDiffRank = Math.abs(newRank - this.rank);
        if ((absDiffFile == 0 && absDiffRank == 1) || (absDiffFile == 1 && absDiffRank == 0) || (absDiffFile == 1 &&
                absDiffRank == 1)) {
            // king attempts to move a Chebyshev distance of one
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
        } else if (this.move == 1) {
            // check for castling condition
            if (newFile == 1) {
                // king attempts to castle with the queenside rook
                if (board[this.rank][1] == null && board[this.rank][2] == null && board[this.rank][3] == null) {
                    if (board[this.rank][0] instanceof Rook) {
                        Rook queenside = (Rook) board[this.rank][0];
                        if (queenside.getCastle() && (queenside.getColor() == this.color)) {
                            // king can castle with the queenside rook
                            board[this.rank][2] = new Rook(this.color, 1, this.rank);
                            board[this.rank][0] = null;
                            return true;
                        }
                    }
                }
            } else if (newFile == 6) {
                // king attempts to castle with the kingside rook
                if (board[this.rank][5] == null && board[this.rank][6] == null) {
                    if (board[this.rank][7] instanceof Rook) {
                        Rook kingside = (Rook) board[this.rank][7];
                        if (kingside.getCastle() && (kingside.getColor() == this.color)) {
                            // king can castle with the kingside rook
                            board[this.rank][5] = new Rook(this.color, 5, this.rank);
                            board[this.rank][7] = null;
                            return true;
                        }
                    }
                }
            }
            // king cannot castle
            return false;
        }
        // king cannot move a Chebyshev distance of more than one, and king cannot castle
        return false;
    }

}