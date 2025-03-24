package app.boardMaker.previews;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

import app.boardMaker.maker.Maker;
import app.utils.SVGUtil;
import bridge.Bridge;
import game.Game;
import game.equipment.Equipment;
import game.equipment.Item;
import game.equipment.container.board.Board;
import game.players.Players;
import other.context.Context;
import other.trial.Trial;
import util.PlaneType;
import view.container.styles.BoardStyle;

public class MancalaPreview extends JPanel
{
	private Double boardratio = 1.0;
	private Maker maker;
	private String svg;
	
	public Game game;
	
	public MancalaPreview(Maker maker) {
		this.maker = maker;
		
		setPreferredSize(new Dimension(400, 500));
	}
	
	public void render(Board board) {
		Game game = new Game(maker.getName(), maker.getPlayers(), maker.getMode(), new Equipment(new Item[] {board}), null);
		game.create();
		game.setMetadata(null);
		this.game = game;
		
		Context context = new Context(game, new Trial(game));
		Bridge bridge = new Bridge();
				
		BoardStyle style = new BoardStyle(bridge, board);
		int boardsize = Math.min(this.getHeight(), (int) (this.getWidth() * boardratio));
		style.setPlacement(context, new Rectangle(0,0,boardsize, boardsize));
		style.render(PlaneType.BOARD, context);
		
		String svg = style.containerSVGImage();
		if (svg == null || svg.equals("")) {
			return;
		}
		this.svg = svg;
		
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		// TODO Auto-generated method stub
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		if (svg != null) {
			int boardsize = Math.min(this.getHeight(), (int) (this.getWidth() * boardratio));
			g2d.drawImage(SVGUtil.createSVGImage(svg, boardsize, boardsize), 0, 0, null);
		}
	}
	
}
