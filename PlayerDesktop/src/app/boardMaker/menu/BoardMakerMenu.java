package app.boardMaker.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;

import app.boardMaker.BoardMaker;

/**
 * Menu bar of the frame
 */
public class BoardMakerMenu extends JMenuBar
{
	
	private static final long serialVersionUID = 1L;

	public BoardMakerMenu(BoardMaker boardMaker) {
		final ActionListener al = new BoardMakerMenuListener();
		
		JMenuItem menuItem;
		
		//---------------------------------------------------------------------
		// File menu
		JMenu menu = new JMenu("File (NYI)");
		this.add(menu);
		
		menuItem = new JMenuItem("Save (NYI)");
		menuItem.addActionListener(al);
		menu.add(menuItem);
		
		//---------------------------------------------------------------------
		menu = new JMenu("Run (NYI)");
		this.add(menu);
		
		menuItem = new JMenuItem("Compile (NYI)");
		menuItem.addActionListener(al);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Play (NYI)");
		menuItem.addActionListener(al);
		menu.add(menuItem);
	}
}
