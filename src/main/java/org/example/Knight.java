package org.example;

import java.util.ArrayList;
import java.util.List;

public class Knight {
    public int x;
    public int y;

    public Knight(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int[][] setKnight(int[][] board) {
        if (board[this.x][this.y] != 0){
            throw new IllegalStateException("Pole zajete");
        }
        board[this.x][this.y] = 1;
        return board;
    }

    public List<int[]> calculateAttack(int[][] board) {
        List<int[]> attackedSquares = new ArrayList<>();
        int n = board.length;

        int[] dx = { 2, 1, -1, -2, -2, -1, 1, 2 };
        int[] dy = { 1, 2, 2, 1, -1, -2, -2, -1 };

        for (int i = 0; i < 8; i++) {
            int targetX = this.x + dx[i];
            int targetY = this.y + dy[i];

            if (targetX >= 0 && targetX < n && targetY >= 0 && targetY < n) {
                attackedSquares.add(new int[]{targetX, targetY});
            }
        }

        return attackedSquares;
    }

    public boolean moveKnight(int[][] board, int newX, int newY) {
        if(newX<0 || newX >= board.length || newY < 0 || newY >= board[0].length){
            return false;
        }

        int dx = Math.abs(this.x - newX);
        int dy = Math.abs(this.y - newY);
        boolean isL = (dx == 2 && dy == 1) || (dx == 1 && dy == 2);

        if (!isL){
            return false;
        }

        if(board[newX][newY] == 1){
            return false;
        }

        board[this.x][this.y] = 0;
        this.x =  newX;
        this.y = newY;
        board[newX][newY] = 1;

        return true;
    }


    private int applyBounce(int coordinate, int boardsize){
        return coordinate;
    }
    public int  getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
