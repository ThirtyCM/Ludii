package app.boardMaker.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import app.boardMaker.maker.Maker;
import game.functions.dim.DimConstant;
import game.functions.graph.generators.basis.brick.Brick;
import game.functions.graph.generators.basis.brick.BrickShapeType;
import game.functions.graph.generators.basis.square.DiagonalsType;
import game.functions.graph.generators.basis.square.Square;
import game.functions.graph.generators.basis.square.SquareShapeType;

public class SquarePanel extends JPanel
{
	private JDialog dialog;
	private Maker maker;
	private JComboBox<SquareShapeType> cBox;
	private JSpinner dimSpinner;
	private JComboBox<DiagonalsType> diagBox;
	private boolean pyramid = false;
	private boolean diagEnabled = true;
	
	public SquarePanel(JDialog dialog, Maker maker) {
		super(new BorderLayout());
		
		this.dialog = dialog;
		this.maker = maker;
		
		JPanel optionPanel = new JPanel();
		optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
		
		optionPanel.add(Box.createVerticalGlue());
		
		JPanel panel;
		JLabel label;
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Board shape: ");
		panel.add(label);
		cBox = new JComboBox<SquareShapeType>(SquareShapeType.values());
		cBox.removeItem(SquareShapeType.NoShape);
		cBox.setSelectedItem(SquareShapeType.Square);
		panel.add(cBox);
		optionPanel.add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Cells/Vertices per side");
		dimSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		panel.add(label);
		panel.add(dimSpinner);
		optionPanel.add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Type of diagonals: ");
		panel.add(label);
		diagBox = new JComboBox<DiagonalsType>(DiagonalsType.values());
		diagBox.setSelectedItem(DiagonalsType.Implied);
		panel.add(diagBox);
		optionPanel.add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Pyramidal stacking: ");
		panel.add(label);
		ButtonGroup group = new ButtonGroup();
		JRadioButton button = new JRadioButton("Yes");
		button.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				pyramid = true;
			}
		});
		group.add(button);
		panel.add(button);
		button = new JRadioButton("No");
		button.setSelected(true);
		button.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				pyramid = false;
			}
		});
		group.add(button);
		panel.add(button);
		optionPanel.add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Select active: ");
		group = new ButtonGroup();
		button = new JRadioButton("Diagonal");
		button.setSelected(true);
		button.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				diagEnabled = true;
			}
		});
		group.add(button);
		panel.add(button);
		button = new JRadioButton("Pyramidal");
		button.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				diagEnabled = false;
			}
		});
		group.add(button);
		panel.add(button);
		optionPanel.add(panel);
		
		optionPanel.add(Box.createVerticalGlue());
		
		add(optionPanel, BorderLayout.WEST);
		
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
				SquareShapeType shapeType = (SquareShapeType) cBox.getSelectedItem();
				DimConstant dim = new DimConstant((int)dimSpinner.getValue());
				DiagonalsType diagType = (DiagonalsType) diagBox.getSelectedItem();
				maker.setGraphFunction(Square.construct(shapeType, dim, diagEnabled ? diagType : null, diagEnabled ? null : pyramid));
				dialog.dispose();
			}
		});
		return button;
	}
}
