package tetris.se.liu.ida.antni450.tetris;

/**
 * this is the collision handler for the powerup heavy
 */
public class Heavy implements CollisionHandler
{

    /**
     * when the falling poly collides with a block that cannot collapse or an outside block it will return true
     * when it collides with a block that can collapse, the column will be collapsed and false is returned.
     * @param board the tetris board
     * @return true if the falling poly collides with an OUTSIDE block or a column that can be collapsed, false otherwise
     */
    @Override public boolean hasCollision(Board board) {
	int fallingPolyLength = board.getFallingPoly().block[0].length;
	int fallingPolyHeight = board.getFallingPoly().block.length;
	for (int y = 0; y < fallingPolyHeight; y++) {
	    for (int x = 0; x < fallingPolyLength; x++) {
		if (board.getFallingSquareTypeAt(x, y) != SquareType.EMPTY &&
		    board.getSquareTypeAt(x + board.getFallingX(), y + board.getFallingY()) != SquareType.EMPTY) {
		    if (!board.columnCanCollapse(x + board.getFallingX(), y + board.getFallingY()) ||
			board.getSquareTypeAt(x + board.getFallingX(), y + board.getFallingY()) == SquareType.OUTSIDE) {
			return true;
		    }
		}
	    }
	}
	for (int y = 0; y < fallingPolyHeight; y++) {
	    for (int x = 0; x < fallingPolyLength; x++) {
		if (board.getFallingSquareTypeAt(x, y) != SquareType.EMPTY && board.getSquareTypeAt(x + board.getFallingX(), y + board.getFallingY()) != SquareType.EMPTY) {
		    board.pushDown(x+board.getFallingX(), y+board.getFallingY());
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
	String description = "Heavy";
	return description;
    }
}
