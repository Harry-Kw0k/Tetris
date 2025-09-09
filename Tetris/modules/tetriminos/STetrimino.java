package modules.tetriminos;

public class STetrimino extends Tetrimino {

    // Initializes the S tetrimino via parent constructor with a specific shape and type.
    public STetrimino() {
        super(new int[][] { 
            {150, 30}, {120, 30}, {150, 0}, {180, 0} // Specific coordinates for STetrimino
        }, "S");
    }
}
