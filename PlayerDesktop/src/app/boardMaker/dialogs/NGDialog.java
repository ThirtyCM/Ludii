package app.boardMaker.dialogs;

import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import app.boardMaker.maker.Maker;
import app.boardMaker.window.BoardMakerPanel;
import game.types.play.ModeType;

public class NGDialog extends JDialog {
	
	private Maker maker;
	private JDialog dialog;
	private JTextField name;
	private JSpinner players;
	private JComboBox<ModeType> mode;
	
	public NGDialog(Maker maker, BoardMakerPanel bm) {
		super();
		
		this.maker = maker;
		this.dialog = this;
		
		setTitle("New game");
		setContentPane(createPanel(bm));
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(DEFAULT_MODALITY_TYPE);
		setResizable(false);
		
		requestFocus();
		pack();
		setLocationRelativeTo(rootPane);
		setVisible(true);
	}
	
	private JPanel createPanel(BoardMakerPanel bm) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel label;
		JPanel tmp;
		
		tmp = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Enter the name of the game: ");
		tmp.add(label);
		panel.add(tmp);
		
		tmp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		name = new JTextField();
		name.setPreferredSize(new Dimension(110, 25));
		tmp.add(name);
		panel.add(tmp);
		
		tmp = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Select the number of players: ");
		tmp.add(label);
		panel.add(tmp);
		
		tmp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		players = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		players.setPreferredSize(new Dimension(110, 25));
		tmp.add(players);
		panel.add(tmp);
		
		tmp = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("Select game mode: ");
		tmp.add(label);
		panel.add(tmp);
		
		tmp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		mode = new JComboBox<ModeType>(ModeType.values());
		mode.setPreferredSize(new Dimension(110, 25));
		tmp.add(mode);
		panel.add(tmp);
		
		tmp = new JPanel();
		JButton button = new JButton("Create");
		button.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				maker.createNewGame(name.getText(),(Integer)players.getValue(),(ModeType)mode.getSelectedItem());
				bm.showBoardMaker();
				dialog.dispose();
			}
		});
		tmp.add(button);
		panel.add(tmp);
		
		return panel;
	}
	
}
