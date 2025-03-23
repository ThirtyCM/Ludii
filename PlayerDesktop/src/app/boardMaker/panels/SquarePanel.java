package app.boardMaker.panels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

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
import app.boardMaker.tools.Coordinates;
import app.boardMaker.tools.PolygonMaker;
import app.boardMaker.tools.PreviewPanel;
import game.functions.dim.DimConstant;
import game.functions.graph.GraphFunction;
import game.functions.graph.generators.basis.brick.Brick;
import game.functions.graph.generators.basis.brick.BrickShapeType;
import game.functions.graph.generators.basis.square.DiagonalsType;
import game.functions.graph.generators.basis.square.Square;
import game.functions.graph.generators.basis.square.SquareShapeType;
import game.util.graph.Poly;

public class SquarePanel extends ParameterPanel implements ItemListener
{
	private JDialog dialog;
	private Maker maker;
	private JComboBox<SquareShapeType> cBox;
	private JSpinner dimSpinner;
	private JComboBox<DiagonalsType> diagBox;
	private boolean pyramid = false;
	private boolean diagEnabled = true;
	
	private GraphFunction graph;
	private JPanel cards;
	private PolygonMaker pm;
	
	public SquarePanel(JDialog dialog, Maker maker, PreviewPanel al) {
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
		cBox.addActionListener(al);
		cBox.addItemListener(this);
		panel.add(cBox);
		optionPanel.add(panel);
		
		//-------------------------------------------------------------------------------
		// Board shape
		JPanel boardCard = new JPanel();
		boardCard.setLayout(new BoxLayout(boardCard, BoxLayout.Y_AXIS));
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Cells/Vertices per side");
		dimSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		dimSpinner.addChangeListener(al);
		panel.add(label);
		panel.add(dimSpinner);
		boardCard.add(panel);
		
		JPanel diagPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Type of diagonals: ");
		diagPanel.add(label);
		diagBox = new JComboBox<DiagonalsType>(DiagonalsType.values());
		diagBox.setSelectedItem(DiagonalsType.Implied);
		diagBox.addActionListener(al);
		diagPanel.add(diagBox);
		boardCard.add(diagPanel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Pyramidal stacking: ");
		panel.add(label);
		ButtonGroup group = new ButtonGroup();
		JRadioButton button = new JRadioButton("Yes");
		button.addActionListener(al);
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
		button.addActionListener(al);
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
		boardCard.add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Select active: ");
		group = new ButtonGroup();
		button = new JRadioButton("Diagonal");
		button.setSelected(true);
		button.addActionListener(al);
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
		button.addActionListener(al);
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
		boardCard.add(panel);
		
		//-------------------------------------------------------------------------------
		// Custom shape
		JPanel customCard = new JPanel();
		customCard.setLayout(new BoxLayout(customCard, BoxLayout.Y_AXIS));
		
		pm = new PolygonMaker(this, al);
		customCard.add(pm);
		customCard.add(diagPanel);
		
		//-------------------------------------------------------------------------------
		// Cards
		cards = new JPanel(new CardLayout());
		cards.add(boardCard,"Shape");
		cards.add(customCard,"Custom");
		optionPanel.add(cards);
		
		//-------------------------------------------------------------------------------
		
		optionPanel.add(Box.createVerticalGlue());
		
		add(optionPanel, BorderLayout.WEST);
		
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
		SquareShapeType shapeType = (SquareShapeType) cBox.getSelectedItem();
		if (!shapeType.equals(SquareShapeType.Custom)) {
			DimConstant dim = new DimConstant((int)dimSpinner.getValue());
			DiagonalsType diagType = (DiagonalsType) diagBox.getSelectedItem();
			graph = Square.construct(shapeType, dim, diagEnabled ? diagType : null, diagEnabled ? null : pyramid);
		} else {
			List<Coordinates> polygon = pm.getPoly();
			if (polygon.size() > 2) {
				Poly poly = makePoly(polygon);
				graph = Square.construct(poly, null, (DiagonalsType) diagBox.getSelectedItem());
			} else {
				graph = null;
			}
		}
	}

	@Override
	public GraphFunction getGraph()
	{
		// TODO Auto-generated method stub
		return graph;
	}

	@Override
	public void itemStateChanged(ItemEvent e)
	{
		// TODO Auto-generated method stub
		CardLayout cl = (CardLayout) cards.getLayout();
		if (((SquareShapeType)e.getItem()).equals(SquareShapeType.Custom)) {
			cl.show(cards, "Custom");
		} else {
			cl.show(cards, "Shape");
		}
	}
}
