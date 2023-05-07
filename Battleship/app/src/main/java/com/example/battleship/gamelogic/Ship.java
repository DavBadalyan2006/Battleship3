package com.example.battleship.gamelogic;

public class Ship {
    private int size;
    private int shipID;
    private int hit;

    public Ship(int size, int shipID) {
        this.size = size;
        this.shipID = shipID;
        this.hit = 0;
    }

    public int getSize() {
        return size;
    }

    public int getShipID() {
        return shipID;
    }

    public int getHit() {
        return hit;
    }

    public void incrementHit() {
        hit++;
    }

    public boolean isSunk() {
        return hit == size;
    }
}
