package Tetris;

public class Board
{
    private SquareType[][] squares;
    private int  width, height;

    public Board(final int width, final int height) {
	this.width = width;
	this.height = height;
	this.squares = new SquareType[height][width];



    }
}
