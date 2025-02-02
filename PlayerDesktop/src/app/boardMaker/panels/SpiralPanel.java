package app.boardMaker.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import app.boardMaker.maker.Maker;
import game.functions.dim.DimConstant;
import game.functions.graph.generators.basis.tri.Tri;
import game.functions.graph.generators.basis.tri.TriShapeType;
import game.functions.graph.generators.shape.Spiral;

public class SpiralPanel extends JPanel
{
	private JDialog dialog;
	private Maker maker;
	private JSpinner turns;
	private JSpinner sites;
	private boolean clock = true;
	
	public SpiralPanel(JDialog dialog, Maker maker) {
		super(new BorderLayout());
		
		this.dialog = dialog;
		this.maker = maker;
		
		JPanel optionPanel = new JPanel(new GridLayout(0,1,5,0));
		JPanel panel;
		JLabel label;
		
		panel = new JPanel();
		label = new JLabel("Number of turns: ");
		panel.add(label);
		turns = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		panel.add(turns);
		optionPanel.add(panel);
		
		panel = new JPanel();
		label = new JLabel("Number of sites: ");
		panel.add(label);
		sites = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		panel.add(sites);
		optionPanel.add(panel);
		
		panel = new JPanel();
		label = new JLabel("Rotation direction: ");
		panel.add(label);
		ButtonGroup group = new ButtonGroup();
		JRadioButton button = new JRadioButton("Clockwise");
		button.setSelected(true);
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
		
		add(optionPanel,BorderLayout.WEST);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(makeCreateButton());
		buttonPanel.add(makeCancelButton());
		add(buttonPanel,BorderLayout.SOUTH);
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
				maker.setGraphFunction(new Spiral(new DimConstant((int) turns.getValue()), new DimConstant((int) sites.getValue()), clock));
				dialog.dispose();
			}
		});
		return button;
	}
}
