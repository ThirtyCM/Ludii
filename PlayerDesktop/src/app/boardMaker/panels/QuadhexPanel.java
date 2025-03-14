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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import app.boardMaker.maker.Maker;
import app.boardMaker.tools.PreviewPanel;
import game.functions.dim.DimConstant;
import game.functions.graph.GraphFunction;
import game.functions.graph.generators.basis.brick.Brick;
import game.functions.graph.generators.basis.brick.BrickShapeType;
import game.functions.graph.generators.basis.quadhex.Quadhex;

public class QuadhexPanel extends ParameterPanel
{
	private JDialog dialog;
	private Maker maker;
	private JSpinner layerSpinner;
	private boolean thirds = false;
	
	private GraphFunction graph;
	private ActionListener al;
	
	public QuadhexPanel(JDialog dialog, Maker maker, PreviewPanel al) {
		super(new BorderLayout());
		
		this.dialog = dialog;
		this.maker = maker;
		this.al = al;
		
		JPanel optionPanel = new JPanel();
		optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
		
		optionPanel.add(Box.createVerticalGlue());
		
		JPanel panel;
		JLabel label;
		
		label = new JLabel("Number of layers: ");
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(label);
		layerSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		layerSpinner.addChangeListener(al);
		panel.add(layerSpinner);
		optionPanel.add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Split board in 3: ");
		panel.add(label);
		ButtonGroup group = new ButtonGroup();
		JRadioButton buttonYes = new JRadioButton("Yes");
		buttonYes.setEnabled(false);
		buttonYes.addActionListener(al);
		buttonYes.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				thirds = true;
			}
		});
		group.add(buttonYes);
		panel.add(buttonYes);
		JRadioButton buttonNo = new JRadioButton("No");
		buttonNo.setSelected(true);
		buttonNo.addActionListener(al);
		buttonNo.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				thirds = false;
			}
		});
		group.add(buttonNo);
		panel.add(buttonNo);
		optionPanel.add(panel);
		
		layerSpinner.addChangeListener(new ChangeListener()
		{
			
			@Override
			public void stateChanged(ChangeEvent e)
			{
				// TODO Auto-generated method stub
				if ((Integer)layerSpinner.getValue() == 1) {
					buttonNo.setSelected(true);
					buttonYes.setSelected(false);
					buttonYes.setEnabled(false);
					
					thirds = false;
				} else {
					buttonYes.setEnabled(true);
				}
			}
		});
		
		optionPanel.add(Box.createVerticalGlue());
		
		add(optionPanel,BorderLayout.WEST);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(makeCreateButton());
		buttonPanel.add(makeCancelButton());
		add(buttonPanel,BorderLayout.SOUTH);
		
		createBoard();
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
				maker.setGraphFunction(graph);
				dialog.dispose();
			}
		});
		return button;
	}

	@Override
	public void createBoard()
	{
		// TODO Auto-generated method stub
		DimConstant dimA = new DimConstant((int)layerSpinner.getValue());
		graph = new Quadhex(dimA, thirds);
	}

	@Override
	public GraphFunction getGraph()
	{
		// TODO Auto-generated method stub
		return graph;
	}
}
