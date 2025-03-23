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
import game.functions.graph.generators.basis.hex.Hex;
import game.functions.graph.generators.basis.hex.HexShapeType;
import game.util.graph.Poly;

public class HexPanel extends ParameterPanel implements ItemListener
{
	private JDialog dialog;
	private Maker maker;
	private JComboBox<HexShapeType> cBox;
	private JSpinner rowSpinner;
	private JSpinner colSpinner;
	
	private GraphFunction graph;
	private ActionListener al;
	
	private PolygonMaker polygonMaker;
	private JPanel cards;
	
	public HexPanel(JDialog dialog, Maker maker, PreviewPanel al) {
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
		cBox = new JComboBox<HexShapeType>(HexShapeType.values());
		cBox.removeItem(HexShapeType.NoShape);
		cBox.setSelectedItem(HexShapeType.Hexagon);
		cBox.addActionListener(al);
		cBox.addItemListener(this);
		panel.add(label);
		panel.add(cBox);
		optionPanel.add(panel);
		
		//---------------------------------------------------------------------------------------
		// Board shape
		JPanel boardCard = new JPanel();
		boardCard.setLayout(new BoxLayout(boardCard, BoxLayout.Y_AXIS));
		
		//boardCard.add(Box.createVerticalGlue());
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Primary number of sites per side: ");
		panel.add(label);
		rowSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		rowSpinner.addChangeListener(al);
		panel.add(rowSpinner);
		boardCard.add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Secondary number of sites (optional): ");
		panel.add(label);
		colSpinner = new JSpinner(new SpinnerNumberModel(0,0,Integer.MAX_VALUE,1));
		colSpinner.addChangeListener(al);
		panel.add(colSpinner);
		boardCard.add(panel);
		
		//---------------------------------------------------------------------------------------
		// Custom shape
		JPanel customCard = new JPanel();
		polygonMaker = new PolygonMaker(this, al);
		customCard.add(polygonMaker);
		
		//---------------------------------------------------------------------------------------
		// Cards
		cards = new JPanel(new CardLayout());
		cards.add(boardCard,"Shape");
		cards.add(customCard,"Custom");
		optionPanel.add(cards);
		
		//---------------------------------------------------------------------------------------
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
		HexShapeType shape = (HexShapeType) cBox.getSelectedItem();
		if (!shape.equals(HexShapeType.Custom)) {
			DimConstant dimA = new DimConstant((int)rowSpinner.getValue());
			DimConstant dimB = new DimConstant((int)colSpinner.getValue());
			graph = Hex.construct(shape, dimA, (dimB.eval() == 0) ? null : dimB);
		} else {
			List<Coordinates> polygon = polygonMaker.getPoly();
			if (polygon.size() > 2) {
				Poly poly = makePoly(polygon);
				graph = Hex.construct(poly, null);
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
		if (((HexShapeType)e.getItem()).equals(HexShapeType.Custom)) {
			cl.show(cards, "Custom");
		} else {
			cl.show(cards, "Shape");
		}
	}
}
