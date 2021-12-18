package com.example.androidchess;

import com.example.androidchess.piece.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class ChessGame {

    static final int REGULAR_MOVE = 0;
    static final int CAPTURE = 1;
    static final int QUEENSIDE_CASTLE = 2;
    static final int KINGSIDE_CASTLE = 3;
    static final int PROMOTION = 4;
    static final int PROMOTION_CAPTURE = 5;
    static final int PAWN_FIRST_MOVE = 6;
    static final int EN_PASSANT = 7;

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

    static final int DRAW = 0;
    static final int WHITE_RESIGNS = 1;
    static final int BLACK_RESIGNS = 2;
    static final int WHITE_CHECKMATES = 3;
    static final int BLACK_CHECKMATES = 4;

    static final int ROOK = 0;
    static final int KNIGHT = 1;
    static final int BISHOP = 2;
    static final int QUEEN = 3;

    public ArrayList<Piece[][]> game;
    public Piece[][] currentBoard;
    public int gameEndType;
    public boolean playerTurn;

    public String name;
    public Calendar date;

    //    private class Square {
//
//        int file;
//        int rank;
//
//        public Square(int file, int rank) {
//            this.file = file;
//            this.rank = rank;
//        }
//
//        @Override
//        public boolean equals(Object obj) {
//            if (obj == null) return false;
//            return (file == ((Square) obj).file && rank == ((Square) obj).rank);
//        }
//
//    }
    public ChessGame() {
        game = new ArrayList<>();
        currentBoard = new Piece[8][8];
        initializeBoard(currentBoard);
        game.add(currentBoard);
        gameEndType = -1;
        playerTurn = WHITE;
    }
    public void initializeBoard(Piece[][] board) {
        for (int i = 0; i < board.length; i++) {
            board[i][1] = new Pawn(board, WHITE, i, 1);
            board[i][6] = new Pawn(board, BLACK, i, 6);
        }
        board[0][0] = new Rook(board, WHITE, 0, 0);
        board[1][0] = new Knight(board, WHITE, 1, 0);
        board[2][0] = new Bishop(board, WHITE, 2, 0);
        board[3][0] = new Queen(board, WHITE, 3, 0);
        board[4][0] = new King(board, WHITE, 4, 0);
        board[5][0] = new Bishop(board, WHITE, 5, 0);
        board[6][0] = new Knight(board, WHITE, 6, 0);
        board[7][0] = new Rook(board, WHITE, 7, 0);
        board[0][7] = new Rook(board, BLACK, 0, 7);
        board[1][7] = new Knight(board, BLACK, 1, 7);
        board[2][7] = new Bishop(board, BLACK, 2, 7);
        board[3][7] = new Queen(board, BLACK, 3, 7);
        board[4][7] = new King(board, BLACK, 4, 7);
        board[5][7] = new Bishop(board, BLACK, 5, 7);
        board[6][7] = new Knight(board, BLACK, 6, 7);
        board[7][7] = new Rook(board, BLACK, 7, 7);
        updateAllMoves(board);
    }
    public int attemptMove(Piece[][] board, BoardHolder result, int file, int rank, int newFile, int newRank) {
        Move move = null;
        boolean moveExists = false;
        for (Move m : board[file][rank].moves) {
            if (m.file == newFile && m.rank == newRank) {
                move = m;
                moveExists = true;
                break;
            }
        }

        if (!moveExists) {
            return ILLEGAL_MOVE;
        }

        result.board = cloneBoard(board);
        Piece piece = result.board[file][rank];
        piece.file = newFile;
        piece.rank = newRank;
        if (move.type == REGULAR_MOVE || move.type == CAPTURE) {
            result.board[file][rank] = null;
            result.board[newFile][newRank] = piece;
            if (isChecked(result.board, piece.color)) {
                return SELF_CHECK;
            } else {
                return VALID_MOVE;
            }
        } else if (move.type == QUEENSIDE_CASTLE) {
            Square s1 = new Square(4, rank);
            Square s2 = new Square(3, rank);
            Square s3 = new Square(2, rank);
            boolean safe = true;
            for (Square s : getThreats(board, !piece.color)) {
                if (s.equals(s1) || s.equals(s2) || s.equals(s3)) {
                    safe = false;
                }
            }
            if (safe) {
                result.board[file][rank] = null;
                result.board[newFile][newRank] = piece;
                piece = result.board[0][rank];
                result.board[0][rank] = null;
                result.board[3][rank] = piece;
                return VALID_MOVE;
            } else {
                return ILLEGAL_CASTLE;
            }
        } else if (move.type == KINGSIDE_CASTLE) {
            Square s1 = new Square(4, rank);
            Square s2 = new Square(5, rank);
            Square s3 = new Square(6, rank);
            boolean safe = true;
            for (Square s : getThreats(board, !piece.color)) {
                if (s.equals(s1) || s.equals(s2) || s.equals(s3)) {
                    safe = false;
                }
            }
            if (safe) {
                result.board[file][rank] = null;
                result.board[newFile][newRank] = piece;
                piece = result.board[7][rank];
                result.board[7][rank] = null;
                result.board[5][rank] = piece;
                return VALID_MOVE;
            } else {
                return ILLEGAL_CASTLE;
            }
        } else if (move.type == PROMOTION || move.type == PROMOTION_CAPTURE) {
            result.board[file][rank] = null;
            result.board[newFile][newRank] = piece;
            if (isChecked(result.board, piece.color)) {
                return SELF_CHECK;
            } else {
                return PAWN_PROMOTION;
            }
        } else if (move.type == PAWN_FIRST_MOVE) {
            ((Pawn) result.board[file][rank]).firstMove = false;
            ((Pawn) result.board[file][rank]).enPassant = true;
            result.board[file][rank] = null;
            result.board[newFile][newRank] = piece;
            if (isChecked(result.board, piece.color)) {
                return SELF_CHECK;
            } else {
                return VALID_MOVE;
            }
        } else if (move.type == EN_PASSANT) {
            result.board[file][rank] = null;
            result.board[newFile][newRank] = piece;
            result.board[newFile][rank] = null;
            if (isChecked(result.board, piece.color)) {
                return SELF_CHECK;
            } else {
                return VALID_MOVE;
            }
        }
        return -1;
    }
    public void updateAllMoves(Piece[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != null) {
                    board[i][j].updateMoves();
                }
            }
        }
    }
    public ArrayList<Square> getThreats(Piece[][] board, boolean color) {
        ArrayList<Square> threats = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].color == color) {
                        if (board[i][j] instanceof Pawn) {
                            for (Move m : ((Pawn) board[i][j]).threatenedSquares) {
                                threats.add(new Square(m.file, m.rank));
                            }
                        } else {
                            for (Move m : board[i][j].moves) {
                                threats.add(new Square(m.file, m.rank));
                            }
                        }
                    }
                }
            }
        }
        return threats;
    }
    public boolean isChecked(Piece[][] board, boolean color) {
        updateAllMoves(board);
        Square king = null;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != null) {
                    if (board[i][j] instanceof King && board[i][j].color == color) {
                        king = new Square(i, j);
                    }
                }
            }
        }
        ArrayList<Square> threats = getThreats(board, !color);
        for (Square s : threats) {
            if (king.equals(s)) return true;
        }
        return false;
    }
    public boolean isCheckmated(Piece[][] board, boolean color) {
        updateAllMoves(board);
        boolean checkmated = true;
        BoardHolder result = new BoardHolder(null);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].color == color) {
                        for (Move m : board[i][j].moves) {
                            int outcome = attemptMove(board, result, i, j, m.file, m.rank);
                            if (outcome == VALID_MOVE || outcome == PAWN_PROMOTION) {
                                checkmated = false;
                                break;
                            }
                        }
                    }
                }
                if (checkmated == false) {
                    break;
                }
            }
            if (checkmated == false) {
                break;
            }
        }
        return checkmated;
    }
    public int[] getRandomMove(Piece[][] board, boolean color) {
        BoardHolder result = new BoardHolder(null);
        ArrayList<Square> squares = new ArrayList<>();
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                squares.add(new Square(i, j));
            }
        }
        Collections.shuffle(squares);
        for (Square s : squares) {
            if (board[s.file][s.rank] != null) {
                if (board[s.file][s.rank].color == color) {
                    Collections.shuffle(board[s.file][s.rank].moves);
                    for (Move m : board[s.file][s.rank].moves) {
                        int outcome = attemptMove(board, result, s.file, s.rank, m.file, m.rank);
                        if (outcome == VALID_MOVE || outcome == PAWN_PROMOTION) {
                            int[] originDestinationPair = new int[4];
                            originDestinationPair[0] = s.file;
                            originDestinationPair[1] = s.rank;
                            originDestinationPair[2] = m.file;
                            originDestinationPair[3] = m.rank;
                            return originDestinationPair;
                        }
                    }
                }
            }
        }
        return null;
    }
    public int move(int file, int rank, int newFile, int newRank) {
        disableEnPassants(currentBoard, playerTurn);

        if (currentBoard[file][rank] == null) {
            return NO_PIECE_EXISTS;
        } else if (currentBoard[file][rank].color != playerTurn) {
            return INVALID_PLAYER_TURN;
        }

        BoardHolder result = new BoardHolder(null);
        int outcome = attemptMove(currentBoard, result, file, rank, newFile, newRank);

        if (outcome == ILLEGAL_MOVE || outcome == SELF_CHECK || outcome == ILLEGAL_CASTLE) {
            return outcome;
        }

        currentBoard = result.board;

        if (outcome == PAWN_PROMOTION) {
            return outcome;
        }

        game.add(currentBoard);
        updateAllMoves(currentBoard);

        if (isChecked(currentBoard, !playerTurn)) {
            outcome = CHECKED;
        }
        if (isCheckmated(currentBoard, !playerTurn)) {
            gameEndType = playerTurn ? BLACK_CHECKMATES : WHITE_CHECKMATES;
            outcome = CHECKMATED;
        }

        playerTurn = !playerTurn;
        return outcome;
    }
    public void disableEnPassants(Piece[][] board, boolean color) {
        if (color == WHITE) {
            for (int i = 0; i < board.length; i++) {
                if (board[i][3] instanceof Pawn) {
                    if (board[i][3].color == color) {
                        ((Pawn) board[i][3]).enPassant = false;
                    }
                }
            }
        } else {
            for (int i = 0; i < board.length; i++) {
                if (board[i][4] instanceof Pawn) {
                    if (board[i][4].color == color) {
                        ((Pawn) board[i][4]).enPassant = false;
                    }
                }
            }
        }
    }
    public int promote(int type, int file, int rank) {
        if (type == ROOK) {
            currentBoard[file][rank] = new Rook(currentBoard, currentBoard[file][rank].color, file, rank);
            ((Rook) currentBoard[file][rank]).canCastle = false;
        } else if (type == KNIGHT) {
            currentBoard[file][rank] = new Knight(currentBoard, currentBoard[file][rank].color, file, rank);
        } else if (type == BISHOP) {
            currentBoard[file][rank] = new Bishop(currentBoard, currentBoard[file][rank].color, file, rank);
        } else if (type == QUEEN) {
            currentBoard[file][rank] = new Queen(currentBoard, currentBoard[file][rank].color, file, rank);
        }

        game.add(currentBoard);
        updateAllMoves(currentBoard);

        int outcome = PAWN_PROMOTION;
        if (isChecked(currentBoard, !playerTurn)) {
            outcome = CHECKED;
        }
        if (isCheckmated(currentBoard, !playerTurn)) {
            gameEndType = playerTurn ? WHITE_CHECKMATES : BLACK_CHECKMATES;
            outcome = CHECKMATED;
        }

        playerTurn = !playerTurn;
        return outcome;
    }
    public void undo() {
        if (game.size() == 1) {
            return;
        }
        game.remove(game.size() - 1);
        currentBoard = game.get(game.size() - 1);
        playerTurn = !playerTurn;
    }
    public Piece[][] cloneBoard(Piece[][] board) {
        Piece[][] clone = new Piece[8][8];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != null) {
                    board[i][j].copyToBoard(clone);
                }
            }
        }
        return clone;
    }
    public static void drawBoard(Piece[][] board) {
        for (int j = 7; j >= 0; j--) {
            for (int i = 0; i < board.length; i++) {
                if (board[i][j] == null) {
                    if ((i + j) % 2 == 0) {
                        System.out.print("  ");
                    } else {
                        System.out.print("##");
                    }
                } else {
                    if (board[i][j].color == false) {
                        if (board[i][j] instanceof Pawn) {
                            System.out.print("wp");
                        } else if (board[i][j] instanceof Rook) {
                            System.out.print("wR");
                        } else if (board[i][j] instanceof Knight) {
                            System.out.print("wN");
                        } else if (board[i][j] instanceof Bishop) {
                            System.out.print("wB");
                        } else if (board[i][j] instanceof Queen) {
                            System.out.print("wQ");
                        } else if (board[i][j] instanceof King) {
                            System.out.print("wK");
                        }
                    } else {
                        if (board[i][j] instanceof Pawn) {
                            System.out.print("bp");
                        } else if (board[i][j] instanceof Rook) {
                            System.out.print("bR");
                        } else if (board[i][j] instanceof Knight) {
                            System.out.print("bN");
                        } else if (board[i][j] instanceof Bishop) {
                            System.out.print("bB");
                        } else if (board[i][j] instanceof Queen) {
                            System.out.print("bQ");
                        } else if (board[i][j] instanceof King) {
                            System.out.print("bK");
                        }
                    }
                }
                System.out.print(" ");
            }
            System.out.println(j + 1);
        }
        for (char c = 'a'; c <= 'h'; c++) {
            System.out.print(" " + c + " ");
        }
        System.out.println();
    }

}
