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
import app.boardMaker.tools.PolygonListener;
import app.boardMaker.tools.PolygonView;
import app.boardMaker.tools.PreviewPanel;
import game.functions.dim.DimConstant;
import game.functions.graph.GraphFunction;
import game.functions.graph.generators.basis.brick.Brick;
import game.functions.graph.generators.basis.brick.BrickShapeType;
import game.functions.graph.generators.basis.celtic.Celtic;
import game.util.graph.Poly;
import main.math.Polygon;

public class CelticPanel extends ParameterPanel implements ItemListener
{
	private JDialog dialog;
	private JPanel cards;
	private Maker maker;
	private JSpinner rowSpinner;
	private JSpinner colSpinner;
	private JComboBox<String> shapeCBox;
	private PolygonView polygonView;
	
	private PolygonListener pl;
	private GraphFunction graph;
	private ActionListener al;
	
	public CelticPanel(JDialog dialog, Maker maker, PreviewPanel al) {
		super(new BorderLayout());
		
		this.dialog = dialog;
		this.maker = maker;
		this.al = al;
		
		JPanel optionPanel = new JPanel();
		optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
		
		optionPanel.add(Box.createVerticalGlue());
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel label = new JLabel("Board shape: ");
		String[] shapeItems = new String[] {"Rectangle", "Custom"};
		shapeCBox = new JComboBox<String>(shapeItems);
		shapeCBox.addActionListener(al);
		shapeCBox.addItemListener(this);
		panel.add(label);
		panel.add(shapeCBox);
		optionPanel.add(panel);
		
		//----------------------------------------------------------------------------------------------
		//Rectangle card panel linked to first item of shapeItems
		JPanel rectangleCard = new JPanel();
		rectangleCard.setLayout(new BoxLayout(rectangleCard, BoxLayout.Y_AXIS));
		
		rectangleCard.add(Box.createVerticalGlue());
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Number of rows: ");
		panel.add(label);
		rowSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		rowSpinner.addChangeListener(al);
		panel.add(rowSpinner);
		rectangleCard.add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Number of columns (optional): ");
		panel.add(label);
		colSpinner = new JSpinner(new SpinnerNumberModel(0,0,Integer.MAX_VALUE,1));
		colSpinner.addChangeListener(al);
		panel.add(colSpinner);
		rectangleCard.add(panel);
		rectangleCard.add(Box.createVerticalGlue());
		
		//----------------------------------------------------------------------------------------------
		//PolygonView linked to the second item of shapeItems
		JPanel polygonCard = new JPanel();
		polygonView = new PolygonView(300,300,10);
		this.pl = new PolygonListener(polygonView,this,al);
		polygonView.addMouseListener(pl);
		polygonCard.add(polygonView);
		
		//----------------------------------------------------------------------------------------------
		//Card panel
		cards = new JPanel(new CardLayout());
		cards.add(rectangleCard,shapeItems[0]);
		cards.add(polygonCard,shapeItems[1]);
		optionPanel.add(cards);
		
		//----------------------------------------------------------------------------------------------
		
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
	public void itemStateChanged(ItemEvent e)
	{
		// TODO Auto-generated method stub
		CardLayout cl = (CardLayout) cards.getLayout();
		cl.show(cards, (String) e.getItem());
	}

	@Override
	public GraphFunction getGraph() {
		return graph;
	}
	
	@Override
	public void createBoard()
	{
		// TODO Auto-generated method stub
		if (((String)shapeCBox.getSelectedItem()).equals("Rectangle")) {
			DimConstant dimA = new DimConstant((int)rowSpinner.getValue());
			DimConstant dimB = new DimConstant((int)colSpinner.getValue());
			graph = new Celtic(dimA, (dimB.eval() == 0) ? null : dimB);
		} else if (((String)shapeCBox.getSelectedItem()).equals("Custom")) {
			List<Coordinates> polygon = polygonView.getPoly();
			if (polygon.size() > 2) {
				Float[][] pts = new Float[polygon.size()][2];
				
				for (int i = 0; i < polygon.size(); i++) {
					pts[i][0] = (float) polygon.get(i).getX();
					pts[i][1] = (float) polygon.get(i).getY();
				}
				
				Poly poly = new Poly(pts, null);
				graph = new Celtic(poly, null);
			} else {
				graph = null;
			}
		}
	}
}
