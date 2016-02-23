package Tetris;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Color;
import java.util.EnumMap;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class TetrisComponent extends JComponent
{
    private Board board;
    final int BLOCK_SIZE = 40;
    final static EnumMap<SquareType,Color> SQUARE_COLOR;
    static {
	SQUARE_COLOR = new EnumMap<>(SquareType.class);
	SQUARE_COLOR.put(SquareType.I, Color.GREEN);
	SQUARE_COLOR.put(SquareType.O, Color.ORANGE);
	SQUARE_COLOR.put(SquareType.T, Color.YELLOW);
	SQUARE_COLOR.put(SquareType.S, Color.CYAN);
	SQUARE_COLOR.put(SquareType.Z, Color.RED);
	SQUARE_COLOR.put(SquareType.J, Color.BLUE);
	SQUARE_COLOR.put(SquareType.L, Color.MAGENTA);
    }

    public TetrisComponent(final Board board) {
	this.board = board;
    }

    @Override public Dimension getPreferredSize() {
	final int width = board.getWidth() * BLOCK_SIZE;
	final int height = board.getHeight() * BLOCK_SIZE;

	return new Dimension(width, height);
    }

    @Override protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;

	for (int x = 0; x < board.getWidth(); x++) {
	    for (int y = 0; y < board.getHeight(); y++) {
		g2d.setColor(SQUARE_COLOR.get(board.getSquareType(x, y)));
		g2d.fillRect(x*BLOCK_SIZE, y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
	    }
	}
    }

}