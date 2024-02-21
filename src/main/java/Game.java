import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * A class for the GUI, which is used to store the game state
 */
public class Game {
    GUI gui;
    Board board;
    Player player;
    /**
     * The constructor of the game class.
     * @param gui the GUI of the game
     * @param board the board of the game
     * @param player the player of the game
     */
    public Game(GUI gui, Board board, Player player) {
        this.gui = gui;
        this.board = board;
        this.player = player;
    }

    /**
     * The method to move the player to a given direction.
     * If the player is not able to move to the given direction, the player will not move.
     * @param direction the direction to move the player to
     */
    public void movePlayer(Player.Direction direction) {
        switch (direction) {
            case Up -> {
                // Check if the player is able to move up ( not at the top of the board )
                if (player.location.y > 0) {
                    Point newLocation = new Point(player.location.x, player.location.y - 1);
                    // Check if the tile is walkable
                    if(board.getTile(newLocation).isWalkable()){
                        player.move(direction);
                        getDialog(newLocation);

                    }
                }
            }

            case Down -> {
                // Check if the player is able to move down ( not at the bottom of the board )
                if (player.location.y < board.height - 1) {
                    Point newLocation = new Point(player.location.x, player.location.y + 1);
                    // Check if the tile is walkable
                    if(board.getTile(newLocation).isWalkable()){
                        player.move(direction);
                        getDialog(newLocation);
                    }
                }
            }

            case Left -> {
                // Check if the player is able to move left ( not at the left of the board )
                if (player.location.x > 0) {
                    Point newLocation = new Point(player.location.x - 1, player.location.y);
                    // Check if the tile is walkable
                    if(board.getTile(newLocation).isWalkable()){
                        player.move(direction);
                        getDialog(newLocation);
                    }
                }
            }

            case Right -> {
                // Check if the player is able to move right ( not at the right of the board )
                if (player.location.x < board.width - 1) {
                    Point newLocation = new Point(player.location.x + 1, player.location.y);
                    // Check if the tile is walkable
                    if(board.getTile(newLocation).isWalkable()){
                        player.move(direction);
                        getDialog(newLocation);
                    }
                }
            }
        }
        gui.repaint();
    }

    /**
     * The method to get the dialog of the tile the player is currently on.
     * @param newLocation the location of the player
     */
    private void getDialog(Point newLocation) {
        if(board.getTile(newLocation).getNPC() == null) {
            String description = board.getTile(newLocation).getDescription();
            String randEvent = board.getTile(newLocation).getEvent(this.player);
            String choiceEvent = board.getTile(newLocation).getChoice().getChoices();
            String[] options = choiceEvent.split("\n");
            gui.interactiveDialogBox = new InteractiveDialogBox(this.gui, new String[]{description, randEvent});
            gui.interactiveDialogBox.setOptions(options, "Make a choice!", "You should choose from the options!");
        }else{
            NPC npc = board.getTile(newLocation).getNPC();
            String introduction = npc.getIntroduction();
            String choiceEvent = npc.getChoice().getChoices();
            String[] options = choiceEvent.split("\n");
            gui.interactiveDialogBox = new InteractiveDialogBox(this.gui, new String[]{introduction});
            gui.interactiveDialogBox.setOptions(options, "Make a choice!", "You should choose from the options!");
        }
    }

    /** Saves the current game.
     */
    public void saveGame() {
        saveBoard();
        savePlayer();
    }

    /** Saves the current board.
     */
    public void saveBoard() {
        //Create a new Gson to save the board.
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(Tile.class, new TileAdapter()).create();

        //Save the board in JSON format.
        try (FileWriter fw = new FileWriter("./src/main/resources/board-save.txt")) {
            gson.toJson(board,fw);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /** Saves the current player.
     */
    public void savePlayer() {
        //Create a new gson.
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(Item.class, new ItemAdapter())
                .setPrettyPrinting().create();

        //Save the player in JSON format.
        try (FileWriter fw = new FileWriter("./src/main/resources/player-save.txt")) {
            gson.toJson(player,fw);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /** Loads the game currently saved.
     */
    public void loadGame() {
        loadBoard();
        loadPlayer();
    }

    /** Loads the board that is currently saved.
     */
    public void loadBoard() {

        //Create a new gson and add the TileAdapter.
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(Tile.class, new TileAdapter()).create();
        JsonReader jsonReader;

        //Get the board type.
        final Type BOARD_TYPE = TypeToken.getParameterized(Board.class).getType();

        //Load the json file.
        try {
            jsonReader = new JsonReader(new FileReader("./src/main/resources/board-save.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //Convert the json object into a board and return.
        this.board = gson.fromJson(jsonReader, BOARD_TYPE);

    }

    public void loadPlayer() {

        //Create a new gson and add the TileAdapter.
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(Item.class, new ItemAdapter())
                .setPrettyPrinting().create();
        JsonReader jsonReader;

        //Get the player type.
        final Type PLAYER_TYPE = TypeToken.getParameterized(Player.class).getType();

        //Load the json file.
        try {
            jsonReader = new JsonReader(new FileReader("./src/main/resources/player-save.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //Convert the json object into a board and return.
        this.player = gson.fromJson(jsonReader, PLAYER_TYPE);

    }



}
