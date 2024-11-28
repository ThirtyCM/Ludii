package app.boardMaker.window.TabbedPane;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

/**
 * Tabbed menu of the board maker
 */
public class BoardMakerTabbedBar extends JPanel
{
	private static final long serialVersionUID = 1L;
	private final ActionListener boardButtonListener;

	public BoardMakerTabbedBar() {
		super(new BorderLayout());
		
		boardButtonListener = new BoardButtonListener();
		
		JToolBar toolbar = null;
		JTabbedPane tabbedPane = new JTabbedPane();
		
		toolbar = initToolBar();
		makeBoardButtons(toolbar);
		tabbedPane.addTab("Boards", toolbar);
		
		toolbar = initToolBar();
		tabbedPane.addTab("Functions", toolbar);
		makeFunctionsButtons(toolbar);
		
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
			button.setActionCommand(name);
			button.setToolTipText(name);
			button.addActionListener(boardButtonListener);
			tb.add(button);
		}
	}
	
	private void makeFunctionsButtons(JToolBar tb) {
		// TODO
	}
}
