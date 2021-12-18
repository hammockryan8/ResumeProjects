package com.example.androidchess;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidchess.Model.Bishop;
import com.example.androidchess.Model.King;
import com.example.androidchess.Model.Knight;
import com.example.androidchess.Model.Pawn;
import com.example.androidchess.Model.Piece;
import com.example.androidchess.Model.Queen;
import com.example.androidchess.Model.Rook;

public class WatchGame extends AppCompatActivity {

    public Piece[][] data_board = new Piece [8][8];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watch_game);
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
    }

    public void next(View view){

    }

    public void prev(View view){

    }

    public void backToList(View view){
        Button back = (Button)view;
        Intent chess_intent=new Intent(this, GameList.class);
        startActivity(chess_intent);
    }

}
