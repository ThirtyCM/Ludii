package app.boardMaker.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import app.boardMaker.maker.Maker;
import game.functions.dim.DimConstant;
import game.functions.graph.generators.basis.brick.Brick;
import game.functions.graph.generators.basis.brick.BrickShapeType;
import game.functions.graph.generators.basis.celtic.Celtic;

public class CelticPanel extends JPanel
{
	private JDialog dialog;
	private Maker maker;
	private JSpinner rowSpinner;
	private JSpinner colSpinner;
	
	
	public CelticPanel(JDialog dialog, Maker maker) {
		super(new BorderLayout());
		
		this.dialog = dialog;
		this.maker = maker;
		
		JPanel optionPanel = new JPanel(new GridLayout(0,1,5,0));
		
		JPanel panel = new JPanel();
		JLabel label;
		
		label = new JLabel("Number of rows: ");
		panel.add(label);
		rowSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		panel.add(rowSpinner);
		optionPanel.add(panel);
		
		panel = new JPanel();
		label = new JLabel("Number of columns (optional): ");
		panel.add(label);
		colSpinner = new JSpinner(new SpinnerNumberModel(-1,-1,Integer.MAX_VALUE,1));
		panel.add(colSpinner);
		optionPanel.add(panel);
		
		add(optionPanel,BorderLayout.WEST);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(makeCreateButton());
		buttonPanel.add(makeCancelButton());
		add(buttonPanel,BorderLayout.SOUTH);
	}
	
	public JButton makeCancelButton() {
		JButton button = new JButton("Cancel");
		button.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				dialog.dispose();
			}
		});
		return button;
	}
	
	public JButton makeCreateButton() {
		JButton button = new JButton("Create");
		button.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				DimConstant dimA = new DimConstant((int)rowSpinner.getValue());
				DimConstant dimB = new DimConstant((int)colSpinner.getValue());
				maker.setGraphFunction(new Celtic(dimA, (dimB.eval() == -1) ? null : dimB));
				dialog.dispose();
			}
		});
		return button;
	}
}
