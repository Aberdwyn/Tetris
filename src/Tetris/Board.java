package Tetris;

import javax.swing.*;
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
    private CollisionHandler collisionHandler;
    private final static int WALL_THICKNESS = 2;
    private boolean running = true;
    private int score = 0;

    public Board(final int width, final int height) {
	this.width = width;
	this.height = height;
	this.squares = new SquareType[height+2*WALL_THICKNESS][width+2*WALL_THICKNESS];
	this.boardListeners = new ArrayList<>();
	this.collisionHandler = new DefaultCollisionHandler();

	//initiates the board with a wall of outside blocks around it.
	for (int x=0; x<width+2*WALL_THICKNESS; x++) {
	    for (int y=0; y<height+2*WALL_THICKNESS; y++) {
		if (x<WALL_THICKNESS || x>=WALL_THICKNESS+width || y<WALL_THICKNESS || y>=WALL_THICKNESS+height) {
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

    public SquareType getSquareTypeAt(int x, int y) {
	return squares[y+WALL_THICKNESS][x+WALL_THICKNESS];
    }

    public SquareType getFallingSquareTypeAt(int x, int y) {
	return fallingPoly.block[y][x];
    }

    public int getScore() {
	return score;
    }

    public boolean isRunning() {
	return running;
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
	    if (collisionHandler.hasCollision(this)) {
		running = false;
		fallingY--;

		this.addHighscore();
		System.out.println(HighscoreList.getINSTANCE());
	    }
	    this.notifyListeners();

	}
	else {
	    fallingY++;
	    if (collisionHandler.hasCollision(this)) {
		fallingY--;
		this.addBlock(fallingPoly, fallingX, fallingY);
		fallingPoly = null;
	    }
	    this.notifyListeners();
	}
    }

    /**
     * ädds the score to the highscore list and prints the contents of the highscore list
     */
    private void addHighscore() {
	String name = JOptionPane.showInputDialog("Please input your name");
	Highscore newHighscore = new Highscore(score, name);
	HighscoreList.getINSTANCE().addHighscore(newHighscore);
    }

    public void resetBoard() {
	for (int x=WALL_THICKNESS; x<width+WALL_THICKNESS; x++) {
	    for (int y=WALL_THICKNESS; y<height+WALL_THICKNESS; y++) {
		squares[y][x] = SquareType.EMPTY;
	    }
	}
	fallingPoly = null;
	score = 0;
	this.notifyListeners();
	running = true;
    }

    public void addBlock(Poly poly, int blockX, int blockY) {
	int polyLength = poly.block[0].length;
	int polyHeight = poly.block.length;
	for (int polyX=0; polyX<polyLength; polyX++) {
	    for (int polyY=0; polyY<polyHeight; polyY++) {
		if (poly.block[polyY][polyX] != SquareType.EMPTY) {
		    squares[polyY+blockY+WALL_THICKNESS][polyX+blockX+WALL_THICKNESS] = poly.block[polyY][polyX];
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
	    if (collisionHandler.hasCollision(this)) fallingX++;
	    this.notifyListeners();
	}
    }

    public void moveRight() {
	if (fallingPoly != null) {
	    fallingX++;
	    if (collisionHandler.hasCollision(this)) fallingX--;
	    this.notifyListeners();
	}
    }

    public void moveDown() {
	if (fallingPoly != null) {
	    fallingY++;
	    if (collisionHandler.hasCollision(this)) fallingY--;
	    this.notifyListeners();
	}
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

	    if (collisionHandler.hasCollision(this)) {
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
	for (int y=WALL_THICKNESS; y<height+WALL_THICKNESS; y++) {
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
	for (int y=fullRowY; y>WALL_THICKNESS; y--) {
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

