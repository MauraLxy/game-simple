import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

//data struct. for the game class
public class Board{
    public int width;
    public int height;

    private ArrayList<Tile> tiles = new ArrayList<>();

    /**
     * The constructor of randomly generating tiles on the board.
     * @param width  the width of the board
     * @param height the height of the board
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        tiles.add(new TownTile());
        for (int i = 1; i < width * height; i++) {
            tiles.add(randomTile(i, width));
        }

        for (int i : uniqueRandomNumbers(0, width * height, 6)) {
            tiles.get(i).setNPC(new HighwaymanNPC());
        }

        for (int i : uniqueRandomNumbers(0, width * height, 6)) {
            tiles.get(i).setNPC(new MerchantNPC());
        }

    }

    /**`
     *
     * @return The list of tiles representing the board.
     */
    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    /**
     * Return a tile at a given position
     * @param p the position of the tile
     * @return the tile at the given position
     */
    public Tile getTile(Point p) {
        return tiles.get(p.x + p.y * width);
    }
    /**
     * Return a random tile following certain rules.
     * @param number the index of the current tile
     * @param width  the width of the board
     * @return a random tile type.
     */
    public Tile randomTile(int number, int width) {
        Random random = new Random();
        int rand = random.nextInt(30);
        if (rand <= 12)
            return new ForestTile();
        else if (rand <= 24)
            return new PlainTile();
        else if (rand <= 28)
            return new MountainTile();
        else {
            if (isTownConnected(number, width))
                return new TownTile();
            else
                return randomTile(number, width);
        }
    }

    /**
     * One town tile is not to be connected to another town tile.
     * @param number the index of the current tile
     * @param width  the width of the board
     * @return true if there exists no such two town tiles that are connected; false otherwise.
     */
    public boolean isTownConnected(int number, int width) {
        if ((number) % width == 0) {
            // the tile is in the first column, only need to check its upper tile is not town tile.
            return !(tiles.get(number - width) instanceof TownTile);
        } else if (number <= width) {
            // the tile is in the first row, only need to check its left tile is not town tile.
            return !(tiles.get(number - 1) instanceof TownTile);
        } else {
            // check its left and upper tiles are not town tiles.
            return !(tiles.get(number - 1) instanceof TownTile)
                    && !(tiles.get(number - width) instanceof TownTile);
        }
    }

    /**
     * Return a list of unique random numbers.
     * @param min the minimum possible random number
     * @param max the maximum possible random number
     * @param count the number of random numbers
     * @return a list of count number of unique random numbers from min to max
     */
    public static List<Integer> uniqueRandomNumbers(int min, int max, int count) {
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            list.add(random.nextInt(max - min) + min);
        }
        Set<Integer> set = new HashSet<>(list);

        if(list.size() != set.size()) {
            return uniqueRandomNumbers(min, max, count);
        }
        return list;
    }


    /** Checks to see if two boards are equal.
     * @param otherBoard The board to compare to.
     * @return a boolean describing if two boards are equal.
     */
    @Override
    public boolean equals(Object otherBoard) {
        if (this == otherBoard) return true;
        if (otherBoard == null || getClass() != otherBoard.getClass()) return false;

        Board board = (Board) otherBoard;

        if (width != board.width) return false;
        if (height != board.height) return false;
        return getTiles() != null ? getTiles().equals(board.getTiles()) : board.getTiles() == null;
    }
}
