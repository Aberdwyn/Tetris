package tetris.se.liu.ida.antni450.tetris;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.EnumMap;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;

/**
 * this class creates the graphis component to show tetris by extebdubg Jcomponent and implements the BoardListener interface
 */
public class TetrisComponent extends JComponent implements BoardListener
{
    private Board board;
    final static int BLOCK_SIZE = 40;
    /**
     * this EnumMap sets a SQUARE_COLOR to each SquareType
     */
    //no this is what I want
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

    /**
     * @return a dimension object that consists of the preffered width and height of the tetris component in pixels
     */
    @Override public Dimension getPreferredSize() {
	final int width = board.getWidth() * BLOCK_SIZE;
	final int height = board.getHeight() * BLOCK_SIZE;

	return new Dimension(width, height);
    }

    /**
     * paints everything that should be visible on the board
     * @param g a graphics object
     */
    @Override protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;

	//paints the blocks.
	for (int x = 0; x < board.getWidth(); x++) {
	    for (int y = 0; y < board.getHeight(); y++) {

		if (board.getFallingPoly() == null) {
		    g2d.setColor(SQUARE_COLOR.get(board.getSquareTypeAt(x, y)));
		    g2d.fillRect(x*BLOCK_SIZE, y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
		    continue;
		}

		int fallingPolyLength = board.getFallingPoly().block[0].length;
	    	int fallingPolyHeight = board.getFallingPoly().block.length;

		//if there is a falling Poly on x and y, paint that instead
		if ((board.getFallingX()<=x && x<board.getFallingX()+fallingPolyLength) &&
		    (board.getFallingY()<=y && y<board.getFallingY()+fallingPolyHeight)) {

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
	//writes the score on the screen
	final int leftAligned=0;
	final int scoreSize = 40;
	g2d.setColor(Color.PINK);
	g2d.setFont(new Font(Font.MONOSPACED, Font.PLAIN, scoreSize));
	g2d.drawString(Integer.toString(board.getScore()), leftAligned, scoreSize);

	final int margin = 10;

	//writes the description of the current powerup
	final int powerupSize = 20;
	String powerupText = board.getCollisionHandler().getDescription();
	g2d.setColor(Color.ORANGE);
	g2d.setFont(new Font(Font.MONOSPACED, Font.PLAIN, powerupSize));
	g2d.drawString(powerupText, leftAligned, powerupSize+scoreSize+margin);
    }

    /**
     * an action class that moves the falling poly one step to the left
     */
    private class LeftAction extends AbstractAction {
	@Override public void actionPerformed(final ActionEvent e) {
	    board.move(Move.LEFT);
	}
    }

    /**
     * an action class that moves the falling poly one step to the right
     */
    private class RightAction extends AbstractAction {
    	@Override public void actionPerformed(final ActionEvent e) {

	    board.move(Move.RIGHT);
    	}
    }

    /**
     * an action class that moves the falling poly one step down
     */
    private class DownAction extends AbstractAction {
	@Override public void actionPerformed(final ActionEvent e) {
        	    board.move(Move.DOWN);
        	}
    }

    /**
     * an action class that rotates the falling poly 90 degrees clockwise
     */
    private class RotateAction extends AbstractAction {
	@Override public void actionPerformed(final ActionEvent e) {
	    board.rotate();
	}
    }

    /**
     * this method sets the arrow keys to their corresponding actions
     */
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

    /**
     * this method repaints everything on the board
     */
    public void boardChanged() {
	this.repaint();
    }

}