package tetris.se.liu.ida.antni450.tetris;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * this is the tetris test class, it runs tetris
 */
public final class BoardTest
{
    private BoardTest() {}

    public static void main(String[] args) {
	final int boardWidth = 10;
	final int boardHeight = 20;
	final Board board = new Board(boardWidth, boardHeight);

	final TetrisFrame frame = new TetrisFrame("tetris", board);
	//adds the tetriscomponent in frame as a listener to board
	board.addBoardListener(frame.tetrisComponent);

	/**
	 * this AbstactAction will tick the board forward one step if the game is running
	 */
	final Action doOneStep = new AbstractAction() {
	    public void actionPerformed(ActionEvent e) {
		if (board.isRunning()) {
		    board.tick();
		}
	    }
	};

	final int tickTime = 500;
	//will call doOneStep every time the time reaches tickTime, ticktime is measure in ms
	final Timer clockTimer = new Timer(tickTime, doOneStep);

	clockTimer.setCoalesce(true);
	clockTimer.start();
    }
}
