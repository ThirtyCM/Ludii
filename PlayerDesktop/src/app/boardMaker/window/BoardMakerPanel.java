package app.boardMaker.window;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import app.boardMaker.maker.Maker;
import app.boardMaker.window.TabbedPane.BoardMakerTabbedBar;
import app.boardMaker.window.boardpanel.BoardPanel;

/**
 * Main panel of the frame
 **/

public class BoardMakerPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	public BoardMakerPanel(Maker maker) {
		super(new BorderLayout());
		
		add(new BoardMakerTabbedBar(maker), BorderLayout.NORTH);
		add(new BoardPanel(maker), BorderLayout.CENTER);
		setOpaque(true);
	}
	
}
