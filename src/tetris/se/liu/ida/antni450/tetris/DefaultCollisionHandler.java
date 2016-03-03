package tetris.se.liu.ida.antni450.tetris;

/**
 * this is the default collision handler
 */
public class DefaultCollisionHandler implements CollisionHandler
{
    /**
     * checks if the fallingPoly collides with anything
     * @return true if fallingPoly collides with anything on the board, else false
     */
    @Override public boolean hasCollision(Board board) {
	int fallingPolyLength = board.getFallingPoly().block[0].length;
	int fallingPolyHeight = board.getFallingPoly().block.length;
	for (int x = 0; x < fallingPolyLength; x++) {
	    for (int y = 0; y < fallingPolyHeight; y++) {
		if (board.getFallingSquareTypeAt(x, y) != SquareType.EMPTY &&
		    board.getSquareTypeAt(x + board.getFallingX(), y + board.getFallingY()) != SquareType.EMPTY) {
		    return true;
		}
	    }
	}
	return false;
    }

    /**
     * @return the description of the collision handler
     */
    @Override public String getDescription() {
	//this description will be shown on the board
	String description = "Standard";
	return description;
    }
}
