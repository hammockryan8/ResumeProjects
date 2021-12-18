package com.example.androidchess.Model;

public abstract class Piece {

    public boolean color;
    public int file;
    public int rank;

    /***
     * Checks to see if the intended move for a piece is possible based on the dynamic implementation of it in each class that extends Piece
     * @param board the Piece double array that is used in play
     * @param newFile the intended new file for the piece to be moved to
     * @param newRank the intended new rank for the piece to be moved to
     * @param moveNumber the current number of moves played in the game
     * @return a boolean, false if the move was not legal relative to the method implementation and true if it was
     */
    public abstract boolean isPossibleMove(Piece[][] board, int newFile, int newRank, int moveNumber);
    /***
     * returns color of the piece
     * @return boolean that determines the color, false if white, black if true
     */
    public abstract boolean getColor();
    /***
     * returns the file of the piece
     * @return an int that represents which column the piece is on the board
     */
    public abstract int getFile();
    /***
     * returns the rank of the piece
     * @return an int that represents which row the piece is on the board
     */
    public abstract int getRank();
    /***
     * sets a new file for a piece
     * @param newFile the integer to overwrite the current file
     */
    public abstract void setFile(int newFile);
    /***
     * sets a new rank for a piece
     * @param newRank the integer to overwrite the current rank
     */
    public abstract void setRank(int newRank);
}
