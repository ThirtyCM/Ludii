package app.boardMaker.window.boardpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import app.PlayerApp;
import app.boardMaker.maker.Maker;
import app.boardMaker.window.TabbedPane.BoardTiling;
import app.utils.SVGUtil;
import bridge.Bridge;
import game.equipment.container.board.Board;
import game.functions.dim.math.Min;
import game.types.board.SiteType;
import graphics.svg.SVG;
import other.context.Context;
import util.PlaneType;
import view.container.styles.BoardStyle;

public class BoardPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private Maker maker;
	
	private double boardRatio;
	
	public BoardPanel(Maker maker) {
		super(new BorderLayout());
		
		this.maker = maker;
		this.boardRatio = 1.0;
		maker.setBoardPanel(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		if (maker.getGraphFunction() != null) {
			int boardSize = Math.min(this.getHeight(), (int)(this.getWidth() * boardRatio));
			maker.drawBoard(g2d,boardSize, boardSize);
		}
	}
	
	


}
