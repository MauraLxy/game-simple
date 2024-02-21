import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class MerchantNPC implements NPC{
    Item item;
    Choice choice;
    String introduction = "You see a cart up ahead, there stands a person selling their wares!";

    public MerchantNPC(){
        this.item = createItem();
        choice = new MerchantChoice(this);
    }
    /** getter for the introduction - what is displayed when
     *  the NPC first appears
     * @return the introduction - what is displayed when the
     *                            NPC first appears
     */
    @Override
    public String getIntroduction() {
        return introduction;
    }

    /** Setter for the NPC's introductory statement
     * @param introduction the new introduction - what is displayed
     *                     the merchant first appears.
     */
    @Override
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /** getter for the merchant's choice
     * @return the merchant's current choice
     */
    @Override
    public Choice getChoice() {
        return choice;
    }

    /** Setter for the merchant's choice
     * @param choice the new choice for the merchant
     */
    @Override
    public void setChoice(Choice choice) {
        this.choice = choice;
    }

    /** Creates a random item for the merchant to sell
     *
     * @return a random item
     */
    private Item createItem(){
        //Create a new gson and add the ItemAdapter.
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(Item.class, new ItemAdapter())
                .setPrettyPrinting().create();

        //Initialize jsonReader and create an arraylist for items.
        JsonReader jsonReader;
        ArrayList<Item> items = new ArrayList<>();

        //Get the type of ArrayList<Item>.
        final Type ITEM_LIST_TYPE = TypeToken.getParameterized(ArrayList.class, Item.class).getType();

        try{
            //Read the items file and convert to a list.
            jsonReader = new JsonReader(new FileReader("./src/main/resources/items.txt"));
            items = gson.fromJson(jsonReader, ITEM_LIST_TYPE);
        }catch (Exception e) {
            e.printStackTrace();
        }
        //Pick a random item from the list and return it.
        Random random = new Random();
        int rand = random.nextInt(items.size());
        return items.get(rand);

    }
}

class MerchantChoice implements Choice{
    MerchantNPC npc;
    boolean purchased = false;

    /**Constructor for Merchant Choice
     * @param npc the MerchantNPC this choice belongs to
     */
    public MerchantChoice(MerchantNPC npc){
         this.npc = npc;
    }

    /**getter for the choices descriptions
     * @return a description of the possible choices
     */
    @Override
    public String getChoices() {
        if(!purchased){
            return  "1. Purchase " + npc.item.getName() + " for " + npc.item.getValue() + " silver pieces\n" +
                    "2. Leave\n";
        }
        else {
            return "1. Leave\n";
        }
    }

    /** implements the effects of a chosen event
     * @param choice a number from 1 / 2 which corresponds to a choice
     * @param player the player to implement the effects on
     * @return returns a string describing the outcome of the choice
     */
    @Override
    public String choose(int choice, Player player) {
        if (choice == 1) {
            if (!purchased) {
                player.getInventory().add(npc.item);
                this.purchased = true;
                return "You have purchased " + npc.item.getName() + "!";
            }
            return "Hmm they don't seem to have gotten anything new yet";
        }
        return "'I can always come back if I want' you think";
    }
}
