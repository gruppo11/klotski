package klotski.klotski;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Board board;

    @BeforeEach
    void setUp() {
        board = new Board(1);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testReset() {
        Board snapshot = new Board(board);
        String move = board.getPossibleMoves().get(0);
        board.evaluate(move.charAt(0) - 48, move.charAt(1));
        board.reset();
        assertEquals(snapshot, board);
    }

    @Test
    void testSetConfiguration1() {
        board.setConfiguration1();
        int actualConf = board.getConf();
        assertEquals(1, actualConf);
        assertEquals("24422442211221120330", board.toString());
    }

    @Test
    void testSetConfiguration2() {
        board.setConfiguration2();
        int actualConf = board.getConf();
        assertEquals(2, actualConf);
    }

    @Test
    void testSetConfiguration3() {
        board.setConfiguration3();
        int actualConf = board.getConf();
        assertEquals(3, actualConf);
    }

    @Test
    void testSetConfiguration4() {
        board.setConfiguration4();
        int actualConf = board.getConf();
        assertEquals(4, actualConf);
    }

    @Test
    void testGet() {
        Coordinates actualCoord = board.get(9);
        Coordinates expectedCoord = new Coordinates(102,2);
        assertEquals(expectedCoord, actualCoord);
    }

    @Test
    void testGetBestSolution() {
        String sol = board.getBestSolution();
        for(String s : sol.split(" "))
            assertNotNull(board.evaluate(s.charAt(0) - 48, s.charAt(1)));
        assertTrue(board.checkwin(9,'s'));
    }

    @Test
    void testGetBestSolutionSequence() {
        Vector<String> sol = board.getBestSolutionSequence();
        String[] strsol = board.getBestSolution().split(" ");
        for(int i = 0; i < strsol.length; i++){
            board.evaluate(strsol[i].charAt(0) - 48, strsol[i].charAt(1));
            assertEquals(board.toString(), sol.get(i + 1));
        }
    }

    @Test
    void testSearch() {
        int outcome = board.search(50,450);
        assertEquals(-1, outcome);
        outcome = board.search(10,10);
        assertEquals(4, outcome);
    }

    @Test
    void testEvaluate() {
        String move = board.getPossibleMoves().get(0);
        String outcome = board.evaluate(move.charAt(0) - 48, move.charAt(1));
        assertEquals(move, outcome);
    }

    @Test
    void testCheckwin() {
        board = new Board(Board.strToBoard(board.getBestSolutionSequence().lastElement(), board.getConf()));
        assertTrue(board.checkwin(9,'s'));
    }

    @Test
    void testGetPossibleMoves() {
        for(String move : board.getPossibleMoves()){
            Board tmp = new Board(board);
            assertEquals(move, tmp.evaluate(move.charAt(0) - 48, move.charAt(1)));
        }
    }

    @Test
    void testToString() {
        assertEquals("24422442211221120330", board.toString());
    }

    @Test
    void testToInverseString() {
        board.setConfiguration2();
        assertEquals("14412442202210213333", board.toInverseString());
    }

    @Test
    void testStrToBoard() {
        assertEquals(board, Board.strToBoard("24422442211221120330",1));
    }
}