package main;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import modules.BlockSort;
import modules.NextBlock;
import modules.StoreTetrimino;
import modules.tetriminos.*;
import utils.*;

public class Board extends JPanel implements Runnable{

    // Dimensions of the board
    private final int WIDTH = 300;
    private final int HEIGHT = 600;

    // Size of block
    private final int blockSize = 30;

    // Keeps track of all placed blocks on the board
    private ArrayList<ArrayList<Block>> boardBlocks;

    // Variables to keep track of current Tetrimino and its properties
    private Tetrimino currentTetrimino;
    private Block[] currentTetriminoBlocks;

    // Variables to keep track of the copied current tetrimino 
    // use to visualize where the current tetrimino is going to drop
    private NTetrimino copyTetrimino;
    private Block[] copyTetriminoBlocks;

    // Keeps track of the next tetrimino
    private String nextTetrimino;

    // Variables to keep track of collisions of current tetrimino
    private ArrayList<int[]> leftCollisions;
    private ArrayList<int[]> rightCollisions;
    private int[][] bottomCollisions;
    private int[] distanceCollisions; // used to find the smallest distance for collision

    // X and y restrictions for current tetrimino
    private int[] tetriminoRestrictions;

    // Stores the blocks (Left; right) where their x values are used as references for x collisions
    private Block[] referencePoints;

    // Stores the time of the last game update in milliseconds
    private long lastGameTime = System.currentTimeMillis();

    // Time interval of passive dropping
    private final int dropInterval = 1000;

    // Variable to keep track of score
    public static int totalScore;

    // Variable to keep track of level
    public static int level;

    // Variables for differing GUI on the board
    private final GameScreen background;
    private final NextBlock nextBlock;
    private final StoreTetrimino storedTetrimino; 

    // Keeps track of key input
    private final KeyHandler key = new KeyHandler();

    private Thread gameThread;

    /**
     * Constructs a new Board component with the specified game screen, next block display, and stored tetrimino display.
     * Initializes the board with a black background, double buffering for smoother rendering, and key listener for user input.
     * Initialize game variables.
     *
     * @param background The background of the game screen.
     * @param nextBlock The component displaying the next tetrimino.
     * @param storedTetrimino The component displaying the stored tetrimino.
     */
    public Board(GameScreen background, NextBlock nextBlock, StoreTetrimino storedTetrimino) {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(key);
        this.background = background;
        this.nextBlock = nextBlock;
        this.storedTetrimino = storedTetrimino;

        initializeGameVariables(); 
    }

    // Initializes all game instance variables
    private void initializeGameVariables() {

        currentTetrimino = new JTetrimino(); 
        currentTetriminoBlocks = currentTetrimino.createShapeParts(); 
        copyTetrimino = new NTetrimino(getCopyLocations());
        copyTetriminoBlocks = copyTetrimino.createShapeParts();
        nextTetrimino = nextBlock.generateNextTetriminos(); 
        boardBlocks = new ArrayList<>();
        leftCollisions = new ArrayList<>();
        rightCollisions = new ArrayList<>();
        bottomCollisions = new int[4][2];
        distanceCollisions = new int[4];
        tetriminoRestrictions = new int[2];
        referencePoints = new Block[2];
        gameThread = null;
        totalScore = 0;
        level = 1;
    }

    
    // Starts the game thread by creating a new thread and starting it.
    //Runs the `run` method, which handles the game loop.
    public void startGameThread() {
        gameThread  = new Thread(this);
        gameThread.start();
    }

    /**
     * Generates a new Tetrimino based on the provided type and initializes the copy tetrimino.
     *
     * @param nextTetrimino The type of Tetrimino to be generated.
     */
    private void generateTetrimino(String nextTetrimino) {

        // Generates the new curremnt tetrimino based on the type of the next Tetrimino
        switch (nextTetrimino) {
            case "J" -> currentTetrimino = new JTetrimino();
            case "L" -> currentTetrimino = new LTetrimino();
            case "Z" -> currentTetrimino = new ZTetrimino();
            case "S" -> currentTetrimino = new STetrimino();
            case "T" -> currentTetrimino = new TTetrimino();
            case "O" -> currentTetrimino = new OTetrimino();
            default  -> currentTetrimino = new ITetrimino();
        }

        currentTetriminoBlocks = currentTetrimino.createShapeParts(); // Initializes the blocks for the current Tetrimino

        copyTetrimino = new NTetrimino(getCopyLocations());
        copyTetriminoBlocks = copyTetrimino.createShapeParts(); // Initializes the blocks for the copy of the Tetrimino
    }

    /**
     * Retrieves the current locations of the tetrimino's blocks for use in the copied tetrimino.
     *
     * @return A 2D array representing the locations of the current tetrimino's blocks (x, y).
     */
    private int[][] getCopyLocations() {
        int[][] currentTetriminoBlocksLocations = new int[4][2];

        // Stores the current tetrimino's block coordinates (x, y)
        for (int i = 0; i < currentTetriminoBlocks.length; i++) {
            currentTetriminoBlocksLocations[i][0] = currentTetriminoBlocks[i].getX();
            currentTetriminoBlocksLocations[i][1] = currentTetriminoBlocks[i].getY();
        }

        return currentTetriminoBlocksLocations;
    }

    /**
     * Checks for potential collisions on the left and right sides of the current tetrimino.
     * If there are collisions, the positions are stored in the leftCollisions and rightCollisions lists.
     */
    private void findSideCollide() {

        // Ensure that it checks for new left collisions and right collisions
        leftCollisions.clear();
        rightCollisions.clear();

        // If there are blocks placed on the board, check for side collisions
        if (!(boardBlocks.isEmpty())) {

            // Iterates through placed blocks to compare positions with the current tetrimino's blocks
            for (ArrayList<Block> blocks : boardBlocks) {
                for (Block placedBlock : blocks) {
                    for (Block currentTetriminoBlock : currentTetriminoBlocks) {

                        // Check if the Y positions match
                        if (placedBlock.getY() == currentTetriminoBlock.getY()) {

                            // Check left-side collision
                            if (placedBlock.getX() < currentTetriminoBlock.getX()) {
                                leftCollisions.add(new int[]{placedBlock.getX() + blockSize, currentTetriminoBlock.getY()});

                            // Check right-side collision
                            } else if (placedBlock.getX() > currentTetriminoBlock.getX()) {
                                rightCollisions.add(new int[]{placedBlock.getX() - blockSize, currentTetriminoBlock.getY()});
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Finds collisions with blocks below the current tetrimino.
     * The method calculates the distance to the closest block below each block of the tetrimino
     * and updates the bottom collision data.
     */
    private void findBottomCollide() {

        // Reset collision arrays ensuring new closest collisions are found
        for(int[] coordinates : bottomCollisions) {
            Arrays.fill(coordinates, 0);
        }
        
        Arrays.fill(distanceCollisions, Integer.MAX_VALUE);

        int counter = 0; // Variable to keep track of if the top colliding blocks have already been identified and no longer needs to check
    
        if (!boardBlocks.isEmpty()) {

            // Loop through placed blocks and find collisions
            outer:
            for (ArrayList<Block> rows : boardBlocks) {
                for (Block placedBlock : rows) {
                    for (int i = 0; i < currentTetriminoBlocks.length; i++) {

                        // Only search for collision if the board blocks matches current tetrimino's block s value
                        if (placedBlock.getX() == currentTetriminoBlocks[i].getX()) {

                            int distance = placedBlock.getY() - currentTetriminoBlocks[i].getY();

                            // Distance greater than 0 if under blocks
                            if (distance > 0 && distance < distanceCollisions[i]) {
                                bottomCollisions[i][0] = placedBlock.getX();
                                bottomCollisions[i][1] = placedBlock.getY() - blockSize; // -30 to account for the block size (at where the block will collide)
                                distanceCollisions[i] = distance - blockSize;
                                counter++;
                            }

                            // When 4 collisions are found, stop searching because 4 is the maximum amount of bottom collisions
                            if (counter == 4) {
                                break outer;
                            }
                        }
                    }
                }
            }
        }
    
        // Handle cases where no placed blocks exist in a column
        for (int i = 0; i < currentTetriminoBlocks.length; i++) {
            if (distanceCollisions[i] == Integer.MAX_VALUE) {
                bottomCollisions[i][0] = currentTetriminoBlocks[i].getX(); //this could break something
                bottomCollisions[i][1] = 600 - blockSize;
                distanceCollisions[i] = 600 - blockSize - currentTetriminoBlocks[i].getY();
            }
        }
    }

    /**
     * Checks if the current tetrimino is colliding with any placed blocks below it.
     *
     * @return true if a collision is detected, false otherwise
     */
    private boolean bottomCollide() { 

        for (Block block : currentTetriminoBlocks) {
            for (int[] collision : bottomCollisions) {

                // If block's Y matches the collision Y or the bottom edge (570), and X matches, and block is not placed return true
                if ((block.getY() == collision[1] || block.getY() == 570) &&
                    block.getX() == collision[0] &&
                    block.getIsPlaced() == false) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * The method checks for collisions along the left and right sides of the tetrimino
     * and returns the closest points of contact to restrict the tetrimino's movement.
     *
     * @return an array of two integers where:
     *         - The first element represents the left restriction (x-coordinate of the closest left collision)
     *         - The second element represents the right restriction (x-coordinate of the closest right collision)
     */

    private int[] sideCollide() {

        // Holds the left and right restricions
        int[] restrictions = new int[2];

        // Restriction variables that will continusely update based on closest collision
        int leftRestriction = 0;
        int rightRestriction = 270;
        
        // Used to hold the reference blocks'(in relation to closest collision) x values 
        Block referencePointLeft = currentTetrimino.farLeft();
        Block referencePointRight = currentTetrimino.farRight();
        
        // Variable that will store the closest collision distance
        int currentClosestDistance = Integer.MAX_VALUE;

        // Will be used to hold the distance of current tetrimino blocks and side collisions
        int newDistance;

        // Finds the closest possible left collison
        for (int[] collision: leftCollisions) {
            for (Block currentTetriminoBlock : currentTetriminoBlocks) {
                if (collision[1] == currentTetriminoBlock.getY()) {
                    newDistance = currentTetriminoBlock.getX() - collision[0];

                    // Updates left restriction and reference block if a closer block is found
                    if (newDistance < currentClosestDistance) {
                        leftRestriction = collision[0];
                        currentClosestDistance = newDistance;
                        referencePointLeft = currentTetriminoBlock;
                    }
                }
            }
        }

        referencePoints[0] = referencePointLeft; // Assigns the block closest to left collision
        restrictions[0] = leftRestriction; // Assigns new left restriction

        currentClosestDistance = Integer.MAX_VALUE;

        // Finds the closest possible right collision
        for(int[] collision: rightCollisions) {
            for (Block currentTetriminoBlock : currentTetriminoBlocks) {
                if (collision[1] == currentTetriminoBlock.getY()) {
            
                    newDistance = collision[0] - currentTetriminoBlock.getX();

                    // Updates right restriction and reference block if a closer block is found
                    if (newDistance < currentClosestDistance) { //
                        rightRestriction = collision[0];
                        currentClosestDistance = newDistance;
                        referencePointRight = currentTetriminoBlock;
                    }                                                
                }
            }
        }

        referencePoints[1] = referencePointRight;  // Assigns the block closest to right collision
        restrictions[1] = rightRestriction;  // Assigns new right restriction

        return restrictions;
    }

    /**
     * Adjusts the position of the current tetrimino to ensure it remains within the the restrictions
     *
     * @param leftRestriction  the x-coordinate representing the leftmost permissible boundary
     * @param rightRestriction the x-coordinate representing the rightmost permissible boundary
     * @param closestLeft      the x-coordinate of the current tetrimino's leftmost block
     * @param closestRight     the x-coordinate of the current tetrimino's rightmost block
     */
    private void moveInBounds(int leftRestriction, int rightRestriction, int closestLeft, int closestRight) {

        // If block phases through left restriction push it back so it stays within restriction
        if (closestLeft < leftRestriction) {
            for (Block block : currentTetriminoBlocks) {
                block.moveX(leftRestriction - closestLeft);
            }

        // If block phases through right restriction push it back so it stays within restriction
        } else if (closestRight > rightRestriction) {
            for (Block block : currentTetriminoBlocks) {
                block.moveX(rightRestriction - closestRight);
            }
        }
    }

    /**
     * Updates the total score based on the number of cleared rows and the current level.
     *
     * @param numClearRows the number of rows cleared simultaneously
     * @param level        the current game level, which affects the score multiplier
     */
    private void scoringSystem(int numClearRows, int level) {
        switch (numClearRows) {
            case 1 -> totalScore += 40 * level;
            case 2 -> totalScore += 100 * level;
            case 3 -> totalScore += 300 * level;
            case 4 -> totalScore += 1200 * level;
        }
    }

    /**
     * Clears filled rows from the game board, updates the score, and moves the blocks above downwards.
     *
     * @param level the current game level, used to calculate the score for cleared rows
     */
    private void clearRow(int level) {

        ArrayList<ArrayList<Block>> clearedRows = new ArrayList<>(); // Track rows to be cleared
        ArrayList<Integer> clearedRowsY = new ArrayList<>(); // Y coords of cleared rows

        // Identify filled rows and record their y coordinates
        for(ArrayList<Block> row: boardBlocks) {
            if(row.size() == 10) {
                clearedRows.add(row);
                clearedRowsY.add(row.get(0).getY());
            }
        }

        scoringSystem(clearedRows.size(), level); // Update score
        background.repaint(); // Redraw score situated in the background to reflect changes

        // Remove the rows that are filled
        for (ArrayList<Block> row: clearedRows) {
            Audio.playAudio("clear");
            boardBlocks.remove(row);
        }

        // Shift blocks above the clear down to the next clear
        for (int y: clearedRowsY) {
            for(ArrayList<Block> row: boardBlocks) {
        
                // Skip rows already below the cleared row
                if (row.get(0).getY() > y) {
                    break;
                }

                // Moves blocks in rows above the cleared row downwards
                for (Block placedBlocks: row) {
                    placedBlocks.moveY(blockSize);
                }
            }
        }
    }

    /**
     * Calculates and returns the shortest collision distance from the current Tetrimino to 
     * the closest placed block or the bottom of the game board.
     *
     * @return the shortest distance to a collision. 
     */
    private int getShortestCollisionDistance() {

        int shortestDistance = Integer.MAX_VALUE;

        // Iterates through all calculated collision distances to find the shortes
        for (int dist: distanceCollisions) {
            if(dist < shortestDistance) {
                shortestDistance = dist;
            }
        }
        return shortestDistance;
    }

    /**
     * Drops the current Tetrimino by the specified collision distance, effectively placing it
     * at the lowest possible position on the game board.
     *
     * @param collisionDistance the vertical distance that the Tetrimino should move down 
     *                          before being placed.
     */
    private void drop(int collisionDistance) {

        // Move each block of the current tetrimino down by the collision distance
        for (Block block : currentTetriminoBlocks) {
            block.moveY(collisionDistance);
            block.setIsPlaced(true);
        }
    }


    /**
     * Inspired by: https://www.javatpoint.com/rotate-matrix-by-90-degrees-in-java
     * Rotates the given Tetrimino blocks clockwise around a specified pivot block.
     *
     * @param pivot the block around which the rotation occurs.
     * @param currentTetriminoBlocks an array of blocks representing the current Tetrimino that will be rotated.
     */

    private void rotateClockwise(Block pivot, Block[] currentTetriminoBlocks) {

        for (Block block : currentTetriminoBlocks) {
            // Calculate the relative position of the block to the pivot
            int relativeX = block.getX() - pivot.getX();
            int relativeY = block.getY() - pivot.getY();
        
            // Apply the clockwise rotation matrix
            int rotatedX = relativeY;
            int rotatedY = -relativeX;
        
            // Update the block's position based on the rotation
            block.setX(pivot.getX() - rotatedX);
            block.setY(pivot.getY() - rotatedY);
        }
    }

    /**
     * Updates the position of the tetrimino based on player input and movement restrictions.
     * 
     * @param leftRestriction The left movement boundary.
     * @param rightRestriction The right movement boundary.
     * @param referencePointLeft The left reference point for the tetrimino's movement.
     * @param referencePointRight The right reference point for the tetrimino's movement.
     */
    public void blockControl(int leftRestriction, int rightRestriction, int referencePointLeft, int referencePointRight) {
        if (key.DOWN && currentTetrimino.farBottom() != 570 && !checkGameEnd()) {      
            if(!bottomCollide()){
                currentTetrimino.moveY(blockSize);
            }

        } else if (key.LEFT && referencePointLeft > leftRestriction) {
             currentTetrimino.moveX(-blockSize);
        } else if (key.RIGHT && referencePointRight < rightRestriction) {
            currentTetrimino.moveX(blockSize);
        }
    }
    
    // Checks if the game ends if the block collides
    public boolean checkGameEnd() {
        if (bottomCollide()) {
            for(Block block: currentTetriminoBlocks) {
                if (block.getY() <= 30){
                    gameThread = null;
                    Main.startGame();
                    return true;
                }
            }
        } 
        return false;
    }

    // Inspired by: https://www.youtube.com/watch?v=VpH33Uw-_0E 
    // The main game loop for the application, maintaining a consistent frame rate.
    @Override
    public void run() {

        // Interval in nanoseconds for each frame at 15 FPS
        double drawInterval = 1000000000 / 15; 
        double delta = 0; // Tracks the time elapsed between frames
        long lastTime = System.nanoTime(); // Timestamp of the previous frame
        long currentTime;

        // Main game loop, active as long as the game thread is running
        while (gameThread != null) {
            currentTime = System.nanoTime(); // Capture the current time

            // Add the elapsed time divided by the frame interval
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime; // Update the last frame timestamp

            // If enough time has passed for one frame, update and repaint
            if (delta >= 1) {
                update(); // Update game logic
                repaint(); // Request a screen redraw
                delta--; // Decrease delta by 1 to account for the processed frame
            }
        }
    }

    // Called in the main loop to process all game logic.
    public void update() { 

        // Calculate the time that has passed since the last update
        long currentGameTime = System.currentTimeMillis() - lastGameTime;

        if (currentGameTime >= dropInterval) { // Optional: change drop interval by doing like *0.90 or *0.95

            // If enough time has passed (based on the drop interval), move the Tetrimino blocks down by block size
            for (Block block : currentTetriminoBlocks) {
                block.moveY(blockSize); 
            }
        
            // Update the last recorded game time to the current time
            lastGameTime = System.currentTimeMillis();
        }
        
        // Rotate tetriminos when the rotate key is pressed
        if(key.ROTATE) {
            Audio.playAudio("rotate");
            rotateClockwise(currentTetriminoBlocks[0], currentTetriminoBlocks);
            rotateClockwise(copyTetriminoBlocks[0], copyTetriminoBlocks);
            key.ROTATE = false;
        }
        
        findSideCollide();
        tetriminoRestrictions = sideCollide();

        // Updates movement restrictions
        blockControl(tetriminoRestrictions[0], tetriminoRestrictions[1], referencePoints[0].getX(), referencePoints[1].getX());

        moveInBounds(tetriminoRestrictions[0], tetriminoRestrictions[1], referencePoints[0].getX(), referencePoints[1].getX());
        findBottomCollide();
        int shortestCollisionDistance = getShortestCollisionDistance();
        copyTetrimino.setY(shortestCollisionDistance, currentTetriminoBlocks);

        // Mimics the current tetrimino blocks' x values to the copied tetrimino
        for(int i = 0; i < currentTetriminoBlocks.length; i++) {
            copyTetriminoBlocks[i].setX(currentTetriminoBlocks[i].getX());
        }

        // Places the tetrimino
        if (key.SPACE == true || bottomCollide()) {
            
            // Check if blocks are stacked to the top
            checkGameEnd();
            
            Audio.playAudio("place");
            drop(shortestCollisionDistance);
            BlockSort.sort(currentTetriminoBlocks, boardBlocks);

            // Clears row if eligible
            clearRow(level);

            generateTetrimino(nextTetrimino);

            // Finds new next tetrimino 
            nextTetrimino = nextBlock.generateNextTetriminos();
            nextBlock.repaint();

            // Allows for switching to stored block
            storedTetrimino.setSwitched(false);
            key.SPACE = false;
        }

        if (key.STORE && storedTetrimino.getSwitched() == false) { // Only triggered if not already switched during turn

            storedTetrimino.repaint();
            storedTetrimino.setSwitched(true);
            
            // If its the first time storing a tetrimino,
            // A new tetrimino will be generated
            if (!storedTetrimino.getSaved()) {
                storedTetrimino.setSavedTetrimino(currentTetriminoBlocks[0].getType());
                storedTetrimino.setSaved(true);
                generateTetrimino(nextTetrimino);
                nextTetrimino = nextBlock.generateNextTetriminos();
                nextBlock.repaint();

            // Switches tetrimino with saved tetrimino
            } else {
                String savedTetriminoType = storedTetrimino.getSavedTetrimino();
                storedTetrimino.setSavedTetrimino(currentTetriminoBlocks[0].getType());
                generateTetrimino(savedTetriminoType); // generate saved type tetrimino
            }

            key.STORE = false;
        }  
    }   

    /**
     * Paints the game components, including the board blocks, current Tetrimino, 
     * and copied Tetrimino, onto the game panel.
     * 
     * @param g The Graphics object used for drawing.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Clears the panel and paints the background
        Graphics2D g2 = (Graphics2D) g; // Manipulate more graphics

        // Draw all placed blocks on the board
        for (ArrayList<Block> row : boardBlocks) {
            for (Block block : row) {
                block.draw(g2);
            }
        }

        // Draw the current Tetrimino and its ghost (copy)
        copyTetrimino.draw(g2);
        currentTetrimino.draw(g2);

        // Dispose of the Graphics2D object to free resources
        g2.dispose();
    }
}