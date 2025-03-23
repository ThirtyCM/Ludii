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
import game.functions.graph.generators.basis.tiling.Tiling;
import game.functions.graph.generators.basis.tiling.TilingType;
import game.util.graph.Poly;

public class TilingPanel extends ParameterPanel implements ItemListener
{
	private JDialog dialog;
	private Maker maker;
	private JComboBox<TilingType> tBox;
	private JComboBox<String> cBox;
	private JSpinner pSpinner;
	private JSpinner sSpinner;
	
	private GraphFunction graph;
	private ActionListener al;
	
	private JPanel cards;
	private PolygonMaker pm;
	
	public TilingPanel(JDialog dialog, Maker maker, PreviewPanel al) {
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
		label = new JLabel("Tiling type");
		panel.add(label);
		tBox = new JComboBox<TilingType>(TilingType.values());
		tBox.addActionListener(al);
		panel.add(tBox);
		optionPanel.add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Board shape: ");
		panel.add(label);
		cBox = new JComboBox<String>(new String[] {"Defined", "Custom"});
		cBox.addActionListener(al);
		cBox.addItemListener(this);
		panel.add(cBox);
		optionPanel.add(panel);
		
		//-------------------------------------------------------------------------------
		// Defined
		JPanel dimCard = new JPanel();
		dimCard.setLayout(new BoxLayout(dimCard, BoxLayout.Y_AXIS));
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Primary dimension size: ");
		panel.add(label);
		pSpinner = new JSpinner(new SpinnerNumberModel(1,1,Integer.MAX_VALUE,1));
		pSpinner.addChangeListener(al);
		panel.add(pSpinner);
		dimCard.add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Secondary dimension size (optional): ");
		panel.add(label);
		sSpinner = new JSpinner(new SpinnerNumberModel(0,0,Integer.MAX_VALUE,1));
		sSpinner.addChangeListener(al);
		panel.add(sSpinner);
		dimCard.add(panel);
		
		//-------------------------------------------------------------------------------
		// Custom shape
		JPanel customCard = new JPanel();
		pm = new PolygonMaker(this, al);
		customCard.add(pm);
		
		//-------------------------------------------------------------------------------
		// Cards
		cards = new JPanel(new CardLayout());
		cards.add(dimCard,"Dimensions");
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
		TilingType type = (TilingType) tBox.getSelectedItem();
		if (((String)cBox.getSelectedItem()).equals("Dimensions")) {
			DimConstant dimA = new DimConstant((int)pSpinner.getValue());
			DimConstant dimB = new DimConstant((int)sSpinner.getValue());
			graph = Tiling.construct(type, dimA, (dimB.eval() == 0) ? null : dimB);
		} else {
			List<Coordinates> polygon = pm.getPoly();
			if (polygon.size() > 2) {
				Poly poly = makePoly(polygon);
				graph = Tiling.construct(type, poly, null);
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
		if (((String)e.getItem()).equals("Custom")) {
			cl.show(cards, "Custom");
		} else {
			cl.show(cards, "Dimensions");
		}
	}
}
