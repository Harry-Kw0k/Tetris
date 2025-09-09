package modules;

import java.util.*;
import modules.tetriminos.*;

public class BlockSort {
    

    /**
     * Sorts the blocks of a given tetrimino by their y-values in ascending order and places them into a list of rows on the game board.
     * This sorting makes the clearing logic much easier as it ensures hat the blocks are already arranged in the correct order
     * based on their y-values.
     *
     * @param blocks        An array of blocks that belong to a single tetrimino.
     * @param boardBlocks   A list of rows, where each row is an ArrayList of blocks, representing the blocks placed on the game board.
     */
    public static void sort(Block[] blocks, ArrayList<ArrayList<Block>> boardBlocks) {

        boolean added;

        // Inserts block into sorted rows based on y values
        for(Block block: blocks) {

            // flag whether the block has been added
            // Used to skip unnecessary checks or proceed with next checks
            added = false;

            // When no blocks are on the board, add one block from current tetrimino
            if (boardBlocks.isEmpty()) {
                boardBlocks.add(new ArrayList<>());
                boardBlocks.get(0).add(block);
                added = true;
            }

            // Checks if there is a row where the current block matches with those block's y values and inserts if there is one
            for (int i = 0; i < boardBlocks.size() && added == false; i++) {
                if (block.getY() == boardBlocks.get(i).get(0).getY() && 
                    block.getIsPlaced() == true){

                    boardBlocks.get(i).add(block);
                    added = true;
                } 
            }

            // Checks for y value less than current block y coordinate
            // then creates a new row where the block will now reside
            for (int i = 0; i < boardBlocks.size() && added == false; i++) {
                if (block.getY() < boardBlocks.get(i).get(0).getY() &&
                    block.getIsPlaced() == true) {
                        boardBlocks.add(i, new ArrayList<>());
                        boardBlocks.get(i).add(block);
                        added = true;
                } 
            }

            // If the first element does not have the greatest y value then add the current block at the end of the sorted array
            if(!added) {
                boardBlocks.add(new ArrayList<>());
                boardBlocks.get(boardBlocks.size()-1).add(block);
            }
            
        }
    }

    
}
