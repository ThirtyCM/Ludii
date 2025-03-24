package app.boardMaker.tools;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import app.boardMaker.panels.ParameterPanel;
import app.boardMaker.previews.PreviewPanel;
import game.functions.ints.IntConstant;

public class PolygonListener implements MouseListener
{
	
	private PolygonView grid;
	private ParameterPanel params;
	private PreviewPanel preview;
	
	public PolygonListener(PolygonView view, ParameterPanel params, PreviewPanel preview) {
		this.grid = view;
		this.params = params;
		this.preview = preview;
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
				addy = grid.getAnchorY().size() - 1 - i;  //java coordinates are top to bottom while Ludii's are bottom to top
			}
		}
		
		if (addx != -1 && addy != -1) {
			grid.addCorner(new Coordinates(addx, addy));
			grid.repaint();
			params.createBoard();
			preview.repaint();
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
