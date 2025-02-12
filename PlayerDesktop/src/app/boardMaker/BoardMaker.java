package app.boardMaker;

import javax.swing.SwingUtilities;

import app.DesktopApp;
import app.boardMaker.maker.Maker;
import app.boardMaker.window.BoardMakerFrame;
import app.boardMaker.window.BoardMakerPanel;

public class BoardMaker
{
	/** Main app. */
	public DesktopApp app;
	
	/** Main frame. */
	protected static BoardMakerFrame frame;
	
	/** Main window */
	protected static BoardMakerPanel view;
	
	public Maker maker;
	
	//-------------------------------------------------------------------------
	
	/** 
	 * Constructor. 
	 * */
	public BoardMaker(final DesktopApp app) {
		this.app = app;
		
		maker = new Maker();
	}
	
	//-------------------------------------------------------------------------
	
	/**
	 * Create main Board Maker window.
	 */
	public void createBoardMaker() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createFrame();
			}
		});
	}
	
	//-------------------------------------------------------------------------
	/**
	 * Create main frame
	 */
	public void createFrame() {
		frame = new BoardMakerFrame(maker);
		frame.requestFocus();
	}
}
