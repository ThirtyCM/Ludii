package app.boardMaker.window;

import java.awt.BorderLayout;

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

	public BoardMakerTabbedBar() {
		super(new BorderLayout());
		
		JToolBar toolbar = null;
		JTabbedPane tabbedPane = new JTabbedPane();
		
		toolbar = initToolBar();
		tabbedPane.addTab("Boards", toolbar);
		
		toolbar = initToolBar();
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
}
