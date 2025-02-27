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
	private List<Pair<Integer,Integer>> poly;
	
	public PolygonView() {
		setBackground(Color.white);
		setOpaque(true);
		setBorder(BorderFactory.createLineBorder(Color.black));
		
		this.width = getWidth();
		this.height = getHeight();
	}
	
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
		poly = new ArrayList<Pair<Integer,Integer>>();
		
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
	
	public void addCorner(Pair<Integer,Integer> coordinates) {
		poly.add(coordinates);
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
		
		g2d.setColor(Color.black);
		for (Pair<Integer,Integer> p : poly) {
			g2d.fillOval(anchorX.get(p.getFirst()), anchorY.get(p.getSecond()), dotSize, dotSize);
		}
	}
	
	
}
