package Tetris;

public class BoardTest
{
    public static void main(String[] args) {
	Board board = new Board(10, 10);

	int n=0;
	while (n < 10) {
	    board.randomiseBoard();
	    n++;
	    System.out.println(BoardToTextConverter.convertToText(board));
	}
    }
}
