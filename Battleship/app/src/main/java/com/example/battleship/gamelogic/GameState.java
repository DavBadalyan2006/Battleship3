package com.example.battleship.gamelogic;

public class GameState {
    private Board playerBoard;
    private Board opponentBoard;
    private boolean isPlayerTurn;

    public GameState() {
        playerBoard = new Board();
        opponentBoard = new Board();
        isPlayerTurn = true; // Assuming the player starts the game
    }

    public Board getPlayerBoard() {
        return playerBoard;
    }

    public Board getOpponentBoard() {
        return opponentBoard;
    }

    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    public boolean isHit(int x, int y) {
        return opponentBoard.getCell(x, y) == Board.SHIP;
    }

    public boolean isValidMove(int x, int y) {
        // Delegate to the opponentBoard's isValidMove method
        return opponentBoard.isValidMove(x, y);
    }

    public void makeMove(int x, int y) {
        if (opponentBoard.isValidMove(x, y)) {
            opponentBoard.updateBoard(x, y);

            if (isHit(x, y)) {
                opponentBoard.setCell(x, y, Board.HIT); //Hit
            } else {
                opponentBoard.setCell(x, y, Board.MISS); //Miss
            }

            isPlayerTurn = !isPlayerTurn; //Switch Turns
        }
    }

    public boolean hasWon() {
        return opponentBoard.allShipsSunk();
    }

    public void placeShip(Ship ship, int x, int y, boolean isHorizontal) {
        playerBoard.placeShip(ship, x, y, isHorizontal);
    }

    public boolean isGameOver() {
        return playerBoard.allShipsSunk() || opponentBoard.allShipsSunk();
    }

    public void switchTurns() {
        isPlayerTurn = !isPlayerTurn;
    }

}
