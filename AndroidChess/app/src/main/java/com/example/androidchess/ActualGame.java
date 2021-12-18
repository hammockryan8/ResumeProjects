package com.example.androidchess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.androidchess.Model.*;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import com.example.androidchess.GameControl.ChessBoard;
public class ActualGame extends AppCompatActivity {

    public String[][]backup = new String [8][8];
    public Piece[][] data_board = new Piece [8][8];
    public int moveNumber = 1;
    private ImageButton selected = null;
    private ImageButton prev = null;
    public String move1 = "";
    public String move2="";
    public String undoDest="";
    public String undoSrc="";
    public int prevFile = -1;
    public int prevRank = -1;
    public int movFile = -1;
    public int movRank = -1;
    public String title = "";
    private final String illegal_message = "That was an illegal move, please try again";
    private final String black_check = "Black, you are in check";
    private final String white_check = "White, you are in check";
    public int whiteKingRank = 7;
    public int whiteKingFile = 4;
    public int blackKingRank = 0;
    public int blackKingFile = 4;
    private final HashMap<Character, Integer> files = new HashMap<Character,Integer>();
    private final HashMap<Character, Integer> ranks = new HashMap<Character,Integer>();
    private final HashMap<Integer, Character> filesReverse = new HashMap<Integer,Character>();
    private final HashMap<Integer, Character> ranksReverse = new HashMap<Integer, Character>();
    private final HashMap<String, Integer> buttonID = new HashMap<String, Integer>();
    private ArrayList<Piece> whitePieces = new ArrayList<Piece>();
    private ArrayList<Piece> blackPieces = new ArrayList<Piece>();
    private ChessBoard chessBoard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_game);
        this.data_board[0][0] = new Rook(true, 0, 0);
        this.data_board[0][1] = new Knight(true, 1, 0);
        this.data_board[0][2] = new Bishop(true, 2, 0);
        this.data_board[0][3] = new Queen(true, 3, 0);
        this.data_board[0][4] = new King(true, 4, 0);
        this.data_board[0][5] = new Bishop(true, 5, 0);
        this.data_board[0][6] = new Knight(true, 6, 0);
        this.data_board[0][7] = new Rook(true, 7, 0);
        this.data_board[7][0] = new Rook(false, 0, 7);
        this.data_board[7][1] = new Knight(false, 1, 7);
        this.data_board[7][2] = new Bishop(false, 2, 7);
        this.data_board[7][3] = new Queen(false, 3, 7);
        this.data_board[7][4] = new King(false, 4, 7);
        this.data_board[7][5] = new Bishop(false, 5, 7);
        this.data_board[7][6] = new Knight(false, 6, 7);
        this.data_board[7][7] = new Rook(false, 7, 7);
        for (int i = 0; i < 8; i++) {
            this.data_board[1][i] = new Pawn(true, i, 1, 0, false);
            this.data_board[6][i] = new Pawn(false, i, 6, 0, false);

        }
        files.put('A', 0);
        files.put('B', 1);
        files.put('C', 2);
        files.put('D', 3);
        files.put('E', 4);
        files.put('F', 5);
        files.put('G', 6);
        files.put('H', 7);

        ranks.put('8', 0);
        ranks.put('7', 1);
        ranks.put('6', 2);
        ranks.put('5', 3);
        ranks.put('4', 4);
        ranks.put('3', 5);
        ranks.put('2', 6);
        ranks.put('1', 7);

        filesReverse.put(0, 'A');
        filesReverse.put(1, 'B');
        filesReverse.put(2, 'C');
        filesReverse.put(3, 'D');
        filesReverse.put(4, 'E');
        filesReverse.put(5, 'F');
        filesReverse.put(6, 'G');
        filesReverse.put(7, 'H');

        ranksReverse.put(0, '8');
        ranksReverse.put(1, '7');
        ranksReverse.put(2, '6');
        ranksReverse.put(3, '5');
        ranksReverse.put(4, '4');
        ranksReverse.put(5, '3');
        ranksReverse.put(6, '2');
        ranksReverse.put(7, '1');

        buttonID.put("A8", R.id.A8);
        buttonID.put("A7", R.id.A7);
        buttonID.put("A6", R.id.A6);
        buttonID.put("A5", R.id.A5);
        buttonID.put("A4", R.id.A4);
        buttonID.put("A3", R.id.A3);
        buttonID.put("A2", R.id.A2);
        buttonID.put("A1", R.id.A1);

        buttonID.put("B8", R.id.B8);
        buttonID.put("B7", R.id.B7);
        buttonID.put("B6", R.id.B6);
        buttonID.put("B5", R.id.B5);
        buttonID.put("B4", R.id.B4);
        buttonID.put("B3", R.id.B3);
        buttonID.put("B2", R.id.B2);
        buttonID.put("B1", R.id.B1);

        buttonID.put("C8", R.id.C8);
        buttonID.put("C7", R.id.C7);
        buttonID.put("C6", R.id.C6);
        buttonID.put("C5", R.id.C5);
        buttonID.put("C4", R.id.C4);
        buttonID.put("C3", R.id.C3);
        buttonID.put("C2", R.id.C2);
        buttonID.put("C1", R.id.C1);

        buttonID.put("D8", R.id.D8);
        buttonID.put("D7", R.id.D7);
        buttonID.put("D6", R.id.D6);
        buttonID.put("D5", R.id.D5);
        buttonID.put("D4", R.id.D4);
        buttonID.put("D3", R.id.D3);
        buttonID.put("D2", R.id.D2);
        buttonID.put("D1", R.id.D1);

        buttonID.put("E8", R.id.E8);
        buttonID.put("E7", R.id.E7);
        buttonID.put("E6", R.id.E6);
        buttonID.put("E5", R.id.E5);
        buttonID.put("E4", R.id.E4);
        buttonID.put("E3", R.id.E3);
        buttonID.put("E2", R.id.E2);
        buttonID.put("E1", R.id.E1);

        buttonID.put("F8", R.id.F8);
        buttonID.put("F7", R.id.F7);
        buttonID.put("F6", R.id.F6);
        buttonID.put("F5", R.id.F5);
        buttonID.put("F4", R.id.F4);
        buttonID.put("F3", R.id.F3);
        buttonID.put("F2", R.id.F2);
        buttonID.put("F1", R.id.F1);

        buttonID.put("G8", R.id.G8);
        buttonID.put("G7", R.id.G7);
        buttonID.put("G6", R.id.G6);
        buttonID.put("G5", R.id.G5);
        buttonID.put("G4", R.id.G4);
        buttonID.put("G3", R.id.G3);
        buttonID.put("G2", R.id.G2);
        buttonID.put("G1", R.id.G1);

        buttonID.put("H8", R.id.H8);
        buttonID.put("H7", R.id.H7);
        buttonID.put("H6", R.id.H6);
        buttonID.put("H5", R.id.H5);
        buttonID.put("H4", R.id.H4);
        buttonID.put("H3", R.id.H3);
        buttonID.put("H2", R.id.H2);
        buttonID.put("H1", R.id.H1);

        backup[0][0] = "A8";
        backup[0][1] = "B8";
        backup[0][2] = "C8";
        backup[0][3] = "D8";
        backup[0][4] = "E8";
        backup[0][5] = "F8";
        backup[0][6] = "G8";
        backup[0][7] = "H8";

        backup[1][0] = "A7";
        backup[1][1] = "B7";
        backup[1][2] = "C7";
        backup[1][3] = "D7";
        backup[1][4] = "E7";
        backup[1][5] = "F7";
        backup[1][6] = "G7";
        backup[1][7] = "H7";

        backup[2][0] = "A6";
        backup[2][1] = "B6";
        backup[2][2] = "C6";
        backup[2][3] = "D6";
        backup[2][4] = "E6";
        backup[2][5] = "F6";
        backup[2][6] = "G6";
        backup[2][7] = "H6";

        backup[3][0] = "A5";
        backup[3][1] = "B5";
        backup[3][2] = "C5";
        backup[3][3] = "D5";
        backup[3][4] = "E5";
        backup[3][5] = "F5";
        backup[3][6] = "G5";
        backup[3][7] = "H5";

        backup[4][0] = "A4";
        backup[4][1] = "B4";
        backup[4][2] = "C4";
        backup[4][3] = "D4";
        backup[4][4] = "E4";
        backup[4][5] = "F4";
        backup[4][6] = "G4";
        backup[4][7] = "H4";

        backup[5][0] = "A3";
        backup[5][1] = "B3";
        backup[5][2] = "C3";
        backup[5][3] = "D3";
        backup[5][4] = "E3";
        backup[5][5] = "F3";
        backup[5][6] = "G3";
        backup[5][7] = "H3";

        backup[6][0] = "A2";
        backup[6][1] = "B2";
        backup[6][2] = "C2";
        backup[6][3] = "D2";
        backup[6][4] = "E2";
        backup[6][5] = "F2";
        backup[6][6] = "G2";
        backup[6][7] = "H2";

        backup[7][0] = "A1";
        backup[7][1] = "B1";
        backup[7][2] = "C1";
        backup[7][3] = "D1";
        backup[7][4] = "E1";
        backup[7][5] = "F1";
        backup[7][6] = "G1";
        backup[7][7] = "H1";
    }

    public void remakeWholeBoard(Piece[][]board){
        ImageButton tmp;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                sb.append(filesReverse.get(j));
                sb.append(ranksReverse.get(i));
                if(board[i][j] != null){
                    tmp = (ImageButton)findViewById(buttonID.get(sb));
                    tmp.setImageResource(0);
                }
                else{
                    tmp = (ImageButton)findViewById(buttonID.get(sb));
                    tmp.setImageResource(icon(board, j, i));
                }
                sb.setLength(0);
            }
        }
    }

    public void updateBoardArt(Piece[][]board, String first, String second, int oldF, int oldR, int newF, int newR){
        ImageButton tmp;
        tmp = (ImageButton) findViewById(buttonID.get(second));
        tmp.setImageResource(icon(board, newF, newR));
        tmp = (ImageButton) findViewById(buttonID.get(first));
        tmp.setImageResource(0);
    }

    public int icon(Piece[][]board, int file, int rank){
        if(board[rank][file].getColor()){
            if(board[rank][file] instanceof Pawn) return R.drawable.blackpawn_resize;
            if(board[rank][file] instanceof Bishop) return R.drawable.blackbishop_resize;
            if(board[rank][file] instanceof Knight) return R.drawable.blackknight_resize;
            if(board[rank][file] instanceof Queen) return R.drawable.blackqueen_resize;
            if(board[rank][file] instanceof Rook) return R.drawable.blackrook_resize;
            if(board[rank][file] instanceof King) return R.drawable.blackking_resize;
        }
        else{
            if(board[rank][file] instanceof Pawn) return R.drawable.whitepawn_resize;
            if(board[rank][file] instanceof Bishop) return R.drawable.whitebishop_resize;
            if(board[rank][file] instanceof Knight) return R.drawable.whiteknight_resize;
            if(board[rank][file] instanceof Queen) return R.drawable.whitequeen_resize;
            if(board[rank][file] instanceof Rook) return R.drawable.whiterook_resize;
            if(board[rank][file] instanceof King) return R.drawable.whiteking_resize;
        }
        return 0;
    }

    public void updateData(Piece[][]data_board, int oldF, int oldR, int newF, int newR){
        if(data_board[oldR][oldF] instanceof Pawn) {
            if(!data_board[oldR][oldF].getColor()) {
                    /*if(newRank == 0) {
                        //promotion for white pawns
                        if(move.length() == 5) {
                            data_board[newRank][newFile] = new Queen(data_board[oldR][oldF].getColor(), newFile, newRank);
                        }
                        else if(move.endsWith("N")) {
                            data_board[newRank][newFile] = new Knight(data_board[originRank][originFile].getColor(), newFile, newRank);
                        }
                        else if(move.endsWith("B")) {
                            data_board[newRank][newFile] = new Bishop(data_board[originRank][originFile].getColor(), newFile, newRank);
                        }
                        else if(move.endsWith("R")) {
                            data_board[newRank][newFile] = new Bishop(data_board[originRank][originFile].getColor(), newFile, newRank);
                        }
                    }*/
            }
            else {
                    /*if(newRank == 7) {
                        //promotion for black pawns
                        if(move.length() == 5) {
                            data_board[newRank][newFile] = new Queen(data_board[originRank][originFile].getColor(), newFile, newRank);
                        }
                        else if(move.endsWith("N")) {
                            data_board[newRank][newFile] = new Knight(data_board[originRank][originFile].getColor(), newFile, newRank);
                        }
                        else if(move.endsWith("B")) {
                            data_board[newRank][newFile] = new Bishop(data_board[originRank][originFile].getColor(), newFile, newRank);
                        }
                        else if(move.endsWith("R")) {
                            data_board[newRank][newFile] = new Bishop(data_board[originRank][originFile].getColor(), newFile, newRank);
                        }
                    }*/
            }
            if(Math.abs(oldR - newR) == 2) {
                data_board[newR][newF] = new Pawn((data_board[oldR][oldF]).getColor(), newF, newR, moveNumber, true);
            }
            else {
                data_board[newR][newF] = new Pawn((data_board[oldR][oldF]).getColor(), newF, newR, 0, false);
            }
        }
        else if(data_board[oldR][oldF] instanceof Knight) {
            data_board[newR][newF] = new Knight((data_board[oldR][oldF]).getColor(), newF, newR);
        }
        else if(data_board[oldR][oldF] instanceof Bishop) {
            data_board[newR][newF] = new Bishop((data_board[oldR][oldF]).getColor(), newF, newR);
        }
        else if(data_board[oldR][oldF] instanceof Rook) {
            data_board[newR][newF] = new Rook((data_board[oldR][oldF]).getColor(), newF, newR);
        }
        else if(data_board[oldR][oldF] instanceof Queen) {
            data_board[newR][newF] = new Queen((data_board[oldR][oldF]).getColor(), newF, newR);
        }
        else if(data_board[oldR][oldF] instanceof King) {
            data_board[newR][newF] = new King((data_board[oldR][oldF]).getColor(), newF, newR);
            if(data_board[newR][newF].getColor()){
                blackKingFile = newF;
                blackKingRank = newR;
            }
            else{
                whiteKingFile = newF;
                whiteKingRank = newR;
            }
        }
        data_board[oldR][oldF] = null;
    }

    public void undo(View view){
        if(movRank == -1){
            Snackbar.make(view, illegal_message, BaseTransientBottomBar.LENGTH_SHORT).show();
        }
        else{
            moveNumber--;
            updateData(data_board, movFile, movRank, prevFile, prevRank);
            updateBoardArt(data_board, undoDest, undoSrc, movFile, movRank, prevFile, prevRank);
            prevFile = -1;
            prevRank = -1;
            movFile = -1;
            movRank = -1;
            undoDest="";
            undoSrc="";
        }
    }

    public void resign(View view){
        Button clickedResign = (Button)view;
        if(moveNumber % 2 != 0){
            setContentView(R.layout.black_wins);
        }
        else{
            setContentView(R.layout.white_wins);
        }
    }

    public void drawTheDrawScreen(View view){
        Button clickedDraw = (Button)view;
        setContentView(R.layout.draw_screen);

    }

    public void saveAndReturnToMainScreen(View view){
        Button clickedSubmitted = (Button)view;
        EditText title = (EditText)findViewById(R.id.game_title);

        //fill out this part to actually save the game
        Intent chess_intent=new Intent(this, MainActivity.class);
        startActivity(chess_intent);
    }

    public void returnToMainScreen(View view){
        Button no = (Button)view;
        Intent chess_intent=new Intent(this, MainActivity.class);
        startActivity(chess_intent);
    }

    public void saveGame(View view){
        Button yes = (Button)view;
        setContentView(R.layout.save_game);
    }

    public void doAIThing(View view) {
        /*boolean currColor = true;
        if (moveNumber % 2 == 1) {
            currColor = false;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (data_board[i][j] != null) {
                    if (data_board[i][j].getColor() == currColor) {
                        for (int k = 0; k < 8; k++) {
                            for (int m = 0; m < 8; m++) {
                                if (m != j && k != i) {
                                    if (data_board[i][j].isPossibleMove(data_board, m, k, moveNumber)) {
                                        updateData(data_board, m, k, j, i);
                                        updateBoardArt(data_board, backup[i][j], backup[m][k], j, i, m, k);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }*/
    }

    public ArrayList<Piece> compileThreats(Piece[][]board, boolean color){
        ArrayList<Piece> enemies = new ArrayList<Piece>();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j] != null){
                    if(board[i][j].getColor() != color){
                        enemies.add(board[i][j]);
                    }
                }
            }
        }
        return enemies;
    }

    public boolean isInCheck(Piece[][]board, int kingRank, int kingFile, boolean color){
        ArrayList<Piece>posThreats = compileThreats(board, color);
        for(Piece p : posThreats){
            if(p.isPossibleMove(board, kingFile, kingRank, moveNumber)){
                return true;
            }
        }
        return false;
    }

    public void playOfTheGame(View view){
        ImageButton clicked = (ImageButton)view;

        if(move1.isEmpty()){
            //selected = clicked;
            String buttonFileRank = view.getResources().getResourceName(clicked.getId());
            buttonFileRank = buttonFileRank.substring(buttonFileRank.length() - 2);
            move1 = buttonFileRank.trim();

        }
        else if(!move1.isEmpty()){
            String sidName = view.getResources().getResourceName(clicked.getId());
            move2 = sidName.substring(sidName.length()-2).trim();
            char oldFile =move1.charAt(0);
            int oldF=files.get(oldFile);
            Snackbar.make(view,move1+move2, BaseTransientBottomBar.LENGTH_SHORT).show();
            char oldRank = move1.charAt(1);
            int oldR = ranks.get(oldRank);
            char newFile = move2.charAt(0);
            int newF = files.get(newFile);
            char newRank = move2.charAt(1);
            int newR = ranks.get(newRank);

            if(moveNumber % 2 == 0){
                if(!data_board[oldR][oldF].getColor()){
                    Snackbar.make(view,illegal_message, BaseTransientBottomBar.LENGTH_SHORT).show();
                }
                else{
                    if(data_board[oldR][oldF].isPossibleMove(data_board, newF, newR, moveNumber)){
                        updateData(data_board, oldF, oldR, newF, newR);
                        updateBoardArt(data_board, move1, move2, oldF, oldR, newF, newR);
                        moveNumber++;
                        prevFile = oldF;
                        prevRank = oldR;
                        movFile = newF;
                        movRank = newR;
                        undoSrc=move1;
                        undoDest=move2;
                    }
                    else{
                        Snackbar.make(view,illegal_message, BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                }
            }
            else{
                if(data_board[oldR][oldF].getColor()){
                    Snackbar.make(view,illegal_message, BaseTransientBottomBar.LENGTH_SHORT).show();
                }
                else{
                    if(data_board[oldR][oldF].isPossibleMove(data_board, newF, newR, moveNumber)){
                        updateData(data_board, oldF, oldR, newF, newR);
                        updateBoardArt(data_board, move1, move2, oldF, oldR, newF, newR);
                        moveNumber++;
                        prevFile = oldF;
                        prevRank = oldR;
                        movFile = newF;
                        movRank = newR;
                        undoSrc=move1;
                        undoDest=move2;
                    }
                    else{
                        Snackbar.make(view, illegal_message, BaseTransientBottomBar.LENGTH_SHORT).show();

                    }
                }
            }
            if(moveNumber % 2 == 0){
                if(isInCheck(data_board, blackKingRank, blackKingFile, true)){
                    Snackbar.make(view, black_check, BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            }
            else{
                if(isInCheck(data_board, whiteKingRank, whiteKingFile, false)){
                    Snackbar.make(view, white_check, BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            }


            move1="";
            move2="";
        }
    }



}



