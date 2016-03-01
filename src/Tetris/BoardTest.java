package Tetris;

import javax.swing.*;
import java.awt.event.ActionEvent;

public final class BoardTest
{
    private BoardTest() {}

    public static void main(String[] args) {
	final Board board = new Board(10, 20);

	final TetrisFrame frame = new TetrisFrame("Tetris", board);
	board.addBoardListener(frame.tetrisComponent);

	final Action doOneStep = new AbstractAction() {
	    public void actionPerformed(ActionEvent e) {
		if (board.isRunning()) {
		    board.tick();
		}
	    }
	};

	final Timer clockTimer = new Timer(500, doOneStep);
	clockTimer.setCoalesce(true);
	clockTimer.start();
    }
}
