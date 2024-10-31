package app.boardMaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.DesktopApp;
import app.boardMaker.menu.BoardMakerMenuFunctions;
import app.boardMaker.window.BoardMakerFrame;

public class BoardMaker implements ActionListener
{
	/** Main app. */
	public static DesktopApp app;
	
	/** Main frame. */
	protected static BoardMakerFrame frame;
	
	//-------------------------------------------------------------------------
	
	/** 
	 * Constructor. 
	 * */
	public BoardMaker(final DesktopApp app) {
		BoardMaker.app = app;
	}
	
	//-------------------------------------------------------------------------
	
	/**
	 * Create main Board Maker window.
	 */
	public void createBoardMaker() {
		frame = new BoardMakerFrame(this);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		BoardMakerMenuFunctions.checkActionPerformed(app,e);
	}
}
