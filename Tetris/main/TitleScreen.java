package main;

import java.awt.*;
import javax.swing.*;

public class TitleScreen extends JPanel {

    // Dimensions of title screen
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    // Images for title screen
    private final Image titlescreen = new ImageIcon("assets/images/titlescreen.png").getImage();
    private final Image pressStart = new ImageIcon("assets/images/pressStart.png").getImage();
    
    // Initializes the GUI for the title screen with specific size and black background
    public TitleScreen() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);   
    }

   
    /**
     * Paints the component on the screen, drawing the title screen and press start prompt.
     * 
     * @param g The graphics object used to draw the component.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Clears the panel and paints the background
        Graphics2D g2 = (Graphics2D) g; // Manipulate more graphics

        // Draws the title screen
        g2.drawImage(titlescreen, 0, 0, null);
        g2.drawImage(pressStart, 225, 425, null);

        // Set the font and color for the score
        g2.setFont(new Font("Monospaced", Font.BOLD, 24));
        g2.setColor(Color.WHITE); // Set text color (change if you want different color)

        // Dispose of the Graphics2D object to free resources
        g2.dispose();
    }   
}
