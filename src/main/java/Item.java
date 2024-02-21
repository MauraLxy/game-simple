
public abstract class Item {

    private final String name;
    private final String description;
    private final int value;

    /** Constructor for abstract item class.
     * @param name The name of the item.
     * @param description A description of the item.
     * @param value The cost of the item.
     */
    private Item(String name, String description, int value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

    /**
     * @return the name of the item.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return a description of the item.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @return the value of the item.
     */
    public int getValue() {
        return this.value;
    }

    public static class Weapon extends Item {

        private final int damage;

        /** Constructor to make a Weapon item.
         * @param name The name of the weapon.
         * @param description A description if the weapon.
         * @param value The cost of the weapon.
         * @param damage The damage that the weapon does.
         */
        public Weapon(String name, String description, int value, int damage) {
            super(name, description, value);
            this.damage = damage;
        }

        /**
         * @return the damage that the weapon does.
         */
        public int getDamage() {
            return this.damage;
        }

        /** Method to test if two weapons are equal based on their fields.
         * @param other The object to compare with
         * @return a boolean representing if the weapon equals another weapon.
         */
        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }

            Weapon otherWeapon = (Weapon) other;
            return getName().equals(otherWeapon.getName())
                    && getDescription().equals(otherWeapon.getDescription())
                    && getValue() == otherWeapon.getValue()
                    && getDamage() == otherWeapon.getDamage();

        }
    }

    public static class Armour extends Item {

        private final int armour;

        /** Constructor to make an Armour item.
         * @param name The name of the armour.
         * @param description A description of the armour.
         * @param value The cost of the armour.
         * @param armour The amount of protection the armour gives.
         */
        public Armour(String name, String description, int value, int armour) {
            super(name, description, value);
            this.armour = armour;
        }

        /**
         * @return the armour stat of the piece of Armour.
         */
        public int getArmour() {
            return this.armour;
        }

        /** Method to test if two armours are equal based on their fields.
         * @param other The object to compare with
         * @return a boolean representing if the armour equals another armour.
         */
        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }

            Armour otherArmour = (Armour) other;
            return getName().equals(otherArmour.getName())
                    && getDescription().equals(otherArmour.getDescription())
                    && getValue() == otherArmour.getValue()
                    && getArmour() == otherArmour.getArmour();

        }

    }

    public static class Amulet extends Item {
        public enum AmuletType {
            STRENGTH, DEFENCE, LUCK
        }
        private final AmuletType type;
        private final int strength;

        /** Constructor for Amulet item.
         * @param name The name of the amulet.
         * @param description A description of the amulet.
         * @param value The cost of the amulet.
         * @param type The type of the amulet (what it buffs)
         * @param strength How much the amulet buffs its stat.
         */
        public Amulet(String name, String description, int value, AmuletType type, int strength) {
            super(name, description, value);
            this.type = type;
            this.strength = strength;
        }

        /** Returns the strength of the amulet which represents how much the amulet affects its respective stat.
         * @return the strength of the amulet.
         */
        public int getStrength() {
            return strength;
        }

        /**
         * @return the type of stat that the amulet affects.
         */
        public AmuletType getType() {
            return type;
        }

        /** Method to test if two amulets are equal based on their fields.
         * @param other The object to compare with
         * @return a boolean representing if the amulet equals another object.
         */
        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }

            Amulet otherAmulet = (Amulet) other;
            return getName().equals(otherAmulet.getName())
                    && getDescription().equals(otherAmulet.getDescription())
                    && getValue() == otherAmulet.getValue()
                    && getType() == otherAmulet.getType()
                    && getStrength() == otherAmulet.getStrength();

        }

    }

    public static class Potion extends Item {

        public enum PotionType {
            FOOD, HEALING, STRENGTH, DEFENCE, LUCK
        }

        private final PotionType type;

        /** Constructor for Potion item.
         * @param name The name of the potion.
         * @param description A description of the potion.
         * @param value The cost of the potion.
         * @param type The type of potion (what it buffs/restores)
         */
        public Potion(String name, String description, int value, PotionType type) {
            super(name, description, value);
            this.type = type;
        }

        /** Uses the current item, increasing the respective stat.
         * @param player The player.
         */
        public void use(Player player) {
            switch (type) {
                case FOOD:
                    player.setHealth(player.getHealth()+1);
                case HEALING:
                    player.setHealth(player.getHealth()+2);
                case STRENGTH:
                    player.setStrength(player.getStrength()+1);
                case DEFENCE:
                    player.setDefence(player.getDefence()+1);
                case LUCK:
                    player.setLuck(player.getLuck()+1);
            }
            player.getInventory().remove(this);
        }

        /**
         * @return the type of the potion.
         */
        public PotionType getType() {
            return type;
        }

        /** Method to test if two potions are equal based on their fields.
         *
         * @author Ryan Khennane
         * @param other The object to compare with
         * @return a boolean representing if the potion equals another object.
         */
        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }

            Potion otherPotion = (Potion) other;
            return getName().equals(otherPotion.getName())
                    && getDescription().equals(otherPotion.getDescription())
                    && getValue() == otherPotion.getValue()
                    && getType() == otherPotion.getType();

        }

    }

}
