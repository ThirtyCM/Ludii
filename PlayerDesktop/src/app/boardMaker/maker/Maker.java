package app.boardMaker.maker;

import game.functions.graph.GraphFunction;

/**
 * 
 * A class for handling all user actions
 * 
 */

public class Maker
{
	
	private GraphFunction gFct;
	
	public Maker() {	
	}
	
	public GraphFunction getGraphFunction() {
		return gFct;
	}
	
	public void setGraphFunction(GraphFunction gFct) {
		this.gFct = gFct;
	}
}
