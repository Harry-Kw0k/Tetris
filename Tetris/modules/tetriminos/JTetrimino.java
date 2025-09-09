package modules.tetriminos;

public class JTetrimino extends Tetrimino {

    // Initializes the J tetrimino via parent constructor with a specific shape and type
    public JTetrimino() {
        super(new int[][] { 
            {150, 30}, {120, 30}, {120, 0}, {180, 30} // Specific coordinates for JTetrimino
        }, "J");
    }

}