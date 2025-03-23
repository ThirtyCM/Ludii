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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
import game.functions.graph.generators.basis.tri.Tri;
import game.functions.graph.generators.basis.tri.TriShapeType;
import game.util.graph.Poly;

public class TrianglePanel extends ParameterPanel implements ItemListener
{
	private JDialog dialog;
	private Maker maker;
	private JComboBox<TriShapeType> triBox;
	private JSpinner pSpinner;
	private JSpinner sSpinner;
	
	private GraphFunction graph;
	private ActionListener al;
	
	private PolygonMaker pm;
	private JPanel cards;
	
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
		triBox.addItemListener(this);
		panel.add(triBox);
		optionPanel.add(panel);
		
		//-------------------------------------------------------------------------------
		// Shape
		JPanel shapeCard = new JPanel();
		shapeCard.setLayout(new BoxLayout(shapeCard, BoxLayout.Y_AXIS));
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Primary dimension size: ");
		panel.add(label);
		pSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		pSpinner.addChangeListener(al);
		panel.add(pSpinner);
		shapeCard.add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Secondary dimension size (optional): ");
		panel.add(label);
		sSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		sSpinner.addChangeListener(al);
		panel.add(sSpinner);
		shapeCard.add(panel);
		
		//-------------------------------------------------------------------------------
		// Custom
		JPanel customCard = new JPanel();
		pm = new PolygonMaker(this, al);
		customCard.add(pm);
		
		//-------------------------------------------------------------------------------
		// Cards
		cards = new JPanel(new CardLayout());
		cards.add(shapeCard,"Shape");
		cards.add(customCard,"Custom");
		
		optionPanel.add(cards);
		
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
		if (!shape.equals(TriShapeType.Custom)) {
			DimConstant dimA = new DimConstant((int) pSpinner.getValue());
			DimConstant dimB = new DimConstant((int) sSpinner.getValue());
			graph = Tri.construct(shape, dimA, (dimB.eval() == 0) ? null : dimB);
		} else {
			List<Coordinates> polygon = pm.getPoly();
			if (polygon.size() > 2) {
				Poly poly = makePoly(polygon);
				graph = Tri.construct(poly, null);
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
		if (((TriShapeType)e.getItem()).equals(TriShapeType.Custom)) {
			cl.show(cards, "Custom");
		} else {
			cl.show(cards, "Shape");
		}
	}
}
