package app.boardMaker.tools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import game.functions.ints.IntConstant;

public class PolygonView extends JLabel {
	
	private int width;
	private int height;
	private int n; // Number of dots on the grid
	private int dotSize = 10;
	
	private int incX;
	private int incY;
	
	private List<Integer> anchorX;
	private List<Integer> anchorY;
	private List<Coordinates> poly;
	
	private Coordinates current;
	
	public PolygonView(int width, int height, int n) {
		setBackground(Color.white);
		setOpaque(true);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setPreferredSize(new Dimension(width, height));
		
		this.width = width;
		this.height = height;
		this.n = n;
		
		incX = width/n;
		incY = height/n;
		
		anchorX = new ArrayList<Integer>();
		anchorY = new ArrayList<Integer>();
		poly = new ArrayList<Coordinates>();
		
		for (int x = 0; x < this.width; x += incX) {
			anchorX.add(x + incX/2 - dotSize/2);
		}
		for (int y = 0; y < this.height; y += incY) {
			anchorY.add(y + incY/2 - dotSize/2);
		}
	}
	
	public List<Integer> getAnchorX()
	{
		return anchorX;
	}
	
	public List<Integer> getAnchorY()
	{
		return anchorY;
	}
	
	public List<Coordinates> getPoly() {
		return poly;
	}
	
	public void addCorner(Coordinates coordinates) {
		if (!poly.contains(coordinates)) {
			poly.add(coordinates);
			current = coordinates;
		}
	}
	
	public int getDotSize() {
		return dotSize;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.lightGray);
		
		for (int x = 0; x < anchorX.size(); x++) {
			for (int y = 0; y < anchorY.size(); y++) {
				g2d.fillOval(anchorX.get(x), anchorY.get(y), dotSize, dotSize);
			}
		}
		
		for (Coordinates p : poly) {	
			if (p.equals(current)) {
				g2d.setColor(Color.red);
			} else {
				g2d.setColor(Color.black);
			}
			g2d.fillOval(anchorX.get(p.getX()), anchorY.get(anchorY.size() - 1 - p.getY()), dotSize, dotSize);
		}
	}
	
	
}
