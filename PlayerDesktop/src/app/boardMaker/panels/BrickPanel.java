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

import app.boardMaker.BoardMaker;
import app.boardMaker.maker.Maker;
import app.boardMaker.previews.PreviewPanel;
import game.Game;
import game.functions.dim.DimConstant;
import game.functions.dim.DimFunction;
import game.functions.graph.GraphFunction;
import game.functions.graph.generators.basis.brick.Brick;
import game.functions.graph.generators.basis.brick.BrickShapeType;

public class BrickPanel extends ParameterPanel
{
	private static final long serialVersionUID = 1L;
	private final Maker maker;
	private final JDialog dialog;
	private boolean trim = false;
	private JSpinner rowSpinner;
	private JSpinner colSpinner;
	private JComboBox<BrickShapeType> cBox;
	
	private GraphFunction graph;
	private ActionListener al;
	
	public BrickPanel(JDialog dialog, Maker maker, PreviewPanel al) {
		super(new BorderLayout());
		this.dialog = dialog;
		this.maker = maker;
		this.al = al;
		
		JPanel optionPanel = new JPanel();
		optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
		
		optionPanel.add(Box.createVerticalGlue());
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel label = new JLabel("Board shape: ");
		cBox = new JComboBox<>(BrickShapeType.values());
		cBox.addActionListener(al);
		panel.add(label);
		panel.add(cBox);
		optionPanel.add(panel);
				
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Number of rows: ");
		panel.add(label);
		rowSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		rowSpinner.addChangeListener(al);
		panel.add(rowSpinner);
		optionPanel.add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Number of columns (optional): ");
		panel.add(label);
		colSpinner = new JSpinner(new SpinnerNumberModel(0,0,Integer.MAX_VALUE,1));
		colSpinner.addChangeListener(al);
		panel.add(colSpinner);
		optionPanel.add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Trim (optional): ");
		panel.add(label);
		ButtonGroup group = new ButtonGroup();
		JRadioButton button = new JRadioButton("Enabled");
		button.addActionListener(al);
		button.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				trim = true;
			}
		});
		panel.add(button);
		group.add(button);
		button = new JRadioButton("Disabled");
		button.setSelected(true);
		button.addActionListener(al);
		button.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				trim = false;
			}
		});
		panel.add(button);
		group.add(button);
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
	public GraphFunction getGraph() {
		return graph;
	}

	@Override
	public void createBoard()
	{
		// TODO Auto-generated method stub
		BrickShapeType shape = (BrickShapeType) cBox.getSelectedItem();
		DimConstant dimA = new DimConstant((int)rowSpinner.getValue());
		DimConstant dimB = new DimConstant((int)colSpinner.getValue());
		graph = Brick.construct(shape, dimA, (dimB.eval() == 0) ? null : dimB, trim);
	}
}
