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
import game.types.board.SiteType;
import graphics.svg.SVG;
import other.context.Context;
import util.PlaneType;
import view.container.styles.BoardStyle;

public class BoardPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private Maker maker;
	
	public BoardPanel(Maker maker) {
		super(new BorderLayout());
		
		this.maker = maker;
		maker.setBoardPanel(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		if (maker.getGraphFunction() != null) {
			maker.drawBoard(g2d,this.getWidth(), this.getHeight());
		}
	}
	
	


}
