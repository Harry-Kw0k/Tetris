package modules.tetriminos;

public class ZTetrimino extends Tetrimino{

    // Initializes the Z tetrimino via parent constructor with a specific shape and type.
    public ZTetrimino() {
        super(new int[][] { 
            {150, 30}, {120, 0}, {150, 0}, {180, 30} // Specific coordinates for ZTetrimino
        }, "Z");
    }
}
