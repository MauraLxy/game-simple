import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Player {

    private transient GUI gui;
    private int health = 5;
    private int strength = 1;
    private int defence = 1;
    private int luck = 1;

    private Item.Weapon currentWeapon = new Item.Weapon("Wooden Sword", "It's not a toy!",
            1, 1);
    private Item.Armour currentArmour = new Item.Armour("Rags",
            "A set of rags, they barely protect you from the cold", 1, 1);
    private Item.Amulet currentAmulet = null;

    private int coin = 5;
    private ArrayList<Item> inventory = new ArrayList<>();
    public Point location;

    public enum Direction {
        Up,
        Down,
        Left,
        Right
    }

    /**
     * Constructor for Player
     * @param location is a Point containing the x and y position
     */
    public Player(Point location) {
        this.location = location;
    }

    /** Sets the gui of the player.
     * @param gui The games GUI
     */
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    /**
     * Move the player towards a direction
     * @param direction including up, down, left and right
     */
    public void move(Direction direction) {
        switch (direction) {
            case Up -> location.y--;
            case Down -> location.y++;
            case Left -> location.x--;
            case Right -> location.x++;
        }
    }

    /**
     * The getter of health
     * @return the health of the player
     */
    public int getHealth() {
        return health;
    }

    /**
     * The setter of the health
     * @param health the health of the player
     */
    public void setHealth(int health) {
        this.health = health;
        // End the game if the player's health is 0
        if (health == 0) {
            // Pop up a dialog message box
            JOptionPane.showMessageDialog(this.gui, "You died! Game over!");
            System.exit(0);
        }
    }

    /**
     * The getter of strength
     * @return the strength of the player
     */
    public int getStrength() {
        return strength;
    }

    /**
     * The setter of strength
     * @param strength the strength of the player
     */
    public void setStrength(int strength) {
        this.strength = strength;
    }

    /**
     * The getter of defence
     * @return the defence of the player
     */
    public int getDefence() {
        return defence;
    }

    /**
     * The setter of defence
     * @param defence the defence of the player
     */
    public void setDefence(int defence) {
        this.defence = defence;
    }

    /**
     * The getter of defence
     * @return the defence of the player
     */
    public int getLuck() {
        return luck;
    }

    /**
     * The setter of luck
     * @param luck the luck of the player
     */
    public void setLuck(int luck) {
        this.luck = luck;
    }

    /**
     * The getter of inventory
     * @return the inventory of the player
     */
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    /**
     * Adds an item to the players inventory.
     * @param item Item to add to the inventory.
     */
    public void addItemToInventory(Item item) {
        inventory.add(item);
    }

    /**
     * @return The player's current weapon.
     */
    public Item.Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    /**
     * @return the player's current armour.
     */
    public Item.Armour getCurrentArmour() {
        return currentArmour;
    }

    /**
     * @return the player's current amulet.
     */
    public Item.Amulet getCurrentAmulet() {
        return currentAmulet;
    }

    /**
     * The getter of coin
     * @return the coin of the player
     */
    public int getCoin() {
        return coin;
    }

    /**
     * The setter of coin
     * @param coin the coin of the player
     */
    public void setCoin(int coin) {
        this.coin = coin;
    }

    /**
     * The getter of the player's current status
     * @return the string of current status of the player
     */
    public String toString() {
        return "Player status:\n" +
                "location: (" + location.x + "," + location.y + ")" + "\n" +
                "health: " + health + "\n" +
                "strength: " + strength +"\n" +
                "defence: " + defence + "\n" +
                "luck: " + luck + "\n" +
                "coin: " + coin + "\n" +
                "weapon: " + currentWeapon.getName() + "\n" +
                "weapon damage: " + currentWeapon.getDamage() + "\n" +
                "weapon description: " + currentWeapon.getDescription() + "\n" +
                "armour: " + currentArmour.getName() + "\n" +
                "armour defence: " + currentArmour.getArmour() + "\n" +
                "armour description: " + currentArmour.getDescription() + "\n" +
                "amulet: " + (currentAmulet == null ? "None" : currentAmulet.getName()) + "\n" +
                "amulet strength: " + (currentAmulet == null ? "None" : currentAmulet.getStrength()) + "\n" +
                "amulet description: " + (currentAmulet == null ? "None" : currentAmulet.getDescription());
    }

    /**
     * @param otherPlayer The player to compare to.
     * @return a boolean describing if this player equals another player.
     */
    @Override
    public boolean equals(Object otherPlayer) {
        if (this == otherPlayer) return true;
        if (otherPlayer == null || getClass() != otherPlayer.getClass()) return false;

        Player player = (Player) otherPlayer;

        if (getHealth() != player.getHealth()) {
            return false;
        }
        if (getStrength() != player.getStrength()) {
            return false;
        }
        if (getDefence() != player.getDefence()) {
            return false;
        }
        if (getLuck() != player.getLuck()) {
            return false;
        }
        if (getCoin() != player.getCoin()) {
            return false;
        }
        if (getCurrentWeapon() != null ? !getCurrentWeapon().equals(player.getCurrentWeapon()) : player.getCurrentWeapon() != null)
            return false;
        if (getCurrentArmour() != null ? !getCurrentArmour().equals(player.getCurrentArmour()) : player.getCurrentArmour() != null)
            return false;
        if (getCurrentAmulet() != null ? !getCurrentAmulet().equals(player.getCurrentAmulet()) : player.getCurrentAmulet() != null)
            return false;
        if (getInventory() != null ? !getInventory().equals(player.getInventory()) : player.getInventory() != null)
            return false;
        return Objects.equals(location, player.location);
    }
}
