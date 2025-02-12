package app.boardMaker.maker;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import app.boardMaker.window.boardpanel.BoardPanel;
import app.utils.SVGUtil;
import bridge.Bridge;
import game.Game;
import game.equipment.Equipment;
import game.equipment.Item;
import game.equipment.container.board.Board;
import game.functions.graph.GraphFunction;
import game.functions.ints.count.simple.CountMovesThisTurn;
import game.players.Players;
import other.context.Context;
import other.trial.Trial;
import util.PlaneType;
import view.container.aspects.designs.BoardDesign;
import view.container.styles.BoardStyle;

/**
 * 
 * A class for handling all user actions
 * 
 */

public class Maker
{
	
	private GraphFunction gFct;
	private BoardPanel boardPanel;
	private Game game;
	
	public Maker() {	
	}
	
	public Game getGame() {
		return game;
	}
	
	public GraphFunction getGraphFunction() {
		return gFct;
	}
	
	public void setGraphFunction(GraphFunction gFct) {
		this.gFct = gFct;
		boardPanel.repaint();
	}
	
	public void setBoardPanel(BoardPanel bPanel) {
		this.boardPanel = bPanel;
	}
	
	public void drawBoard(Graphics2D g2d, int width, int height) {
		Board board = new Board(gFct, null, null, null, null, null, null);
		
		Game game = new Game("test", new Players(2), null, new Equipment(new Item[] {board}), null);
		game.create();
		game.setMetadata(null);
		this.game = game;
		
		Context context = new Context(game, new Trial(game));
		Bridge bridge = new Bridge();
		
		BoardStyle style = new BoardStyle(bridge, board);
		style.setPlacement(context, new Rectangle(0,0,width, height));
		style.render(PlaneType.BOARD, context);
		
		String svg = style.containerSVGImage();
		if (svg == null || svg.equals("")) {
			return;
		}
		
		g2d.drawImage(SVGUtil.createSVGImage(svg, width, height), 0, 0, null);
	}
}
