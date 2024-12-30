package app.boardMaker.window.boardpanel;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import app.boardMaker.maker.Maker;

public class BoardPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private Maker maker;
	
	public BoardPanel(Maker maker) {
		super(new BorderLayout());
		
		this.maker = maker;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		if (maker.getGraphFunction() != null) {
			
		}
	}
	
	


}
