import java.util.Random;

public class TownTile implements Tile{
    private Event event = new TownEvent();
    private Choice choice = new TownChoice();
    private final String description = "You find yourself walking through the streets of a town, people are rushing all around you.";

    private NPC npc;

    /** Returns the name of the tile
     * @return the name of the tile
     */
    @Override
    public String getTileName() {
        return "Town";
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

    /** Checks to see if two TownTiles are equal
     * @param otherTile The tile to compare to.
     * @return a boolean describing if two town tiles are equal to each other.
     */
    @Override
    public boolean equals(Object otherTile) {
        if (this == otherTile) return true;
        if (otherTile == null || getClass() != otherTile.getClass()) return false;

        TownTile that = (TownTile) otherTile;

        if (this.npc == null && that.npc == null)
            return true;
        else if (!((this.npc == null) == (that.npc == null)))
            return false;
        else
            return this.npc.getClass() == that.npc.getClass();
    }

}

class TownEvent implements Event{
    String pickpocket = "A small kid is running through the streets and 'accidentally' bumps into you. It was a pickpocket who stole something from your bag!";
    String drunkard = "A man stumbles out of a pub and makes his way to you. 'hey! You're simple simon! Give me my money!' he scruffs you but quickly realises you are in fact not simple simon, apologises and pays you some money for the trouble. You take damage but gain a silver coin.";
    String amulet = "A young woman walks up to you and hands you an amulet, 'You will need this more than me' you feel stronger.";
    String nothing = "'What a quaint little street...' you think to yourself";

    /** Returns a random event that can occur in a town tile
     * @param player the player to apply the effects of the event to
     * @return a string describing the effects of the random event that just occurred.
     */
    @Override
    public String event(Player player) {
        Random random = new Random();
        int rand = random.nextInt(20);

        switch (rand) {
            case (0):
                player.getInventory().remove(random.nextInt(player.getInventory().size()));
                return pickpocket;
            case (17), (18):
                player.setHealth(player.getHealth()-1);
                player.setCoin(player.getCoin()+1);
                return drunkard;
            case (19):
                player.getInventory().add(new Item.Amulet("Amulet of Strength", "An amulet which makes you feel and act stronger", 4, Item.Amulet.AmuletType.STRENGTH, 2));
                return amulet;
            default:
                return nothing;
        }
    }
}

class TownChoice implements Choice{
    String choices = "1. Walk down an ally\n" +
            "2. Talk to some people\n" +
            "3. Stay the night in the local inn\n";

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
        int roll = random.nextInt(20) + player.getLuck();
        switch(choice){
            case 1: {
                if(roll < 10){
                    player.setHealth(player.getHealth());
                    return "The ally is dark and dingy, a rat jumps out and bites you!";
                }
                else{
                    player.setCoin(player.getCoin()+1);
                    return "The ally seems rather dodgy, you decide to turn around and on your way out you find a silver coin!";
                }
            }
            case 2: {
                if(roll < 10){
                    player.setLuck(player.getLuck()-1);
                    return "you start talking with a fortune teller, she looks into her crystal ball 'ohh... ohhh no child! You are in terrible peril!' what bad luck.";
                }
                else{
                    player.setStrength(player.getStrength()+1);
                    player.setHealth(player.getHealth()+1);
                    return "you begin talking with an old man, he tells you stories of his childhood and offers you food and a place to stay for the night. You feel well rested and well fed.";
                }
            }
            default: {
                if(roll < 10){
                    player.setCoin(player.getCoin()-1);
                    return "the inn is rather worse for wear, you pay a coin for the night but the bed is hard and the food is bad";
                }
                else{
                    player.setStrength(player.getStrength()+1);
                    player.setHealth(player.getHealth()+1);
                    return "You walk into the inn and get talking with the keep. You figure out that he is your fourth cousin twice removed and he offers you a bed free of charge!";
                }
            }
        }
    }
}