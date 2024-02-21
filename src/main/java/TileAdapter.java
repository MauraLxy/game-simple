import com.google.gson.*;

import java.lang.reflect.Type;

public class TileAdapter implements JsonSerializer<Tile>, JsonDeserializer<Tile> {

    /** Takes tile and serializes it into JSON format.
     * @param src the object that needs to be converted to Json.
     * @param typeOfSrc the actual type (fully genericized version) of the source object.
     * @param context
     * @return a JSON object which represents the tile saved in JSON format.
     */
    @Override
    public JsonElement serialize(Tile src, Type typeOfSrc, JsonSerializationContext context) {

        //Create a new json object.
        JsonObject obj = new JsonObject();

        //Initialize the type of tile.
        String tileType = src.getClass().getCanonicalName();
        String tileTypeJson;

        //Convert the tile type to a nicer format.
        switch (tileType) {
            case "ForestTile" -> tileTypeJson = "Forest";
            case "MountainTile" -> tileTypeJson = "Mountain";
            case "PlainTile" -> tileTypeJson = "Plain";
            case "TownTile" -> tileTypeJson = "Town";
            default -> tileTypeJson = "Custom";
        }

        //Add the tile type to the json object.
        obj.addProperty("type", tileTypeJson);

        //Get the type of npc that is on the tile (blank if none)
        NPC npc = src.getNPC();
        String npcJson = "";

        //If there is a npc on the tile, get the type.
        if (npc != null) {
            String npcClass = npc.getClass().getCanonicalName();

            //Convert the npc string to a nicer format.
            switch (npcClass) {
                case "HighwaymanNPC" -> npcJson = "Highwayman";
                case "MerchantNPC" -> npcJson = "Merchant";
            }
        }

        //Add the npc to the object.
        obj.addProperty("npc", npcJson);

        return obj;
    }

    /** Deserializes json elements into their respective tile.
     * @param json The Json data being deserialized
     * @param typeOfT The type of the Object to deserialize to
     * @param context
     * @return a tile which represents the json element.
     * @throws JsonParseException
     */
    @Override
    public Tile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        //Get the json object.
        JsonObject obj = json.getAsJsonObject();

        //Get the type and npc.
        String tileType = obj.get("type").getAsString();
        String npc = obj.get("npc").getAsString();

        Tile tile;

        //Create a new tile depending on the type.
        switch (tileType) {
            case "Forest" -> tile = new ForestTile();
            case "Mountain" -> tile = new MountainTile();
            case "Plain" -> tile = new PlainTile();
            case "Town" -> tile = new TownTile();
            default -> tile = new CustomTile(null, null, "TileAdapterDeserialize", null);
        }

        //Add the tiles npc.
        switch (npc) {
            case "Merchant" -> tile.setNPC(new MerchantNPC());
            case "Highwayman" -> tile.setNPC(new HighwaymanNPC());
        }

        return tile;
    }


}
