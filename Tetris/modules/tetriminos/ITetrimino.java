package modules.tetriminos;

public class ITetrimino extends Tetrimino{

    // Initializes the I tetrimino via parent constructor with a specific shape and type.
    public ITetrimino() {
        super(new int[][] { 
            {150, 30}, {120, 30}, {180, 30}, {210, 30} // Specific coordinates for ITetrimino
        }, "I");
    }
}
