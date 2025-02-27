package app.boardMaker.tools;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import game.functions.ints.IntConstant;

public class PolygonListener implements MouseListener
{
	
	private PolygonView grid;
	
	public PolygonListener(PolygonView view) {
		this.grid = view;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		
		int addx = -1;
		int addy = -1;
		for (int i = 0; i < grid.getAnchorX().size(); i++) {
			int distance = (x - grid.getAnchorX().get(i));
			if (distance > 0 && distance < grid.getDotSize()) {
				addx = i;
			}
		}
		for (int i = 0; i < grid.getAnchorY().size(); i++) {
			int distance = y - grid.getAnchorY().get(i);
			if (distance > 0 && distance < grid.getDotSize()) {
				addy = i;
			}
		}
		
		if (addx != -1 && addy != -1) {
			grid.addCorner(new Pair<Integer,Integer>(addx, addy));
			grid.repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}
}
