public class CustomTile implements Tile{
    String description;
    Choice choice;
    Event event;

    NPC npc;

    /** Constructor method for the custom tile
     * @param event the possible events that can occur in the custom tile.
     * @param description a brief description of the tile.
     */
    public CustomTile(Event event, Choice choice, String description, NPC npc) {
        this.event = event;
        this.choice = choice;
        this.description = description;
        this.npc = npc;
    }

    /** Returns the name of the tile
     * @return the name of the tile
     */
    @Override
    public String getTileName() {
        return "Custom";
    }

    /** Returns whether the tile is walkable
     * @return whether the tile is walkable
     */
    @Override
    public boolean isWalkable() {
        return true;
    }

    /** Returns a brief description of the tile
     * @return: a brief description of the tile
     */
    @Override
    public String getDescription() {
        return description;
    }

    /** Returns and implements the events of an event defined by the tiles Event
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
}
