package tetris.se.liu.ida.antni450.tetris;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * this class handles the game mechanics in tetris
 */
public class Board
{
    private SquareType[][] squares;
    private int  width, height, fallingX, fallingY;
    private Random rnd = new Random();
    //a pointer to the current falling poly
    private Poly fallingPoly = null;
    private List<BoardListener> boardListeners;
    private CollisionHandler collisionHandler;
    //the thickness of the wall of OUTSIDE blocks around the board
    private final static int WALL_THICKNESS = 2;
    private boolean running = true;
    private int score = 0;
    private int powerupCounter = 0;
    private List<CollisionHandler> powerups;

    public Board(final int width, final int height) {
	this.width = width;
	this.height = height;
	this.squares = new SquareType[height+2*WALL_THICKNESS][width+2*WALL_THICKNESS];
	this.boardListeners = new ArrayList<>();
	this.powerups = new ArrayList<>();
	this.collisionHandler = new DefaultCollisionHandler();

	//add the powerups to the powerup list
	this.powerups.add(new Fallthrough());
	this.powerups.add(new Heavy());

	//initiates the board with a wall of SquareType.OUTSIDE blocks around it.
	for (int x=0; x<width+2*WALL_THICKNESS; x++) {
	    for (int y=0; y<height+2*WALL_THICKNESS; y++) {
		if (x<WALL_THICKNESS || x>=WALL_THICKNESS+width || y<WALL_THICKNESS || y>=WALL_THICKNESS+height) {
		    squares[y][x] = SquareType.OUTSIDE;
		}
		else squares[y][x] = SquareType.EMPTY;
	    }
	}
    }

    /**
     *
     * @return the width of the gameboard
     */
    public int getWidth() {
	return width;
    }

    /**
     *
     * @return the height of the gameboard
     */
    public int getHeight() {
	return height;
    }

    /**
     *
     * @return the x-coord of the falling poly
     */
    public int getFallingX() {
	return fallingX;
    }

    /**
     *
     * @return the y-coord of the falling poly
     */
    public int getFallingY() {
	return fallingY;
    }

    /**
     *
     * @return the falling poly object
     */
    public Poly getFallingPoly() {
	return fallingPoly;
    }

    /**
     * looks up the SquareType at specified x and y coord in the  gameboard 2D array.
     * it ignores the walls of OUTSIDE squaretypes.
     * @param x column x in 2d array
     * @param y row y in 2d array
     * @return the SquareType at specified x- and y-coord
     */
    public SquareType getSquareTypeAt(int x, int y) {
	return squares[y+WALL_THICKNESS][x+WALL_THICKNESS];
    }

    /**
     * looks up the SquareType at specified x and y coord in the fallingPoly 2D array
     * @param x column x in 2d array
     * @param y row y in 2d array
     * @return the SquareType at specified x- and y-coord in the current falling poly
     */
    public SquareType getFallingSquareTypeAt(int x, int y) {
	return fallingPoly.block[y][x];
    }

    /**
     *
     * @return the current score
     */
    public int getScore() {
	return score;
    }

    /**
     *
     * @return the current collisionHandler
     */
    public CollisionHandler getCollisionHandler() {
	return collisionHandler;
    }

    /**
     *
     * @return returns boolean value that shows if the game is currently running or not
     */
    public boolean isRunning() {
	return running;
    }

    /**
     * add the specified boardlistener to the list of boardlisteners
     * @param bl the boardlistener you want to add
     */
    public void addBoardListener(BoardListener bl) {
	this.boardListeners.add(bl);
    }

    /**
     * sends a call to all boardlisteners added that the board has changed
     */
    private void notifyListeners() {
	for (BoardListener bl: boardListeners) {
	    bl.boardChanged();
	}
    }

    /**
     * set the squaretype at specified x- and y-coord to the specified squaretype.
     * avoids the wall of OUTSIDE blocks around the game board.
     * @param x number of column in a 2d array
     * @param y number of row in a 2d array
     * @param squareType type of square
     */
    public void setSquareTypeAt(int x, int y, SquareType squareType) {
	squares[y+WALL_THICKNESS][x+WALL_THICKNESS] = squareType;
    }

    /**
     * this method is used every time the timer ticks.
     * it makes the game go forward
     */
    public void tick() {
	if (fallingPoly == null) {
	    //uses the factory class TetraminoMaker to create a new random falling poly.
	    fallingPoly = TetraminoMaker.getPoly(rnd.nextInt(TetraminoMaker.getNumberOfTypes()));
	    fallingX = fallingPoly.block.length/2 + width/2 - WALL_THICKNESS;
	    fallingY = 0;
	    if (collisionHandler.hasCollision(this)) {
		running = false;
		fallingY--;

		this.addHighscore();
		//prints all highscores
		System.out.println(HighscoreList.getINSTANCE());
	    }

	    //how often the poly will be a powerup
	    final int powerupPolyCount = 5;
	    if (powerupCounter > powerupPolyCount) {
		this.collisionHandler = powerups.get(rnd.nextInt(powerups.size()));
		powerupCounter = 0;
	    }
	    else {
		this.collisionHandler = new DefaultCollisionHandler();
		powerupCounter++;
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
     * adds the score to the highscore list
     */
    private void addHighscore() {
	String name = JOptionPane.showInputDialog("Please input your name");
	Highscore newHighscore = new Highscore(score, name);
	HighscoreList.getINSTANCE().addHighscore(newHighscore);
    }

    /**
     * resets the game
     */
    public void resetBoard() {
	for (int x=WALL_THICKNESS; x<width+WALL_THICKNESS; x++) {
	    for (int y=WALL_THICKNESS; y<height+WALL_THICKNESS; y++) {
		squares[y][x] = SquareType.EMPTY;
	    }
	}
	fallingPoly = null;
	score = 0;
	powerupCounter = 0;
	this.notifyListeners();
	running = true;
    }

    /**
     * adds the specified poly to the gameboard at specified x- and y-coord.
     * also checks if there are any full rows and in that case, removes them.
     * increases the score by how many rows was removed.
     * @param poly the poly that you want to add
     * @param blockX x-coord of the poly
     * @param blockY y-coord of the poly
     */
    public void addBlock(Poly poly, int blockX, int blockY) {
	int polyLength = poly.block[0].length;
	int polyHeight = poly.block.length;
	for (int polyX=0; polyX<polyLength; polyX++) {
	    for (int polyY=0; polyY<polyHeight; polyY++) {
		if (this.getFallingSquareTypeAt(polyX, polyY) != SquareType.EMPTY) {
		    this.setSquareTypeAt(polyX+blockX, polyY+blockY, this.getFallingSquareTypeAt(polyX, polyY));
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

    /**
     * moves the falling poly in the specified direction
     * @param direction Movetype direction
     */
    public void move(Move direction) {
	if (fallingPoly != null) {
	    fallingX += direction.getDeltaX();
	    fallingY += direction.getDeltaY();
	    if (collisionHandler.hasCollision(this)) {
		fallingX -= direction.getDeltaX();
		fallingY -= direction.getDeltaY();
	    }
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
     * @return the first y-coord found which has a full row
     * -1 if there is no full row
     */
    public int findFullRow() {
	int counter=0;
	for (int y = 0; y<height; y++) {
	    for (int x = 0; x < width ; x++) {
		if (this.getSquareTypeAt(x, y) != SquareType.EMPTY) {
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
	    for (int x = 0; x<width; x++) {
		this.setSquareTypeAt(x, y, this.getSquareTypeAt(x, y-1));
	    }
	}
	this.notifyListeners();

    }

    /**
     * sets the squaretype at specified x and y in the gameboard to EMPTY
     * @param x column in 2d array
     * @param y row in 2d array
     */
    public void removeBlockAt(int x, int y) {
	this.setSquareTypeAt(x, y, SquareType.EMPTY);
    }

    /**
     * increases score based on the specified number of rows removed
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

    /**
     * if there is an empty block somewhere in this specified x column, it pushes the block above it down one step.
     * @param x specifies the column in which it will happen
     * @param endY specifies the end y-coord of this push down,
     */
    public void pushDown(int x, int endY) {
	for (int y=height-1; y>endY; y--) {
	    if (this.getSquareTypeAt(x, y) == SquareType.EMPTY) {
		this.setSquareTypeAt(x, y, this.getSquareTypeAt(x, y-1));
		this.removeBlockAt(x, y-1);

	    }
	}

    }

    /**
     * checks if the specified x-coord can collapse
     * a collapse can only happen if there is atleast one empty block in the column
     * @param x column in 2d array
     * @param startY start value of collapse
     * @return true or false whether the column can collapse or not
     */
    public boolean columnCanCollapse(int x, int startY) {
	for (int y = startY; y<height; y++) {
	    if (this.getSquareTypeAt(x, y) == SquareType.EMPTY) {
		return true;
	    }
	}
	return false;
    }
}

