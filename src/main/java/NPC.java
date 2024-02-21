public interface NPC {
    //Gets an introductory sentence for interaction with this npc
    public String getIntroduction();
    //Setter for the introduction
    public void setIntroduction(String Introduction);

    //gets the choices associated with this NPC
    public Choice getChoice();
    //setter for the choice
    public void setChoice(Choice choice);

}
