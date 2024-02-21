import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TileTest {

    /**
     *  Test method for the ForestTile
     */
    @Test
    public void testForest() {
        Tile tile = new ForestTile();
        String description = tile.getDescription();
        assertNotNull(description);
        tile.setEvent(new TileTestEvent());
        assertEquals("TEST", (tile.getEvent(new Player(new Point(0, 0)))));
    }

    /**
     *  Test method for the MountainTiles
     */
    @Test
    public void testMountain() {
        Tile tile = new MountainTile();
        String description = tile.getDescription();
        assertNotNull(description);
        tile.setEvent(new TileTestEvent());
        assertEquals("TEST", (tile.getEvent(new Player(new Point(0, 0)))));
    }

    /**
     * Test method for the plainTile
     */
    @Test
    public void testPlain() {
        Tile tile = new PlainTile();
        String description = tile.getDescription();
        assertNotNull(description);
        tile.setEvent(new TileTestEvent());
        assertEquals("TEST", (tile.getEvent(new Player(new Point(0, 0)))));
    }

    /**
     * Test method for the townTile
     */
    @Test
    public void testTown() {
        Tile tile = new TownTile();
        String description = tile.getDescription();
        assertNotNull(description);
        tile.setEvent(new TileTestEvent());
        assertEquals("TEST", (tile.getEvent(new Player(new Point(0, 0)))));
    }

    /**
     * Test method for the customTile
     */
    @Test
    public void testCustom() {
        Tile tile = new CustomTile(new TileTestEvent(), null, "description", null);
        String description = tile.getDescription();
        assertNotNull(description);
        tile.setEvent(new TileTestEvent());
        assertEquals("TEST", (tile.getEvent(new Player(new Point(0, 0)))));
    }

}

class TileTestEvent implements Event{
    @Override
    public String event(Player p) {
        return "TEST";
    }
}