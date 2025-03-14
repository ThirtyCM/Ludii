package app.boardMaker.panels;

import java.awt.LayoutManager;

import javax.swing.JPanel;

import game.functions.graph.GraphFunction;

public abstract class ParameterPanel extends JPanel
{
	public ParameterPanel() {
		super();
	}
	
	public ParameterPanel(LayoutManager lm) {
		super(lm);
	}
	
	public abstract void createBoard();
	
	public abstract GraphFunction getGraph();
}
