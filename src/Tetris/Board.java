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

    public void randomiseBoard() {
	for (int x=0; x<width; x++) {
	    for (int y=0; y <height; y++) {
		squares[x][y] = SquareType.values()[rnd.nextInt(TetraminoMaker.getNumberOfTypes())];
	    }
	}
	this.notifyListeners();
    }

    public void clearBoard() {
	for (int x=0; x<width; x++) {
	    for (int y=0; y <height; y++) {
		squares[x][y] = SquareType.EMPTY;
	    }
	}
	this.notifyListeners();
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
		//fundera på detta här
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
	for (int boardX=0; boardX<width; boardX++) {
	    for (int boardY=0; boardY<height; boardY++) {
		if (blockX == boardX && blockY == boardY) {

		    for (int polyX=0; polyX<polyLength; polyX++) {
			for (int polyY=0; polyY<polyHeight; polyY++) {
			    if (poly.block[polyY][polyX] != SquareType.EMPTY) {
				squares[polyY+boardY][polyX+boardX+WALL_THICKNESS] = poly.block[polyY][polyX];
			    }
			}
		    }
		}
	    }
	}
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
}

