import org.example.Board;
import org.example.Knight;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class test {
    @Test
    void checkBoard(){
        Board board = new Board(8);
        int[][] board1 = board.createBoard();
        assertThat(board1).hasDimensions(8,8);
        assertThat(board1[0][0]).isEqualTo(0);
        assertThat(board1[0][7]).isEqualTo(0);
        assertThat(board1[7][0]).isEqualTo(0);
        assertThat(board1[7][7]).isEqualTo(0);
        assertThat(board1[6][7]).isEqualTo(0);
        assertThat(board1[5][2]).isEqualTo(0);
    }

    @Test
    void checkKnigthBounds(){
        Knight knight = new Knight(7, 1);
        assertThat(knight.getX()).isGreaterThan(-1).isLessThan(8);
        assertThat(knight.getY()).isGreaterThan(-1).isLessThan(8);
    }

    @Test
    void checkIfPostionisFree(){
        Board board = new Board(9);
        int[][] currentBoard = board.createBoard();
        Knight knight = new Knight(7, 1);
        assertThat(currentBoard[knight.getX()][knight.getY()]).isEqualTo(0);
        currentBoard = knight.setKnight(currentBoard);
        assertThat(currentBoard[knight.getX()][knight.getY()]).isEqualTo(1);
    }

    @Test
    void spawningKnightonObstacle(){
        Board board = new Board(8);
        int[][] board1 = board.createBoard();
        int targetX = 3;
        int targetY = 3;

        board1[targetX][targetY] = 2;
        Knight knight = new Knight(targetX, targetY);
        assertThatThrownBy(() -> knight.setKnight(board1)).isInstanceOf(IllegalStateException.class).hasMessage("Pole zajete");
        assertThat(board1[targetX][targetY]).isEqualTo(2);
    }

//    @Test
//    void checkIfEnemyOnPath(){
//        Board board = new Board(8);
//        int[][] board1 = board.createBoard();
//        Knight knight = new Knight(5, 4);
//        board1[6][7] = 2;
//        board1[3][3] = 2;
//        boolean calculate = knight.calculateAttack(board1);
//        assertThat(calculate).isTrue();
//    }

    @Test
    void moveToEmptySquare(){
        Board board = new Board(8);
        int[][] board1 = board.createBoard();
        Knight knight = new Knight(5, 4);
        //board1[3][3] = 2;
        //boolean move = knight.moveKnight(board1,6 ,7);
        boolean move = knight.moveKnight(board1,3 ,3);
        assertThat(move).isTrue();
        assertThat(board1[5][4]).isEqualTo(0);
        assertThat(board1[3][3]).isEqualTo(1);
    }

    @Test
    void captureEnemySqueare(){
        Board board = new Board(8);
        int[][] board1 = board.createBoard();
        Knight knight = new Knight(3, 3);
        board1[4][5] = 2;
        boolean result = knight.moveKnight(board1,4 ,5);
        assertThat(result).isTrue();
        assertThat(board1[4][5]).isEqualTo(1);
    }

    @Test
    void moveInLShape(){
        Board board = new Board(8);
        int[][] board1 = board.createBoard();
        Knight knight = new Knight(3, 3);
        board1[3][3] = 1;

        boolean result = knight.moveKnight(board1,5 ,5);
        assertThat(result).isFalse();
        assertThat(knight.getX()).isEqualTo(3);
    }

    @Test
    void calculateAttackfromCenter(){
        Board board = new Board(8);
        int [][] board1 = board.createBoard();
        Knight knight = new Knight(3, 3);

        List<int[]> attacks = knight.calculateAttack(board1);
        assertThat(attacks).hasSize(8);
        assertThat(attacks).containsExactlyInAnyOrder(
                new int[]{5, 4}, new int[]{4, 5}, new int[]{2, 5}, new int[]{1, 4},
                new int[]{1, 2}, new int[]{2, 1}, new int[]{4, 1}, new int[]{5, 2}
        );
    }

    @Test
    void calculateAttackfromCorner(){ //should return only 2 valid squares
        Board board = new Board(8);
        int[][] board1 = board.createBoard();
        Knight knight = new Knight(0, 0);

        List<int[]> attacks = knight.calculateAttack(board1);
        assertThat(attacks).hasSize(2);
        assertThat(attacks).containsExactlyInAnyOrder(
                new int[]{1,2}, new int[]{2,1} //,new int[]{3,3}
        );
    }

    @Test
    void calculateAttackfromEdge(){
        Board board = new Board(8);
        int[][] board1 = board.createBoard();
        Knight knight = new Knight(0, 4);

        List<int[]> attacks = knight.calculateAttack(board1);
        assertThat(attacks).hasSize(4);
        assertThat(attacks).containsExactlyInAnyOrder(
                new int[]{1,6}, new int[]{2,5}, new int[]{2,3}, new int[]{1,2}
        );

    }

    @Test
    void calculateBouncesfromTopLeftCorner(){
        Board board = new Board(8);
        int[][] board1 = board.createBoard();
        Knight knight = new Knight(0, 0);

        List<int[]> attacks = knight.calculateBounce(board1);
        assertThat(attacks).hasSize(8);
        assertThat(attacks).containsExactlyInAnyOrder(
                new int[]{1,2}, new int[]{2,1}, new int[]{1,2}, new int[]{2,1}, new int[]{1,2}, new int[]{2,1}, new int[]{1,2}, new int[]{2,1}
        );
    }

    @Test
    void calculateBouncesfromTopRightCorner(){
        Board board = new Board(8);
        int[][] board1 = board.createBoard();
        Knight knight = new Knight(7, 0);

        List<int[]> attacks = knight.calculateBounce(board1);
        assertThat(attacks).hasSize(2);
        assertThat(attacks).containsExactlyInAnyOrder(
                new int[]{5,1}, new int[]{6,2}, new int[]{5,1}, new int[]{6,2},new int[]{5,1}, new int[]{6,2}, new int[]{5,1}, new int[]{6,2}
        );
    }

    @Test
    void calculateBouncesfromEdge(){
        Board board = new Board(8);
        int[][] board1 = board.createBoard();
        Knight knight = new Knight(0, 4);

        List<int[]> attacks = knight.calculateBounce(board1);

    }
}