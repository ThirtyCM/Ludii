package app.boardMaker.menu;

import java.awt.event.ActionEvent;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import app.DesktopApp;

public class BoardMakerMenuFunctions extends JMenuBar
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static void checkActionPerformed(final DesktopApp app, final ActionEvent e) {
		final JMenuItem source = (JMenuItem) e.getSource();
		System.out.println(source.getText());
	}

}
