package app.boardMaker.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import app.boardMaker.maker.Maker;
import app.boardMaker.tools.PreviewPanel;
import game.functions.dim.DimConstant;
import game.functions.graph.GraphFunction;
import game.functions.graph.generators.basis.brick.Brick;
import game.functions.graph.generators.basis.brick.BrickShapeType;
import game.functions.graph.generators.basis.tri.Tri;
import game.functions.graph.generators.basis.tri.TriShapeType;

public class TrianglePanel extends ParameterPanel
{
	private JDialog dialog;
	private Maker maker;
	private JComboBox<TriShapeType> triBox;
	private JSpinner pSpinner;
	private JSpinner sSpinner;
	
	private GraphFunction graph;
	private ActionListener al;
	
	public TrianglePanel(JDialog dialog, Maker maker, PreviewPanel al) {
		super(new BorderLayout());
		
		this.dialog = dialog;
		this.maker = maker;
		this.al = al;
		
		JPanel optionPanel = new JPanel();
		optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
		
		optionPanel.add(Box.createVerticalGlue());
		
		JPanel panel;
		JLabel label;
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Board shape: ");
		panel.add(label);
		triBox = new JComboBox<TriShapeType>(TriShapeType.values());
		triBox.removeItem(TriShapeType.NoShape);
		triBox.setSelectedItem(TriShapeType.Triangle);
		triBox.addActionListener(al);
		panel.add(triBox);
		optionPanel.add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Primary dimension size: ");
		panel.add(label);
		pSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		pSpinner.addChangeListener(al);
		panel.add(pSpinner);
		optionPanel.add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Secondary dimension size (optional): ");
		panel.add(label);
		sSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		sSpinner.addChangeListener(al);
		panel.add(sSpinner);
		optionPanel.add(panel);
		
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
		TriShapeType shape = (TriShapeType) triBox.getSelectedItem();
		DimConstant dimA = new DimConstant((int) pSpinner.getValue());
		DimConstant dimB = new DimConstant((int) sSpinner.getValue());
		graph = Tri.construct(shape, dimA, (dimB.eval() == 0) ? null : dimB);
	}

	@Override
	public GraphFunction getGraph()
	{
		// TODO Auto-generated method stub
		return graph;
	}
}
