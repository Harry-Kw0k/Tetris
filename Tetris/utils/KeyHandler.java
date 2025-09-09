package utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// // Inspired by: https://www.youtube.com/watch?v=VpH33Uw-_0E 
public class KeyHandler implements KeyListener{
    
    // Game control keys
    public boolean UP, DOWN, LEFT, RIGHT, SPACE, ROTATE, STORE;

    // Flags to ensure each key action is only triggered once per key press
    private boolean spaceHandled = false;
    private boolean rotateHandled = false;
    private boolean storeHandled = false;

    /**
     * This method is called when a key is typed (key release + key press).
     * Not used in this implementation
     *
     * @param e The KeyEvent triggered by the typing of a key
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * This method is called when a key is pressed and updates the state of the corresponding control.
     *
     * @param e The KeyEvent triggered by the key press
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // Updates key states based on the key pressed
        switch (keyCode) {
            case KeyEvent.VK_DOWN -> DOWN = true; // Down arrow key
            case KeyEvent.VK_LEFT -> LEFT = true; // Left arrow key
            case KeyEvent.VK_RIGHT -> RIGHT = true; // Right arrow key
            case KeyEvent.VK_UP -> { 
                // Handles the rotate key with a flag to prevent multiple rotations in quick succession
                if(!rotateHandled) {
                    ROTATE = true; // Rotate action
                    rotateHandled = true; // Mark rotation as handled
                }
            }
            case KeyEvent.VK_SPACE -> {
                // Ensures space bar action is only processed once per press
                if (!spaceHandled) {
                    SPACE = true; // Space bar action (e.g., drop block)
                    spaceHandled = true; // Mark space as handled
                }
            }
            case KeyEvent.VK_C -> {
                // Ensures the store action is only processed once per press
                if(!storeHandled) {
                    STORE = true; // Store action
                    storeHandled = false; // Reset store flag
                }
            }
            default -> {
                // No action for other keys
            }
        }
    }

    /**
     * This method is called when a key is released. It resets the state of the key 
     * that was released, ensuring it no longer holds the active state.
     *
     * @param e The KeyEvent triggered by the key release
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // Resets key states when keys are released
        switch (keyCode) {
            case KeyEvent.VK_DOWN -> DOWN = false; // Down arrow key released
            case KeyEvent.VK_LEFT -> LEFT = false; // Left arrow key released
            case KeyEvent.VK_RIGHT -> RIGHT = false; // Right arrow key released
            case KeyEvent.VK_UP -> { 
                ROTATE = false; // Rotate action released
                rotateHandled = false; // Reset rotate flag
            }
            case KeyEvent.VK_SPACE -> {
                SPACE = false; // Space bar action released
                spaceHandled = false; // Reset space flag
            }
            case KeyEvent.VK_C -> {
                STORE = false; // Store action released
                storeHandled = false; // Reset store flag
            }
            default -> {
                // No action for other keys
            }
        }
    }
}
