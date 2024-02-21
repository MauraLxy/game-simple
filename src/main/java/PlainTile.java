import java.util.Random;

public class PlainTile implements Tile{
    private Event event = new PlainEvent();
    private Choice choice = new PlainChoice();
    private final String description = "You find yourself wandering through lush plains with gently rolling hills.";

    private NPC npc;

    /** Returns the name of the tile
     * @return the name of the tile
     */
    @Override
    public String getTileName() {
        return "Plain";
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
        return this.event.event(p);
    }

    /** Setter for the tiles' Event to allow for a custom event to occur on a given tile.
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
        return this.choice;
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

    /** Checks to see if two PlainTiles are equal
     * @param otherTile The tile to compare to.
     * @return a boolean describing if two plain tiles are equal to each other.
     */
    @Override
    public boolean equals(Object otherTile) {
        if (this == otherTile) return true;
        if (otherTile == null || getClass() != otherTile.getClass()) return false;

        PlainTile that = (PlainTile) otherTile;

        if (this.npc == null && that.npc == null)
            return true;
        else if (!((this.npc == null) == (that.npc == null)))
            return false;
        else
            return this.npc.getClass() == that.npc.getClass();
    }

}
class PlainEvent implements Event{
    String farmer = "You decide to cut across a field to save some time but accidentally trample some crops. A farmer sees and demands payment. You lose one silver coin.";
    String food = "while walking along a public road, a wagon filled with pumpkins rushes by. It hits a pothole and one of the pumpkins falls off hitting you on the head. You gain some food but take some damage.";
    String river = "A small river crossing is before you. You fill your cask with the fresh water and take a drink. You feel refreshed and gain more health!";
    String nothing = "It's a beautiful day, you walk on with the sun shining on your back.";

    /** Returns a random event that can occur in a plains tile.
     * @param player the player to apply the effects of the event to.
     * @return a string describing the effects of the random event that just occurred.
     */
    @Override
    public String event(Player player) {
        Random random = new Random();
        int rand = random.nextInt(20);

        switch (rand) {
            case (0):
                player.setCoin(player.getCoin()-1);
                return farmer;
            case (17), (18):
                player.getInventory().add(new Item.Potion("Pumpkin", "A great vegetable, very yummy!", 1, Item.Potion.PotionType.FOOD));
                player.setHealth(player.getHealth()-1);
                return food;
            case (19):
                player.setHealth(player.getHealth()+1);
                player.setStrength(player.getStrength()+1);
                return river;
            default:
                return nothing;
        }
    }
}

class PlainChoice implements Choice{
    String choices = "1. Rest\n" +
                     "2. Collect berries\n" +
                     "3. Smell the roses\n";

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
    public String choose(int choice, Player player) {
        Random random = new Random();
        int roll = random.nextInt(20)+ player.getLuck();
        switch(choice){
            case 1: {
                if(roll < 10){
                    player.setHealth(player.getHealth()-1);
                    return "you decide to get some sleep in a field. It is a cold and rainy night... 'what a poor decision' you think";
                }
                else{
                    player.setHealth(player.getHealth()+1);
                    return "You find a lovely field to spend the night in and rest under a blanket of stars";
                }
            }
            case 2: {
                if(roll < 10){
                    player.setHealth(player.getHealth()-1);
                    return "you pick some berries from a bush and scratch your hand.";
                }
                else{
                    player.setLuck(player.getLuck());
                    return "You see a bird eating some berries off a bush and instantly dropping dead! PHEW!! that was close.";
                }
            }
            default: {
                return "Hmmm sometimes it's good to slow down a bit but I'd best be on my way now.";
            }
        }
    }
}