public interface Choice {
    //provides a description of each of the possible choices
    public String getChoices();
    //enacts the effects of a choice
    public String choose(int choice, Player player);
}
