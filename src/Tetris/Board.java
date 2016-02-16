package Tetris;

import java.util.Random;

public class Board
{
    private SquareType[][] squares;
    private int  width, height, x, y;
    private Random rnd = new Random();
    private Poly polyFalling = null;

    public Board(final int width, final int height) {
	this.width = width;
	this.height = height;
	this.squares = new SquareType[height][width];

	for (int x=0; x<width-1; x++) {
	    for (int y=0; y<height-1; y++) {
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

    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }

    public Poly getPolyFalling() {
	return polyFalling;
    }

    public SquareType getSquareType(int x, int y) {
	return squares[x][y];
    }

    public static void main(String[] args) {
	Board board = new Board(10, 10);
	System.out.println(board);
    }

    public void randomiseBoard() {
	for (int x=0; x<width-1; x++) {
	    for (int y=0; y <height-1; y++) {
		squares[x][y] = SquareType.values()[rnd.nextInt(8)];
	    }

	}
    }
}
