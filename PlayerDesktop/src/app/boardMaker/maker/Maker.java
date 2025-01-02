package app.boardMaker.maker;

import app.boardMaker.window.boardpanel.BoardPanel;
import game.functions.graph.GraphFunction;

/**
 * 
 * A class for handling all user actions
 * 
 */

public class Maker
{
	
	private GraphFunction gFct;
	private BoardPanel boardPanel;
	
	public Maker() {	
	}
	
	public GraphFunction getGraphFunction() {
		return gFct;
	}
	
	public void setGraphFunction(GraphFunction gFct) {
		this.gFct = gFct;
		boardPanel.repaint();
	}
	
	public void setBoardPanel(BoardPanel bPanel) {
		this.boardPanel = bPanel;
	}
}
