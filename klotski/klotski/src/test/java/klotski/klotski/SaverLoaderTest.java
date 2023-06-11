package klotski.klotski;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SaverLoaderTest {
    Board board;
    Vector<String> History;
    SaverLoader sl;

    @BeforeEach
    void setUp() {
        History = new Vector<>();

        board = new Board(1);
        History.add(board.toString());

        String move = board.getPossibleMoves().get(0);
        board.evaluate(move.charAt(0) - 48, move.charAt(1));

        History.add(board.toString());

        sl = new SaverLoader();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testSave() {
        assertEquals("ok", sl.save(board.getConf(), History));
    }

    @Test
    void testLoad() {
        Vector<String> load = sl.load();
        assertNotNull(load);
        Board tmp = new Board(Board.strToBoard(load.lastElement(), Integer.parseInt(load.remove(0))));
        assertEquals(History, load);
        assertEquals(tmp, board);
    }
}