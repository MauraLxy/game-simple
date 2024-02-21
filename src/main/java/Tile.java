public interface Tile {

    // Return the name of the tile
    public String getTileName();
    // Return whether the tile is walkable
    public boolean isWalkable();

    //gets an initial description of the surroundings
    public String getDescription();

    //gets and implements the effects of a randomised event
    public String getEvent(Player p);
    //sets the randomised event
    public void setEvent(Event event);

    //getter for the tiles' Choice (choose-able-event)
    public Choice getChoice();
    //setter for the tiles' Choice
    public void setChoice(Choice choice);

    public void setNPC(NPC npc);
    public NPC getNPC();
}
