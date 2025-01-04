package app.boardMaker.panels;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;

import app.boardMaker.maker.Maker;

public class ConcentricPanel extends JPanel
{
	public ConcentricPanel(JDialog dialog, Maker maker) {
		super(new BorderLayout());
	}
}
