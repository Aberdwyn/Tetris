package Tetris;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board
{
    private SquareType[][] squares;
    private int  width, height, fallingX, fallingY;
    private Random rnd = new Random();
    private Poly fallingPoly = null;
    private List<BoardListener> boardListeners;
    public final static int WALL_THICKNESS = 2;
    public boolean running = true;
    private int score = 0;

    public Board(final int width, final int height) {
	this.width = width;
	this.height = height;
	this.squares = new SquareType[height+WALL_THICKNESS][width+2*WALL_THICKNESS];
	this.boardListeners = new ArrayList<>();

	//initiates the board with a wall of outside blocks around it. not on top though
	for (int x=0; x<width+2*WALL_THICKNESS; x++) {
	    for (int y=0; y<height+WALL_THICKNESS; y++) {
		if (x<WALL_THICKNESS || x>=WALL_THICKNESS+width || y>=height) {
		    squares[y][x] = SquareType.OUTSIDE;
		}
		else squares[y][x] = SquareType.EMPTY;
	    }
	}
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public int getFallingX() {
	return fallingX;
    }

    public int getFallingY() {
	return fallingY;
    }

    public Poly getFallingPoly() {
	return fallingPoly;
    }

    public SquareType getSquareType(int x, int y) {
	return squares[y][x+WALL_THICKNESS];
    }

    public int getScore() {
	return score;
    }

    public void addBoardListener(BoardListener bl) {
	this.boardListeners.add(bl);
    }

    private void notifyListeners() {
	for (BoardListener bl: boardListeners) {
	    bl.boardChanged();
	}
    }

    public void tick() {
	if (fallingPoly == null) {
	    fallingPoly = TetraminoMaker.getPoly(rnd.nextInt(TetraminoMaker.getNumberOfTypes()));
	    fallingX = width/2 - fallingPoly.block.length/2;
	    fallingY = 0;
	    if (this.hasCollision()) {
		running = false;
		//fundera på detta här (game over skiten, vart ska blocket synas?)
		fallingY-=2;
	    }
	    this.notifyListeners();

	}
	else {
	    fallingY++;
	    if (this.hasCollision()) {
		fallingY--;
		this.addBlock(fallingPoly, fallingX, fallingY);
		fallingPoly = null;
	    }
	    this.notifyListeners();
	}
    }

    public void addBlock(Poly poly, int blockX, int blockY) {
	int polyLength = poly.block[0].length;
	int polyHeight = poly.block.length;
	for (int polyX=0; polyX<polyLength; polyX++) {
	    for (int polyY=0; polyY<polyHeight; polyY++) {
		if (poly.block[polyY][polyX] != SquareType.EMPTY) {
		    squares[polyY+blockY][polyX+blockX+WALL_THICKNESS] = poly.block[polyY][polyX];
		}
	    }
	}

	int rowsRemoved = 0;
	while (hasFullRow()) {
	    removeFullRow();
	    rowsRemoved++;
	}
	increaseScore(rowsRemoved);

	this.notifyListeners();
    }

    public void moveLeft() {
	if (fallingPoly != null) {
	    fallingX--;
	    if (this.hasCollision()) fallingX++;
	    this.notifyListeners();
	}
    }

    public void moveRight() {
	if (fallingPoly != null) {
	    fallingX++;
	    if (this.hasCollision()) fallingX--;
	    this.notifyListeners();
	}
    }

    public void moveDown() {
	if (fallingPoly != null) {
	    fallingY++;
	    if (this.hasCollision()) fallingY--;
	    this.notifyListeners();
	}
    }

    /**
     *
     * @return true if fallingPoly collides with anything on the board, else false
     */
    public boolean hasCollision() {
	int fallingPolyLength = fallingPoly.block[0].length;
	int fallingPolyHeight = fallingPoly.block.length;
	for (int x=0; x<fallingPolyLength; x++) {
	    for (int y=0; y<fallingPolyHeight; y++) {
		if (fallingPoly.block[y][x] != SquareType.EMPTY && squares[y + fallingY][x + fallingX + WALL_THICKNESS] != SquareType.EMPTY) {
		    return true;
		}
	    }
	}
	return false;
    }

    /**
     * rotates fallingPoly to the right. if this results in a collision, reverts the change.
     */
    public void rotate() {
	if (fallingPoly != null) {
	    int size = fallingPoly.block.length;

	    Poly newPoly = new Poly(new SquareType[size][size]);
	    Poly oldPoly = fallingPoly;

	    for (int r = 0; r < size; r++) {
		for (int c = 0; c < size; c++) {
		    newPoly.block[c][size - 1 - r] = fallingPoly.block[r][c];
		}
	    }

	    fallingPoly = newPoly;

	    if (hasCollision()) {
		fallingPoly = oldPoly;
	    }

	    this.notifyListeners();
	}
    }

    /**
     *
     * @return the first y-coord found which has a full row
     */
    public int findFullRow() {
	int counter=0;
	for (int y=0; y<height; y++) {
	    for (int x = WALL_THICKNESS; x < width + WALL_THICKNESS; x++) {
		if (squares[y][x] != SquareType.EMPTY) {
		    counter++;
		}
		if (counter == width) {
		    return y;
		}
	    }
	    counter = 0;
	}
	return -1;
    }

    /**
     *
     * @return true if there is a full row, false otherwise
     */
    public boolean hasFullRow() {
	return (findFullRow() != -1);
    }


    /**
     * removes the first full row it finds and moves the rows above this row down one step
     */
    public void removeFullRow() {
    int fullRowY = findFullRow();
	for (int y=fullRowY; y>0; y--) {
	    for (int x = WALL_THICKNESS; x<width+WALL_THICKNESS; x++) {
		squares[y][x] = squares[y-1][x];
	    }
	}
	this.notifyListeners();

    }

    /**
     * increases score based on the specified rows removed
     * @param rowsRemoved the number of rows removed
     */
    public void increaseScore(int rowsRemoved) {
	switch (rowsRemoved) {
	    case 1:
		final int oneRowRemoved = 100;
		score += oneRowRemoved;
		break;
	    case 2:
		final int twoRowsRemoved = 300;
		score += twoRowsRemoved;
		break;
	    case 3:
		final int threeRowsRemoved = 500;
		score += threeRowsRemoved;
		break;
	    case 4:
		final int fourRowsRemoved = 800;
		score += fourRowsRemoved;
		break;
	}
    }
}

