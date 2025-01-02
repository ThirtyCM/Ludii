package app.boardMaker.window.TabbedPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import app.boardMaker.maker.Maker;

/**
 * Tabbed menu of the board maker
 */
public class BoardMakerTabbedBar extends JPanel
{
	private static final long serialVersionUID = 1L;
	private final ActionListener boardButtonListener;
	private final Maker maker;

	public BoardMakerTabbedBar(Maker maker) {
		super(new BorderLayout());
		this.maker = maker;
		
		boardButtonListener = new BoardButtonListener(maker);
		
		JToolBar toolbar = null;
		JTabbedPane tabbedPane = new JTabbedPane();
		
		toolbar = initToolBar();
		makeBoardButtons(toolbar);
		tabbedPane.addTab("Boards", toolbar);
		
		toolbar = initToolBar();
		makeFunctionsButtons(toolbar);
		tabbedPane.addTab("Functions", toolbar);
		
		setOpaque(true);
		
		add(tabbedPane);
	}
	
	private JToolBar initToolBar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setRollover(true);
		toolbar.setFloatable(false);
		return toolbar;
	}
	
	/**
	 * Adds buttons to select board tiling to the toolbar.
	 * 
	 * @param tb, the toolbar to add buttons.
	 */
	private void makeBoardButtons(JToolBar tb) {
		JButton button = new JButton("New classical");
		button.setActionCommand("Classic");
		button.setPreferredSize(new Dimension(150,30));
		button.addActionListener(boardButtonListener);
		tb.add(button);
		
		button = new JButton("New mancala");
		button.setActionCommand("Mancala");
		button.setPreferredSize(new Dimension(150,30));
		button.addActionListener(boardButtonListener);
		tb.add(button);
		
		button = new JButton("New surakarta");
		button.setActionCommand("Surakarta");
		button.setPreferredSize(new Dimension(150,30));
		button.addActionListener(boardButtonListener);
		tb.add(button);
	}
	
	private void makeFunctionsButtons(JToolBar tb) {
		// TODO
	}

}
