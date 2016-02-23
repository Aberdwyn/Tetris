package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TetrisFrame_v1 extends JFrame
{
    Board board;
    JTextArea textarea;

    public TetrisFrame_v1(final String title, final Board board) throws HeadlessException {
	super(title);
	this.board = board;

	int width = board.getWidth();
	int height = board.getHeight();
	String text = BoardToTextConverter.convertToText(board);

	this.setLayout(new BorderLayout());

	//menu
	final JMenuBar menubar = new JMenuBar();

	final JMenu menu = new JMenu("Menu");
	final JMenuItem help = new JMenuItem("Help");
	final JMenuItem exit = new JMenuItem("Exit");

	exit.addActionListener(new ExitListener());


	menu.add(help);
	menu.add(exit);
	menubar.add(menu);

	this.setJMenuBar(menubar);

	//gameboard
	textarea = new JTextArea(height, width);
	final int FONT_SIZE = 20;
	textarea.setFont(new Font("Monospaced", Font.PLAIN, FONT_SIZE));
	textarea.setText(text);

	this.add(textarea, BorderLayout.CENTER);

	this.pack();

	this.setVisible(true);
    }

    public class ExitListener implements ActionListener
    {
	public void actionPerformed(ActionEvent e) {
	    if (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "", JOptionPane.YES_NO_OPTION) ==
		JOptionPane.YES_OPTION)
	    {
		System.exit(0);
	    }

        }
    }

}


