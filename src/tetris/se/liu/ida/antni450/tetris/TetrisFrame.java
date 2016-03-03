package tetris.se.liu.ida.antni450.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * this class creates the tetris game screen
 */
public class TetrisFrame extends JFrame
{
    private final Board board;
    private final TetrisComponent tetrisComponent;

    public TetrisFrame(final String title, final Board board) throws HeadlessException {
	super(title);
	this.board = board;

	this.setLayout(new BorderLayout());

	//menubar additions
	final JMenuBar menubar = new JMenuBar();

	final JMenu menu = new JMenu("Menu");
	final JMenuItem help = new JMenuItem("Help");
	final JMenuItem reset = new JMenuItem("Reset");
	final JMenuItem exit = new JMenuItem("Exit");

	exit.addActionListener(new ExitListener());
	reset.addActionListener(new ResetListener());


	menu.add(help);
	menu.add(reset);
	menu.add(exit);
	menubar.add(menu);

	this.setJMenuBar(menubar);

	//gameboard
	tetrisComponent = new TetrisComponent(board);
	this.add(tetrisComponent, BorderLayout.CENTER);


	this.pack();

	this.setVisible(true);
    }

    private class ExitListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    if (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "", JOptionPane.YES_NO_OPTION) ==
		JOptionPane.YES_OPTION) {
		System.exit(0);
	    }

        }
    }

    private class ResetListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    board.resetBoard();
	}
    }



}


