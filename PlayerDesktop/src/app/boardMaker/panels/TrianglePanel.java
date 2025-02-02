package app.boardMaker.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import app.boardMaker.maker.Maker;
import game.functions.dim.DimConstant;
import game.functions.graph.generators.basis.brick.Brick;
import game.functions.graph.generators.basis.brick.BrickShapeType;
import game.functions.graph.generators.basis.tri.Tri;
import game.functions.graph.generators.basis.tri.TriShapeType;

public class TrianglePanel extends JPanel
{
	private JDialog dialog;
	private Maker maker;
	private JComboBox<TriShapeType> triBox;
	private JSpinner pSpinner;
	private JSpinner sSpinner;
	
	public TrianglePanel(JDialog dialog, Maker maker) {
		super(new BorderLayout());
		
		this.dialog = dialog;
		this.maker = maker;
		
		JPanel optionPanel = new JPanel(new GridLayout(0,1,5,0));
		JPanel panel;
		JLabel label;
		
		panel = new JPanel();
		label = new JLabel("Board shape: ");
		panel.add(label);
		triBox = new JComboBox<TriShapeType>(TriShapeType.values());
		triBox.removeItem(TriShapeType.NoShape);
		triBox.setSelectedItem(TriShapeType.Triangle);
		panel.add(triBox);
		optionPanel.add(panel);
		
		panel = new JPanel();
		label = new JLabel("Primary dimension size: ");
		panel.add(label);
		pSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		panel.add(pSpinner);
		optionPanel.add(panel);
		
		panel = new JPanel();
		label = new JLabel("Secondary dimension size (optional): ");
		panel.add(label);
		sSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		panel.add(sSpinner);
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
				TriShapeType shape = (TriShapeType) triBox.getSelectedItem();
				DimConstant dimA = new DimConstant((int) pSpinner.getValue());
				DimConstant dimB = new DimConstant((int) sSpinner.getValue());
				maker.setGraphFunction(Tri.construct(shape, dimA, (dimB.eval() == 0) ? null : dimB));
				dialog.dispose();
			}
		});
		return button;
	}
}
