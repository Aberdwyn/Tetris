package tetris.se.liu.ida.antni450.tetris;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.EnumMap;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;

public class TetrisComponent extends JComponent implements BoardListener
{
    private Board board;
    final static int BLOCK_SIZE = 40;
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
	SQUARE_COLOR.put(SquareType.EMPTY, Color.WHITE);
    }

    public TetrisComponent(final Board board) {
	this.board = board;
	this.setKeyBinds();
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

		if (board.getFallingPoly() == null) {
		    g2d.setColor(SQUARE_COLOR.get(board.getSquareTypeAt(x, y)));
		    g2d.fillRect(x*BLOCK_SIZE, y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
		    continue;
		}

		int fallingPolyLength = board.getFallingPoly().block[0].length;
	    	int fallingPolyHeight = board.getFallingPoly().block.length;

		if ((board.getFallingX()<=x && x<board.getFallingX()+fallingPolyLength) &&
		    (board.getFallingY()<=y && y<board.getFallingY()+fallingPolyHeight)) {
		    //x-board.getFallingX() is needed to give the x-coordinate in the block
		    if (board.getFallingPoly().block[y-board.getFallingY()][x-board.getFallingX()] == SquareType.EMPTY) {
			g2d.setColor(SQUARE_COLOR.get(board.getSquareTypeAt(x, y)));
			g2d.fillRect(x*BLOCK_SIZE, y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
		    } else {
			g2d.setColor(SQUARE_COLOR.get(board.getFallingPoly().block[y-board.getFallingY()][x-board.getFallingX()]));
			g2d.fillRect(x*BLOCK_SIZE, y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
		    }
		}

		else {
		    g2d.setColor(SQUARE_COLOR.get(board.getSquareTypeAt(x, y)));
		    g2d.fillRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
		}
	    }
	}
	int leftAligned=0;
	int scoreSize = 40;
	g2d.setColor(Color.PINK);
	g2d.setFont(new Font(Font.MONOSPACED, Font.PLAIN, scoreSize));
	g2d.drawString(Integer.toString(board.getScore()), leftAligned, scoreSize);

	int margin = 10;

	int powerupSize = 20;
	String powerupText = board.getCollisionHandler().getDescription();
	g2d.setColor(Color.ORANGE);
	g2d.setFont(new Font(Font.MONOSPACED, Font.PLAIN, powerupSize));
	g2d.drawString(powerupText, leftAligned, powerupSize+scoreSize+margin);
    }

    private class LeftAction extends AbstractAction {
	@Override public void actionPerformed(final ActionEvent e) {
	    board.moveLeft();
	}
    }

    private class RightAction extends AbstractAction {
    	@Override public void actionPerformed(final ActionEvent e) {
    	    board.moveRight();
    	}
    }

    private class DownAction extends AbstractAction {
	@Override public void actionPerformed(final ActionEvent e) {
        	    board.moveDown();
        	}
    }

    private class RotateAction extends AbstractAction {
	@Override public void actionPerformed(final ActionEvent e) {
	    board.rotate();
	}
    }



    public void setKeyBinds() {
	InputMap keybinds = getInputMap(WHEN_IN_FOCUSED_WINDOW);
	ActionMap actions = getActionMap();

    	keybinds.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
	actions.put("moveLeft", new LeftAction());

	keybinds.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
	actions.put("moveRight", new RightAction());

	keybinds.put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
	actions.put("moveDown", new DownAction());

	keybinds.put(KeyStroke.getKeyStroke("UP"), "rotate");
	actions.put("rotate", new RotateAction());
        }

    public void boardChanged() {
	this.repaint();
    }

}