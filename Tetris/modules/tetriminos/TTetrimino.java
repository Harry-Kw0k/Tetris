package modules.tetriminos;

public class TTetrimino extends Tetrimino{

    // Initializes the T tetrimino via parent constructor with a specific shape and type.
    public TTetrimino() {
        super(new int[][] { 
            {150, 30}, {120, 30}, {150, 0}, {180, 30} // Specific coordinates for TTetrimino
        }, "T");
    }
}
