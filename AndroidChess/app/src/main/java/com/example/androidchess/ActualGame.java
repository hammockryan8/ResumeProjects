package com.example.androidchess;

import android.content.Intent;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidchess.piece.*;

import java.io.IOException;
import java.util.Calendar;

public class ActualGame extends AppCompatActivity {

    static final int DRAW = 0;
    static final int WHITE_RESIGNS = 1;
    static final int BLACK_RESIGNS = 2;
    static final int WHITE_CHECKMATES = 3;
    static final int BLACK_CHECKMATES = 4;

    static final boolean WHITE = false;
    static final boolean BLACK = true;

    static final int ILLEGAL_MOVE = 0;
    static final int SELF_CHECK = 1;
    static final int VALID_MOVE = 2;
    static final int ILLEGAL_CASTLE = 3;
    static final int PAWN_PROMOTION = 4;
    static final int NO_PIECE_EXISTS = 5;
    static final int INVALID_PLAYER_TURN = 6;
    static final int CHECKED = 7;
    static final int CHECKMATED = 8;

    static final int ROOK = 0;
    static final int KNIGHT = 1;
    static final int BISHOP = 2;
    static final int QUEEN = 3;

    TextView message;

    private int[][] buttonID;
    private ChessGame chessGame;
    private boolean canUndo;
    String buttonName1, buttonName2;
    private int file, rank, newFile, newRank;

    RecordedGames rg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_game);

        this.rg = MainActivity.rg;

        message = findViewById(R.id.message);

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

        chessGame = new ChessGame();
        canUndo = false;
        file = -1;
        rank = -1;
        newFile = -1;
        newRank = -1;
    }

    public void drawBoard() {
        ImageButton button;
        for (int i = 0; i < buttonID.length; i++) {
            for (int j = 0; j < buttonID[0].length; j++) {
                button = (ImageButton) findViewById(buttonID[i][j]);
                button.setImageResource(icon(chessGame.currentBoard[i][j]));
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

    public void squareSelection(View view) throws InterruptedException {
        ImageButton button = (ImageButton) view;
        if (file == -1) {
            buttonName1 = view.getResources().getResourceName(button.getId());
            buttonName1 = buttonName1.substring(buttonName1.length() - 2);
            file = buttonName1.charAt(0) - 'A';
            rank = buttonName1.charAt(1) - '1';
            message.setText((chessGame.playerTurn ? "Black" : "White") + "'s turn to move. "
                    + buttonName1 + " selected; choose your destination square.");
        } else {
            System.out.println("here");
            buttonName2 = view.getResources().getResourceName(button.getId());
            buttonName2 = buttonName2.substring(buttonName2.length() - 2);
            newFile = buttonName2.charAt(0) - 'A';
            newRank = buttonName2.charAt(1) - '1';
            int result = chessGame.move(file, rank, newFile, newRank);
            String text = "";

            if (result ==  NO_PIECE_EXISTS) {
                text += "No piece exists at " + buttonName1 + ". Try again, "
                        + (chessGame.playerTurn ? "Black" : "White") + ". ";
            } else if (result == ILLEGAL_MOVE) {
                text += "Illegal move. Try again, " + (chessGame.playerTurn ? "Black" : "White")
                        + ". ";
            } else if (result == SELF_CHECK) {
                text += "Illegal move; your king is under check. Try again, "
                        + (chessGame.playerTurn ? "Black" : "White") + ". ";
            } else if (result == VALID_MOVE || result == CHECKED || result == CHECKMATED) {
                text += "Move successful. ";
                canUndo = true;
            } else if (result == PAWN_PROMOTION) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("What would you like to promote your pawn to?");

                String[] animals = {"Rook", "Knight", "Bishop", "Queen"};
                builder.setItems(animals, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: promote(ROOK); break;
                            case 1: promote(KNIGHT); break;
                            case 2: promote(BISHOP); break;
                            case 3: promote(QUEEN); break;
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                return;
            } else if (result == INVALID_PLAYER_TURN) {
                text += "Invalid move; you cannot move your opponent's pieces. Try again, "
                        + (chessGame.playerTurn ? "Black" : "White") + ". ";
            }

            if (result == CHECKED) {
                text += (chessGame.playerTurn ? "Black" : "White") + " has been checked. "
                        + (chessGame.playerTurn ? "Black" : "White") + "'s turn to move.";
                message.setText(text);
            } else if (result == CHECKMATED) {
                text += (chessGame.playerTurn ? "Black" : "White") + " has been checkmated.";
                message.setText(text);
                chessGame.gameEndType = chessGame.playerTurn ? WHITE_CHECKMATES : BLACK_CHECKMATES;
                endGame();
            } else {
                text += (chessGame.playerTurn ? "Black" : "White") + "'s turn to move.";
                message.setText(text);
            }

            drawBoard();

            file = -1;
            rank = -1;
            newFile = -1;
            newRank = -1;
        }
    }

    public void promote(int type) {
        int result = chessGame.promote(type, newFile, newRank);
        chessGame.updateAllMoves(chessGame.currentBoard);
        String text = "Pawn promoted. ";
        canUndo = true;

        if (result == CHECKED) {
            text += (chessGame.playerTurn ? "Black" : "White") + " has been checked. "
                    + (chessGame.playerTurn ? "Black" : "White") + "'s turn to move.";
            message.setText(text);
        } else if (result == CHECKMATED) {
            text += (chessGame.playerTurn ? "Black" : "White") + " has been checkmated.";
            message.setText(text);
            chessGame.gameEndType = chessGame.playerTurn ? WHITE_CHECKMATES : BLACK_CHECKMATES;
            endGame();
        } else {
            text += (chessGame.playerTurn ? "Black" : "White") + "'s turn to move.";
            message.setText(text);
        }

        drawBoard();

        file = -1;
        rank = -1;
        newFile = -1;
        newRank = -1;
    }

    public void undo(View view) {
        if (canUndo) {
            canUndo = false;
            chessGame.undo();
            drawBoard();
        } else {
            message.setText("You can only undo once.");
        }
    }

    public void ai(View view) {
        while (true) {
            int[] move = chessGame.getRandomMove(chessGame.currentBoard, chessGame.playerTurn);
            int result = chessGame.move(move[0], move[1], move[2], move[3]);
            if (result == ILLEGAL_MOVE || result == SELF_CHECK || result == ILLEGAL_CASTLE) {
                continue;
            } else if (result == PAWN_PROMOTION) {
                int promoteTo = (int) (4 * Math.random());
                chessGame.promote(promoteTo, move[2], move[3]);
                chessGame.updateAllMoves(chessGame.currentBoard);
            }

            String text = "AI has moved. ";

            if (result == CHECKED) {
                text += (chessGame.playerTurn ? "Black" : "White") + " has been checked. "
                        + (chessGame.playerTurn ? "Black" : "White") + "'s turn to move.";
                message.setText(text);
            } else if (result == CHECKMATED) {
                text += (chessGame.playerTurn ? "Black" : "White") + " has been checkmated.";
                message.setText(text);
                chessGame.gameEndType = chessGame.playerTurn ? WHITE_CHECKMATES : BLACK_CHECKMATES;
                endGame();
            } else {
                text += (chessGame.playerTurn ? "Black" : "White") + "'s turn to move.";
                message.setText(text);
            }

            canUndo = true;
            drawBoard();
            break;
        }
    }

    public void draw(View view) {
        chessGame.gameEndType = DRAW;
        endGame();
    }

    public void resign(View view) {
        chessGame.gameEndType = chessGame.playerTurn ? BLACK_RESIGNS : WHITE_RESIGNS;
        endGame();
    }

    public void endGame() {
        if(chessGame.gameEndType == BLACK_RESIGNS || chessGame.gameEndType == WHITE_CHECKMATES){
            setContentView(R.layout.white_wins);
        }
        else if(chessGame.gameEndType == WHITE_RESIGNS || chessGame.gameEndType == BLACK_CHECKMATES){
            setContentView(R.layout.black_wins);
        }
        else{
            setContentView(R.layout.draw_screen);
        }
    }

    public void returnToMainMenu(View view){
        Intent chess_intent=new Intent(this, MainActivity.class);
        startActivity(chess_intent);
    }

    public void saveGame(View view){
        setContentView(R.layout.save_game);
    }

    public void saveAndReturnToMainMenu(View view) {
        EditText title = (EditText)findViewById(R.id.game_title);
        chessGame.name = title.getText().toString();
        chessGame.date = Calendar.getInstance();
        rg.rg.add(chessGame);
        Intent chess_intent=new Intent(this, MainActivity.class);
        startActivity(chess_intent);
    }

}