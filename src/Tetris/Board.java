package Tetris;

import java.util.Random;

public class Board
{
    private SquareType[][] squares;
    private int  width, height, fallingX, fallingY;
    private Random rnd = new Random();
    private Poly fallingPoly = null;

    public Board(final int width, final int height) {
	this.width = width;
	this.height = height;
	this.squares = new SquareType[height][width];

	for (int x=0; x<width; x++) {
	    for (int y=0; y<height; y++) {
		squares[x][y] = SquareType.EMPTY;
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
	return squares[y][x];
    }

    public void randomiseBoard() {
	for (int x=0; x<width; x++) {
	    for (int y=0; y <height; y++) {
		squares[x][y] = SquareType.values()[rnd.nextInt(8)];
	    }

	}
    }
}
