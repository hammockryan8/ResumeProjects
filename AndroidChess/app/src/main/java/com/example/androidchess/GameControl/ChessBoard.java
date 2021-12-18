package com.example.androidchess.GameControl;

import com.example.androidchess.Model.*;
import java.util.*;
import java.util.Scanner;

public class ChessBoard {


    /***
     * moves the intended piece on the board from board[originRank][originFile] to board[newRank][newFile]
     * @param moveNumber the current number of moves that have been played during the game
     * @param originFile the original rank of the piece on the board
     * @param originRank the original file of the piece on the board
     * @param newFile the intended file to move the piece to on the board
     * @param newRank the intended rank to move the piece to on the board
     * @param move the string that the player input that represents the move
     * @param board the Piece board double array that stores all the pieces
     * @param colorKing something to update where the respective king piece is for one side if it changed
     */
    public static void movePiece(int moveNumber, int originFile, int originRank, int newFile, int newRank, String move, Piece[][]board, Piece colorKing) {
        if(board[originRank][originFile] instanceof Pawn) {
            if(!board[originRank][originFile].getColor()) {
                if(newRank == 0) {
                    //promotion for white pawns
                    if(move.length() == 5) {
                        board[newRank][newFile] = new Queen(board[originRank][originFile].getColor(), newFile, newRank);
                    }
                    else if(move.endsWith("N")) {
                        board[newRank][newFile] = new Knight(board[originRank][originFile].getColor(), newFile, newRank);
                    }
                    else if(move.endsWith("B")) {
                        board[newRank][newFile] = new Bishop(board[originRank][originFile].getColor(), newFile, newRank);
                    }
                    else if(move.endsWith("R")) {
                        board[newRank][newFile] = new Bishop(board[originRank][originFile].getColor(), newFile, newRank);
                    }
                }
            }
            else {
                if(newRank == 7) {
                    //promotion for black pawns
                    if(move.length() == 5) {
                        board[newRank][newFile] = new Queen(board[originRank][originFile].getColor(), newFile, newRank);
                    }
                    else if(move.endsWith("N")) {
                        board[newRank][newFile] = new Knight(board[originRank][originFile].getColor(), newFile, newRank);
                    }
                    else if(move.endsWith("B")) {
                        board[newRank][newFile] = new Bishop(board[originRank][originFile].getColor(), newFile, newRank);
                    }
                    else if(move.endsWith("R")) {
                        board[newRank][newFile] = new Bishop(board[originRank][originFile].getColor(), newFile, newRank);
                    }
                }
            }
            if(Math.abs(originRank - newRank) == 2) {
                board[newRank][newFile] = new Pawn((board[originRank][originFile]).getColor(), newFile, newRank, moveNumber, true);
            }
            else {
                board[newRank][newFile] = new Pawn((board[originRank][originFile]).getColor(), newFile, newRank, 0, false);
            }
        }
        else if(board[originRank][originFile] instanceof Knight) {
            board[newRank][newFile] = new Knight((board[originRank][originFile]).getColor(), newFile, newRank);
        }
        else if(board[originRank][originFile] instanceof Bishop) {
            board[newRank][newFile] = new Bishop((board[originRank][originFile]).getColor(), newFile, newRank);
        }
        else if(board[originRank][originFile] instanceof Rook) {
            board[newRank][newFile] = new Rook((board[originRank][originFile]).getColor(), newFile, newRank);
        }
        else if(board[originRank][originFile] instanceof Queen) {
            board[newRank][newFile] = new Queen((board[originRank][originFile]).getColor(), newFile, newRank);
        }
        else if(board[originRank][originFile] instanceof King) {
            board[newRank][newFile] = new King((board[originRank][originFile]).getColor(), newFile, newRank);
            colorKing.setRank(newRank);
            colorKing.setFile(newFile);
        }
        board[originRank][originFile] = null;
    }

    public static boolean checkmate(boolean color, Piece [][] board, int kingRank, int kingFile) {
        ArrayList<Piece> enemies = new ArrayList<Piece>();
        ArrayList<Piece> allies = new ArrayList<Piece>();
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(board[i][j] != null && (board[i][j].getColor() == color)) {
                    if(i != kingRank && j != kingFile) {
                        allies.add(board[i][j]);
                    }
                }
            }
        }
        ArrayList<Piece> possiblePawnMoves = new ArrayList<Piece>();
        ArrayList<Piece> possibleKnightMoves = new ArrayList<Piece>();
        ArrayList<Piece> possibleBishopMoves = new ArrayList<Piece>();
        for(Piece ally : allies) {
            if(ally instanceof Pawn) {

            }
            else if(ally instanceof Knight) {

            }
            else if(ally instanceof Bishop) {

            }
            else if(ally instanceof Rook) {

            }
            else if(ally instanceof Queen) {

            }

        }
        return false;
    }
	/*
	public static boolean isInCheckmate(boolean color, Piece [][] board, int kingRank, int kingFile, ArrayList<Piece> allies, int moveNumber) {
		boolean noSafeSquare = false;
		boolean noAlly = false;

		boolean bottomLeftCorner = false;
		boolean bottomRightCorner = false;
		boolean topLeftCorner = false;
		boolean topRightCorner = false;
		boolean floor = false;
		boolean ceiling = false;
		boolean leftWall = false;
		boolean rightWall = false;
		boolean freeForAll = false;

		//make a check to see if there's an unthreatened square the king can move to
		if(kingRank == 7 && kingFile == 0) {
			bottomLeftCorner = true;
			boolean frontRight = false;
			boolean front = false;
			boolean right = false;

			if(board[6][1] != null) {
				frontRight = true;
			}
			else {
				frontRight = isInCheck(color, board, 6, 1);
			}

			if(board[6][0] != null) {
				front = true;
			}
			else {
				front = isInCheck(color, board, 6, 0);
			}

			if(board[7][1] != null) {
				right = true;
			}
			else {
				right = isInCheck(color, board, 7, 1);
			}

			if(frontRight && front && right) {
				noSafeSquare = true;
			}
		}
		else if(kingRank == 7 && kingFile == 7) {
			bottomRightCorner = true;
			boolean frontLeft = false;
			boolean front = false;
			boolean left = false;

			if(board[6][6] != null) {
				frontLeft = true;
			}
			else {
				frontLeft = isInCheck(color, board, 6, 6);
			}

			if(board[6][7] != null) {
				front = true;
			}
			else {
				front = isInCheck(color, board, 6, 7);
			}

			if(board[7][6] != null) {
				left = true;
			}
			else {
				left = isInCheck(color, board, 7, 6);
			}

			if(frontLeft && front && left) {
				noSafeSquare = true;
			}
		}
		else if(kingRank == 0 && kingFile == 0) {
			topLeftCorner = true;
			boolean bottomRight = false;
			boolean bottom = false;
			boolean right = false;

			if(board[1][1] != null) {
				bottomRight = true;
			}
			else {
				bottomRight = isInCheck(color, board, 1, 1);
			}

			if(board[1][0] != null) {
				bottom = true;
			}
			else {
				bottom = isInCheck(color, board, 1, 0);
			}

			if(board[0][1] != null) {
				right = true;
			}
			else {
				right = isInCheck(color, board, 0, 1);
			}

			if(bottomRight && bottom && right) {
				noSafeSquare = true;
			}
		}
		else if(kingRank == 0 && kingFile == 7) {
			topRightCorner = true;
			boolean bottomLeft = false;
			boolean bottom = false;
			boolean left = false;

			if(board[1][6] != null) {
				bottomLeft = true;
			}
			else{
				bottomLeft = isInCheck(color, board, 1, 6);
			}

			if(board[1][7] != null) {
				bottom = true;
			}
			else {
				bottom = isInCheck(color, board, 1, 7);
			}

			if(board[0][6] != null) {
				left = true;
			}
			else {
				left = isInCheck(color, board, 0, 6);
			}

			if(bottomLeft && bottom && left) {
				noSafeSquare = true;
			}
		}
		else if(kingRank == 7) {
			floor = true;
			boolean topRight = false;
			boolean topLeft = false;
			boolean top = false;
			boolean left = false;
			boolean right = false;

			if(board[6][kingFile + 1] != null) {
				topRight = true;
			}
			else {
				topRight = isInCheck(color, board, 6, kingFile + 1);
			}

			if(board[6][kingFile - 1] != null) {
				topLeft = true;
			}
			else {
				topLeft = isInCheck(color, board, 6, kingFile - 1);
			}

			if(board[6][kingFile] != null) {
				top = true;
			}
			else {
				top = isInCheck(color, board, 6, kingFile);
			}

			if(board[7][kingFile - 1] != null) {
				left = true;
			}
			else {
				left = isInCheck(color, board, 7, kingFile - 1);
			}

			if(board[7][kingFile + 1] != null) {
				right = true;
			}
			else {
				right = isInCheck(color, board, 7, kingFile + 1);
			}

			if(topRight && topLeft && top && left && right) {
				noSafeSquare = true;
			}
		}
		else if(kingRank == 0) {
			ceiling = true;
			boolean bottomRight = false;
			boolean bottomLeft = false;
			boolean bottom = false;
			boolean left = false;
			boolean right = false;

			if(board[1][kingFile + 1] != null) {
				bottomRight = true;
			}
			else {
				bottomRight = isInCheck(color, board, 1, kingFile + 1);
			}

			if(board[1][kingFile - 1] != null) {
				bottomLeft = true;
			}
			else {
				bottomLeft = isInCheck(color, board, 1, kingFile - 1);
			}

			if(board[1][kingFile] != null) {
				bottom = true;
			}
			else {
				bottom = isInCheck(color, board, 1, kingFile);
			}

			if(board[0][kingFile - 1] != null) {
				left = true;
			}
			else {
				left = isInCheck(color, board, 0, kingFile - 1);
			}

			if(board[0][kingFile + 1] != null) {
				right = true;
			}
			else {
				right = isInCheck(color, board, 0, kingFile + 1);
			}

			if(bottomRight && bottomLeft && bottom && left && right) {
				noSafeSquare = true;
			}
		}
		else if(kingFile == 0) {
			leftWall = true;
			boolean topRight = false;
			boolean bottomRight = false;
			boolean top = false;
			boolean bottom = false;
			boolean right = false;

			if(board[kingRank - 1][1] != null) {
				topRight = true;
			}
			else {
				topRight = isInCheck(color, board, kingRank - 1, 1);
			}

			if(board[kingRank + 1][1] != null) {
				bottomRight = true;
			}
			else {
				bottomRight = isInCheck(color, board, kingRank + 1, 1);
			}

			if(board[kingRank - 1][0] != null) {
				top = true;
			}
			else {
				top = isInCheck(color, board, kingRank - 1, 0);
			}

			if(board[kingRank + 1][0] != null) {
				bottom = true;
			}
			else {
				bottom = isInCheck(color, board, kingRank + 1, 0);
			}

			if(board[kingRank][1] != null) {
				right = true;
			}
			else {
				right = isInCheck(color, board, kingRank, 1);
			}

			if(topRight && bottomRight && top && bottom && right) {
				noSafeSquare = true;
			}
		}
		else if(kingFile == 7) {
			rightWall = true;
			boolean topLeft = false;
			boolean bottomLeft = false;
			boolean top = false;
			boolean bottom = false;
			boolean left = false;

			if(board[kingRank - 1][6] != null) {
				topLeft = true;
			}
			else {
				topLeft = isInCheck(color, board, kingRank - 1, 6);
			}

			if(board[kingRank + 1][6] != null) {
				bottomLeft = true;
			}
			else {
				bottomLeft = isInCheck(color, board, kingRank + 1, 6);
			}

			if(board[kingRank - 1][7] != null) {
				top = true;
			}
			else {
				top = isInCheck(color, board, kingRank - 1, 7);
			}

			if(board[kingRank + 1][7] != null) {
				bottom = true;
			}
			else {
				bottom = isInCheck(color, board, kingRank + 1, 7);
			}

			if(board[kingRank][6] != null) {
				left = true;
			}
			else {
				left = isInCheck(color, board, kingRank, 6);
			}

			if(topLeft && bottomLeft && top && bottom && left) {
				noSafeSquare = true;
			}
		}
		else {
			freeForAll = true;
			boolean topRight = false;
			boolean bottomRight = false;
			boolean topLeft = false;
			boolean bottomLeft = false;
			boolean top = false;
			boolean bottom = false;
			boolean left = false;
			boolean right = false;

			if(board[kingRank - 1][kingFile + 1] != null) {
				topRight = true;
			}
			else {
				topRight = isInCheck(color, board, kingRank - 1, kingFile + 1);
			}

			if(board[kingRank + 1][kingFile + 1] != null) {
				bottomRight = true;
			}
			else {
				bottomRight = isInCheck(color, board, kingRank + 1, kingFile + 1);
			}

			if(board[kingRank - 1][kingFile - 1] != null) {
				topLeft = true;
			}
			else {
				topLeft = isInCheck(color, board, kingRank - 1, kingFile - 1);
			}

			if(board[kingRank + 1][kingFile - 1] != null) {
				bottomLeft = true;
			}
			else {
				bottomLeft = isInCheck(color, board, kingRank + 1, kingFile - 1);
			}

			if(board[kingRank - 1][kingFile] != null) {
				top = true;
			}
			else {
				top = isInCheck(color, board, kingRank - 1, kingFile);
			}

			if(board[kingRank + 1][kingFile] != null) {
				bottom = true;
			}
			else {
				bottom = isInCheck(color, board, kingRank + 1, kingFile);
			}

			if(board[kingRank][kingFile - 1] != null) {
				left = true;
			}
			else {
				left = isInCheck(color, board, kingRank, kingFile - 1);
			}

			if(board[kingRank][kingFile + 1] != null) {
				right = true;
			}
			else {
				right = isInCheck(color, board, kingRank, kingFile + 1);
			}

			if(topRight && bottomRight && topLeft && bottomLeft && top && bottom && left && right) {
				noSafeSquare = true;
			}
		}


		if(noSafeSquare) {
			ArrayList<Piece> threateningTheKing = new ArrayList<Piece>();
			if(bottomLeftCorner) {
				for(int i = 6; i > -1; i--) {
					if(board[i][0] != null) {
						if(board[i][0] instanceof Rook) {
							Rook possRook = (Rook) board[i][0];
							if(possRook.getColor() != color) {
								threateningTheKing.add(board[i][0]);
							}
						}
						else if(board[i][0] instanceof Queen) {
							Queen possQueen = (Queen) board[i][0];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][0]);
							}
						}
						break;
					}
				}
				for(int i = 1; i < 8; i++) {
					if(board[7][i] != null) {
						if(board[7][i] instanceof Rook) {
							Rook possRook = (Rook) board[7][i];
							if(possRook.getColor() != color) {
								threateningTheKing.add(board[7][i]);
							}
						}
						else if(board[7][i] instanceof Queen) {
							Queen possQueen = (Queen) board[7][i];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[7][i]);
							}
						}
						break;
					}
				}
				for(int i = 6, j = 1; i > -1 && j < 8; i--, j++) {
					if(board[i][j] != null) {
						if(board[i][j] instanceof Bishop) {
							Bishop possBishop = (Bishop) board[i][j];
							if(possBishop.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						else if(board[i][j] instanceof Queen) {
							Queen possQueen = (Queen) board[i][j];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						break;
					}
				}
				if(board[5][1] != null) {
					if(board[5][1] instanceof Knight) {
						Knight possKnight = (Knight) board[5][1];
						if(possKnight.getColor() != color) {
							threateningTheKing.add(board[5][1]);
						}
					}
				}
				if(board[6][2] != null) {
					if(board[6][2] instanceof Knight) {
						Knight possKnight = (Knight) board[6][2];
						if(possKnight.getColor() != color) {
							threateningTheKing.add(board[6][2]);
						}
					}
				}
			}
			else if(bottomRightCorner) {
				for(int i = 6; i > -1; i--) {
					if(board[i][7] != null) {
						if(board[i][7] instanceof Rook) {
							Rook possRook = (Rook) board[i][7];
							if(possRook.getColor() != color){
								threateningTheKing.add(board[i][7]);
							}
						}
						else if(board[i][7] instanceof Queen) {
							Queen possQueen = (Queen) board[i][7];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][7]);
							}
						}
						break;
					}
				}
				for(int i = 6; i > -1; i--) {
					if(board[7][i] != null) {
						if(board[7][i] instanceof Rook) {
							Rook possRook = (Rook) board[7][i];
							if(possRook.getColor() != color) {
								threateningTheKing.add(board[7][i]);
							}
						}
						else if(board[7][i] instanceof Queen) {
							Queen possQueen = (Queen) board[7][i];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[7][i]);
							}
						}
						break;
					}
				}
				for(int i = 6, j = 6; i > -1 && j > -1; i--, j--) {
					if(board[i][j] != null) {
						if(board[i][j] instanceof Bishop) {
							Bishop possBishop = (Bishop) board[i][j];
							if(possBishop.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						else if(board[i][j] instanceof Queen) {
							Queen possQueen = (Queen) board[i][j];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						break;
					}
				}
				if(board[6][5] != null) {
					if(board[6][5] instanceof Knight) {
						Knight possKnight = (Knight) board[6][5];
						if(possKnight.getColor() != color) {
							threateningTheKing.add(board[6][5]);
						}
					}
				}
				if(board[5][6] != null) {
					if(board[6][5] instanceof Knight) {
						Knight possKnight = (Knight) board[5][6];
						if(possKnight.getColor() != color) {
							threateningTheKing.add(board[5][6]);
						}
					}
				}
			}
			else if(topLeftCorner) {
				for(int i = 1; i < 8; i++) {
					if(board[i][0] != null) {
						if(board[i][0] instanceof Rook) {
							Rook possRook = (Rook) board[i][0];
							if(possRook.getColor() != color) {
								threateningTheKing.add(possRook);
							}
						}
						else if(board[i][0] instanceof Queen) {
							Queen possQueen = (Queen) board[i][0];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][0]);
							}
						}
						break;
					}
				}
				for(int i = 1; i < 8; i++) {
					if(board[0][i] != null) {
						if(board[0][i] instanceof Rook) {
							Rook possRook = (Rook) board[0][i];
							if(possRook.getColor() != color) {
								threateningTheKing.add(possRook);
							}
						}
						else if(board[i][0] instanceof Queen) {
							Queen possQueen = (Queen) board[0][i];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[0][i]);
							}
						}
						break;
					}
				}
				for(int i = 1, j = 1; i < 8 && j < 8; i++, j++) {
					if(board[i][j] != null) {
						if(board[i][j] instanceof Bishop) {
							Bishop possBishop = (Bishop) board[i][j];
							if(possBishop.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						else if(board[i][j] instanceof Queen) {
							Queen possQueen = (Queen) board[i][j];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						break;
					}
				}
				if(board[2][1] != null) {
					if(board[2][1] instanceof Knight) {
						Knight possKnight = (Knight)board[2][1];
						if(possKnight.getColor() != color) {
							threateningTheKing.add(board[2][1]);
						}
					}
				}
				if(board[1][2] != null) {
					if(board[1][2] instanceof Knight) {
						Knight possKnight = (Knight)board[1][2];
						if(possKnight.getColor() != color) {
							threateningTheKing.add(board[1][2]);
						}
					}
				}
			}
			else if(topRightCorner) {
				for(int i = 1; i < 8; i++) {
					if(board[i][7] != null) {
						if(board[i][7] instanceof Rook) {
							Rook possRook = (Rook) board[i][7];
							if(possRook.getColor() != color){
								threateningTheKing.add(board[i][7]);
							}
						}
						else if(board[i][7] instanceof Queen) {
							Queen possQueen = (Queen) board[i][7];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][7]);
							}
						}
						break;
					}
				}
				for(int i = 6; i > -1; i--) {
					if(board[0][i] != null) {
						if(board[0][i] instanceof Rook) {
							Rook possRook = (Rook) board[0][i];
							if(possRook.getColor() != color) {
								threateningTheKing.add(board[0][i]);
							}
						}
						else if(board[7][i] instanceof Queen) {
							Queen possQueen = (Queen) board[0][i];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[0][i]);
							}
						}
						break;
					}
				}
				for(int i = 1, j = 6; i < 8 && j > -1; i++, j--) {
					if(board[i][j] != null) {
						if(board[i][j] instanceof Bishop) {
							Bishop possBishop = (Bishop) board[i][j];
							if(possBishop.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						else if(board[i][j] instanceof Queen) {
							Queen possQueen = (Queen) board[i][j];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						break;
					}
				}
				if(board[2][6] != null) {
					if(board[2][6] instanceof Knight) {
						Knight possKnight = (Knight) board[2][6];
						if(possKnight.getColor() != color) {
							threateningTheKing.add(board[2][6]);
						}
					}
				}
				if(board[1][5] != null) {
					if(board[1][5] instanceof Knight) {
						Knight possKnight = (Knight) board[1][5];
						if(possKnight.getColor() != color) {
							threateningTheKing.add(board[1][5]);
						}
					}
				}
			}
			else if(floor) {
				for(int i = kingRank - 1; i > -1; i--) {
					if(board[i][kingFile] != null) {
						if(board[i][kingFile] instanceof Rook) {
							Rook possRook = (Rook) board[i][kingFile];
							if(possRook.getColor() != color) {
								threateningTheKing.add(board[i][kingFile]);
							}
						}
						else if(board[i][kingFile] instanceof Queen) {
							Queen possQueen = (Queen) board[i][kingFile];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][kingFile]);
							}
						}
						break;
					}
				}
				for(int i = kingFile - 1; i > -1; i--) {
					if(board[kingRank][i] != null) {
						if(board[kingRank][i] instanceof Rook) {
							Rook possRook = (Rook) board[kingRank][i];
							if(possRook.getColor() != color) {
								threateningTheKing.add(board[kingRank][i]);
							}
						}
						else if(board[kingRank][i] instanceof Queen) {
							Queen possQueen = (Queen) board[kingRank][i];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[kingRank][i]);
							}
						}
						break;
					}
				}
				for(int i = kingFile + 1; i < 8; i++) {
					if(board[kingRank][i] != null) {
						if(board[kingRank][i] instanceof Rook) {
							Rook possRook = (Rook) board[kingRank][i];
							if(possRook.getColor() != color) {
								threateningTheKing.add(board[kingRank][i]);
							}
						}
						else if(board[kingRank][i] instanceof Queen) {
							Queen possQueen = (Queen) board[kingRank][i];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[kingRank][i]);
							}
						}
						break;
					}
				}
				for(int i = kingRank - 1, j = kingFile - 1; i > -1 && j > -1; i--, j--) {
					if(board[i][j] != null) {
						if(board[i][j] instanceof Bishop) {
							Bishop possBishop = (Bishop) board[i][j];
							if(possBishop.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						else if(board[i][j] instanceof Queen) {
							Queen possQueen = (Queen) board[i][j];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						break;
					}
				}
				for(int i = kingRank - 1, j = kingFile + 1; i > -1 && j < 8; i--, j++) {
					if(board[i][j] != null) {
						if(board[i][j] instanceof Bishop) {
							Bishop possBishop = (Bishop) board[i][j];
							if(possBishop.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						else if(board[i][j] instanceof Queen) {
							Queen possQueen = (Queen) board[i][j];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						break;
					}
				}
				if(kingFile - 2 >= 0) {
					if(board[kingRank - 1][kingFile - 2] != null) {
						if(board[kingRank - 1][kingFile - 2] instanceof Knight) {
							Knight possKnight = (Knight) board[kingRank - 1][kingFile - 2];
							if(possKnight.getColor() != color) {
								threateningTheKing.add(board[kingRank - 1][kingFile - 2]);
							}
						}
					}
				}
				if(kingFile + 2 <= 7) {
					if(board[kingRank - 1][kingRank + 2] != null) {
						if(board[kingRank - 1][kingRank + 2] instanceof Knight) {
							Knight possKnight = (Knight) board[kingRank - 1][kingRank + 2];
							if(possKnight.getColor() != color) {
								threateningTheKing.add(board[kingRank - 1][kingRank + 2]);
							}
						}
					}
				}
				if(board[kingRank - 2][kingFile - 1] != null) {
					if(board[kingRank - 2][kingFile - 1] instanceof Knight) {
						Knight possKnight = (Knight)board[kingRank - 2][kingFile - 1];
						if(possKnight.getColor() != color) {
							threateningTheKing.add(board[kingRank - 2][kingFile - 1]);
						}
					}
				}
				if(board[kingRank - 2][kingFile + 1] != null) {
					if(board[kingRank - 2][kingFile + 1] instanceof Knight) {
						Knight possKnight = (Knight)board[kingRank - 2][kingFile + 1];
						if(possKnight.getColor() != color) {
							threateningTheKing.add(board[kingRank - 2][kingFile + 1]);
						}
					}
				}
			}
			else if(ceiling) {
				for(int i = kingRank + 1; i < 8; i++) {
					if(board[i][kingFile] != null) {
						if(board[i][kingFile] instanceof Rook) {
							Rook possRook = (Rook) board[i][kingFile];
							if(possRook.getColor() != color) {
								threateningTheKing.add(possRook);
							}
						}
						else if(board[i][kingFile] instanceof Queen) {
							Queen possQueen = (Queen) board[i][kingFile];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][kingFile]);
							}
						}
						break;
					}
				}
				for(int i = kingFile - 1; i > -1; i--) {
					if(board[kingRank][i] != null) {
						if(board[kingRank][i] instanceof Rook) {
							Rook possRook = (Rook) board[kingRank][i];
							if(possRook.getColor() != color) {
								threateningTheKing.add(board[kingRank][i]);
							}
						}
						else if(board[kingRank][i] instanceof Queen) {
							Queen possQueen = (Queen) board[kingRank][i];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[kingRank][i]);
							}
						}
						break;
					}
				}
				for(int i = kingFile + 1; i < 8; i++) {
					if(board[kingRank][i] != null) {
						if(board[kingRank][i] instanceof Rook) {
							Rook possRook = (Rook) board[kingRank][i];
							if(possRook.getColor() != color) {
								threateningTheKing.add(board[kingRank][i]);
							}
						}
						else if(board[kingRank][i] instanceof Queen) {
							Queen possQueen = (Queen) board[kingRank][i];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[kingRank][i]);
							}
						}
						break;
					}
				}
				for(int i = kingRank + 1, j = kingFile + 1; i < 8 && j < 8; i++, j++) {
					if(board[i][j] != null) {
						if(board[i][j] instanceof Bishop) {
							Bishop possBishop = (Bishop) board[i][j];
							if(possBishop.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						else if(board[i][j] instanceof Queen) {
							Queen possQueen = (Queen) board[i][j];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						break;
					}
				}
				for(int i = kingRank + 1, j = kingFile - 1; i < 8 && j > -1; i++, j--) {
					if(board[i][j] != null) {
						if(board[i][j] instanceof Bishop) {
							Bishop possBishop = (Bishop) board[i][j];
							if(possBishop.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						else if(board[i][j] instanceof Queen) {
							Queen possQueen = (Queen) board[i][j];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						break;
					}
				}
				if(kingFile - 2 >= 0) {
					if(board[kingRank + 1][kingFile - 2] != null) {
						if(board[kingRank + 1][kingFile - 2] instanceof Knight) {
							Knight possKnight = (Knight) board[kingRank + 1][kingFile - 2];
							if(possKnight.getColor() != color) {
								threateningTheKing.add(board[kingRank + 1][kingFile - 2]);
							}
						}
					}
				}
				if(kingFile + 2 <= 7) {
					if(board[kingRank + 1][kingRank + 2] != null) {
						if(board[kingRank + 1][kingRank + 2] instanceof Knight) {
							Knight possKnight = (Knight) board[kingRank + 1][kingRank + 2];
							if(possKnight.getColor() != color) {
								threateningTheKing.add(board[kingRank + 1][kingRank + 2]);
							}
						}
					}
				}
				if(board[kingRank + 2][kingFile - 1] != null) {
					if(board[kingRank + 2][kingFile - 1] instanceof Knight) {
						Knight possKnight = (Knight)board[kingRank + 2][kingFile - 1];
						if(possKnight.getColor() != color) {
							threateningTheKing.add(board[kingRank + 2][kingFile - 1]);
						}
					}
				}
				if(board[kingRank + 2][kingFile + 1] != null) {
					if(board[kingRank + 2][kingFile + 1] instanceof Knight) {
						Knight possKnight = (Knight)board[kingRank + 2][kingFile + 1];
						if(possKnight.getColor() != color) {
							threateningTheKing.add(board[kingRank + 2][kingFile + 1]);
						}
					}
				}
			}
			else if(leftWall) {
				for(int i = kingRank - 1; i > -1; i--) {
					if(board[i][kingFile] != null) {
						if(board[i][kingFile] instanceof Rook) {
							Rook possRook = (Rook) board[i][kingFile];
							if(possRook.getColor() != color) {
								threateningTheKing.add(board[i][kingFile]);
							}
						}
						else if(board[i][kingFile] instanceof Queen) {
							Queen possQueen = (Queen) board[i][kingFile];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][kingFile]);
							}
						}
						break;
					}
				}
				for(int i = kingRank + 1; i < 8; i++) {
					if(board[i][kingFile] != null) {
						if(board[i][kingFile] instanceof Rook) {
							Rook possRook = (Rook) board[i][kingFile];
							if(possRook.getColor() != color) {
								threateningTheKing.add(possRook);
							}
						}
						else if(board[i][kingFile] instanceof Queen) {
							Queen possQueen = (Queen) board[i][kingFile];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][kingFile]);
							}
						}
						break;
					}
				}
				for(int i = kingFile + 1; i < 8; i++) {
					if(board[kingRank][i] != null) {
						if(board[kingRank][i] instanceof Rook) {
							Rook possRook = (Rook) board[kingRank][i];
							if(possRook.getColor() != color) {
								threateningTheKing.add(board[kingRank][i]);
							}
						}
						else if(board[kingRank][i] instanceof Queen) {
							Queen possQueen = (Queen) board[kingRank][i];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[kingRank][i]);
							}
						}
						break;
					}
				}
				for(int i = kingRank - 1, j = kingFile + 1; i > -1 && j < 8; i--, j++) {
					if(board[i][j] != null) {
						if(board[i][j] instanceof Bishop) {
							Bishop possBishop = (Bishop) board[i][j];
							if(possBishop.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						else if(board[i][j] instanceof Queen) {
							Queen possQueen = (Queen) board[i][j];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						break;
					}
				}
				for(int i = kingRank + 1, j = kingFile + 1; i < 8 && j < 8; i++, j++) {
					if(board[i][j] != null) {
						if(board[i][j] instanceof Bishop) {
							Bishop possBishop = (Bishop) board[i][j];
							if(possBishop.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						else if(board[i][j] instanceof Queen) {
							Queen possQueen = (Queen) board[i][j];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						break;
					}
				}
				if(kingRank - 2 >= 0) {
					if(board[kingRank - 2][kingFile + 1] != null) {
						if(board[kingRank - 2][kingFile + 1] instanceof Knight) {
							Knight possKnight = (Knight) board[kingRank - 2][kingFile + 1];
							if(possKnight.getColor() != color) {
								threateningTheKing.add(board[kingRank - 2][kingFile + 1]);
							}
						}
					}
				}
				if(kingRank + 2 <= 7) {
					if(board[kingRank + 2][kingFile + 1] != null) {
						if(board[kingRank + 2][kingFile + 1] instanceof Knight) {
							Knight possKnight = (Knight) board[kingRank + 2][kingFile + 1];
							if(possKnight.getColor() != color) {
								threateningTheKing.add(board[kingRank + 2][kingFile + 1]);
							}
						}
					}
				}
				if(board[kingRank - 1][kingFile + 2] != null) {
					if(board[kingRank - 1][kingFile + 2] instanceof Knight) {
						Knight possKnight = (Knight) board[kingRank - 1][kingFile + 2];
						if(possKnight.getColor() != color) {
							threateningTheKing.add(board[kingRank - 1][kingFile + 2]);
						}
					}
				}
				if(board[kingRank + 1][kingFile + 2] != null) {
					if(board[kingRank + 1][kingFile + 2] instanceof Knight) {
						Knight possKnight = (Knight) board[kingRank + 1][kingFile + 2];
						if(possKnight.getColor() != color) {
							threateningTheKing.add(board[kingRank + 1][kingFile + 2]);
						}
					}
				}

			}
			else if(rightWall) {
				for(int i = kingRank - 1; i > -1; i--) {
					if(board[i][kingFile] != null) {
						if(board[i][kingFile] instanceof Rook) {
							Rook possRook = (Rook) board[i][kingFile];
							if(possRook.getColor() != color) {
								threateningTheKing.add(board[i][kingFile]);
							}
						}
						else if(board[i][kingFile] instanceof Queen) {
							Queen possQueen = (Queen) board[i][kingFile];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][kingFile]);
							}
						}
						break;
					}
				}
				for(int i = kingRank + 1; i < 8; i++) {
					if(board[i][kingFile] != null) {
						if(board[i][kingFile] instanceof Rook) {
							Rook possRook = (Rook) board[i][kingFile];
							if(possRook.getColor() != color) {
								threateningTheKing.add(possRook);
							}
						}
						else if(board[i][kingFile] instanceof Queen) {
							Queen possQueen = (Queen) board[i][kingFile];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][kingFile]);
							}
						}
						break;
					}
				}
				for(int i = kingRank + 1, j = kingFile - 1; i < 8 && j > -1; i++, j--) {
					if(board[i][j] != null) {
						if(board[i][j] instanceof Bishop) {
							Bishop possBishop = (Bishop) board[i][j];
							if(possBishop.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						else if(board[i][j] instanceof Queen) {
							Queen possQueen = (Queen) board[i][j];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						break;
					}
				}
				for(int i = kingRank - 1, j = kingFile - 1; i > -1 && j > -1; i--, j--) {
					if(board[i][j] != null) {
						if(board[i][j] instanceof Bishop) {
							Bishop possBishop = (Bishop) board[i][j];
							if(possBishop.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						else if(board[i][j] instanceof Queen) {
							Queen possQueen = (Queen) board[i][j];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						break;
					}
				}
				for(int i = kingFile - 1; i > -1; i--) {
					if(board[kingRank][i] != null) {
						if(board[kingRank][i] instanceof Rook) {
							Rook possRook = (Rook) board[kingRank][i];
							if(possRook.getColor() != color) {
								threateningTheKing.add(board[kingRank][i]);
							}
						}
						else if(board[kingRank][i] instanceof Queen) {
							Queen possQueen = (Queen) board[kingRank][i];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[kingRank][i]);
							}
						}
						break;
					}
				}
				if(kingRank - 2 >= 0) {
					if(board[kingRank - 2][kingFile - 1] != null) {
						if(board[kingRank - 2][kingFile - 1] instanceof Knight) {
							Knight possKnight = (Knight)board[kingRank - 2][kingFile - 1];
							if(possKnight.getColor() != color) {
								threateningTheKing.add(board[kingRank - 2][kingFile - 1]);
							}
						}
					}
				}
				if(kingRank + 2 <= 7) {
					if(board[kingRank + 2][kingFile - 1] != null) {
						if(board[kingRank + 2][kingFile - 1] instanceof Knight) {
							Knight possKnight = (Knight)board[kingRank + 2][kingFile - 1];
							if(possKnight.getColor() != color) {
								threateningTheKing.add(board[kingRank + 2][kingFile - 1]);
							}
						}
					}
				}
				if(board[kingRank + 1][kingFile - 2] != null) {
					if(board[kingRank + 1][kingFile - 2] instanceof Knight) {
						Knight possKnight = (Knight)board[kingRank + 1][kingFile - 2];
						if(possKnight.getColor() != color) {
							threateningTheKing.add(board[kingRank + 1][kingFile - 2]);
						}
					}
				}
				if(board[kingRank - 1][kingFile - 2] != null) {
					if(board[kingRank - 1][kingFile - 2] instanceof Knight) {
						Knight possKnight = (Knight)board[kingRank - 1][kingFile - 2];
						if(possKnight.getColor() != color) {
							threateningTheKing.add(board[kingRank - 1][kingFile - 2]);
						}
					}
				}
			}
			else if(freeForAll) {
				for(int i = kingRank - 1; i > -1; i--) {
					if(board[i][kingFile] != null) {
						if(board[i][kingFile] instanceof Rook) {
							Rook possRook = (Rook) board[i][kingFile];
							if(possRook.getColor() != color) {
								threateningTheKing.add(board[i][kingFile]);
							}
						}
						else if(board[i][kingFile] instanceof Queen) {
							Queen possQueen = (Queen) board[i][kingFile];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][kingFile]);
							}
						}
						break;
					}
				}
				for(int i = kingRank + 1; i < 8; i++) {
					if(board[i][kingFile] != null) {
						if(board[i][kingFile] instanceof Rook) {
							Rook possRook = (Rook) board[i][kingFile];
							if(possRook.getColor() != color) {
								threateningTheKing.add(possRook);
							}
						}
						else if(board[i][kingFile] instanceof Queen) {
							Queen possQueen = (Queen) board[i][kingFile];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][kingFile]);
							}
						}
						break;
					}
				}
				for(int i = kingFile + 1; i < 8; i++) {
					if(board[kingRank][i] != null) {
						if(board[kingRank][i] instanceof Rook) {
							Rook possRook = (Rook) board[kingRank][i];
							if(possRook.getColor() != color) {
								threateningTheKing.add(board[kingRank][i]);
							}
						}
						else if(board[kingRank][i] instanceof Queen) {
							Queen possQueen = (Queen) board[kingRank][i];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[kingRank][i]);
							}
						}
						break;
					}
				}
				for(int i = kingFile - 1; i > -1; i--) {
					if(board[kingRank][i] != null) {
						if(board[kingRank][i] instanceof Rook) {
							Rook possRook = (Rook) board[kingRank][i];
							if(possRook.getColor() != color) {
								threateningTheKing.add(board[kingRank][i]);
							}
						}
						else if(board[kingRank][i] instanceof Queen) {
							Queen possQueen = (Queen) board[kingRank][i];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[kingRank][i]);
							}
						}
						break;
					}
				}
				for(int i = kingRank - 1, j = kingFile + 1; i > -1 && j < 8; i--, j++) {
					if(board[i][j] != null) {
						if(board[i][j] instanceof Bishop) {
							Bishop possBishop = (Bishop) board[i][j];
							if(possBishop.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						else if(board[i][j] instanceof Queen) {
							Queen possQueen = (Queen) board[i][j];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						break;
					}
				}
				for(int i = kingRank + 1, j = kingFile + 1; i < 8 && j < 8; i++, j++) {
					if(board[i][j] != null) {
						if(board[i][j] instanceof Bishop) {
							Bishop possBishop = (Bishop) board[i][j];
							if(possBishop.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						else if(board[i][j] instanceof Queen) {
							Queen possQueen = (Queen) board[i][j];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						break;
					}
				}
				for(int i = kingRank + 1, j = kingFile - 1; i < 8 && j > -1; i++, j--) {
					if(board[i][j] != null) {
						if(board[i][j] instanceof Bishop) {
							Bishop possBishop = (Bishop) board[i][j];
							if(possBishop.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						else if(board[i][j] instanceof Queen) {
							Queen possQueen = (Queen) board[i][j];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						break;
					}
				}
				for(int i = kingRank - 1, j = kingFile - 1; i > -1 && j > -1; i--, j--) {
					if(board[i][j] != null) {
						if(board[i][j] instanceof Bishop) {
							Bishop possBishop = (Bishop) board[i][j];
							if(possBishop.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						else if(board[i][j] instanceof Queen) {
							Queen possQueen = (Queen) board[i][j];
							if(possQueen.getColor() != color) {
								threateningTheKing.add(board[i][j]);
							}
						}
						break;
					}
				}
				if(kingRank - 2 >= 0) {
					if(board[kingRank - 2][kingFile + 1] != null) {
						if(board[kingRank - 2][kingFile + 1] instanceof Knight) {
							Knight possKnight = (Knight) board[kingRank - 2][kingFile + 1];
							if(possKnight.getColor() != color) {
								threateningTheKing.add(board[kingRank - 2][kingFile + 1]);
							}
						}
					}
				}
				if(kingRank + 2 <= 7) {
					if(board[kingRank + 2][kingFile + 1] != null) {
						if(board[kingRank + 2][kingFile + 1] instanceof Knight) {
							Knight possKnight = (Knight) board[kingRank + 2][kingFile + 1];
							if(possKnight.getColor() != color) {
								threateningTheKing.add(board[kingRank + 2][kingFile + 1]);
							}
						}
					}
				}
				if(board[kingRank - 1][kingFile + 2] != null) {
					if(board[kingRank - 1][kingFile + 2] instanceof Knight) {
						Knight possKnight = (Knight) board[kingRank - 1][kingFile + 2];
						if(possKnight.getColor() != color) {
							threateningTheKing.add(board[kingRank - 1][kingFile + 2]);
						}
					}
				}
				if(board[kingRank + 1][kingFile + 2] != null) {
					if(board[kingRank + 1][kingFile + 2] instanceof Knight) {
						Knight possKnight = (Knight) board[kingRank + 1][kingFile + 2];
						if(possKnight.getColor() != color) {
							threateningTheKing.add(board[kingRank + 1][kingFile + 2]);
						}
					}
				}
				if(kingRank - 2 >= 0) {
					if(board[kingRank - 2][kingFile - 1] != null) {
						if(board[kingRank - 2][kingFile - 1] instanceof Knight) {
							Knight possKnight = (Knight)board[kingRank - 2][kingFile - 1];
							if(possKnight.getColor() != color) {
								threateningTheKing.add(board[kingRank - 2][kingFile - 1]);
							}
						}
					}
				}
				if(kingRank + 2 <= 7) {
					if(board[kingRank + 2][kingFile - 1] != null) {
						if(board[kingRank + 2][kingFile - 1] instanceof Knight) {
							Knight possKnight = (Knight)board[kingRank + 2][kingFile - 1];
							if(possKnight.getColor() != color) {
								threateningTheKing.add(board[kingRank + 2][kingFile - 1]);
							}
						}
					}
				}
				if(board[kingRank + 1][kingFile - 2] != null) {
					if(board[kingRank + 1][kingFile - 2] instanceof Knight) {
						Knight possKnight = (Knight)board[kingRank + 1][kingFile - 2];
						if(possKnight.getColor() != color) {
							threateningTheKing.add(board[kingRank + 1][kingFile - 2]);
						}
					}
				}
				if(board[kingRank - 1][kingFile - 2] != null) {
					if(board[kingRank - 1][kingFile - 2] instanceof Knight) {
						Knight possKnight = (Knight)board[kingRank - 1][kingFile - 2];
						if(possKnight.getColor() != color) {
							threateningTheKing.add(board[kingRank - 1][kingFile - 2]);
						}
					}
				}
			}
			int enemyNumber = threateningTheKing.size();
			for(Piece p : threateningTheKing) {
				int enemyRank = p.getRank();
				int enemyFile = p.getFile();
				for(Piece ally : allies) {
					int allyRank = ally.getRank();
					int allyFile = ally.getFile();
					if(ally.isPossibleMove(board, enemyRank, enemyFile, moveNumber)) {
						Piece temp = (Pawn) p;
						if(p instanceof Pawn) {
							temp = (Pawn) p;
						}
						else if(p instanceof Knight) {
							temp = (Knight) p;
						}
						else if(p instanceof Bishop) {
							temp = (Bishop) p;
						}
						else if(p instanceof Rook) {
							temp = (Rook) p;
						}
						else if(p instanceof Queen) {
							temp = (Queen) p;
						}
						board[allyRank][allyFile] = null;
						movePiece(moveNumber, allyFile, allyRank, enemyFile, enemyRank, board, board[kingRank][kingFile]);
						if(isInCheck(color,board,kingRank,kingFile)) {
							movePiece(moveNumber, enemyFile, enemyRank, allyFile, allyRank, board, board[kingRank][kingFile]);
							board[enemyRank][enemyFile] = temp;
						}
						else {
							movePiece(moveNumber, enemyFile, enemyRank, allyFile, allyRank, board, board[kingRank][kingFile]);
							board[enemyRank][enemyFile] = temp;
							return false;
						}
					}
				}

			}
			for(Piece p : threateningTheKing) {
				int pFile = p.getFile();
				int pRank = p.getRank();
				for(Piece ally : allies) {
					int allyFile = ally.getFile();
					int allyRank = ally.getRank();
					if(p instanceof Rook || p instanceof Queen) {
						int lower = 0, higher = 0;
						if(allyFile == pFile) {
							if(allyRank < pRank) {
								lower = pRank;
								higher = allyRank;
							}
							else {
								lower = allyRank;
								higher = pRank;
							}
						}
						for(int k = higher + 1; k < lower; k++) {
							if(ally instanceof King) {
								break;
							}
							else {
								if(ally.isPossibleMove(board, k + 1, allyRank, moveNumber)) {
									ally.setFile(k + 1);
									ally.setRank(allyRank);
									board[allyRank][k + 1] = ally;
									board[allyRank][allyFile] = null;
									if(isInCheck(color, board, kingRank, kingFile)) {
										ally.setFile(allyFile);
										ally.setRank(allyRank);
										board[allyRank][k + 1] = null;
										board[allyRank][allyFile] = ally;
									}
									else {
										return false;
									}
								}
							}
						}
					}
					else if(p instanceof Bishop || p instanceof Queen) {
						if(allyFile < pFile && allyRank < pRank) {
							for(int k = allyRank + 1, l = allyFile + 1; k < pRank; k++, l++) {
								ally.setFile(l + 1);
								ally.setRank(k + 1);
								board[allyRank][allyFile] = null;
								board[k + 1][l + 1] = null;

							}
						}
					}
				}
			}
			if(noAlly) {
				return true;
			}
		}
		else {
			return false;
		}

		return false;
	}*/
    /***
     * Makes a check to see if a player's king is in check
     * @param color the color of the king that you want to see if it is in check
     * @param board the Piece board double array that hold all the pieces
     * @param kingRank the rank of the king that sees if it is in check
     * @param kingFile the file of the king that sees if it is in check
     * @return a boolean, false if the king is in check and true if it is
     */

    public static boolean isInCheck(boolean color, Piece [][]board, int kingRank, int kingFile) {
        int backShortL = 0;
        int backLongL = 0;
        int frontShortL = 0;
        int frontLongL = 0;
        int frontDiagonalRight = 0;
        int frontDiagonalLeft = 0;
        int backDiagonalRight = 0;
        int backDiagonalLeft = 0;
        int frontRank = 0;
        int backRank = 0;
        int leftFile = 0;
        int rightFile = 0;
        if(!color) {
            if(kingRank >= 6) {
                backLongL = 1;
                if(kingRank == 7) {
                    backShortL = 1;
                    backDiagonalRight = 1;
                    backDiagonalLeft = 1;
                    backRank = 1;
                }
            }
            if(kingRank <= 1) {
                frontLongL = 1;
                if(kingRank == 0) {
                    frontShortL = 1;
                    frontDiagonalRight = 1;
                    frontDiagonalLeft = 1;
                    frontRank = 1;
                }
            }
            if(kingFile == 0) {
                backDiagonalLeft = 1;
                frontDiagonalLeft = 1;
                leftFile = 1;
            }
            if(kingFile == 7) {
                frontDiagonalRight = 1;
                backDiagonalRight = 1;
                rightFile = 1;
            }
            if(rightFile == 0) {
                for(int i = kingFile + 1; i < 7; i++) {
                    if(board[kingRank][i] != null) {
                        if(board[kingRank][i] instanceof Rook) {
                            Rook rookThreat = (Rook) board[kingRank][i];
                            if(rookThreat.getColor()) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else if(board[kingRank][i] instanceof Queen) {
                            Queen queenThreat = (Queen) board[kingRank][i];
                            if(queenThreat.getColor()) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                }
            }
            if(leftFile == 0) {
                for(int i = kingFile - 1; i > -1; i--) {
                    if(board[kingRank][i] != null) {
                        if(board[kingRank][i] instanceof Rook) {
                            Rook rookThreat = (Rook) board[kingRank][i];
                            if(rookThreat.getColor()) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else if(board[kingRank][i] instanceof Queen) {
                            Queen queenThreat = (Queen) board[kingRank][i];
                            if(queenThreat.getColor()) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                }
            }
            if(backRank == 0) {
                for(int i = kingRank + 1; i < 8; i++) {
                    if(board[i][kingFile] != null) {
                        if(board[i][kingFile] instanceof Rook) {
                            Rook rookThreat = (Rook) board[i][kingFile];
                            if(rookThreat.getColor()) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else if(board[i][kingFile] instanceof Queen) {
                            Queen queenThreat = (Queen) board[i][kingFile];
                            if(queenThreat.getColor()) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                }
            }
            if(frontRank == 0) {
                for(int i = kingRank - 1; i > -1; i--) {
                    if(board[i][kingFile] != null) {
                        if(board[i][kingFile] instanceof Rook) {
                            Rook rookThreat = (Rook) board[i][kingFile];
                            if(rookThreat.getColor()) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else if(board[i][kingFile] instanceof Queen) {
                            Queen queenThreat = (Queen) board[i][kingFile];
                            if(queenThreat.getColor()) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                }
            }
            if(frontDiagonalRight == 0) {
                if(board[kingRank - 1][kingFile + 1] != null) {
                    if(board[kingRank - 1][kingFile + 1] instanceof Pawn) {
                        Pawn pawnThreat = (Pawn) board[kingRank - 1][kingFile + 1];
                        if(pawnThreat.getColor()) {
                            return true;
                        }
                    }
                }
                for(int i = kingRank - 1, j = kingFile + 1; i > -1 && j < 8; i--, j++) {
                    if(board[i][j] != null) {
                        if(board[i][j] instanceof Bishop) {
                            Bishop bishopThreat = (Bishop) board[i][j];
                            if(bishopThreat.getColor()) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else if(board[i][j] instanceof Queen) {
                            Queen queenThreat = (Queen) board[i][j];
                            if(queenThreat.getColor()) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                }
            }
            if(frontDiagonalLeft == 0) {
                if(board[kingRank - 1][kingFile - 1] != null) {
                    if(board[kingRank - 1][kingFile - 1] instanceof Pawn) {
                        Pawn pawnThreat = (Pawn) board[kingRank - 1][kingFile - 1];
                        if(pawnThreat.getColor()) {
                            return true;
                        }
                    }
                }
                for(int i = kingRank - 1, j = kingFile - 1; i > -1 && j > -1; i--, j--) {
                    if(board[i][j] != null) {
                        if(board[i][j] instanceof Bishop) {
                            Bishop bishopThreat = (Bishop) board[i][j];
                            if(bishopThreat.getColor()) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else if(board[i][j] instanceof Queen) {
                            Queen queenThreat = (Queen) board[i][j];
                            if(queenThreat.getColor()) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                }
            }
            if(backDiagonalRight == 0) {
                for(int i = kingRank + 1, j = kingFile + 1; i < 8 && j < 8; i++, j++) {
                    if(board[i][j] != null) {
                        if(board[i][j] instanceof Bishop) {
                            Bishop bishopThreat = (Bishop) board[i][j];
                            if(bishopThreat.getColor()) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else if(board[i][j] instanceof Queen) {
                            Queen queenThreat = (Queen) board[i][j];
                            if(queenThreat.getColor()) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                }
            }
            if(backDiagonalLeft == 0) {
                for(int i = kingRank + 1, j = kingFile - 1; i < 8 && j > -1; i++, j--) {
                    if(board[i][j] != null) {
                        if(board[i][j] instanceof Bishop) {
                            Bishop bishopThreat = (Bishop) board[i][j];
                            if(bishopThreat.getColor()) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else if(board[i][j] instanceof Queen) {
                            Queen queenThreat = (Queen) board[i][j];
                            if(queenThreat.getColor()) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                }
            }
            if(backShortL == 0) {
                if(kingRank - 1 >= 0) {
                    if(kingFile + 2 <= 7) {
                        if(board[kingRank - 1][kingFile + 2] != null) {
                            if(board[kingRank - 1][kingFile + 2] instanceof Knight) {
                                Knight knightThreat = (Knight) board[kingRank - 1][kingFile + 2];
                                if(knightThreat.getColor()) {
                                    return true;
                                }
                            }
                        }
                    }
                    if(kingFile - 2 >= 0) {
                        if(board[kingRank - 1][kingFile - 2] != null) {
                            if(board[kingRank - 1][kingFile - 2] instanceof Knight) {
                                Knight knightThreat = (Knight) board[kingRank - 1][kingFile - 2];
                                if(knightThreat.getColor()) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            if(backLongL == 0) {
                if(kingRank + 2 <= 7) {
                    if(kingFile - 1 >= 0) {
                        if(board[kingRank + 2][kingFile - 1] != null) {
                            if(board[kingRank + 2][kingFile - 1] instanceof Knight) {
                                Knight knightThreat = (Knight) board[kingRank + 2][kingFile - 1];
                                if(knightThreat.getColor()) {
                                    return true;
                                }
                            }

                        }

                    }
                    if(kingFile + 1 <= 7) {
                        if(board[kingRank + 2][kingFile + 1] != null) {
                            if(board[kingRank + 2][kingFile + 1] instanceof Knight) {
                                Knight knightThreat = (Knight) board[kingRank + 2][kingFile + 1];
                                if(knightThreat.getColor()) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            if(frontShortL == 0) {
                if(kingRank - 1 >= 0) {
                    if(kingFile + 2 <= 7) {
                        if(board[kingRank - 1][kingFile + 2] != null) {
                            if(board[kingRank - 1][kingFile + 2] instanceof Knight) {
                                Knight knightThreat = (Knight) board[kingRank - 1][kingFile + 2];
                                if(knightThreat.getColor()) {
                                    return true;
                                }
                            }
                        }
                    }
                    if(kingFile - 2 >= 0) {
                        if(board[kingRank - 1][kingFile - 2] != null) {
                            if(board[kingRank - 1][kingFile - 2] instanceof Knight) {
                                Knight knightThreat = (Knight) board[kingRank - 1][kingFile - 2];
                                if(knightThreat.getColor()) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            if(frontLongL == 0) {
                if(kingRank - 2 >= 0) {
                    if(kingFile + 1 <= 7) {
                        if(board[kingRank - 2][kingFile + 1] != null) {
                            if(board[kingRank - 2][kingFile + 1] instanceof Knight) {
                                Knight knightThreat = (Knight) board[kingRank - 2][kingFile + 1];
                                if(knightThreat.getColor()) {
                                    return true;
                                }
                            }
                        }
                    }
                    if(kingFile - 1 >= 0) {
                        if(board[kingRank - 2][kingFile - 1] != null) {
                            if(board[kingRank - 2][kingFile - 1] instanceof Knight) {
                                Knight knightThreat = (Knight) board[kingRank - 2][kingFile - 1];
                                if(knightThreat.getColor()) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        else {
            if(kingRank >= 6) {
                backLongL = 1;
                if(kingRank == 7) {
                    backShortL = 1;
                    backDiagonalRight = 1;
                    backDiagonalLeft = 1;
                    backRank = 1;
                }
            }
            if(kingRank <= 1) {
                frontLongL = 1;
                if(kingRank == 0) {
                    frontShortL = 1;
                    frontDiagonalRight = 1;
                    frontDiagonalLeft = 1;
                    frontRank = 1;
                }
            }
            if(kingFile == 0) {
                backDiagonalLeft = 1;
                frontDiagonalLeft = 1;
                leftFile = 1;
            }
            if(kingFile == 7) {
                frontDiagonalRight = 1;
                backDiagonalRight = 1;
                rightFile = 1;
            }
            if(rightFile == 0) {
                for(int i = kingFile + 1; i < 7; i++) {
                    if(board[kingRank][i] != null) {
                        if(board[kingRank][i] instanceof Rook) {
                            Rook rookThreat = (Rook) board[kingRank][i];
                            if(!(rookThreat.getColor())) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else if(board[kingRank][i] instanceof Queen) {
                            Queen queenThreat = (Queen) board[kingRank][i];
                            if(!(queenThreat.getColor())) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                }
            }
            if(leftFile == 0) {
                for(int i = kingFile - 1; i > 0; i--) {
                    if(board[kingRank][i] != null) {
                        if(board[kingRank][i] instanceof Rook) {
                            Rook rookThreat = (Rook) board[kingRank][i];
                            if(!(rookThreat.getColor())) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else if(board[kingRank][i] instanceof Queen) {
                            Queen queenThreat = (Queen) board[kingRank][i];
                            if(!(queenThreat.getColor())) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                }
            }
            if(backRank == 0) {
                for(int i = kingRank + 1; i < 8; i++) {
                    if(board[i][kingFile] != null) {
                        if(board[i][kingFile] instanceof Rook) {
                            Rook rookThreat = (Rook) board[i][kingFile];
                            if(!(rookThreat.getColor())) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else if(board[i][kingFile] instanceof Queen) {
                            Queen queenThreat = (Queen) board[i][kingFile];
                            if(!(queenThreat.getColor())) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                }
            }
            if(frontRank == 0) {
                for(int i = kingRank - 1; i > -1; i--) {
                    if(board[i][kingFile] != null) {
                        if(board[i][kingFile] instanceof Rook) {
                            Rook rookThreat = (Rook) board[i][kingFile];
                            if(!(rookThreat.getColor())) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else if(board[i][kingFile] instanceof Queen) {
                            Queen queenThreat = (Queen) board[i][kingFile];
                            if(!(queenThreat.getColor())) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                }
            }
            if(frontDiagonalRight == 0) {
                if(board[kingRank - 1][kingFile + 1] != null) {
                    if(board[kingRank - 1][kingFile + 1] instanceof Pawn) {
                        Pawn pawnThreat = (Pawn) board[kingRank - 1][kingFile + 1];
                        if(pawnThreat.getColor()) {
                            return true;
                        }
                    }
                }
                for(int i = kingRank - 1, j = kingFile + 1; i > -1 && j < 8; i--, j++) {
                    if(board[i][j] != null) {
                        if(board[i][j] instanceof Bishop) {
                            Bishop bishopThreat = (Bishop) board[i][j];
                            if(!(bishopThreat.getColor())) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else if(board[i][j] instanceof Queen) {
                            Queen queenThreat = (Queen) board[i][j];
                            if(!(queenThreat.getColor())) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                }
            }
            if(frontDiagonalLeft == 0) {
                if(board[kingRank - 1][kingFile - 1] != null) {
                    if(board[kingRank - 1][kingFile - 1] instanceof Pawn) {
                        Pawn pawnThreat = (Pawn) board[kingRank - 1][kingFile + 1];
                        if(!(pawnThreat.getColor())) {
                            return true;
                        }
                    }
                }
                for(int i = kingRank - 1, j = kingFile - 1; i > -1 && j > -1; i--, j--) {
                    if(board[i][j] != null) {
                        if(board[i][j] instanceof Bishop) {
                            Bishop bishopThreat = (Bishop) board[i][j];
                            if(!(bishopThreat.getColor())) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else if(board[i][j] instanceof Queen) {
                            Queen queenThreat = (Queen) board[i][j];
                            if(!(queenThreat.getColor())) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                }
            }
            if(backDiagonalRight == 0) {
                for(int i = kingRank + 1, j = kingFile + 1; i < 8 && j < 8; i++, j++) {
                    if(board[i][j] != null) {
                        if(board[i][j] instanceof Bishop) {
                            Bishop bishopThreat = (Bishop) board[i][j];
                            if(!(bishopThreat.getColor())) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else if(board[i][j] instanceof Queen) {
                            Queen queenThreat = (Queen) board[i][j];
                            if(!(queenThreat.getColor())) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                }
            }
            if(backDiagonalLeft == 0) {
                for(int i = kingRank + 1, j = kingFile - 1; i < 8 && j > -1; i++, j--) {
                    if(board[i][j] != null) {
                        if(board[i][j] instanceof Bishop) {
                            Bishop bishopThreat = (Bishop) board[i][j];
                            if(!(bishopThreat.getColor())) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else if(board[i][j] instanceof Queen) {
                            Queen queenThreat = (Queen) board[i][j];
                            if(!(queenThreat.getColor())) {
                                return true;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            break;
                        }
                    }
                }
            }
            if(backShortL == 0) {
                if(kingRank - 1 >= 0) {
                    if(kingFile + 2 <= 7) {
                        if(board[kingRank - 1][kingFile + 2] != null) {
                            if(board[kingRank - 1][kingFile + 2] instanceof Knight) {
                                Knight knightThreat = (Knight) board[kingRank - 1][kingFile + 2];
                                if(!(knightThreat.getColor())) {
                                    return true;
                                }
                            }
                        }
                    }
                    if(kingFile - 2 >= 0) {
                        if(board[kingRank - 1][kingFile - 2] != null) {
                            if(board[kingRank - 1][kingFile - 2] instanceof Knight) {
                                Knight knightThreat = (Knight) board[kingRank - 1][kingFile - 2];
                                if(!(knightThreat.getColor())) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            if(backLongL == 0) {
                if(kingRank + 2 <= 7) {
                    if(kingFile - 1 >= 0) {
                        if(board[kingRank + 2][kingFile - 1] != null) {
                            if(board[kingRank + 2][kingFile - 1] instanceof Knight) {
                                Knight knightThreat = (Knight) board[kingRank + 2][kingFile - 1];
                                if(!(knightThreat.getColor())) {
                                    return true;
                                }
                            }

                        }

                    }
                    if(kingFile + 1 <= 7) {
                        if(board[kingRank + 2][kingFile + 1] != null) {
                            if(board[kingRank + 2][kingFile + 1] instanceof Knight) {
                                Knight knightThreat = (Knight) board[kingRank + 2][kingFile + 1];
                                if(!(knightThreat.getColor())) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            if(frontShortL == 0) {
                if(kingRank - 1 >= 0) {
                    if(kingFile + 2 <= 7) {
                        if(board[kingRank - 1][kingFile + 2] != null) {
                            if(board[kingRank - 1][kingFile + 2] instanceof Knight) {
                                Knight knightThreat = (Knight) board[kingRank - 1][kingFile + 2];
                                if(!(knightThreat.getColor())) {
                                    return true;
                                }
                            }
                        }
                    }
                    if(kingFile - 2 >= 0) {
                        if(board[kingRank - 1][kingFile - 2] != null) {
                            if(board[kingRank - 1][kingFile - 2] instanceof Knight) {
                                Knight knightThreat = (Knight) board[kingRank - 1][kingFile - 2];
                                if(!(knightThreat.getColor())) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            if(frontLongL == 0) {
                if(kingRank - 2 >= 0) {
                    if(kingFile + 1 <= 7) {
                        if(board[kingRank - 2][kingFile + 1] != null) {
                            if(board[kingRank - 2][kingFile + 1] instanceof Knight) {
                                Knight knightThreat = (Knight) board[kingRank - 2][kingFile + 1];
                                if(!(knightThreat.getColor())) {
                                    return true;
                                }
                            }
                        }
                    }
                    if(kingFile - 1 >= 0) {
                        if(board[kingRank - 2][kingFile - 1] != null) {
                            if(board[kingRank - 2][kingFile - 1] instanceof Knight) {
                                Knight knightThreat = (Knight) board[kingRank - 2][kingFile - 1];
                                if(!(knightThreat.getColor())) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /***
     * Sets up the Piece double array board to the default chess board set up and initialize all corresponding piece objects
     * @param board the Piece double array used in the game
     * @param whitePieces an ArrayList that holds all the white pieces
     * @param blackPieces an ArrayList that holds all the black pieces
     */

    public static void initializeBoard(Piece[][] board, ArrayList<Piece>whitePieces, ArrayList<Piece>blackPieces) {
        board[0][0] = new Rook(true, 0, 0);
        board[0][1] = new Knight(true, 1, 0);
        board[0][2] = new Bishop(true, 2, 0);
        board[0][3] = new Queen(true, 3, 0);
        board[0][4] = new King(true, 4, 0);
        board[0][5] = new Bishop(true, 5, 0);
        board[0][6] = new Knight(true, 6, 0);
        board[0][7] = new Rook(true, 7, 0);
        board[7][0] = new Rook(false, 0, 7);
        board[7][1] = new Knight(false, 1, 7);
        board[7][2] = new Bishop(false, 2, 7);
        board[7][3] = new Queen(false, 3, 7);
        board[7][4] = new King(false, 4, 7);
        board[7][5] = new Bishop(false, 5, 7);
        board[7][6] = new Knight(false, 6, 7);
        board[7][7] = new Rook(false, 7, 7);
        blackPieces.add(board[0][0]);
        blackPieces.add(board[0][1]);
        blackPieces.add(board[0][2]);
        blackPieces.add(board[0][3]);
        blackPieces.add(board[0][4]);
        blackPieces.add(board[0][5]);
        blackPieces.add(board[0][6]);
        blackPieces.add(board[0][7]);
        whitePieces.add(board[7][0]);
        whitePieces.add(board[7][1]);
        whitePieces.add(board[7][2]);
        whitePieces.add(board[7][3]);
        whitePieces.add(board[7][4]);
        whitePieces.add(board[7][5]);
        whitePieces.add(board[7][6]);
        whitePieces.add(board[7][7]);
        for (int i = 0; i < board.length; i++) {
            board[1][i] = new Pawn(true, i, 1, 0, false);
            board[6][i] = new Pawn(false, i, 6, 0, false);
            blackPieces.add(board[1][i]);
            whitePieces.add(board[6][i]);
        }
    }

    /***
     * Updates the ASCII art associated with the board between each move
     * @param board the Piece double array that is used in play
     */

    public static void updateBoardArt(Piece [][] board) {
        String [][] graphicBoard = new String[9][9];
        graphicBoard[0][8] = "8";
        graphicBoard[1][8] = "7";
        graphicBoard[2][8] = "6";
        graphicBoard[3][8] = "5";
        graphicBoard[4][8] = "4";
        graphicBoard[5][8] = "3";
        graphicBoard[6][8] = "2";
        graphicBoard[7][8] = "1";
        graphicBoard[8][0] = "a";
        graphicBoard[8][1] = "b";
        graphicBoard[8][2] = "c";
        graphicBoard[8][3] = "d";
        graphicBoard[8][4] = "e";
        graphicBoard[8][5] = "f";
        graphicBoard[8][6] = "g";
        graphicBoard[8][7] = "h";
        for(int i = 0; i < graphicBoard.length; i++) {
            for(int j = 0; j < graphicBoard[0].length; j++) {
                if(i == 8 && j == 8) {
                    System.out.print(" ");
                }
                if(i < 8 && j < 8) {
                    if(board[i][j] != null) {
                        if(board[i][j] instanceof Rook) {
                            if(board[i][j].getColor()) {
                                System.out.print("bR ");
                            }
                            else {
                                System.out.print("wR ");
                            }
                        }
                        else if(board[i][j] instanceof Pawn) {
                            if(board[i][j].getColor()) {
                                System.out.print("bp ");
                            }
                            else {
                                System.out.print("wp ");
                            }
                        }
                        else if(board[i][j] instanceof Knight) {
                            if(board[i][j].getColor()) {
                                System.out.print("bN ");
                            }
                            else {
                                System.out.print("wN ");
                            }
                        }
                        else if(board[i][j] instanceof Bishop) {
                            if(board[i][j].getColor()) {
                                System.out.print("bB ");
                            }
                            else {
                                System.out.print("wB ");
                            }
                        }
                        else if(board[i][j] instanceof Queen) {
                            if(board[i][j].getColor()) {
                                System.out.print("bQ ");
                            }
                            else {
                                System.out.print("wQ ");
                            }
                        }
                        else if(board[i][j] instanceof King) {
                            if(board[i][j].getColor()) {
                                System.out.print("bK ");
                            }
                            else {
                                System.out.print("wK ");
                            }
                        }
                    }
                    else {
                        if(j%2==0 && i%2!=0) {
                            System.out.print("## ");
                        }
                        else if(j%2!=0 && i%2==0) {
                            System.out.print("## ");
                        }
                        else {
                            System.out.print("   ");
                        }
                    }
                }
                else {
                    if(i == 8) {
                        if(i == 8 && j == 8) {
                            continue;
                        }
                        else {
                            System.out.print(" " + graphicBoard[i][j] + " ");
                        }
                    }else {
                        System.out.print(graphicBoard[i][j]);
                    }

                }
            }
            System.out.println(" ");
        }

    }

    /***
     * Main method, allows the users to input moves back and forth to actually play the intended game of chess
     * @param args
     */


    public static void main(String[] args) {
        HashMap<Character, Integer> files = new HashMap<Character,Integer>();
        files.put('a', 0);
        files.put('b', 1);
        files.put('c', 2);
        files.put('d', 3);
        files.put('e', 4);
        files.put('f', 5);
        files.put('g', 6);
        files.put('h', 7);
        ArrayList<Piece> whitePieces = new ArrayList<Piece>();
        ArrayList<Piece> blackPieces = new ArrayList<Piece>();
        Piece[][] board = new Piece[8][8];
        initializeBoard(board, whitePieces, blackPieces);
        updateBoardArt(board);
        Piece whiteKing = (King) board[7][4];
        Piece blackKing = (King) board[0][4];
        Scanner input = new Scanner(System.in);
        int moves = 0;
        String move;
        int originFile = 0;
        int originRank = 0;
        int newFile = 0;
        int newRank = 0;
        while(true) {
            if(moves % 2 == 0) {
                if(isInCheck(false, board, whiteKing.getRank(), whiteKing.getFile())) {
                    System.out.println("Check");
                }
                System.out.println("White's move: ");
                move = input.nextLine();
                if(move.equals("resign")) {
                    System.out.println("Black wins");
                    break;
                }
                if(move.endsWith("draw?")) {
                    move = input.nextLine();
                    if(move.equals("draw")) {
                        break;
                    }
                }
                while(!(files.containsKey(move.charAt(0))) || !(files.containsKey(move.charAt(3)))) {
                    System.out.println("Illegal move, try again");
                    move = input.nextLine();
                }
                originFile = files.get(move.charAt(0));
                originRank = 8 - Integer.parseInt(Character.toString(move.charAt(1)));
                newFile = files.get(move.charAt(3));
                newRank = 8 - Integer.parseInt(Character.toString(move.charAt(4)));
                while((board[originRank][originFile] == null) || ((board[originRank][originFile]).getColor()) ||
                        !((board[originRank][originFile]).isPossibleMove(board, newFile, newRank, moves)) || (originRank > 8 || originRank < 1)
                        || (newRank > 8 || newRank < 1)) {
                    if(board[originRank][originFile] instanceof Pawn) {
                        Pawn temp = (Pawn) board[originRank][originFile];
                        temp.subtractForIllegalMove();
                        board[originRank][originFile] = temp;
                    }
                    System.out.println("Illegal move, try again");
                    move = input.nextLine();
                    originFile = files.get(move.charAt(0));
                    originRank = 8 - Integer.parseInt(Character.toString(move.charAt(1)));
                    newFile = files.get(move.charAt(3));
                    newRank = 8 - Integer.parseInt(Character.toString(move.charAt(4)));
                }
                movePiece(moves, originFile, originRank, newFile, newRank, move, board, whiteKing);
                while(isInCheck(false, board, whiteKing.getRank(), whiteKing.getFile())) {
                    movePiece(moves, newFile, newRank, originFile, originRank, move, board, whiteKing);
                    System.out.println("Illegal move, try again");
                    move = input.nextLine();
                    originFile = files.get(move.charAt(0));
                    originRank = 8 - Integer.parseInt(Character.toString(move.charAt(1)));
                    newFile = files.get(move.charAt(3));
                    newRank = 8 - Integer.parseInt(Character.toString(move.charAt(4)));
                    while((board[originRank][originFile] == null) || ((board[originRank][originFile]).getColor()) ||
                            !((board[originRank][originFile]).isPossibleMove(board, newFile, newRank, moves)) || (originRank > 8 || originRank < 1)
                            || (newRank > 8 || newRank < 1)) {
                        if(board[originRank][originFile] instanceof Pawn) {
                            Pawn temp = (Pawn) board[originRank][originFile];
                            temp.subtractForIllegalMove();
                            board[originRank][originFile] = temp;
                        }
                        System.out.println("Illegal move, try again");
                        move = input.nextLine();
                        originFile = files.get(move.charAt(0));
                        originRank = 8 - Integer.parseInt(Character.toString(move.charAt(1)));
                        newFile = files.get(move.charAt(3));
                        newRank = 8 - Integer.parseInt(Character.toString(move.charAt(4)));
                    }
                    movePiece(moves, originFile, originRank, newFile, newRank, move, board, whiteKing);
                }
            }
            else {
                if(isInCheck(true, board, blackKing.getRank(), blackKing.getFile())) {
                    System.out.println("Check");
                }
                System.out.println("Black's move: ");
                move = input.nextLine();
                if(move.equals("resign")) {
                    System.out.println("White wins");
                    break;
                }
                if(move.endsWith("draw?")) {
                    move = input.nextLine();
                    if(move.equals("draw")) {
                        break;
                    }
                }
                while(!(files.containsKey(move.charAt(0))) || !(files.containsKey(move.charAt(3)))) {
                    System.out.println("Illegal move, try again");
                    move = input.nextLine();
                }
                originFile = files.get(move.charAt(0));
                originRank = 8 - Integer.parseInt(Character.toString(move.charAt(1)));
                newFile = files.get(move.charAt(3));
                newRank = 8 - Integer.parseInt(Character.toString(move.charAt(4)));
                while((board[originRank][originFile] == null) || (!(board[originRank][originFile]).getColor()) ||
                        !((board[originRank][originFile]).isPossibleMove(board, newFile, newRank, moves)) || (originRank > 8 || originRank < 0)
                        || (newRank > 8 || newRank < 0)) {
                    if(board[originRank][originFile] instanceof Pawn) {
                        Pawn temp = (Pawn) board[originRank][originFile];
                        temp.subtractForIllegalMove();
                        board[originRank][originFile] = temp;
                    }
                    System.out.println("Illegal move, try again");
                    move = input.nextLine();
                    originFile = files.get(move.charAt(0));
                    originRank = 8 - Integer.parseInt(Character.toString(move.charAt(1)));
                    newFile = files.get(move.charAt(3));
                    newRank = 8 - Integer.parseInt(Character.toString(move.charAt(4)));
                }
                movePiece(moves, originFile, originRank, newFile, newRank, move, board, blackKing);
                while(isInCheck(true, board, blackKing.getRank(), blackKing.getFile())) {
                    movePiece(moves, newFile, newRank, originFile, originRank, move, board, blackKing);
                    System.out.println("Illegal move, try again");
                    move = input.nextLine();
                    originFile = files.get(move.charAt(0));
                    originRank = 8 - Integer.parseInt(Character.toString(move.charAt(1)));
                    newFile = files.get(move.charAt(3));
                    newRank = 8 - Integer.parseInt(Character.toString(move.charAt(4)));
                    while((board[originRank][originFile] == null) || (!(board[originRank][originFile]).getColor()) ||
                            !((board[originRank][originFile]).isPossibleMove(board, newFile, newRank, moves)) || (originRank > 8 || originRank < 0)
                            || (newRank > 8 || newRank < 0)) {
                        if(board[originRank][originFile] instanceof Pawn) {
                            Pawn temp = (Pawn) board[originRank][originFile];
                            temp.subtractForIllegalMove();
                            board[originRank][originFile] = temp;
                        }
                        System.out.println("Illegal move, try again");
                        move = input.nextLine();
                        originFile = files.get(move.charAt(0));
                        originRank = 8 - Integer.parseInt(Character.toString(move.charAt(1)));
                        newFile = files.get(move.charAt(3));
                        newRank = 8 - Integer.parseInt(Character.toString(move.charAt(4)));
                    }
                    movePiece(moves, originFile, originRank, newFile, newRank, move, board, blackKing);
                }
            }
            System.out.println(" ");
            updateBoardArt(board);
            moves++;
        }
        input.close();
    }
}

