package tetris.se.liu.ida.antni450.tetris;

public class Heavy implements CollisionHandler
{

    public boolean hasCollision(Board board) {
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

    public String getDescription() {
	String description = "Heavy";
	return description;
    }
}
