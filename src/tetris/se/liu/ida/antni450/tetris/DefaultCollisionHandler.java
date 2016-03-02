package tetris.se.liu.ida.antni450.tetris;

public class DefaultCollisionHandler implements CollisionHandler
{
    /**
     *
     * @return true if fallingPoly collides with anything on the board, else false
     */
    public boolean hasCollision(Board board) {
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

    public String getDescription() {
	String description = "Standard";
	return description;
    }
}
