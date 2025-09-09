package modules;

import java.awt.*;
import javax.swing.*;

public class StoreTetrimino extends JPanel{

    // Dimensions of panel
    private final int WIDTH = 171;
    private final int HEIGHT = 130;

    // Variable to keep track of saved tetrimino
    private String savedTetrimino;

    // Variable to keep track if there is a tetrimino that has been saved
    private boolean saved;

    // Variable to keep track if the current tetrimino has been switched with thesaved
    private boolean switched;

     // Images of the blocks.
    private final Image JTetrimino = new ImageIcon("assets/images/JTetrimino.png").getImage();
    private final Image LTetrimino = new ImageIcon("assets/images/LTetrimino.png").getImage();
    private final Image ZTetrimino = new ImageIcon("assets/images/ZTetrimino.png").getImage();
    private final Image STetrimino = new ImageIcon("assets/images/STetrimino.png").getImage();
    private final Image TTetrimino = new ImageIcon("assets/images/TTetrimino.png").getImage();
    private final Image OTetrimino = new ImageIcon("assets/images/OTetrimino.png").getImage();
    private final Image ITetrimino = new ImageIcon("assets/images/ITetrimino.png").getImage();

    // Images of the panel that holds the displayed saved tetrimino
    private final Image holdPanel = new ImageIcon("assets/images/hold.png").getImage();

    // Initializes a new StoreTetrimino component with specified size, opacity, and initial states.
    public StoreTetrimino() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setOpaque(false);
        this.savedTetrimino = "none";
        this.saved = false;
        this.switched = false;
    }

    /**
     * Sets the saved tetrimino type.
     *
     * @param newSavedTetrimino Saved tetrimino.
     */
    public void setSavedTetrimino(String newSavedTetrimino) {
        this.savedTetrimino = newSavedTetrimino;
    }

    /**
     * Sets whether a tetrimino has been saved.
     *
     * @param saved Indicated if the tetrimino is saved.
     */
    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    /**
     * Sets whether a tetrimino has been switched.
     *
     * @param switched A boolean indicating if the tetrimino has been switched.
     */
    public void setSwitched(boolean switched) {
        this.switched = switched;
    }

    /**
     * Gets the type of the saved tetrimino.
     *
     * @return The type of the saved tetrimino.
     */
    public String getSavedTetrimino() {
        return this.savedTetrimino;
    }

    /**
     * Gets whether a tetrimino has been saved.
     *
     * @return True if the tetrimino has been saved, false otherwise.
     */
    public boolean getSaved() {
        return this.saved;
    }

    /**
     * Gets whether a tetrimino has been switched.
     *
     * @return True if the tetrimino has been switched, false otherwise.
     */
    public boolean getSwitched() {
        return this.switched;
    }

    /**
     * Paints the component on the screen, drawing the hold panel and the saved tetrimino.
     * 
     * @param g The graphics object used to draw the component.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // clears the panel and paints the background
        Graphics2D g2 = (Graphics2D) g; // Manipulate more graphics

        // Draw the panel of which holds the saved tetrimino
        g2.drawImage(holdPanel, 0, 0, null);
        
        // Draws the saved tetrimino based on its type
        switch (this.savedTetrimino) {
            case "J" -> g2.drawImage(JTetrimino, 36, 40, null);
            case "L" -> g2.drawImage(LTetrimino, 36, 40, null);
            case "Z" -> g2.drawImage(ZTetrimino, 36, 45, null);
            case "S" -> g2.drawImage(STetrimino, 36, 45, null);
            case "T" -> g2.drawImage(TTetrimino, 36, 40, null);
            case "O" -> g2.drawImage(OTetrimino, 51, 43, null);
            case "I" -> g2.drawImage(ITetrimino, 20, 55, null);
        } 
        
        // Dispose of the Graphics2D object to free resources
        g2.dispose();
    }
}