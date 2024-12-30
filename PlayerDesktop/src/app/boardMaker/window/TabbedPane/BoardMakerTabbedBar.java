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

	public BoardMakerTabbedBar(Maker maker) {
		super(new BorderLayout());
		
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
	 * Adds buttons to select board shape to the toolbar.
	 * 
	 * @param tb
	 */
	private void makeBoardButtons(JToolBar tb) {
		for (BoardShapes shape : BoardShapes.values()) {
			String name = shape.toString();
			JButton button = new JButton(name);
			button.setPreferredSize(new Dimension(150,30));
			button.setActionCommand(name);
			button.addActionListener(boardButtonListener);
			tb.add(button);
		}
	}
	
	private void makeFunctionsButtons(JToolBar tb) {
		// TODO
	}

}
