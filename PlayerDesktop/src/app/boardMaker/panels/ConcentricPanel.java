package app.boardMaker.panels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.NumberFormatter;

import app.boardMaker.maker.Maker;
import app.boardMaker.previews.PreviewPanel;
import game.functions.booleans.BaseBooleanFunction;
import game.functions.booleans.BooleanConstant;
import game.functions.booleans.BooleanConstant.FalseConstant;
import game.functions.booleans.BooleanConstant.TrueConstant;
import game.functions.booleans.BooleanFunction;
import game.functions.dim.DimConstant;
import game.functions.graph.GraphFunction;
import game.functions.graph.generators.basis.tri.Tri;
import game.functions.graph.generators.basis.tri.TriShapeType;
import game.functions.graph.generators.shape.concentric.Concentric;
import game.functions.graph.generators.shape.concentric.ConcentricShapeType;

public class ConcentricPanel extends ParameterPanel implements ItemListener
{
	/* Midpoints -> 0 / JoinMidpoints -> 1 / JoinCorners -> 2 / Stagger -> 3*/
	private boolean[] parameters = new boolean[] {true,true,false,false};
	
	private JSpinner ringSpinner;
	private JSpinner stepSpinner;
	private JSpinner sideSpinner;
	private JPanel cards;
	private JComboBox<ConcentricShapeType> shapes;
	private JComboBox<String> cb;
	private JTextField dimensions;
	private int selectedShapeParameter;
	
	private JDialog dialog;
	private Maker maker;
	
	private ActionListener al;
	private GraphFunction graph;
	
	public ConcentricPanel(JDialog dialog, Maker maker, PreviewPanel al) {
		super(new BorderLayout());
		
		this.dialog = dialog;
		this.maker = maker;
		this.al = al;
		
		JPanel optionPanel = new JPanel();
		optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
		
		optionPanel.add(Box.createVerticalGlue());
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		String[] comboBoxItems = new String[] {"Board ring shape: ", "Number of sides: ", "Number of cells per ring: "};
		cb = new JComboBox<String>(comboBoxItems);
		cb.setEditable(false);
		cb.addActionListener(al);
		cb.addItemListener(this);
		
		JPanel card1 = new JPanel();
		shapes = new JComboBox<ConcentricShapeType>(ConcentricShapeType.values());
		shapes.setSelectedItem(ConcentricShapeType.Triangle);
		shapes.addActionListener(al);
		card1.add(shapes);
		
		JPanel card2 = new JPanel();
		sideSpinner = new JSpinner(new SpinnerNumberModel(3,3,Integer.MAX_VALUE,1));
		sideSpinner.addChangeListener(al);
		card2.add(sideSpinner);
		
		JPanel card3 = new JPanel();
		dimensions = new JTextField();
		dimensions.setText("5 5");
		dimensions.setColumns(10);
		dimensions.addActionListener(al);
		card3.add(dimensions);
		
		cards = new JPanel(new CardLayout());
		cards.add(card1,comboBoxItems[0]);
		cards.add(card2,comboBoxItems[1]);
		cards.add(card3,comboBoxItems[2]);
		
		panel.add(cb);
		panel.add(cards);
		optionPanel.add(panel);
				
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel l = new JLabel("Number of rings: ");
		p.add(l);
		ringSpinner = new JSpinner(new SpinnerNumberModel(3, 1, Integer.MAX_VALUE, 1));
		ringSpinner.addChangeListener(al);
		p.add(ringSpinner);
		optionPanel.add(p);
		
		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		l = new JLabel("Number of steps: ");
		p.add(l);
		stepSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		stepSpinner.addChangeListener(al);
		p.add(stepSpinner);
		optionPanel.add(p);
		
		createGroupButton(optionPanel, "Midpoints: ", 0);
		createGroupButton(optionPanel, "Join midpoints: ", 1);
		createGroupButton(optionPanel, "Join corners: ", 2);
		createGroupButton(optionPanel, "Stagger: ", 3);
		
		optionPanel.add(Box.createVerticalGlue());
		
		add(optionPanel,BorderLayout.WEST);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(makeCreateButton());
		buttonPanel.add(makeCancelButton());
		add(buttonPanel,BorderLayout.SOUTH);
		
		createBoard();
	}
	
	private void createGroupButton(JPanel panel, String label, int paramIdx) {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel l = new JLabel(label);
		p.add(l);
		ButtonGroup group = new ButtonGroup();
		JRadioButton button = new JRadioButton("Yes");
		button.setSelected(parameters[paramIdx]);
		button.addActionListener(al);
		button.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				parameters[paramIdx] = true;
			}
		});
		group.add(button);
		p.add(button);
		button = new JRadioButton("No");
		button.setSelected(!parameters[paramIdx]);
		button.addActionListener(al);
		button.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				parameters[paramIdx] = false;
			}
		});
		group.add(button);
		p.add(button);
		panel.add(p);
	}
	
	private JButton makeCancelButton() {
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
	
	private JButton makeCreateButton() {
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
	
	public boolean isCorrectFormat(String s) {
		for (char n : dimensions.getText().toCharArray()) {
			if (!Character.isDigit(n) && !Character.isWhitespace(n)) {
				return false;
			}
		}
		return true;
	}
	
	private DimConstant[] parseDimensions() {
		ArrayList<DimConstant> d = new ArrayList<DimConstant>();
		for (char n : dimensions.getText().toCharArray()) {
			if (Character.isDigit(n)) {
				d.add(new DimConstant(Character.getNumericValue(n)));
			}
		}
		DimConstant[] array = new DimConstant[d.size()];
		return d.toArray(array);
	}

	@Override
	public void itemStateChanged(ItemEvent e)
	{
		// TODO Auto-generated method stub
		CardLayout cl = (CardLayout) cards.getLayout();
		cl.show(cards, (String) e.getItem());
		selectedShapeParameter = cb.getSelectedIndex();
	}

	@Override
	public void createBoard()
	{
		// TODO Auto-generated method stub
		BooleanFunction bf0 = (parameters[0]) ? new BooleanConstant(true) : new BooleanConstant(false);
		BooleanFunction bf1 = (parameters[1]) ? new BooleanConstant(true) : new BooleanConstant(false);
		BooleanFunction bf2 = (parameters[2]) ? new BooleanConstant(true) : new BooleanConstant(false);
		BooleanFunction bf3 = (parameters[3]) ? new BooleanConstant(true) : new BooleanConstant(false);
		
		switch (selectedShapeParameter)
		{
		case 0:
			graph = Concentric.construct((ConcentricShapeType)shapes.getSelectedItem(), null, null, new DimConstant((int)ringSpinner.getValue()), 
					((int)stepSpinner.getValue() == 0) ? null : new DimConstant((int) stepSpinner.getValue()), bf0, bf1, bf2, bf3);
			break;
		case 1:
			graph = Concentric.construct(null, new DimConstant((int)sideSpinner.getValue()), null, new DimConstant((int)ringSpinner.getValue()), 
					((int)stepSpinner.getValue() == 0) ? null : new DimConstant((int) stepSpinner.getValue()), bf0, bf1, bf2, bf3);
			break;
		case 2:
			if (isCorrectFormat(dimensions.getText())) {
				graph = Concentric.construct(null, null, parseDimensions(), new DimConstant((int)ringSpinner.getValue()), 
						((int)stepSpinner.getValue() == 0) ? null : new DimConstant((int) stepSpinner.getValue()), bf0, bf1, bf2, bf3);
			} else {
				// dialog to reformat
				JOptionPane.showMessageDialog(dialog, "Dimensions does not respect the right format.\n"
						+ "Input should be composed of integers separated by a blank space where each number represents "
						+ "the number of cells in the corresponding ring.\n"
						+ "Example of valid input: 3 4 5 ...");
			}
			break;
		default:
			break;
		}
	}

	@Override
	public GraphFunction getGraph()
	{
		// TODO Auto-generated method stub
		return graph;
	}
}
