package app.boardMaker;

import app.DesktopApp;
import app.boardMaker.window.BoardMakerFrame;
import app.boardMaker.window.BoardMakerPanel;

public class BoardMaker
{
	/** Main app. */
	public static DesktopApp app;
	
	/** Main frame. */
	protected static BoardMakerFrame frame;
	
	/** Main window */
	protected static BoardMakerPanel view;
	
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
		frame.requestFocus();
	}
}
