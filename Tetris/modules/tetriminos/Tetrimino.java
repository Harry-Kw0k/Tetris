package modules.tetriminos;

import java.awt.*;

public abstract class Tetrimino {

    // First block = main
    protected final Block[] tetrimino; // Array for blocks
    protected final int[][] locations; // Locations for blocks
    protected final String type;

    /* Types
     * I -> Line shape
     * O -> Square shape
     * T -> T shape
     * L -> L shape
     * J -> Mirror L shape
     * Z -> Z shape
     * S -> Mirror Z shape 
     * N -> Next shape
     */

    // References to the farthest left and right blocks of the tetrimino
    private Block farLeftBlock;
    private Block farRightBlock;

    /**
     * Initializes the tetrimino with a specific shape and type.
     * 
     * @param key The KeyHandler to detect player input.
     * @param locations The coordinates for the blocks of the tetrimino.
     * @param type The type of tetrimino.
     */
    public Tetrimino(int[][] locations, String type) {
        this.tetrimino = new Block[4];
        this.locations = locations;
        this.type = type;
    }

    /**
     * Creates the blocks for the tetrimino.
     * 
     * @return An array of Block objects representing the tetrimino.
     */
    public Block[] createShapeParts() {
        for (int i = 0; i < 4; i++) {
            Block block = new Block(locations[i][0], locations[i][1], false, type);
            tetrimino[i] = block;
        }
        return tetrimino;
    }

    /**
     * Calculates the next position for a block based on its current position and the velocity.
     * 
     * @param block The block for which to calculate the next position.
     * @return The y-coordinate of the block's next position.
     */
    public int getNextBlockPosition(Block block, int blockSize) {
        return block.getY() + blockSize;
    }

    /**
     * Moves all blocks of the tetrimino vertically by a given velocity.
     * 
     * @param yVelocity The vertical velocity to move the blocks.
     */
    public void moveY(int yVelocity) {
        for (Block block : tetrimino) {
            block.moveY(yVelocity);
        }
    }

    /**
     * Moves all blocks of the tetrimino horizontally by a given velocity.
     * 
     * @param xVelocity The horizontal velocity to move the blocks.
     */
    public void moveX(int xVelocity) {
        for (Block block : tetrimino) {
            block.moveX(xVelocity);
        }
    }

    /**
     * Finds the leftmost block of the tetrimino.
     * 
     * @return The farthest left block.
     */
    public Block farLeft() {
        int leftMost = 270;
        for (Block block : tetrimino) {
            if (block.getX() < leftMost) {
                leftMost = block.getX();
                farLeftBlock = block;
            }
        }
        return farLeftBlock;
    }

    /**
     * Finds the rightmost block of the tetrimino.
     * 
     * @return The farthest right block.
     */
    public Block farRight() {
        int rightMost = 0;
        for (Block block : tetrimino) {
            if (block.getX() > rightMost) {
                farRightBlock = block;
                rightMost = block.getX();
            }
        }
        return farRightBlock;
    }

    /**
     * Finds the bottommost block of the tetrimino.
     * 
     * @return The y-coordinate of the bottommost block.
     */
    public int farBottom() {
        int bottomMost = 0;
        for (Block block : tetrimino) {
            if (block.getY() > bottomMost) {
                bottomMost = block.getY();
            }
        }
        return bottomMost;
    }

    /**
     * Draws the tetrimino blocks onto the game screen.
     * 
     * @param g2 The Graphics2D object used for drawing the blocks.
     */
    public void draw(Graphics2D g2) {
        for (Block block : tetrimino) {
            if (block != null) {
                block.draw(g2);
            }
        }
    }
}