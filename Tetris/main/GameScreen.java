package main;

import java.awt.*;
import javax.swing.*;

public class GameScreen extends JPanel{
    
    // Screen dimensions
    private final int WIDTH = 800;
    private final int HEIGHT = 600; 

    // Image of screen
    private final Image background = new ImageIcon("assets/images/background.png").getImage(); // Background image


    // Constructs a GameScreen with a fixed size and default background color.
    public GameScreen() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.white); // Default background (overridden by image)
    }

    /**
     * Paints the game screen, including the background image and score display.
     *
     * @param g The Graphics object for rendering.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Clears the panel and paints the background
        Graphics2D g2 = (Graphics2D) g; // Manipulate more graphics

        // Draw the background image
        g2.drawImage(background, 0, 0, null);

        // Draw the score
        g2.setFont(new Font("Monospaced", Font.BOLD, 24));
        g2.setColor(Color.WHITE);
        g2.drawString("SCORE", 570, 430); // Score label
        g2.drawString("" + Board.totalScore, 570, 460); // Score value
    }
}

