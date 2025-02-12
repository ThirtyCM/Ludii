package app.boardMaker.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import game.functions.dim.DimConstant;
import game.functions.graph.generators.basis.brick.Brick;
import game.functions.graph.generators.basis.brick.BrickShapeType;
import game.functions.graph.generators.basis.tiling.Tiling;
import game.functions.graph.generators.basis.tiling.TilingType;

public class TilingPanel extends JPanel
{
	private JDialog dialog;
	private Maker maker;
	private JComboBox<TilingType> tBox;
	private JSpinner pSpinner;
	private JSpinner sSpinner;
	
	public TilingPanel(JDialog dialog, Maker maker) {
		super(new BorderLayout());
		
		this.dialog = dialog;
		this.maker = maker;
		
		JPanel optionPanel = new JPanel();
		optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
		
		optionPanel.add(Box.createVerticalGlue());
		
		JPanel panel;
		JLabel label;
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Tiling type");
		panel.add(label);
		tBox = new JComboBox<TilingType>(TilingType.values());
		panel.add(tBox);
		optionPanel.add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Primary dimension size: ");
		panel.add(label);
		pSpinner = new JSpinner(new SpinnerNumberModel(1,1,Integer.MAX_VALUE,1));
		panel.add(pSpinner);
		optionPanel.add(panel);
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Secondary dimension size (optional): ");
		panel.add(label);
		sSpinner = new JSpinner(new SpinnerNumberModel(0,0,Integer.MAX_VALUE,1));
		panel.add(sSpinner);
		optionPanel.add(panel);
		
		optionPanel.add(Box.createVerticalGlue());
		
		add(optionPanel, BorderLayout.WEST);
		
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
				TilingType type = (TilingType) tBox.getSelectedItem();
				DimConstant dimA = new DimConstant((int)pSpinner.getValue());
				DimConstant dimB = new DimConstant((int)sSpinner.getValue());
				maker.setGraphFunction(Tiling.construct(type, dimA, (dimB.eval() == 0) ? null : dimB));
				dialog.dispose();
			}
		});
		return button;
	}
}
