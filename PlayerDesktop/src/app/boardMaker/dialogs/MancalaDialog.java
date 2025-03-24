package app.boardMaker.dialogs;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;

import app.boardMaker.maker.Maker;
import app.boardMaker.panels.MancalaPanel;
import app.boardMaker.panels.ParameterPanel;
import app.boardMaker.previews.MancalaPreview;
import app.boardMaker.previews.PreviewPanel;
import game.equipment.container.board.custom.MancalaBoard;

public class MancalaDialog extends JDialog
{
	private Maker maker;
	
	public MancalaDialog(Maker maker) {
		super();
		
		this.maker = maker;
		
		setContentPane(createPanel());
		setTitle("Mancala board");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setModalityType(DEFAULT_MODALITY_TYPE);
		
		requestFocus();
		pack();
		setLocationRelativeTo(rootPane);
		setVisible(true);
	}
	
	private JPanel createPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		MancalaPreview preview = new MancalaPreview(maker);
		panel.add(new MancalaPanel(this, maker, preview),BorderLayout.WEST);
		panel.add(preview,BorderLayout.EAST);
		return panel;
	}
}
