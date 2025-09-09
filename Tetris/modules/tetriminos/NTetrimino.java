package modules.tetriminos;

public class NTetrimino extends Tetrimino{

    /**
     * Initializes tetrimino with block locations copied from the current tetrimino.
     * 
     * @param locations A 2D array representing the initial locations that are a copy of the current tetrimino.
     */
    public NTetrimino(int[][] locations) {
        super(locations, "N"); 
    }

    /**
     * Sets the Y position for each block in the tetrimino to adjust for the closest collision distance.
     * 
     * @param closestCollisionDistance The Y distance to the closest block or floor where the tetrimino should stop.
     * @param currentTetrimino An array of Block objects representing the current tetrimino's blocks.
     */
    public void setY(int closestCollisionDistance, Block[] currentTetrimino) {
        for (int i = 0; i < tetrimino.length; i++) {
            // Adjusts the Y position of each block in the tetrimino
            tetrimino[i].setY(closestCollisionDistance + currentTetrimino[i].getY());
        }
    }
}
