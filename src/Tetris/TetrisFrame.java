package Tetris;

import javax.swing.*;
import java.awt.*;

public class TetrisFrame extends JFrame
{
    Board board;
    JTextArea textarea;

    public TetrisFrame(final String title, final Board board) throws HeadlessException {
	super(title);
	this.board = board;

	int width = board.getWidth();
	int height = board.getHeight();
	String text = BoardToTextConverter.convertToText(board);

	textarea = new JTextArea(height, width);
	final int FONT_SIZE = 20;
	textarea.setFont(new Font("Monospaced", Font.PLAIN, FONT_SIZE));
	textarea.setText(text);

	this.setLayout(new BorderLayout());

	this.add(textarea, BorderLayout.CENTER);

	this.pack();

	this.setVisible(true);
    }
}


