package app.boardMaker.window;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import app.boardMaker.BoardMaker;
import app.boardMaker.menu.BoardMakerMenu;
import app.display.dialogs.visual_editor.view.designPalettes.DesignPalette;
import app.util.SettingsDesktop;

/**
 * Main frame of the Board Maker
 */

public class BoardMakerFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	//-------------------------------------------------------------------------

	/** Window title. */
	private static final String title = "Ludii Board Maker";
	/** Ludii Icon. */
	private static final ImageIcon icon = DesignPalette.LUDII_ICON;
	
	//-------------------------------------------------------------------------
	
	public BoardMakerFrame(BoardMaker boardMaker) {
		setTitle(title);
		setIconImage(icon.getImage());
		setPreferredSize(new Dimension(SettingsDesktop.defaultWidth, SettingsDesktop.defaultHeight));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		BoardMakerMenu menuBar = new BoardMakerMenu(boardMaker);
		setJMenuBar(menuBar);
		setContentPane(new BoardMakerPanel(boardMaker.maker));
		pack();
		setVisible(true);
	}
}
