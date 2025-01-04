package app.boardMaker.panels;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;

import app.boardMaker.maker.Maker;

public class CelticPanel extends JPanel
{
	private JDialog dialog;
	private Maker maker;
	
	public CelticPanel(JDialog dialog, Maker maker) {
		super(new BorderLayout());
		
		this.dialog = dialog;
		this.maker = maker;
	}
}
