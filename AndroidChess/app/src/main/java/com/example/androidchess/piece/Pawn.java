package com.example.androidchess.piece;

import java.io.Serializable;
import java.util.ArrayList;

public class Pawn extends Piece implements Serializable {

    public ArrayList<Move> threatenedSquares;
    public boolean firstMove;
    public boolean enPassant;

    public Pawn(Piece[][] board, boolean color, int file, int rank) {
        this.board = board;
        this.color = color;
        this.file = file;
        this.rank = rank;
        firstMove = true;
        enPassant = false;
    }

    public void updateMoves() {
        moves = new ArrayList<>();
        threatenedSquares = new ArrayList<>();
        if (!color) {
            if (file < 7) {
                threatenedSquares.add(new Move(file + 1, rank + 1, CAPTURE));
            }
            if (file > 0) {
                threatenedSquares.add(new Move(file - 1, rank + 1, CAPTURE));
            }
            if (rank + 1 == 7) {
                if (board[file][rank + 1] == null) {
                    moves.add(new Move(file, rank + 1, PROMOTION));
                }
                if (file < 7) {
                    if (board[file + 1][rank + 1] != null) {
                        if (board[file + 1][rank + 1].color != color) {
                            moves.add(new Move(file + 1, rank + 1, PROMOTION_CAPTURE));
                        }
                    }
                }
                if (file > 0) {
                    if (board[file - 1][rank + 1] != null) {
                        if (board[file - 1][rank + 1].color != color) {
                            moves.add(new Move(file - 1, rank + 1, PROMOTION_CAPTURE));
                        }
                    }
                }
            } else if (rank != 7) {
                if (board[file][rank + 1] == null) {
                    moves.add(new Move(file, rank + 1, REGULAR_MOVE));
                    if (firstMove && board[file][rank + 2] == null) {
                        moves.add(new Move(file, rank + 2, PAWN_FIRST_MOVE));
                    }
                }
                if (file < 7) {
                    if (board[file + 1][rank + 1] != null) {
                        if (board[file + 1][rank + 1].color != color) {
                            moves.add(new Move(file + 1, rank + 1, CAPTURE));
                        }
                    } else if (board[file + 1][rank] instanceof Pawn) {
                        if (board[file + 1][rank].color != color && ((Pawn) board[file + 1][rank]).enPassant) {
                            moves.add(new Move(file + 1, rank + 1, EN_PASSANT));
                        }
                    }
                }
                if (file > 0) {
                    if (board[file - 1][rank + 1] != null) {
                        if (board[file - 1][rank + 1].color != color) {
                            moves.add(new Move(file - 1, rank + 1, CAPTURE));
                        }
                    } else if (board[file - 1][rank] instanceof Pawn) {
                        if (board[file - 1][rank].color != color && ((Pawn) board[file - 1][rank]).enPassant) {
                            moves.add(new Move(file - 1, rank + 1, EN_PASSANT));
                        }
                    }
                }
            }
        } else {
            if (file < 7) {
                threatenedSquares.add(new Move(file + 1, rank - 1, CAPTURE));
            }
            if (file > 0) {
                threatenedSquares.add(new Move(file - 1, rank - 1, CAPTURE));
            }
            if (rank - 1 == 0) {
                if (board[file][rank - 1] == null) {
                    moves.add(new Move(file, rank - 1, PROMOTION));
                }
                if (file < 7) {
                    if (board[file + 1][rank - 1] != null) {
                        if (board[file + 1][rank - 1].color != color) {
                            moves.add(new Move(file + 1, rank - 1, PROMOTION_CAPTURE));
                        }
                    }
                }
                if (file > 0) {
                    if (board[file - 1][rank - 1] != null) {
                        if (board[file - 1][rank - 1].color != color) {
                            moves.add(new Move(file - 1, rank - 1, PROMOTION_CAPTURE));
                        }
                    }
                }
            } else if (rank != 0) {
                if (board[file][rank - 1] == null) {
                    moves.add(new Move(file, rank - 1, REGULAR_MOVE));
                    if (firstMove && board[file][rank - 2] == null) {
                        moves.add(new Move(file, rank - 2, PAWN_FIRST_MOVE));
                    }
                }
                if (file < 7) {
                    if (board[file + 1][rank - 1] != null) {
                        if (board[file + 1][rank - 1].color != color) {
                            moves.add(new Move(file + 1, rank - 1, CAPTURE));
                        }
                    } else if (board[file + 1][rank] instanceof Pawn) {
                        if (board[file + 1][rank].color != color && ((Pawn) board[file + 1][rank]).enPassant) {
                            moves.add(new Move(file + 1, rank - 1, EN_PASSANT));
                        }
                    }
                }
                if (file > 0) {
                    if (board[file - 1][rank - 1] != null) {
                        if (board[file - 1][rank - 1].color != color) {
                            moves.add(new Move(file - 1, rank - 1, CAPTURE));
                        }
                    } else if (board[file - 1][rank] instanceof Pawn) {
                        if (board[file - 1][rank].color != color && ((Pawn) board[file - 1][rank]).enPassant) {
                            moves.add(new Move(file - 1, rank - 1, EN_PASSANT));
                        }
                    }
                }
            }
        }
    }

    public void copyToBoard(Piece[][] board) {
        Pawn clone = new Pawn(board, color, file, rank);
        clone.moves = moves;
        clone.threatenedSquares = threatenedSquares;
        clone.firstMove = firstMove;
        clone.enPassant = enPassant;
        board[file][rank] = clone;
    }

}
