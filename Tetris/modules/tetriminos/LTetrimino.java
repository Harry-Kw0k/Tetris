package modules.tetriminos;

public class LTetrimino extends Tetrimino{

    //I nitializes the L tetrimino via parent constructor with a specific shape and type.
    public LTetrimino() {
        super(new int[][] { 
            {150, 30}, {120, 30}, {180, 0}, {180, 30} // Specific coordinates for LTetrimino
        }, "L");
    }
}
