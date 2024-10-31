package app.boardMaker.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;

import app.boardMaker.BoardMaker;

public class BoardMakerMenu extends JMenuBar
{
	
	private static final long serialVersionUID = 1L;

	public BoardMakerMenu(BoardMaker boardMaker) {
		final ActionListener al = boardMaker;
		
		JMenuItem menuItem;
		
		//---------------------------------------------------------------------
		// File menu
		JMenu menu = new JMenu("File (NYI)");
		this.add(menu);
		
		menuItem = new JMenuItem("New File (NYI)");
		menuItem.addActionListener(al);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("New Board (NYI)");
		menuItem.addActionListener(al);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Save (NYI)");
		menuItem.addActionListener(al);
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new JMenuItem("Compile (NYI)");
		menuItem.addActionListener(al);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Compile & close (NYI)");
		menuItem.addActionListener(al);
		menu.add(menuItem);
		
		//---------------------------------------------------------------------
		// Boards menu
		menu = new JMenu("Boards (NYI)");
		this.add(menu);
		
		menuItem = new JMenuItem("Square (NYI)");
		menuItem.addActionListener(al);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Rectangle (NYI)");
		menuItem.addActionListener(al);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Triangle (NYI)");
		menuItem.addActionListener(al);
		menu.add(menuItem);
		
		//---------------------------------------------------------------------
		// Graph functions menu
		menu = new JMenu("Functions (NYI)");
		this.add(menu);
	}
}
