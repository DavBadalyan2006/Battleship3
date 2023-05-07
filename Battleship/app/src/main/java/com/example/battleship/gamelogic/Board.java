package com.example.battleship.gamelogic;

public class Board {
    public static final int BOARD_SIZE = 10;
    public static final int EMPTY = 0;
    public static final int SHIP = 1;
    public static final int HIT = 2;
    public static final int MISS = 3;

    private int[][] board;

    public Board() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
    }

    public int getCell(int x, int y) {
        return board[x][y];
    }

    public void setCell(int x, int y, int value) {
        board[x][y] = value;
    }

    private boolean isCellEmpty(int x, int y) {
        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE && board[x][y] == EMPTY;
    }

    public boolean canPlaceShip(Ship ship, int x, int y, boolean isHorizontal) {
        if (isHorizontal) {
            if (x + ship.getSize() > BOARD_SIZE) {
                return false;
            }
            for (int i = -1; i <= ship.getSize(); i++) {
                for (int j = -1; j <= 1; j++) {
                    if (!isCellEmpty(x + i, y + j)) {
                        return false;
                    }
                }
            }
        } else {
            if (y + ship.getSize() > BOARD_SIZE) {
                return false;
            }
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= ship.getSize(); j++) {
                    if (!isCellEmpty(x + i, y + j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean placeShip(Ship ship, int x, int y, boolean isHorizontal) {
        if (canPlaceShip(ship, x, y, isHorizontal)) {
            if (isHorizontal) {
                for (int i = 0; i < ship.getSize(); i++) {
                    board[x + i][y] = SHIP;
                }
            } else {
                for (int i = 0; i < ship.getSize(); i++) {
                    board[x][y + i] = SHIP;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isValidMove(int x, int y) {
        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE && board[x][y] == EMPTY;
    }

    public void updateBoard(int x, int y) {
        if (board[x][y] == SHIP) {
            board[x][y] = HIT;
        } else {
            board[x][y] = MISS;
        }
    }

    public boolean allShipsSunk() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == SHIP) {
                    return false;
                }
            }
        }
        return true;
    }
}

