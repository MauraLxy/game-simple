import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import java.awt.*;
import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test for player class
 * @author Qingxuan Yan
 */
public class PlayerTest {
    Player player = new Player(new Point(1,1));

    @Test
    public void testMove() {
        player.move(Player.Direction.Up);
        assertEquals(new Point(1,0), player.location);
        player.move(Player.Direction.Down);
        assertEquals(new Point(1,1), player.location);
        player.move(Player.Direction.Left);
        assertEquals(new Point(0,1), player.location);
        player.move(Player.Direction.Right);
        assertEquals(new Point(1,1), player.location);
    }
    @Test
    public void testGetHealth() {
        assertEquals(5, player.getHealth());
    }

    @Test
    public void testGetStrength() {
        assertEquals(1, player.getStrength());
    }

    @Test
    public void testGetDefence() {
        assertEquals(1, player.getDefence());
    }

    @Test
    public void testSetHealth() {
        player.setHealth(3);
        assertEquals(3, player.getHealth());
    }

    @Test
    public void testSetStrength() {
        player.setStrength(3);
        assertEquals(3, player.getStrength());
    }

    @Test
    public void testSetDefence() {
        player.setDefence(3);
        assertEquals(3, player.getDefence());
    }

    @Test
    public void testGetCoin() {
        assertEquals(5, player.getCoin());
    }

    @Test
    public void testSetCoin() {
        player.setCoin(3);
        assertEquals(3, player.getCoin());
    }

    @Test
    public void testGetLuck() {
        assertEquals(1, player.getLuck());
    }

    @Test
    public void testSetLuck() {
        player.setLuck(100);
        assertEquals(100, player.getLuck());
    }

    @Test
    public void testAddItem() {
        player.addItemToInventory(new Item.Weapon("test1", "test1Description", 1, 1));
        player.addItemToInventory(new Item.Armour("test2", "test2Description", 1, 1));
        player.addItemToInventory(new Item.Potion("test3", "test3Description", 33, Item.Potion.PotionType.DEFENCE));
        player.addItemToInventory(new Item.Potion("test4", "test4Description", 1, Item.Potion.PotionType.STRENGTH));
        assertEquals(4, player.getInventory().size());
    }

    @Test
    public void testGetInventory() {
        player.addItemToInventory(new Item.Weapon("test1", "test1Description", 1, 1));
        player.addItemToInventory(new Item.Armour("test2", "test2Description", 1, 1));
        player.addItemToInventory(new Item.Potion("test3", "test3Description", 33, Item.Potion.PotionType.DEFENCE));
        player.addItemToInventory(new Item.Potion("test4", "test4Description", 1, Item.Potion.PotionType.STRENGTH));
        assertEquals("test1", player.getInventory().get(0).getName());
        assertEquals("test2Description", player.getInventory().get(1).getDescription());
        assertEquals(33, player.getInventory().get(2).getValue());
    }

    @Test
    public void testSaveLoad() {

        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(Item.class, new ItemAdapter())
                .setPrettyPrinting().create();

        String jsonString = gson.toJson(player);

        final Type PLAYER_TYPE = TypeToken.getParameterized(Player.class).getType();
        Player playerDeserialized = gson.fromJson(jsonString, PLAYER_TYPE);

        assertEquals(player, playerDeserialized);

    }

}
