package com.example.androidchess;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidchess.piece.*;

public class WatchGame extends AppCompatActivity {

    static final boolean WHITE = false;
    static final boolean BLACK = true;

    ChessGame chessGame;
    private int[][] buttonID;
    int index = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watch_game);
        chessGame = GameList.chessGame;
        buttonID = new int[8][8];
        {
            buttonID[0][0] = R.id.A1;
            buttonID[0][1] = R.id.A2;
            buttonID[0][2] = R.id.A3;
            buttonID[0][3] = R.id.A4;
            buttonID[0][4] = R.id.A5;
            buttonID[0][5] = R.id.A6;
            buttonID[0][6] = R.id.A7;
            buttonID[0][7] = R.id.A8;
            buttonID[1][0] = R.id.B1;
            buttonID[1][1] = R.id.B2;
            buttonID[1][2] = R.id.B3;
            buttonID[1][3] = R.id.B4;
            buttonID[1][4] = R.id.B5;
            buttonID[1][5] = R.id.B6;
            buttonID[1][6] = R.id.B7;
            buttonID[1][7] = R.id.B8;
            buttonID[2][0] = R.id.C1;
            buttonID[2][1] = R.id.C2;
            buttonID[2][2] = R.id.C3;
            buttonID[2][3] = R.id.C4;
            buttonID[2][4] = R.id.C5;
            buttonID[2][5] = R.id.C6;
            buttonID[2][6] = R.id.C7;
            buttonID[2][7] = R.id.C8;
            buttonID[3][0] = R.id.D1;
            buttonID[3][1] = R.id.D2;
            buttonID[3][2] = R.id.D3;
            buttonID[3][3] = R.id.D4;
            buttonID[3][4] = R.id.D5;
            buttonID[3][5] = R.id.D6;
            buttonID[3][6] = R.id.D7;
            buttonID[3][7] = R.id.D8;
            buttonID[4][0] = R.id.E1;
            buttonID[4][1] = R.id.E2;
            buttonID[4][2] = R.id.E3;
            buttonID[4][3] = R.id.E4;
            buttonID[4][4] = R.id.E5;
            buttonID[4][5] = R.id.E6;
            buttonID[4][6] = R.id.E7;
            buttonID[4][7] = R.id.E8;
            buttonID[5][0] = R.id.F1;
            buttonID[5][1] = R.id.F2;
            buttonID[5][2] = R.id.F3;
            buttonID[5][3] = R.id.F4;
            buttonID[5][4] = R.id.F5;
            buttonID[5][5] = R.id.F6;
            buttonID[5][6] = R.id.F7;
            buttonID[5][7] = R.id.F8;
            buttonID[6][0] = R.id.G1;
            buttonID[6][1] = R.id.G2;
            buttonID[6][2] = R.id.G3;
            buttonID[6][3] = R.id.G4;
            buttonID[6][4] = R.id.G5;
            buttonID[6][5] = R.id.G6;
            buttonID[6][6] = R.id.G7;
            buttonID[6][7] = R.id.G8;
            buttonID[7][0] = R.id.H1;
            buttonID[7][1] = R.id.H2;
            buttonID[7][2] = R.id.H3;
            buttonID[7][3] = R.id.H4;
            buttonID[7][4] = R.id.H5;
            buttonID[7][5] = R.id.H6;
            buttonID[7][6] = R.id.H7;
            buttonID[7][7] = R.id.H8;
        }
    }

    public void drawBoard() {
        Piece[][] currentBoard = chessGame.game.get(index);
        ImageButton button;
        for (int i = 0; i < buttonID.length; i++) {
            for (int j = 0; j < buttonID[0].length; j++) {
                button = (ImageButton) findViewById(buttonID[i][j]);
                button.setImageResource(icon(currentBoard[i][j]));
            }
        }
    }

    public int icon(Piece p) {
        if (p == null){
            return 0;
        } else if (p.color == WHITE) {
            if (p instanceof Pawn) return R.drawable.whitepawn_resize;
            if (p instanceof Rook) return R.drawable.whiterook_resize;
            if (p instanceof Knight) return R.drawable.whiteknight_resize;
            if (p instanceof Bishop) return R.drawable.whitebishop_resize;
            if (p instanceof Queen) return R.drawable.whitequeen_resize;
            if (p instanceof King) return R.drawable.whiteking_resize;
        } else {
            if (p instanceof Pawn) return R.drawable.blackpawn_resize;
            if (p instanceof Rook) return R.drawable.blackrook_resize;
            if (p instanceof Knight) return R.drawable.blackknight_resize;
            if (p instanceof Bishop) return R.drawable.blackbishop_resize;
            if (p instanceof Queen) return R.drawable.blackqueen_resize;
            if (p instanceof King) return R.drawable.blackking_resize;
        }
        return 0;
    }

    public void next(View view){
        if (index < chessGame.game.size() - 1) {
            index++;
            drawBoard();
        }
    }

    public void prev(View view){
        if (index > 0) {
            index--;
            drawBoard();
        }
    }

    public void backToList(View view){
        Button back = (Button)view;
        Intent chess_intent=new Intent(this, GameList.class);
        startActivity(chess_intent);
    }

}