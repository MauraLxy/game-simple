import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * GUI - a simple GUI for our RPG game
 */
public class GUI extends JComponent implements Runnable, ChangeListener {

    JFrame jframe;
    Game game;

    InteractiveDialogBox interactiveDialogBox;

    Boolean actionFlag = true; // flag if the player can take action
    JMenu gameMenu; // the menu
    JMenuBar menuBar; // the menu bar
    JMenuItem newGame, resizeMap, loadGame, saveGame, quit; // the menu items

    Map<String,Image> textures =new HashMap<>();
    static int statusBarHeight = 40; // the height of the status bar
    static int tileSize = 40; // the size of each tile

    /**
     * Constructor for the GUI
     */
    public GUI() {
        SwingUtilities.invokeLater(this);
    }

    /**
     * Main method for the GUI
     * @param args the arguments for the main method
     */
    public static void main(String[] args) {
        new GUI();
    }

    /**
     * Set up the size of the GUI
     * @return the Dimension of the GUI containing the width and height
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(game.board.width * tileSize, game.board.height * tileSize + statusBarHeight);
    }

    /**
     * Run the GUI
     */
    public void run() {


        // Set up player
        Player player = new Player(new Point(0,0));
        player.setGui(this);

        // Set up board
        Board board = new Board(20,15);

        // Set up new game
        game = new Game(this, board,player);

        // Preload textures
        loadTextures();





        // Set up GUI
        jframe = new JFrame("Game");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the window not resizable
        jframe.setResizable(false);

        // Set up the game menu
        menuBar = new JMenuBar();
        gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_M); // Alt + M to open menu
        menuBar.add(gameMenu);
        jframe.setJMenuBar(menuBar);

        // Set up menu items
        newGame = new JMenuItem("New Game", KeyEvent.VK_N); // Alt + N to start new game
        gameMenu.add(newGame);
        newGame.addActionListener((ActionEvent event) -> {
            String message = "Are you sure you want to start a new game?" + "\n"+
                    "Your current progress will be lost.";
            int result = JOptionPane.showConfirmDialog(jframe, message, "Start a new game", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                //Create a new player and board and then reset.
                game.player = new Player(new Point(0,0));
                game.player.setGui(this);
                game.board = new Board(20,15);
                interactiveDialogBox = null;
                actionFlag = true;
                repaint();
            }
        });

        resizeMap = new JMenuItem("Resize Map", KeyEvent.VK_R); // Alt + R to resize map
        gameMenu.add(resizeMap);
        resizeMap.addActionListener((ActionEvent event) -> {
            String message1 = "Are you sure you want to resize the map?" + "\n"+
                    "This is equivalent to restarting the game, your current progress will be lost.";
            int result1 = JOptionPane.showConfirmDialog(jframe, message1, "Resize the map", JOptionPane.YES_NO_OPTION);

            // Prompt the user to enter the new width and height of the map, the input must be equal or greater than 5
            if (result1 == JOptionPane.YES_OPTION) {
                String message2 = "Please enter the new width of the map (must be equal or greater than 5):";
                String message3 = "Please enter the new height of the map (must be equal or greater than 5):";
                int width = 0;
                int height = 0;
                while (width < 5) {
                    String input = JOptionPane.showInputDialog(jframe, message2);
                    try {
                        width = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(jframe, "Please enter a valid number!");
                    }
                }
                while (height < 5) {
                    String input = JOptionPane.showInputDialog(jframe, message3);
                    try {
                        height = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(jframe, "Please enter a valid number!");
                    }
                }
                game.player = new Player(new Point(0,0));
                game.player.setGui(this);
                game.board = new Board(width,height);
                interactiveDialogBox = null;
                actionFlag = true;
                repaint();
            }
        });

        loadGame = new JMenuItem("Load", KeyEvent.VK_L); // Alt + L to load game
        gameMenu.add(loadGame);
        loadGame.addActionListener((ActionEvent event) -> {
            // Pop up a confirmation dialog
            String message = "Are you sure you want to load your saved game?" + "\n"+
                    "Your current progress will be lost.";
            int result = JOptionPane.showConfirmDialog(jframe, message, "Load Game", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                // Load the game
                this.game.loadGame();
                game.player.setGui(this);
                interactiveDialogBox = null;
                actionFlag = true;
                repaint();
            }
        });

        saveGame = new JMenuItem("Save", KeyEvent.VK_S); // Alt + S to save game
        gameMenu.add(saveGame);
        saveGame.addActionListener((ActionEvent event) -> {
            String message = """
                    Note: You only have one archive capacity.
                    This will overwrite your previously saved game.
                    Are you sure you want to save your current game?""";
            int result = JOptionPane.showConfirmDialog(jframe, message, "Save Game", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION){
                this.game.saveGame();
            }
        });

        quit = new JMenuItem("Quit", KeyEvent.VK_Q); // Alt + Q to quit game
        gameMenu.add(quit);
        quit.addActionListener((ActionEvent event) -> System.exit(0));


        // make the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(this, BorderLayout.CENTER);

        // add panel to the frame and make viewable
        jframe.getContentPane().add(mainPanel);
        jframe.setVisible(true);
        jframe.pack();

        // Set up key bindings
        setKeyBinding();

    }

    /**
     * Set up the key bindings for the GUI
     */
    private void setKeyBinding() {
        Action upAction = new Action(this,"UP");
        Action downAction = new Action(this,"DOWN");
        Action leftAction = new Action(this,"LEFT");
        Action rightAction = new Action(this,"RIGHT");
        Action enterAction = new Action(this,"ENTER");
        Action spaceAction = new Action(this,"SPACE");

        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0,false),"upAction");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0,false),"downAction");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0,false),"leftAction");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0,false),"rightAction");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W,0,false),"upAction");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S,0,false),"downAction");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A,0,false),"leftAction");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D,0,false),"rightAction");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false),"enterAction");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,0,false),"spaceAction");
        this.getActionMap().put("upAction", upAction);
        this.getActionMap().put("downAction", downAction);
        this.getActionMap().put("leftAction", leftAction);
        this.getActionMap().put("rightAction", rightAction);
        this.getActionMap().put("enterAction", enterAction);
        this.getActionMap().put("spaceAction", spaceAction);
    }

    /**
     * Do something when the state of the GUI changes
     * @param e  a ChangeEvent object
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        repaint();
    }

    /**
     * Paint the GUI
     * @param g the Graphics object
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the status bar
        g.drawImage(textures.get("StatusBar"),0,0,game.board.width * tileSize,statusBarHeight,null);

        // HP String
        int yPos = statusBarHeight / 8;
        int hpHeight = statusBarHeight * 50 / 50 / 4 * 3;
        int hpHwidth = hpHeight * 2;
        g.drawImage(textures.get("HP"),10,5, hpHwidth, hpHeight,null);

        // HP Hearts
        int heartX = 10 + hpHwidth + 10;
        for (int i = 0; i < game.player.getHealth(); i++) {
            g.drawImage(textures.get("Heart"),heartX,yPos, hpHeight, hpHeight,null);
            heartX += hpHeight + 5;
        }


        // Draw board background
        for (int i = 0; i < game.board.width; i++){
            for (int j = 0; j < game.board.height; j++){
                String tileName = game.board.getTile(new Point(i,j)).getTileName();
                float yScale = Objects.equals(tileName, "Plain") ? 1.0f : 1.2f;
                // Draw texture
                g.drawImage(textures.get(tileName), i*tileSize,
                        (int) (j*tileSize - (yScale-1)*tileSize) + statusBarHeight ,tileSize,
                        (int) (tileSize * yScale) + 1,null);
            }
        }

        // Draw player
        g.drawImage(textures.get("Player"),
                (int) (game.player.location.x * tileSize + 0.1 * tileSize),
                (int) (game.player.location.y * tileSize + 0.1 * tileSize) + statusBarHeight,
                (int) (tileSize * 0.8), (int) (tileSize * 0.8), null);

        // Draw the interactive dialog box
        drawInteractiveDialogBox(g);
    }

    /**
     * Draw the interactive dialog box
     * @param g the Graphics object
     */
    private void drawInteractiveDialogBox(Graphics g){
        if (interactiveDialogBox == null) {
            return;
        }
        Dimension dim = this.getSize();
        int dialogWidth = interactiveDialogBox.width;
        int dialogHeight = interactiveDialogBox.height;
        int dialogYOffset = interactiveDialogBox.yOffSet;
        // set the color to black and 50% transparent
        g.setColor(new Color(75, 75, 75, 180));
        g.fillRoundRect(dim.width / 2 - dialogWidth / 2, dim.height - dialogHeight + dialogYOffset, dialogWidth, dialogHeight, 10, 10);
        // set the color to white
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        int lineHeight = g.getFontMetrics().getHeight();
        int lineYOffset = 0;
        for (String line : interactiveDialogBox.messages[interactiveDialogBox.currentMessage].split("\n")) {
            // if the line is too long, split it into multiple lines
            if (g.getFontMetrics().stringWidth(line) > dialogWidth - 20) {
                String[] words = line.split(" ");
                StringBuilder currentLine = new StringBuilder();
                for (String word : words) {
                    if (g.getFontMetrics().stringWidth(currentLine + word) > dialogWidth - 20) {
                        g.drawString(currentLine.toString(), dim.width / 2 - dialogWidth / 2 + 10,
                                dim.height - dialogHeight + dialogYOffset + 30 + lineYOffset);
                        lineYOffset += lineHeight;
                        currentLine = new StringBuilder(word + " ");
                    } else {
                        currentLine.append(word).append(" ");
                    }
                }
                g.drawString(currentLine.toString(), dim.width / 2 - dialogWidth / 2 + 10,
                        dim.height - dialogHeight + dialogYOffset + 30 + lineYOffset);
                lineYOffset += lineHeight;
            } else {
                g.drawString(line, dim.width / 2 - dialogWidth / 2 + 10,
                        dim.height - dialogHeight + dialogYOffset + 30 + lineYOffset);
                lineYOffset += lineHeight;
            }
            lineYOffset += lineHeight;
        }

        // Draw 'Press Enter to continue' message at the right bottom corner
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        int stringWidth = g.getFontMetrics().stringWidth("Press Enter to continue >>");

        g.drawString("Press Enter to continue >>",
                dim.width / 2 + dialogWidth / 2 - stringWidth - 10,
                dim.height - dialogHeight + dialogYOffset + dialogHeight - 10);
    }


    /**
     * preload the textures
     */
    private void loadTextures() {
        File directory = new File("src/main/java/src/");
        String[] files = directory.list();
        if (files != null) {
            for (String file : files){
                if (file.endsWith(".png")){
                    try {
                        Image texture = ImageIO.read(new File("src/main/java/src/" + file));
                        // remove the .png from the file name
                        textures.put(file.substring(0,file.length()-4), texture);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    /**
     * Action class for the GUI, which is used to move the player when a key is pressed
     */
    public class Action extends AbstractAction{
        GUI gui;
        String key;

        /**
         * Constructor
         * @param gui the GUI object
         * @param key the key that is pressed
         */
        public Action(GUI gui, String key) {
            this.gui = gui;
            this.key = key;
        }
        @Override
        public void actionPerformed(ActionEvent e) {

            switch (key) {
                case "UP":
                    if(actionFlag)
                        game.movePlayer(Player.Direction.Up);
                    break;
                case "DOWN" :
                    if(actionFlag)
                        game.movePlayer(Player.Direction.Down);
                    break;
                case "LEFT" :
                    if(actionFlag)
                        game.movePlayer(Player.Direction.Left);
                    break;
                case "RIGHT" :
                    if(actionFlag)
                        game.movePlayer(Player.Direction.Right);
                    break;
                case "ENTER":
                    continueDialog();
                    break;
                case "SPACE":
                    showPlayerStatus();
            }
        }
    }

    /**
     * Continue the dialog box when the player presses ENTER
     */
    private void continueDialog() {
        // show option dialog
        if (interactiveDialogBox != null){
            interactiveDialogBox.continueDialog();
        }
    }

    /**
     * Pop up a window and show the player status
     */
    private void showPlayerStatus() {
        // show dialog box
        JOptionPane.showMessageDialog(this, game.player.toString());
    }

    /**
     * Show the option dialog box
     * @param options the options for the player to choose
     * @param title the title of the dialog box
     * @param message the message of the dialog box
     */
    public void showOptionDialog(String[] options, String title, String message){
        int result = JOptionPane.showOptionDialog(this, message, title,
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        //this implements the option chosen by the user on the player and displays the result
        //if there is no NPC it is just a tile
        Tile currentTile = this.game.board.getTile(this.game.player.location);
        if(currentTile.getNPC() == null){
            interactiveDialogBox = new InteractiveDialogBox(
                    this, new String[]{currentTile.getChoice().choose(result+1, game.player)});
        }
        else{
            interactiveDialogBox = new InteractiveDialogBox(
                    this, new String[]{currentTile.getNPC().getChoice().choose(result+1, game.player)});
        }
    }
}
