package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BoardTest
{
    private BoardTest() {}

    public static void main(String[] args) {
	final Board board = new Board(10, 10);

	final TetrisFrame frame = new TetrisFrame("Tetris", board);
	board.addBoardListener(frame.tetrisComponent);

	//board.clearBoard();

	final Action doOneStep = new AbstractAction() {
	    public void actionPerformed(ActionEvent e) {
		board.tick();
	    }
	};

	final Timer clockTimer = new Timer(500, doOneStep);
	clockTimer.setCoalesce(true);
	clockTimer.start();
    }
}
