package klotski.klotski;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest {
    Solver solver;
    Board[] boards;

    @BeforeEach
    void setUp() {
        boards = new Board[4];
        boards[0] = new Board(1);
        boards[1] = new Board(2);
        boards[2] = new Board(3);
        boards[3] = new Board(4);
        for(int j = 0; j < 4; j++)
            for(int i = 0; i < 10; i++){
                String move = boards[j].getPossibleMoves().get(i%boards[j].getPossibleMoves().size());
                boards[j].evaluate(move.charAt(0) - 48, move.charAt(1));
            }
        solver = new Solver(boards[0]);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetNextMove() {
        for(int i = 0; i < 4; i++) {
            while (!boards[i].checkwin(9, 's')) {
                boards[i] = solver.getNextMove(boards[i]);
                assertNotNull(boards[i]);
            }

            assertTrue(boards[i].checkwin(9, 's'));
        }
    }
}