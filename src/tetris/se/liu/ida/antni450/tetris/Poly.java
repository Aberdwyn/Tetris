package tetris.se.liu.ida.antni450.tetris;

/**
 * the class for polymino tetris blocks
 */
public class Poly
{
    /**
     * every block is a 2d array consisting of different SquareTypes
     */
    public SquareType[][] block;

    public Poly(final SquareType[][] block) {
	this.block = block;
    }
}
