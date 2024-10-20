package app.boardMaker;

import app.PlayerApp;
import app.boardMaker.window.BoardMakerFrame;

public class BoardMaker
{
	/** Main app. */
	public static PlayerApp app;
	
	/** Main frame. */
	protected static BoardMakerFrame frame;
	
	//-------------------------------------------------------------------------
	
	/** 
	 * Constructor. 
	 * */
	public BoardMaker(final PlayerApp app) {
		BoardMaker.app = app;
	}
	
	//-------------------------------------------------------------------------
	
	/**
	 * Create main Board Maker window.
	 */
	public void createBoardMaker() {
		frame = new BoardMakerFrame();
	}
}
