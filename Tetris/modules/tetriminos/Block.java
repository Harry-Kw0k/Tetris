package modules.tetriminos;

import java.awt.*;
import javax.swing.*;

public class Block{
   
    private boolean isPlaced; // Indicates whether the block has been placed on the board
    private String type; // The type of the block 
    private int x, y; // The coordinates of the block

    private final Image blockImage; // The image associated with the block type

    /**
     * Constructs a new block object at the specified position with the specified type.
     * 
     * @param x The x-coordinate of the block.
     * @param y The y-coordinate of the block.
     * @param main A boolean indicating if this block is the main block.
     * @param type The type of the block.
     */
    public Block(int x, int y, boolean main, String type) {
        this.x = x;
        this.y = y;
        this.isPlaced = false;
        this.type = type;
        this.blockImage = new ImageIcon("assets/images/" + type + "Block.png").getImage(); // Load block image
    }

    /**
     * Moves the block horizontally by the given velocity.
     * 
     * @param xVelocity The horizontal velocity to move the block by.
     */
    public void moveX(int xVelocity) {
        this.x += xVelocity;
    }

    /**
     * Moves the block vertically by the given velocity.
     * 
     * @param yVelocity The vertical velocity to move the block by.
     */
    public void moveY(int yVelocity) {
        this.y += yVelocity;
    }

    /**
     * Sets the x-coordinate of the block.
     * 
     * @param x The new x-coordinate of the block.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of the block.
     * 
     * @param y The new y-coordinate of the block.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Sets the placed state of the block.
     * 
     * @param state A boolean indicating whether the block is placed.
     */
    public void setIsPlaced(boolean state) {
        this.isPlaced = state;
    }

    /**
     * Sets the type of the block.
     * 
     * @param type The new type of the block (e.g., "I", "O", etc.).
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the x-coordinate of the block.
     * 
     * @return The x-coordinate of the block.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Gets the y-coordinate of the block.
     * 
     * @return The y-coordinate of the block.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Gets the placed state of the block.
     * 
     * @return A boolean indicating whether the block is placed.
     */
    public boolean getIsPlaced() {
        return this.isPlaced;
    }

    /**
     * Gets the type of the block.
     * 
     * @return The type of the block.
     */
    public String getType() {
        return this.type;
    }

     /**
     * Displays the block.
     *
     * @param g The Graphics object used to paint the component.
     */
    public void draw(Graphics2D g) {
        g.drawImage(blockImage, x, y, null);
    }
}   
