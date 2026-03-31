import org.example.Board;
import org.example.Knight;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class test {
    @Test
    void checkBoard(){
        Board board = new Board();
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
        Board board = new Board();
        int[][] currentBoard = board.createBoard();
        Knight knight = new Knight(7, 1);
        assertThat(currentBoard[knight.getX()][knight.getY()]).isEqualTo(0);
        currentBoard = knight.setKnight(currentBoard);
        assertThat(currentBoard[knight.getX()][knight.getY()]).isEqualTo(1);
    }

    @Test
    void spawningKnightonObstacle(){
        Board board = new Board();
        int[][] board1 = board.createBoard();
        int targetX = 3;
        int targetY = 3;

        board1[targetX][targetY] = 2;
        Knight knight = new Knight(targetX, targetY);
        assertThatThrownBy(() -> knight.setKnight(board1)).isInstanceOf(IllegalStateException.class).hasMessage("Pole zajete");
        assertThat(board1[targetX][targetY]).isEqualTo(2);
    }

    @Test
    void checkIfEnemyOnPath(){
        Board board = new Board();
        int[][] board1 = board.createBoard();
        Knight knight = new Knight(5, 4);
        board1[6][7] = 2;
        board1[3][3] = 2;
        boolean calculate = knight.calculateAttack(board1);
        assertThat(calculate).isTrue();
    }

    @Test
    void moveToEmptySquare(){
        Board board = new Board();
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
        Board board = new Board();
        int[][] board1 = board.createBoard();
        Knight knight = new Knight(3, 3);
        board1[4][5] = 2;
        boolean result = knight.moveKnight(board1,4 ,5);
        assertThat(result).isTrue();
        assertThat(board1[4][5]).isEqualTo(1);
    }

    @Test
    void moveInLShape(){
        Board board = new Board();
        int[][] board1 = board.createBoard();
        Knight knight = new Knight(3, 3);
        board1[3][3] = 1;

        boolean result = knight.moveKnight(board1,5 ,5);
        assertThat(result).isFalse();
        assertThat(knight.getX()).isEqualTo(3);
    }
}