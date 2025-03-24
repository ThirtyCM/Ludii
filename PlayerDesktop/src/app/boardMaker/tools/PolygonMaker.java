package app.boardMaker.tools;

import java.util.List;

import javax.swing.JPanel;

import app.boardMaker.panels.ParameterPanel;
import app.boardMaker.previews.PreviewPanel;

public class PolygonMaker extends JPanel
{
	
	private PolygonView pv;
	private PolygonListener pl;
	
	public PolygonMaker(ParameterPanel params, PreviewPanel pp) {
		this.pv = new PolygonView(300, 300, 10);
		this.pl = new PolygonListener(pv, params, pp);
		pv.addMouseListener(pl);
		add(pv);
	}
	
	public List<Coordinates> getPoly() {
		return pv.getPoly();
	}
}
