import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * An InteractiveDialogBox class for the GUI, which is used to display a dialog box with a message
 * Enter key is required to be pressed to continue
 */
public class InteractiveDialogBox {
    GUI gui;
    String[] messages;
    String[] options;
    String optionTitle;
    String optionMessage;
    int currentMessage;
    double startTime;
    int width;
    int height;
    int yOffSet;
    int easing; // The time it takes to move the dialog box up and down

    /**
     * Constructor for the InteractiveDialogBox class
     * @param gui      the parent GUI object
     * @param messages the message to display
     * @param width    the width of the dialog box
     * @param height   the height of the dialog box
     * @param easing   the time it takes to move the dialog box up and down
     */
    public InteractiveDialogBox(GUI gui, String[] messages, int width, int height, int easing) {
        this.gui = gui;
        this.messages = messages;
        this.startTime = System.currentTimeMillis();
        this.width = width;
        this.height = height;
        this.yOffSet = height;
        this.easing = easing;
        this.currentMessage = 0;
        easeIn();
    }

    /**
     * Constructor for the InteractiveDialogBox class
     * @param gui      the parent GUI object
     * @param messages the message to display
     */
    public InteractiveDialogBox(GUI gui, String[] messages) {
        this.gui = gui;
        this.messages = messages;
        this.startTime = System.currentTimeMillis();
        this.width = gui.getSize().width / 5 * 4;
        this.height = 150;
        this.yOffSet = height;
        this.easing = 300;
        this.currentMessage = 0;
        easeIn();
    }

    /**
     * Sets the options for the dialog box
     * @param options the options to display
     * @param title the title of the dialog box
     * @param message the message to display
     */
    public void setOptions(String[] options, String title, String message) {
        this.options = options;
        this.optionTitle = title;
        this.optionMessage = message;
    }

    /**
     * easeIn method to ease in the dialog box
     * Set a timer to decrease the yOffSet to -10 within the easing time
     */
    public void easeIn() {
        // Disable player movement
        gui.actionFlag = false;
        Timer timer1 = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double time = System.currentTimeMillis() - startTime;
                if (time < easing) {
                    yOffSet = (int) (height - (height + 10) * (time / easing));
                } else {
                    yOffSet = -10;
                    // Stop the timer
                    ((Timer) e.getSource()).stop();
                }
                gui.repaint();
            }
        });
        timer1.setRepeats(true);
        timer1.start();
    }

    /**
     * easeOut method to ease out the dialog box
     * Set a timer to increase the yOffSet to the height within the easing time
     */
    public void easeOut() {
        startTime = System.currentTimeMillis();
        easing = easing / 2; // Make the easing time shorter
        Timer timer2 = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double time = System.currentTimeMillis() - startTime - easing;
                if (time >= 0 && time < easing) {
                    yOffSet = (int) (-10 + (height + 10) * (time / easing));
                }

                if (time >= easing) {
                    yOffSet = height;
                    // Stop the timer
                    ((Timer) e.getSource()).stop();
                    closeDialog();
                    gui.actionFlag = true;
                }
                gui.repaint();
            }
        });
        timer2.setRepeats(true);
        timer2.start();
    }

    /**
     * continueDialog method to continue the dialog box, if there are more messages to display or selection options
     */
    public void continueDialog() {
        // If there are more messages to display
        if (currentMessage < messages.length - 1) {
            currentMessage++;
            gui.repaint();
            // If there are no more messages to display, but there are options to choose from
        } else if (options != null) {
            // Display the options
            gui.showOptionDialog(this.options, this.optionTitle, this.optionMessage);
            // If there are no more messages to display and no options to choose from
        } else {
            // Ease out the dialog box
            easeOut();
        }
    }

    /**
     * closeDialog method to close the dialog box
     */
    public void closeDialog() {
        gui.interactiveDialogBox = null;
    }
}
