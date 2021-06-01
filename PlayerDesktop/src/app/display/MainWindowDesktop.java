package app.display;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import app.PlayerApp;
import app.display.dialogs.SettingsDialog;
import app.display.dialogs.MoveDialog.SandboxDialog;
import app.display.util.DevTooltip;
import app.display.util.Spinner;
import app.display.util.ZoomBox;
import app.display.views.OverlayView;
import app.display.views.tabs.TabView;
import app.loading.FileLoading;
import app.move.MouseHandler;
import app.move.MoveHandler;
import app.utils.GUIUtil;
import app.utils.sandbox.SandboxValueType;
import app.views.BoardView;
import app.views.View;
import app.views.players.PlayerView;
import app.views.tools.ToolView;
import game.equipment.container.Container;
import game.rules.play.moves.Moves;
import main.Constants;
import other.context.Context;
import other.location.Location;
import other.move.Move;
import other.topology.Cell;
import other.topology.Edge;
import other.topology.Vertex;
import util.LocationUtil;

//-----------------------------------------------------------------------------

/**
 * Main Window for displaying the application
 *
 * @author Matthew.Stephenson and cambolbro and Eric.Piette 
 */
public final class MainWindowDesktop extends JPanel implements MouseListener, MouseMotionListener
{
	private final PlayerApp app;
	
	private static final long serialVersionUID = 1L;

	/** List of view panels, including all sub-panels of the main five. */
	protected List<View> panels = new CopyOnWriteArrayList<>();
	
	// The main five views that are always present in the window.
	/** View that covers the entire window. */
	protected OverlayView overlayPanel;
	/** View that covers the left half of the window, where the board is drawn. */
	protected BoardView boardPanel;
	/** View that covers the top right area of the window, where the player views are drawn. */
	protected PlayerView playerPanel;
	/** View that covers the bottom right area of the window, where the tool buttons are drawn. */
	protected ToolView toolPanel;
	/** View that covers the middle right area of the window, where the tab pages are drawn. */
	protected TabView tabPanel;

	/** Width of the MainView. */
	protected int width;
	/** Height of the MainView. */
	protected int height;

	/** array of bounding rectangles for each player's swatch. */
	public Rectangle[] playerSwatchList = new Rectangle[Constants.MAX_PLAYERS+1];
	/** array of bounding rectangles for each player's name. */
	public Rectangle[] playerNameList = new Rectangle[Constants.MAX_PLAYERS+1];
	
	/** array of booleans for representing if the mouse cursor is over any player's swatch. */
	public boolean[] playerSwatchHover = new boolean[Constants.MAX_PLAYERS+1];
	/** array of booleans for representing if the mouse cursor is over any player's name. */
	public boolean[] playerNameHover = new boolean[Constants.MAX_PLAYERS+1];

	public static final int MIN_UI_FONT_SIZE =  12;
	public static final int MAX_UI_FONT_SIZE = 24;
	
	/** Temporary message to be printed at the bottom of the Window. */
	private String temporaryMessage = "";
	static String volatileMessage = "";
	
	/** Store a spinner for each player, to represent if an AI is thinking about a move for it. */
	public Spinner[] spinners = new Spinner[Constants.MAX_PLAYERS+1];
	
	/** ZoomBox (magnifying glass) pane. */
	public ZoomBox zoomBox;

	//-------------------------------------------------------------------------

	/**
	 * Constructor.
	 */
	public MainWindowDesktop(final PlayerApp app)
	{
		addMouseListener(this);
		addMouseMotionListener(this);
		this.app = app;
		zoomBox = new ZoomBox(app, this);
	}

	//-------------------------------------------------------------------------

	/**
	 * Create UI panels.
	 */
	public void createPanels()
	{
		panels.clear();
		removeAll();
		app.graphicsCache().clearAllCachedImages();
		spinners = new Spinner[Constants.MAX_PLAYERS+1];
		
		// Create board panel
		boardPanel = new BoardView(app);
		panels.add(boardPanel);
		
		// create the player panel
		playerPanel = new PlayerView(app);
		panels.add(playerPanel);

		// Create tool panel
		toolPanel = new ToolView(app);
		panels.add(toolPanel);

		// Create tab panel
		tabPanel = new TabView(app);
		panels.add(tabPanel);
		
		// Create overlay panel
		overlayPanel = new OverlayView(app);
		panels.add(overlayPanel());
	}

	//-------------------------------------------------------------------------

	@Override
	public void paintComponent(final Graphics g)
	{		
		try
		{			
			final Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			
			if (!app.bridge().settingsVC().thisFrameIsAnimated())
					app.contextSnapshot().setContext(app);
			
			setDisplayFont(app);
			app.graphicsCache().drawnImageInfo().clear();
			
			if (panels.isEmpty() || width != getWidth() || height != getHeight())
			{
				width = getWidth();
				height = getHeight();
				
				// Need to reset the tabs if the resolution of the app has changed.
				EventQueue.invokeLater(() -> 
				{
					createPanels();
					tabPanel().resetTabs();
				});
			}

			g2d.setColor(Color.white);
			g2d.fillRect(0, 0, getWidth(), getHeight());

			for (final View panel : panels)
				if (g.getClipBounds().intersects(panel.placement()))
					panel.paint(g2d);
			
			if (app.bridge().settingsVC().errorReport() != "")
			{
				app.addTextToStatusPanel(app.bridge().settingsVC().errorReport());
				app.bridge().settingsVC().setErrorReport("");
			}
			
			final metadata.graphics.Graphics graphics = app.contextSnapshot().getContext(app).game().metadata().graphics();
			if (graphics.getErrorReport() != "")
			{
				app.addTextToStatusPanel(graphics.getErrorReport());
				graphics.setErrorReport("");
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			
			EventQueue.invokeLater(() -> 
			{
				setTemporaryMessage("Error painting components.");
				FileLoading.writeErrorFile("error_report.txt", e);
			});
		}
	}

	//-------------------------------------------------------------------------

	/**
	 * Check if the point pressed overlaps any "buttons"
	 * (e.g. pass or swap buttons, player swatches or names, tool/tab views)
	 * If activateButton is True, then the overlapped button is also pressed.
	 */
	private boolean checkPointOverlapsButton(final MouseEvent e, final boolean pressButton)
	{
		// Need to check if the legal moves contains a player select move.
		boolean playerSelectMoveLegal = false;
		final Context context = app.contextSnapshot().getContext(app);
		final Moves legal = context.moves(context);
		for (final Move m : legal.moves())
		{
			if (m.playerSelected() != Constants.UNDEFINED)
			{
				playerSelectMoveLegal = true;
				break;
			}
		}
		
		if (GUIUtil.pointOverlapsRectangles(e.getPoint(), playerSwatchList))
		{
			if (playerSelectMoveLegal)
			{
				for (int i = 0; i < playerSwatchList.length; i++)
				{
					final Rectangle r = playerSwatchList[i];
					if (GUIUtil.pointOverlapsRectangle(e.getPoint(), r))
						MoveHandler.playerSelectMove(app, i);
				}
			}
			else if (pressButton)
			{
				SettingsDialog.createAndShowGUI(app);
			}
			return true;
		}
		else if (GUIUtil.pointOverlapsRectangles(e.getPoint(), playerNameList))
		{
			if (pressButton)
				SettingsDialog.createAndShowGUI(app);
			return true;
		}
		else if (tabPanel.placement().contains(e.getPoint()))
		{
			if (pressButton)
				tabPanel.clickAt(e.getPoint());
			return true;
		}
		else if (toolPanel.placement().contains(e.getPoint()))
		{
			if (pressButton)
				toolPanel.clickAt(e.getPoint());
			return true;
		}
//		else if (app.settingsPlayer().sandboxMode())
//		{
//			if (pressButton)
//			{
//				final Location location = LocationUtil.calculateNearestLocation(context, app.bridge(), e.getPoint(), LocationUtil.getAllLocations(context, app.bridge()));
//				SandboxDialog.createAndShowGUI(app, location, SandboxValueType.Component);
//			}
//			return true;
//		}
		return false;
	}

	//-------------------------------------------------------------------------

	@Override
	public void mousePressed(final MouseEvent e)
	{
		if (!checkPointOverlapsButton(e, false))
			MouseHandler.mousePressedCode(app, e.getPoint());
	}
	
	//-------------------------------------------------------------------------

	@Override
	public void mouseReleased(final MouseEvent e)
	{		
		// Important that this is delayed slightly, to take place after the mouseClicked function.
		EventQueue.invokeLater(() -> 
		{
			if (!checkPointOverlapsButton(e, false))
				MouseHandler.mouseReleasedCode(app, e.getPoint());
		});
	}
	
	//-------------------------------------------------------------------------
	
	@Override
	public void mouseClicked(final MouseEvent e)
	{
		checkPointOverlapsButton(e, true);
		
		if (app.settingsPlayer().sandboxMode())
		{
			final Context context = app.contextSnapshot().getContext(app);
			final Location location = LocationUtil.calculateNearestLocation(context, app.bridge(), e.getPoint(), LocationUtil.getAllLocations(context, app.bridge()));
			SandboxDialog.createAndShowGUI(app, location, SandboxValueType.Component);
		}
		
		MouseHandler.mouseClickedCode(app, e.getPoint());
	}
	
	//-------------------------------------------------------------------------
	
	@Override
	public void mouseDragged(final MouseEvent e)
	{
		MouseHandler.mouseDraggedCode(app, e.getPoint());
	}

	//-------------------------------------------------------------------------

	@Override
	public void mouseMoved(final MouseEvent e)
	{
		try
		{
			for (final View view : panels)
				view.mouseOverAt(e.getPoint());

			DevTooltip.displayToolTipMessage(app, e.getPoint());
		}
		catch (final Exception exception)
		{
			// your mouse pointer was outside the app!
		}
	}
	
	//-------------------------------------------------------------------------
	
	@Override
	public void mouseEntered(final MouseEvent e)
	{
		app.repaint();
	}
	
	//-------------------------------------------------------------------------

	@Override
	public void mouseExited(final MouseEvent e)
	{
		app.repaint();
	}
	
	//-------------------------------------------------------------------------
	
	/**
	 * Set the display font based on the size of the graph elements across all containers.
	 */
	protected static void setDisplayFont(final PlayerApp app)
	{
		int maxDisplayNumber = 0;
		int minCellSize = 9999999;
		int maxCoordDigitLength = 0;
		
		for (final Container container : app.contextSnapshot().getContext(app).equipment().containers())
		{
			final int maxVertices = container.topology().cells().size();
			final int maxEdges = container.topology().edges().size();
			final int maxFaces = container.topology().vertices().size();
			maxDisplayNumber = Math.max(maxDisplayNumber, Math.max(maxVertices, Math.max(maxEdges, maxFaces)));
			
			minCellSize = Math.min(minCellSize, app.bridge().getContainerStyle(container.index()).cellRadiusPixels());
			
			for (final Vertex vertex : container.topology().vertices())
				if (vertex.label().length() > maxCoordDigitLength)
					maxCoordDigitLength = vertex.label().length();
			
			for (final Edge edge : container.topology().edges())
				if (edge.label().length() > maxCoordDigitLength)
					maxCoordDigitLength = edge.label().length();
				
			for (final Cell cell : container.topology().cells())
				if (cell.label().length() > maxCoordDigitLength)
					maxCoordDigitLength = cell.label().length();
		}
		
		final int maxStringLength = Math.max(maxCoordDigitLength, Integer.toString(maxDisplayNumber).length());
		
		int fontSize = (int)(minCellSize * (1.0 - maxStringLength * 0.1));
		
		if (fontSize < MIN_UI_FONT_SIZE)
			fontSize = MIN_UI_FONT_SIZE;
		else if (fontSize > MAX_UI_FONT_SIZE)
			fontSize = MAX_UI_FONT_SIZE;
		
		app.bridge().settingsVC().setDisplayFont(new Font("Arial", Font.BOLD, fontSize));
	}

	//-------------------------------------------------------------------------
	
	public BoardView getBoardPanel()
	{
		return boardPanel;
	}
	
	public PlayerView getPlayerPanel()
	{
		return playerPanel;
	}
	
	public List<View> getPanels()
	{
		return panels;
	}

	public TabView tabPanel()
	{
		return tabPanel;
	}

	public ToolView toolPanel()
	{
		return toolPanel;
	}

	public String temporaryMessage()
	{
		return temporaryMessage;
	}
	
	public static String volatileMessage()
	{
		return volatileMessage;
	}
	
	public void setTemporaryMessage(final String s)
	{
		if (s.length()==0)
		{
			temporaryMessage = "";
			volatileMessage = "";
		}
		else if (!temporaryMessage.contains(s))
		{
			temporaryMessage += " " + s;
		}
	}
	
	public static void setVolatileMessage(final PlayerApp app, final String s)
	{
		volatileMessage = s;
		final Timer timer = new Timer(3000, new ActionListener() 
		{
			@Override
			public void actionPerformed(final ActionEvent arg0) 
			{
				volatileMessage = "";
            	app.repaint();
			}
		});
		timer.setRepeats(false);
		timer.start();
	}

	public OverlayView overlayPanel()
	{
		return overlayPanel;
	}
	
	//-------------------------------------------------------------------------

	public int width()
	{
		return width;
	}

	public int height()
	{
		return height;
	}

	public Rectangle[] playerSwatchList()
	{
		return playerSwatchList;
	}

	public Rectangle[] playerNameList()
	{
		return playerNameList;
	}

	public boolean[] playerSwatchHover()
	{
		return playerSwatchHover;
	}

	public boolean[] playerNameHover()
	{
		return playerNameHover;
	}

}
