package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.sound.sampled.Clip;
import javax.swing.*;
import modules.*;
import utils.*;

public class Main extends JFrame{

    // Window for the game
    private static JFrame frame;    

    // The display for the title screen
    private static TitleScreen titleScreen; 

    // Audio clip for game background music
    private static Clip gameMusic;

    // GUI panels
    private static StoreTetrimino holdBar; // used for board
    private static NextBlock nextBlockBar; // Used for board
    private static GameScreen screen; // Canvas for all panels
    private static Board board; // Used to run all game logic

    public static void main(String[] args) {

        // Initialize the window
        frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the window when the user exits
        frame.setResizable(false);
    
        titleScreen = new TitleScreen(); // Initialize the title screen

        // Add a mouse listener to detect when the user clicks on the title screen
        titleScreen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startGame(); // Start the game when the title screen is clicked
            }
        });

        frame.add(titleScreen);
        frame.pack(); // Pack the components in the window to fit
        frame.setVisible(true);
    }

    // Method to start the game
    protected static void startGame() {
        
        // Stop any existing game music if it's playing
        if(gameMusic != null) {
            gameMusic.stop();
        }

        // Remove all components currently in the window (the title screen)
        frame.getContentPane().removeAll();

        // Initialize game panels
        holdBar = new StoreTetrimino();  
        nextBlockBar = new NextBlock();        
        screen = new GameScreen();         
        board = new Board(screen, nextBlockBar, holdBar);

        // Add game panels to the screen
        screen.add(holdBar); 
        screen.add(board); 
        screen.add(nextBlockBar);

        // Add the game screen to the window
        frame.add(screen);
        frame.pack(); // Repack the window to fit all the components

        // Request focus for the board to ensure keyboard inputs are received
        board.requestFocusInWindow();

        // Start the game thread to begin game logic
        board.startGameThread();
        
        // Play the game background music
        gameMusic = Audio.playAudio("music");
    }
}



