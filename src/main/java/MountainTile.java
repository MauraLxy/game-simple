public class MountainTile implements Tile{
    NPC npc = null;
    private Event event = new MountainEvent();
    private Choice choice = new MountainChoice();
    private final String description = "A large impenetrable mountain range towers above you.";

    /** Returns the name of the tile
     * @return the name of the tile
     */
    @Override
    public String getTileName() {
        return "Mountain";
    }

    /** Returns whether the tile is walkable
     * @return whether the tile is walkable
     */
    @Override
    public boolean isWalkable() {
        return false;
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

    /** Checks to see if two MountainTiles are equal
     * @param otherTile The tile to compare to.
     * @return a boolean describing if two mountain tiles are equal to each other.
     */
    @Override
    public boolean equals(Object otherTile) {
        if (this == otherTile) return true;
        if (otherTile == null || getClass() != otherTile.getClass()) return false;

        MountainTile that = (MountainTile) otherTile;

        if (this.npc == null && that.npc == null)
            return true;
        else if (!((this.npc == null) == (that.npc == null)))
            return false;
        else
            return this.npc.getClass() == that.npc.getClass();
    }

}
class MountainEvent implements Event{
    /** Returns the only event that can happen in a mountain tile in order to get
     *  players to move on.
     * @param p the player to apply the effects of the event to.
     * @return a string describing the effects of the event that just occurred.
     */
    @Override
    public String event(Player p) {
        return "It's cold here maybe you should move on.";
    }
}

class MountainChoice implements Choice{

    /**getter for the choices descriptions
     * @return a description of the possible choices
     */
    @Override
    public String getChoices() {
        return "Yeah... it's still REALLY COLD";
    }

    /** implements the effects of a chosen event
     * @param choice a number from 1 - 3 which corresponds to a choice
     * @param player the player to implement the effects on
     * @return an empty string
     */
    @Override
    public String choose(int choice,Player player) {
        return "";
    }
}

