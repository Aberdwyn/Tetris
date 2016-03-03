package tetris.se.liu.ida.antni450.tetris;

/**
 * this is the collision handler for the powerup fallthrough
 */
public class Fallthrough implements CollisionHandler
{
    /**
     * checks if the falling poly collides with an OUTSIDE block
     * if it collides with any block that is not an OUTSIDE block, the block it collides with will be removed.
     * @param board the tetris board
     * @return true if the falling poly collides with an OUTSIDE block, false otherwise
     */
    @Override public boolean hasCollision(Board board) {
	int fallingPolyLength = board.getFallingPoly().block[0].length;
	int fallingPolyHeight = board.getFallingPoly().block.length;
	for (int x = 0; x < fallingPolyLength; x++) {
	    for (int y = 0; y < fallingPolyHeight; y++) {
		if (board.getFallingSquareTypeAt(x, y) != SquareType.EMPTY && board.getSquareTypeAt(x + board.getFallingX(), y + board.getFallingY()) == SquareType.OUTSIDE) {
		    return true;
		}

	    }
	}

	for (int x = 0; x < fallingPolyLength; x++) {
	    for (int y = 0; y < fallingPolyHeight; y++) {
		if (board.getFallingSquareTypeAt(x, y) != SquareType.EMPTY && board.getSquareTypeAt(x + board.getFallingX(), y + board.getFallingY()) != SquareType.EMPTY) {
		    board.removeBlockAt(x + board.getFallingX(), y + board.getFallingY());
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
	String description = "Fallthrough";
	return description;
    }
}
