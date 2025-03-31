package app.boardMaker.maker;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JDialog;
import javax.swing.JFrame;

import annotations.Opt;
import app.boardMaker.dialogs.NGDialog;
import app.boardMaker.window.BoardMakerFrame;
import app.boardMaker.window.boardpanel.BoardPanel;
import app.utils.SVGUtil;
import bridge.Bridge;
import game.Game;
import game.equipment.Equipment;
import game.equipment.Item;
import game.equipment.container.board.Board;
import game.equipment.container.board.custom.MancalaBoard;
import game.functions.graph.GraphFunction;
import game.functions.graph.operators.Dual;
import game.functions.ints.count.simple.CountMovesThisTurn;
import game.mode.Mode;
import game.players.Players;
import game.types.play.ModeType;
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
	private BoardPanel boardPanel;
	private BoardMakerFrame frame;
	
	private GraphFunction gFct;
	private Game game;
	
	private String gameName;
	private Players players;
	private Mode gameMode;
	
	private MancalaBoard mancalaBoard;
	private boolean mancala = false;
	
	public Maker() {	
	}
	
	public void createNewGame(String name, int players, ModeType mode) {
		gameName = name;
		this.players = new Players(players);
		gameMode = new Mode(mode);
	}
	
	//----------------------------------------------------------------------
	
	public Game getGame() {
		return game;
	}
	
	public String getName() {
		return gameName;
	}
	
	public Players getPlayers() {
		return players;
	}
	
	public Mode getMode() {
		return gameMode;
	}
	
	public GraphFunction getGraphFunction() {
		return gFct;
	}
	
	public void setGraphFunction(GraphFunction gFct) {
		this.gFct = gFct;
		mancala = false;
		boardPanel.repaint();
	}
	
	public void setBoardPanel(BoardPanel bPanel) {
		this.boardPanel = bPanel;
	}
	
	public MancalaBoard getMancala() {
		return mancalaBoard;
	}
	
	public void setMancala(MancalaBoard board) {
		this.mancalaBoard = board;
		mancala = true;
		boardPanel.repaint();
	}
	
	public boolean isMancala() {
		return mancala;
	}
	
	public void setFrame(BoardMakerFrame frame) {
		this.frame = frame;
	}
	
	public BoardMakerFrame getFrame() {
		return frame;
	}
	
	//----------------------------------------------------------------------
	
	public void drawBoard(Graphics2D g2d, int width, int height) {
		Board board;
		if (mancala == true) {
			board = mancalaBoard;
		} else {
			board = new Board(gFct, null, null, null, null, null, null);
		}
		
		Game game = new Game(gameName, players, gameMode, new Equipment(new Item[] {board}), null);
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
	
	public void drawPreview(Graphics2D g2d, int width, int height, GraphFunction graph) {
		if (graph != null) {
			Board board = new Board(graph, null, null, null, null, null, null);
			
			Game game = new Game(gameName, players, gameMode, new Equipment(new Item[] {board}), null);
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
	
	//----------------------------------------------------------------------
	
	public void computeDual() {
		gFct = new Dual(gFct);
		boardPanel.repaint();
	}
}
