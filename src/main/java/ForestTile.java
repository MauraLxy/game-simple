import java.util.Random;
public class ForestTile implements Tile{
    private Event event = new ForestEvent();
    private Choice choice = new ForestChoice();
    private final String description = "You happen upon a dense forest, a dirt path winds past trees, small shrubs and brambles.";

    private NPC npc;

    /** Returns the name of the tile
     * @return the name of the tile
     */
    @Override
    public String getTileName() {
        return "Forest";
    }

    /** Returns whether the tile is walkable
     * @return whether the tile is walkable
     */
    @Override
    public boolean isWalkable() {
        return true;
    }

    /** Returns a brief description of the tile
     * @return a brief description of the tile
     */
    @Override
    public String getDescription() {
        return description;
    }

    /** Returns and implements the events of an event defined by the
     *  tiles Event
     * @param p the player to apply any effects of the event to
     * @return a description of the event and it's effects
     */
    @Override
    public String getEvent(Player p) {
        return event.event(p);
    }

    /** Setter for the tiles' Event to allow for a custom
     *  event to occur on a given tile.
     * @param event the Event to set the tile to.
     */
    @Override
    public void setEvent(Event event) {
        this.event = event;
    }

    /** getter for the choice associated with this tile
     * @return the choice object for this class
     */
    @Override
    public Choice getChoice() {
        return choice;
    }

    /** Setter for the choice associated with this tile
     * @param choice the new choice object for this tile
     */
    @Override
    public void setChoice(Choice choice) {
        this.choice = choice;
    }

    /** Setter for the NPC associated with this tile
     * @param npc the new NPC object for this tile
     */
    @Override
    public void setNPC(NPC npc) {
        this.npc = npc;
    }

    /** Getter for the NPC associated with this tile
     * @return the NPC object for this tile
     */
    @Override
    public NPC getNPC() {
        return this.npc;
    }

    /** Checks to see if two ForestTiles are equal
     * @param otherTile The tile to compare to.
     * @return a boolean describing if two forest tiles are equal to eachother.
     */
    @Override
    public boolean equals(Object otherTile) {
        if (this == otherTile) return true;
        if (otherTile == null || getClass() != otherTile.getClass()) return false;

        ForestTile that = (ForestTile) otherTile;

        if (this.npc == null && that.npc == null)
            return true;
        else if (!((this.npc == null) == (that.npc == null)))
            return false;
        else
            return this.npc.getClass() == that.npc.getClass();
    }
}

class ForestEvent implements Event{
    String trip = "As you're walking, you trip on a root and fall over. Without you realising, something fell out of your bag.";
    String shiny = "After some time walking, you see a something shiny in the brambles just ahead of you. You reach in and find a handful of silver coins! But in the process you scratched yourself and took damage.";
    String lucky = "You're walking along and look down to find a four leaf clover and just to the left of it a five leaf clover! Wow how lucky.";
    String nothing = "'Nothing much seems to be happening in this neck of the woods.' you think to yourself.";

    /** Returns a random event that can occur in a forest tile
     * @param player the player to apply the effects of the event to
     * @return a string describing the effects of the random event that just occurred.
     */
    @Override
    public String event(Player player) {
        Random random = new Random();
        int rand = random.nextInt(20);

        switch(rand){
            case(0):
                player.getInventory().remove(random.nextInt(player.getInventory().size()));
                return trip;
            case(17): case(18):
                player.setHealth(player.getHealth()-1);
                switch(random.nextInt(4)){
                    case 0 -> player.getInventory().add(new Item.Potion("Health Potion", "A potion that increases your health", 2, Item.Potion.PotionType.HEALING));
                    case 1 -> player.getInventory().add(new Item.Potion("Strength Potion", "A potion that increases your strength", 2, Item.Potion.PotionType.STRENGTH));
                    case 2 -> player.getInventory().add(new Item.Potion("Defence Potion", "A potion that increases your defence", 2, Item.Potion.PotionType.DEFENCE));
                    case 3 -> player.getInventory().add(new Item.Potion("Luck Potion", "A potion that increases your luck", 2, Item.Potion.PotionType.LUCK));
                }
                return shiny;
            case(19):
                player.setLuck(player.getLuck()+1);
                return lucky;
            default:
                return nothing;
        }
    }
}

class ForestChoice implements Choice{
    String choices = "1. Go hunting\n" +
            "2. Collect firewood\n" +
            "3. Rest under a tree\n";

    /**getter for the choices descriptions
     * @return a description of the possible choices
     */
    @Override
    public String getChoices() {
        return choices;
    }

    /** implements the effects of a chosen event
     * @param choice a number from 1 - 3 which corresponds to a choice
     * @param player the player to implement the effects on
     * @return a description of the outcome of that choice
     */
    @Override
    public String choose(int choice,Player player) {
        Random random = new Random();
        int roll = random.nextInt(20)+ player.getLuck();
        switch(choice){
            case 1: {
                if(roll < 10){
                    player.setLuck(player.getLuck()-1);
                    return "You go out hunting trying to find some deer but you don't find any, how unlucky.";
                }
                else{
                    player.getInventory().add(new Item.Potion("Rabbit Meat", "Rabbit meat is very nutritious and great in a stew!", 1, Item.Potion.PotionType.FOOD));
                    return "Your hunting trip is very successful having caught several rabbits which you store for later.";
                }
            }
            case 2: {
                if(roll < 10){
                    player.setHealth(player.getHealth()-1);
                    return "You go out to collect firewood but the ground is very damp and you get wet and cold.";
                }
                else{
                    player.setHealth(player.getHealth()+1);
                    return "You find some great firewood and create a roaring fire. You feel very well rested";
                }
            }
            default: {
                if(roll < 15){
                    return "This tree has a lot of roots and you really can't get comfortable";
                }
                else{
                    player.setHealth(player.getHealth()+1);
                    return "You take a lovely rest under a grand old oak tree.";
                }
            }
        }
    }
}
