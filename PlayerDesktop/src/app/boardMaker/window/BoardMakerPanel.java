package app.boardMaker.window;

import java.awt.BorderLayout;

import javax.swing.JPanel;

/**
 * Main panel of the frame
 **/

public class BoardMakerPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	public BoardMakerPanel() {
		super(new BorderLayout());
		
		add(new BoardMakerTabbedBar(), BorderLayout.NORTH);
		setOpaque(true);
	}
}
