package Tetris;

public class Fallthrough implements CollisionHandler
{
    public boolean hasCollision(Board board) {
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

    public String getDescription() {
	String description = "Fallthrough";
	return description;
    }
}
