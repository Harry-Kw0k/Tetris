package modules;

import java.awt.*;
import javax.swing.*;

public class NextBlock extends JPanel{

    // Dimensions of the component.
    private final int WIDTH = 171;
    private final int HEIGHT = 130;

    // Keeps track of next tetrimino.
    private String nextTetrimino;

    // Images of the blocks.
    private final Image JTetrimino = new ImageIcon("assets/images/JTetrimino.png").getImage();
    private final Image LTetrimino = new ImageIcon("assets/images/LTetrimino.png").getImage();
    private final Image ZTetrimino = new ImageIcon("assets/images/ZTetrimino.png").getImage();
    private final Image STetrimino = new ImageIcon("assets/images/STetrimino.png").getImage();
    private final Image TTetrimino = new ImageIcon("assets/images/TTetrimino.png").getImage();
    private final Image OTetrimino = new ImageIcon("assets/images/OTetrimino.png").getImage();
    private final Image ITetrimino = new ImageIcon("assets/images/ITetrimino.png").getImage();
    
    
    // Images for the box around the block.
    private final Image nextHeader = new ImageIcon("assets/images/next.png").getImage();
    private final Image nextBackground = new ImageIcon("assets/images/nextBack.png").getImage();

    // Initializes the GUI properties.
    public NextBlock() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT)); // Sets the size of the component
        this.setOpaque(false);
    }

    // Generate names of next Tetriminos.
    public String generateNextTetriminos() {

        // Randomly generates a number from 1-7
        int tetriminoNumber = (int) (Math.random() * 7) + 1;

        // Based on the random number, the type of the tetrimino will be identified
        switch (tetriminoNumber) {
            case 1 -> nextTetrimino = "J";
            case 2 -> nextTetrimino = "L";
            case 3 -> nextTetrimino = "Z";
            case 4 -> nextTetrimino = "S";
            case 5 -> nextTetrimino = "T";
            case 6 -> nextTetrimino = "O";
            default -> nextTetrimino = "I";
        }

        return nextTetrimino;
    }

    /**
     * Displays the next tetrimino infront of a box like image.
     *
     * @param g The Graphics object used to paint the component.
     */
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g); // Clears the panel and paints the background
        Graphics2D g2 = (Graphics2D) g; // Manipulate more graphics

        // Draws the box around the next tetrimino
        g2.drawImage(nextBackground, 0, 0, null);
        g2.drawImage(nextHeader, 0, 2, null);

        // Display next tetrimino
        switch (nextTetrimino) {
            case "J" -> g2.drawImage(JTetrimino, 36, 40, null);
            case "L" -> g2.drawImage(LTetrimino, 36, 40, null);
            case "Z" -> g2.drawImage(ZTetrimino, 36, 45, null);
            case "S" -> g2.drawImage(STetrimino, 36, 45, null);
            case "T" -> g2.drawImage(TTetrimino, 36, 40, null);
            case "O" -> g2.drawImage(OTetrimino, 51, 43, null);
            default -> g2.drawImage(ITetrimino, 20, 55, null);
        }

        // Dispose of the Graphics2D object to free resources
        g2.dispose();
    }

    /**
     * Returns the width of the component.
     *
     * @return The width of the component.
     */

    public int getWIDTH() {
        return WIDTH;
    }

    /**
     * Returns the height of the component.
     *
     * @return The height of the component.
     */
    public int getHEIGHT() {
        return HEIGHT;
    }
}