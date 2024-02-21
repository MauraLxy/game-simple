import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {
    Item.Weapon weapon = new Item.Weapon("testWeapon", "This is a test weapon", 1, 1);
    Item.Armour armour = new Item.Armour("testArmour", "Test Armour", 2, 2);
    Item.Amulet amuletStrength = new Item.Amulet("testAmuletStrength", "Test Amulet Strength",
            10, Item.Amulet.AmuletType.STRENGTH, 2);
    Item.Amulet amuletLuck = new Item.Amulet("testAmuletLuck", "Luck Amulet",
            10, Item.Amulet.AmuletType.LUCK, 2);
    Item.Potion potionHealth = new Item.Potion("testPotion", "blackpink!",
            1, Item.Potion.PotionType.HEALING);


    @Test
    public void testEquals() {

        Item.Weapon weaponDuplicate = new Item.Weapon("testWeapon", "This is a test weapon", 1, 1);
        assertEquals(weapon, weaponDuplicate);

        Item.Armour armourDuplicate = new Item.Armour("testArmour", "Test Armour", 2, 2);
        assertEquals(armour, armourDuplicate);

        Item.Amulet amuletDuplicate = new Item.Amulet("testAmuletStrength", "Test Amulet Strength",
                10, Item.Amulet.AmuletType.STRENGTH, 2);
        assertEquals(amuletStrength, amuletDuplicate);

        Item.Potion potionDuplicate = new Item.Potion("testPotion", "blackpink!",
                1, Item.Potion.PotionType.HEALING);
        assertEquals(potionHealth, potionDuplicate);

    }

    @Test
    public void testSerializationDeserialization() {
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(Item.class, new ItemAdapter())
                .setPrettyPrinting().create();

        ArrayList<Item> itemsOriginal = new ArrayList<>(List.of(weapon, armour, amuletStrength,
                amuletLuck, potionHealth));

        String jsonString = gson.toJson(itemsOriginal);
        final Type ITEM_LIST_TYPE = TypeToken.getParameterized(ArrayList.class, Item.class).getType();
        ArrayList<Item> itemsDeserialized = gson.fromJson(jsonString, ITEM_LIST_TYPE);

        assertEquals(itemsOriginal, itemsDeserialized);

    }

    @Test
    public void testPotionUse() {
        Player player = new Player(new Point(0,0));

        int playerHealth = player.getHealth();
        int inventorySize = player.getInventory().size();

        player.addItemToInventory(potionHealth);
        potionHealth.use(player);

        assertEquals(playerHealth + 2, player.getHealth(), "Player health different");
        assertEquals(inventorySize, player.getInventory().size(), "Inventory size wrong");

    }

}
