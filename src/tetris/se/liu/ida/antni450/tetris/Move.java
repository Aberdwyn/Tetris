package tetris.se.liu.ida.antni450.tetris;


/**
 *  enum for moving in different directions
 *  */
    public enum Move {
    /**
     * move left.
     */
    LEFT(-1,0),
    /**
     * move right
     */
    RIGHT(1,0),
    /**
     * move down.
     */
    DOWN(0,1);

    //change in x
    private final int deltaX;
    //change in y
    private final int deltaY;

    Move(final int deltaX, final int deltaY) {
	this.deltaX = deltaX;
	this.deltaY = deltaY;
	}

    public int getDeltaX() {
	return deltaX;
    }

    public int getDeltaY() {
	return deltaY;
    }
}
