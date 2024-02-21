import com.google.gson.*;

import java.lang.reflect.Type;

public class ItemAdapter implements JsonSerializer<Item>, JsonDeserializer<Item> {

    /** Takes an item and serializes it into JSON format.
     * @param src the object that needs to be converted to Json.
     * @param typeOfSrc the actual type (fully genericized version) of the source object.
     * @param context Json context
     * @return a JsonElement representing the item in JSON format.
     */
    @Override
    public JsonElement serialize(Item src, Type typeOfSrc, JsonSerializationContext context) {

        //Create a new json object.
        JsonObject obj = new JsonObject();

        //Get the type of the item (Weapon, Armour, etc)
        String itemType = src.getClass().getCanonicalName().substring(5);

        //Add the basic properties to the object.
        obj.addProperty("itemType", itemType);
        obj.addProperty("name", src.getName());
        obj.addProperty("description", src.getDescription());
        obj.addProperty("value", src.getValue());

        //Add different properties to the json object depending on what kind of item it is.
        switch (itemType) {
            case "Armour" -> {
                Item.Armour armour = (Item.Armour) src;
                obj.addProperty("armour", armour.getArmour());
            }
            case "Weapon" -> {
                Item.Weapon weapon = (Item.Weapon) src;
                obj.addProperty("damage", weapon.getDamage());
            }
            case "Amulet" -> {
                Item.Amulet amulet = (Item.Amulet) src;
                obj.addProperty("type", amulet.getType().toString());
                obj.addProperty("strength", amulet.getStrength());
            }
            default -> {
                Item.Potion potion = (Item.Potion) src;
                obj.addProperty("type", potion.getType().toString());
            }
        }
        return obj;
    }

    /** Deserializes json elements into their respective item.
     * @param json The Json data being deserialized
     * @param typeOfT The type of the Object to deserialize to
     * @param context Json context
     * @return an Item object representing the JsonElement as its respective Item.
     * @throws JsonParseException
     */
    @Override
    public Item deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        //Convert the jsonElement to an object.
        JsonObject obj = json.getAsJsonObject();

        //Get the basic properties of the item.
        String itemType = obj.get("itemType").getAsString();
        String name = obj.get("name").getAsString();
        String description = obj.get("description").getAsString();
        int value = obj.get("value").getAsInt();

        //Get specific properties of the item depending on what kind of item it is.
        //Create an item object from the properties and return.
        switch (itemType) {
            case "Armour" -> {
                int armour = obj.get("armour").getAsInt();
                return new Item.Armour(name, description, value, armour);
            }
            case "Weapon" -> {
                int damage = obj.get("damage").getAsInt();
                return new Item.Weapon(name, description, value, damage);
            }
            case "Amulet" -> {
                String type = obj.get("type").getAsString();
                Item.Amulet.AmuletType amuletType = Item.Amulet.AmuletType.valueOf(type);
                int strength = obj.get("strength").getAsInt();
                return new Item.Amulet(name, description, value, amuletType, strength);
            }
            default -> {
                String type = obj.get("type").getAsString();
                Item.Potion.PotionType potionType = Item.Potion.PotionType.valueOf(type);
                return new Item.Potion(name, description, value, potionType);
            }
        }
    }
}
