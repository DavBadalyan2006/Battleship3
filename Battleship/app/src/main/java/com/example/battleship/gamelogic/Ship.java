package com.example.battleship.gamelogic;

public class Ship {
    private int length;
    private boolean[] hits;

    public Ship(int length) {
        this.length = length;
        hits = new boolean[length];
    }

    public int getLength() {
        return length;
    }

    public boolean isSunk() {
        for (boolean hit : hits) {
            if (!hit) {
                return false;
            }
        }
        return true;
    }

    public void hit(int position) {
        if (position >= 0 && position < length) {
            hits[position] = true;
        }
    }
}
