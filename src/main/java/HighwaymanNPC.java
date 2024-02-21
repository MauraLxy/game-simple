import java.util.Random;

public class HighwaymanNPC implements NPC{
    Choice choice = new HighwaymanChoice();
    String introduction = "You round a corner and suddenly a man jumps out at you. 'Your money or your life!' he shouts pistol in hand.";

    /** getter for the introduction - what is displayed when
     *  the NPC first appears
     * @return the introduction - what is displayed when the
     *                            NPC first appears
     */
    @Override
    public String getIntroduction() {
        return introduction;
    }

    /** Setter for the highwayman's introductory statement
     * @param introduction the new introduction - what is displayed
     *                     the highwayman first appears.
     */
    @Override
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /** getter for the highwayman's choice
     * @return the highwayman's current choice
     */
    @Override
    public Choice getChoice() {
        return choice;
    }

    /** Setter for the highwayman's choice
     * @param choice the new choice for the highwayman
     */
    @Override
    public void setChoice(Choice choice) {
        this.choice = choice;
    }

    private class HighwaymanChoice implements Choice{
        String choices = "1. Try to flee\n" +
                         "2. Pay the highwayman\n";

        /**getter for the choices descriptions
         * @return a description of the possible choices
         */
        @Override
        public String getChoices() {
            return choices;
        }

        /** implements the effects of a chosen event
         * @param choice a number from 1 / 2 which corresponds to a choice
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
                        player.setCoin(player.getCoin());
                        return "You try to flee, but the highway man races over to you knocking you to the ground. 'I'll give you one more chance, your money or your life!' you pay the highway man and he lets you go on your way";
                    }
                    else{
                        return "You speed off, the highway man tries to catch up to you but his horse is spooked by a snake and he looses you.";
                    }
                }
                default: {
                    player.setCoin(player.getCoin() - 1);
                    return "You hand a silver coin over to the highway man and he lets you go on your way.";
                }
            }
        }
    }
}


