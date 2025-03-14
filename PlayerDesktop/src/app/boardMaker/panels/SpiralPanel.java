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

import app.boardMaker.maker.Maker;
import app.boardMaker.tools.PreviewPanel;
import game.functions.dim.DimConstant;
import game.functions.graph.GraphFunction;
import game.functions.graph.generators.basis.tri.Tri;
import game.functions.graph.generators.basis.tri.TriShapeType;
import game.functions.graph.generators.shape.Spiral;

public class SpiralPanel extends ParameterPanel
{
	private JDialog dialog;
	private Maker maker;
	private JSpinner turns;
	private JSpinner sites;
	private boolean clock = true;
	
	private GraphFunction graph;
	private ActionListener al;
	
	public SpiralPanel(JDialog dialog, Maker maker, PreviewPanel al) {
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
		label = new JLabel("Number of turns: ");
		panel.add(label);
		turns = new JSpinner(new SpinnerNumberModel(3, 3, Integer.MAX_VALUE, 1));
		turns.addChangeListener(al);
		panel.add(turns);
		optionPanel.add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Number of sites: ");
		panel.add(label);
		sites = new JSpinner(new SpinnerNumberModel(3, 3, Integer.MAX_VALUE, 1));
		sites.addChangeListener(al);
		panel.add(sites);
		optionPanel.add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Rotation direction: ");
		panel.add(label);
		ButtonGroup group = new ButtonGroup();
		JRadioButton button = new JRadioButton("Clockwise");
		button.setSelected(true);
		button.addActionListener(al);
		button.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				clock = true;
			}
		});
		group.add(button);
		panel.add(button);
		button = new JRadioButton("Counterclockwise");
		button.addActionListener(al);
		button.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				clock = false;
			}
		});
		group.add(button);
		panel.add(button);
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
		graph = new Spiral(new DimConstant((int) turns.getValue()), new DimConstant((int) sites.getValue()), clock);
	}

	@Override
	public GraphFunction getGraph()
	{
		// TODO Auto-generated method stub
		return graph;
	}
}
