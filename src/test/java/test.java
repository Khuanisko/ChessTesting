import org.example.Board;
import org.example.Knight;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class test {

    // Sprawdza, czy po utworzeniu planszy 8x8 ma ona odpowiednie wymiary i czy kluczowe pola (np. rogi) są domyślnie puste
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

    // Sprawdza, czy skoczek znajduje się na planszy o wymiarach 8x8 (czy nie jest za plansza)
    @Test
    void checkKnigthBounds(){
        Knight knight = new Knight(7, 1);
        assertThat(knight.getX()).isGreaterThan(-1).isLessThan(8);
        assertThat(knight.getY()).isGreaterThan(-1).isLessThan(8);
    }

    // Weryfikuje, czy metoda setKnight poprawnie stawia Skoczka na pustym polu.
    @Test
    void checkIfPostionisFree(){
        Board board = new Board(8);
        int[][] currentBoard = board.createBoard();
        Knight knight = new Knight(7, 1);
        assertThat(currentBoard[knight.getX()][knight.getY()]).isEqualTo(0);
        currentBoard = knight.setKnight(currentBoard);
        assertThat(currentBoard[knight.getX()][knight.getY()]).isEqualTo(1);
    }

    // Próba postawienia Skoczka na polu, na którym już coś stoi (wartość 2).
    // Oczekujemy, że program rzuci błędem (wyjątkiem) "Pole zajete".
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


    // Symuluje normalny ruch Skoczka na puste pole obok.
    // Metoda powinna zwrócić true, a na planszy jedynka musi przenieść się na nowe miejsce.
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

    // Symulujemy bicie skoczkiem
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

    // Próba wykonania ruchu po skosie, a nie w literę "L".
    // Metoda moveKnight musi to zablokować, zwrócić false i nie zmieniać pozycji.
    @Test
    void moveInLShape(){
        Board board = new Board(8);
        int[][] board1 = board.createBoard();
        Knight knight = new Knight(3, 3);
        board1[3][3] = 1;

        boolean result = knight.moveKnight(board1,5 ,5);
        assertThat(result).isFalse();
        assertThat(knight.getX()).isEqualTo(3);
        assertThat(knight.getY()).isEqualTo(3);
    }

    // Żaden z 8 ataków nie wypada za bandę, więc wszystkie odbicia dają standardowe pozycje.
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

    // 4 Testy dla narożników
    // Sprawdzają one fizykę bilardową. Z narożnika większość ataków trafia prosto w bandy.
    // Skoczek uderza w ściany i nakłada swoje ataki w kółko na te same dwa pola.
    @Test
    void calculateBouncesfromTopLeftCorner() {
        Board board = new Board(8);
        int[][] board1 = board.createBoard();
        Knight knight = new Knight(0, 0);

        List<int[]> attacks = knight.calculateAttack(board1);

        assertThat(attacks).hasSize(8);
        assertThat(attacks).containsExactlyInAnyOrder(
                new int[]{1, 2}, new int[]{2, 1}, new int[]{1, 2}, new int[]{2, 1},
                new int[]{1, 2}, new int[]{2, 1}, new int[]{1, 2}, new int[]{2, 1}
        );
    }

    @Test
    void calculateBouncesfromTopRightCorner() {
        Board board = new Board(8);
        int[][] board1 = board.createBoard();
        Knight knight = new Knight(7, 0);

        List<int[]> attacks = knight.calculateAttack(board1);

        assertThat(attacks).hasSize(8);
        assertThat(attacks).containsExactlyInAnyOrder(
                new int[]{5, 1}, new int[]{6, 2}, new int[]{5, 1}, new int[]{6, 2},
                new int[]{5, 1}, new int[]{6, 2}, new int[]{5, 1}, new int[]{6, 2}
        );
    }
    @Test
    void calculateBouncesfromBottomLeftCorner(){
        Board board = new Board(8);
        int[][] board1 = board.createBoard();
        Knight knight = new Knight(0, 7);

        List<int[]> attacks = knight.calculateAttack(board1);
        assertThat(attacks).hasSize(8);
        assertThat(attacks).containsExactlyInAnyOrder(
                new int[]{1,5}, new int[]{2,6}, new int[]{1,5}, new int[]{2,6},new int[]{1,5}, new int[]{2,6}, new int[]{1,5}, new int[]{2,6}
        );
    }

    @Test
    void calculateBouncesfromBottomRightCorner(){
        Board board = new Board(8);
        int[][] board1 = board.createBoard();
        Knight knight = new Knight(7, 7);

        List<int[]> attacks = knight.calculateAttack(board1);
        assertThat(attacks).hasSize(8);
        assertThat(attacks).containsExactlyInAnyOrder(
            new int[]{5,6}, new int[]{6,5}, new int[]{5,6}, new int[]{6,5}, new int[]{5,6}, new int[]{6,5}, new int[]{5,6}, new int[]{6,5}
            );
    }


    // Odbicie od lewej krawędzi (ale nie rogu).
    // Część ruchów ląduje normalnie, a część uderza w bandę X=0 i odbija się z powrotem.
    @Test
    void calculateBouncesfromEdge(){
        Board board = new Board(8);
        int[][] board1 = board.createBoard();
        Knight knight = new Knight(0, 4);

        List<int[]> attacks = knight.calculateAttack(board1);

        assertThat(attacks).hasSize(8);
        assertThat(attacks).containsExactlyInAnyOrder(
                new int[]{2, 5}, new int[]{1, 6}, new int[]{1, 6}, new int[]{2, 5},
                new int[]{2, 3}, new int[]{1, 2}, new int[]{1, 2}, new int[]{2, 3}
        );
    }

    // Test logiczny dla planszy 3x3. Skoczek stoi idealnie na środku.
    // Żaden z jego 8 ruchów nie mieści się na tej planszy, więc każdy z nich musi się odbić.
    // Układają się one w krzyż wokół niego.
    @Test
    void calculateBouncesonSmallBoard(){
        Board board = new Board(3);
        int[][] board1 = board.createBoard();
        Knight knight = new Knight(1, 1);

        List<int[]> attacks = knight.calculateAttack(board1);

        assertThat(attacks).hasSize(8);
        assertThat(attacks).containsOnly(
                new int[]{1, 0},
                new int[]{1, 2},
                new int[]{0, 1},
                new int[]{2, 1}
        );
    }
    // Ten test udowadnia, że nawet obudowany ze wszystkich stron, skoczek ma swoje 8 ataków, ponieważ przeskakuje nad przeszkodami.
    @Test
    void calculateAttackIgnoreObstacle(){
        Board board = new Board(8);
        int[][] board1 = board.createBoard();

        Knight knight = new Knight(4, 4);

        board1[5][4] = 2;
        board1[3][4] = 2;
        board1[4][5] = 2;
        board1[4][3] = 2;

        List<int[]> attacks = knight.calculateAttack(board1);

        assertThat(attacks).hasSize(8);
    }
    // Przypadek testowy sprawdzający pętlę "while" z klasy Knight.
    // Na takiej planszy Skoczek może uderzyć np. w lewą bandę, przelecieć na wylot i uderzyć w prawą.
    // Test sprawdza, czy ostatecznie ląduje w granicach od 0 do 1.
    @Test
    void calculateAttack2x2(){
        Board board = new Board(2);
        int[][] board1 = board.createBoard();

        Knight knight = new Knight(0, 0);

        List<int[]> attacks = knight.calculateAttack(board1);
        assertThat(attacks).hasSize(8);

        for(int[] attack : attacks){
            assertThat(attack[0]).isBetween(0,1);
            assertThat(attack[1]).isBetween(0,1);
        }

    }

    // Skoczek może celować za planszę atakiem, ale fizycznie nie może się ruszyć na pole ujemne
    @Test
    void moveKnight_shouldReturnFalseWhenMovingOutOfBounds(){
        Board board = new Board(8);
        int[][] boardMatrix = board.createBoard();
        Knight knight = new Knight(0, 0);
        boardMatrix[0][0] = 1;

        boolean result = knight.moveKnight(boardMatrix, -1, -2);

        assertThat(result).isFalse();
        assertThat(knight.getX()).isEqualTo(0);
        assertThat(knight.getY()).isEqualTo(0);
    }
    // Zapobieganie "Friendly Fire".
    @Test
    void moveKnight_shouldReturnFalseWhenTargetOccupiedByAlly(){
        Board board = new Board(8);
        int[][] boardMatrix = board.createBoard();
        Knight knight = new Knight(3, 3);
        boardMatrix[3][3] = 1;

        boardMatrix[5][4] = 1;

        boolean result = knight.moveKnight(boardMatrix, 5, 4);

        assertThat(result).isFalse();
        assertThat(boardMatrix[3][3]).isEqualTo(1);
    }



}