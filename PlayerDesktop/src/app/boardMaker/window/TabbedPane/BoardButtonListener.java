package app.boardMaker.window.TabbedPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import app.boardMaker.dialogs.MancalaDialog;
import app.boardMaker.dialogs.ParameterDialog;
import app.boardMaker.maker.Maker;
import game.functions.dim.DimConstant;
import game.functions.graph.generators.basis.square.Square;
import game.functions.graph.generators.basis.square.SquareShapeType;

public class BoardButtonListener implements ActionListener
{
	
	private Maker maker;
	private ParameterDialog dialog;
	
	public BoardButtonListener(Maker maker) {
		super();
		
		this.maker = maker;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String boardType = e.getActionCommand();
		
		switch (boardType) {
			case "Surakarta":
				break;
			case "Mancala":
				new MancalaDialog(maker);
				break;
			case "Classic" : 
				dialog = new ParameterDialog(maker);
				break;
			default :
				break;
		}
	}
}
