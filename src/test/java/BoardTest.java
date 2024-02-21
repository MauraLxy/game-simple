import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    /**
     * @author Xingyu Liu
     * Test method for capacity of the board list
     */
    @Test
    public void testCapacity() {
        Board board1 = new Board(3, 4);
        System.out.println(board1.getTiles().size());
        assertEquals(12, board1.getTiles().size());

        Board board2 = new Board(10, 20);
        System.out.println(board2.getTiles().size());
        assertEquals(200, board2.getTiles().size());

        Board board3 = new Board(10, 5);
        System.out.println(board2.getTiles().size());
        assertEquals(50, board3.getTiles().size());
    }

    @Test
    public void testSaveLoad() {
        Board board = new Board(20, 15);

        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(Tile.class, new TileAdapter())
                .setPrettyPrinting().create();

        String jsonString = gson.toJson(board);

        final Type Board_TYPE = TypeToken.getParameterized(Board.class).getType();
        Board boardDeserialized = gson.fromJson(jsonString, Board_TYPE);

        assertEquals(board, boardDeserialized);

    }

}

