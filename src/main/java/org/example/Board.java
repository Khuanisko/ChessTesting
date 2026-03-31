package org.example;


public class Board {
    int size;

    public Board(int size) {
        this.size = size;
    }

    public int[][] createBoard() {
        return new int[size][size];
    }
}
