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
import game.functions.graph.generators.basis.tri.Tri;
import game.functions.graph.generators.basis.tri.TriShapeType;
import game.functions.graph.generators.shape.Wedge;

public class WedgePanel extends JPanel
{
	private JDialog dialog;
	private Maker maker;
	private JSpinner row;
	private JSpinner col;
	
	public WedgePanel(JDialog dialog, Maker maker) {
		super(new BorderLayout());
		
		this.dialog = dialog;
		this.maker = maker;
		
		JPanel optionPanel = new JPanel(new GridLayout(0,1,5,0));
		JPanel panel;
		JLabel label;
		
		panel = new JPanel();
		label = new JLabel("Number of rows: ");
		panel.add(label);
		row = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		panel.add(row);
		optionPanel.add(panel);
		
		panel = new JPanel();
		label = new JLabel("Number of columns (optional): ");
		panel.add(label);
		col = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		panel.add(col);
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
				maker.setGraphFunction(new Wedge(new DimConstant((int) row.getValue()), ((int)col.getValue() == 0) ? null : new DimConstant((int) col.getValue())));
				dialog.dispose();
			}
		});
		return button;
	}
}
