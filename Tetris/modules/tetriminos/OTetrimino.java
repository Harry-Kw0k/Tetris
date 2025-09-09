package modules.tetriminos;


public class OTetrimino extends Tetrimino{

    // Initializes the O tetrimino via parent constructor with a specific shape and type.
    public OTetrimino() {
        super(new int[][] { 
            {150, 30}, {150, 0}, {180, 0}, {180, 30} // Specific coordinates for OTetrimino
        }, "O");
    }
}
