package app.boardMaker.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import app.boardMaker.maker.Maker;
import app.boardMaker.previews.MancalaPreview;
import app.boardMaker.previews.PreviewPanel;
import game.equipment.container.board.Board;
import game.equipment.container.board.custom.MancalaBoard;
import game.functions.graph.GraphFunction;
import game.types.board.StoreType;

public class MancalaPanel extends JPanel implements ActionListener, ChangeListener
{

	private JDialog dialog;
	private Maker maker;
	private MancalaPreview preview;
	private MancalaBoard board;
	
	private JSpinner rowSpinner;
	private JSpinner colSpinner;
	private JSpinner storeSpinner;
	private JComboBox<StoreType> sBox;
	
	private boolean largeStack = false;
	
	public MancalaPanel(JDialog dialog, Maker maker, MancalaPreview preview) {
		super(new BorderLayout());
		
		setPreferredSize(new Dimension(400,500));
		
		this.dialog = dialog;
		this.maker = maker;
		this.preview = preview;
		
		JPanel optionPanel = new JPanel();
		optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel label = new JLabel("Number of rows: ");
		panel.add(label);
		rowSpinner = new JSpinner(new SpinnerNumberModel(2, 2, 6, 1));
		rowSpinner.addChangeListener(this);
		panel.add(rowSpinner);
		optionPanel.add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Number of columns: ");
		panel.add(label);
		colSpinner = new JSpinner(new SpinnerNumberModel(6,1,Integer.MAX_VALUE,1));
		colSpinner.addChangeListener(this);
		panel.add(colSpinner);
		optionPanel.add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Store type: ");
		panel.add(label);
		sBox = new JComboBox<StoreType>(StoreType.values());
		sBox.setSelectedItem(StoreType.Outer);
		sBox.addActionListener(this);
		panel.add(sBox);
		optionPanel.add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Number of stores: ");
		panel.add(label);
		storeSpinner = new JSpinner(new SpinnerNumberModel(2,1,Integer.MAX_VALUE,1));
		storeSpinner.addChangeListener(this);
		panel.add(storeSpinner);
		optionPanel.add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Large stack: ");
		panel.add(label);
		ButtonGroup group = new ButtonGroup();
		JRadioButton button = new JRadioButton("Yes");
		button.addActionListener(this);
		button.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				largeStack = true;
			}
		});
		group.add(button);
		panel.add(button);
		button = new JRadioButton("No");
	    button.setSelected(true);
	    button.addActionListener(this);
	    button.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				largeStack = false;
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
		
		computeBoard();
		preview.render(board);
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
				maker.setMancala(board);
				dialog.dispose();
			}
		});
		return button;
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		// TODO Auto-generated method stub
		computeBoard();
		preview.render(board);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		computeBoard();
		preview.render(board);
	}
	
	public void computeBoard() {
		board = new MancalaBoard((Integer)rowSpinner.getValue(), (Integer)colSpinner.getValue(), (StoreType)sBox.getSelectedItem(), (Integer)storeSpinner.getValue(), largeStack, null, null);
	}
}
