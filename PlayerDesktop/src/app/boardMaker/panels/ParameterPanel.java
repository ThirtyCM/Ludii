package app.boardMaker.panels;

import java.awt.LayoutManager;
import java.util.List;

import javax.swing.JPanel;

import app.boardMaker.tools.Coordinates;
import app.boardMaker.tools.PolygonMaker;
import game.functions.graph.GraphFunction;
import game.util.graph.Poly;

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
	
	public Poly makePoly(List<Coordinates> polygon) {
		Float[][] pts = new Float[polygon.size()][2];
			
		for (int i = 0; i < polygon.size(); i++) {
			pts[i][0] = (float) polygon.get(i).getX();
			pts[i][1] = (float) polygon.get(i).getY();
		}
			
		return new Poly(pts, null);
	}
}
