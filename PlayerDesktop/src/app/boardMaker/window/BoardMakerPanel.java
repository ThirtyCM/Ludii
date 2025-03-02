package app.boardMaker.window;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import app.boardMaker.dialogs.NGDialog;
import app.boardMaker.maker.Maker;
import app.boardMaker.window.TabbedPane.BoardMakerTabbedBar;
import app.boardMaker.window.boardpanel.BoardPanel;

/**
 * Main panel of the frame
 **/

public class BoardMakerPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private Maker maker;
	private BoardMakerPanel bm;
	
	private BoardMakerTabbedBar tb;
	private BoardPanel bp;
	private JPanel welcomePanel;
	
	public BoardMakerPanel(Maker maker) {
		super(new BorderLayout());
		
		this.maker = maker;
		bm = this;
		
		welcomePanel = createWelcome();
		tb = new BoardMakerTabbedBar(maker);
		bp = new BoardPanel(maker);
		
		add(welcomePanel, BorderLayout.CENTER);
		
		setOpaque(true);
	}
	
	private JPanel createWelcome() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.add(Box.createVerticalGlue());
		
		JLabel message = new JLabel("Welcome in Ludii's Board Maker.");
		message.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(message);
		
		message = new JLabel("To begin, click on the button below to create a new game.");
		message.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(message);
		
		JButton button = new JButton("New game");
		button.setAlignmentX(CENTER_ALIGNMENT);
		button.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				JDialog dialog = new NGDialog(maker,bm);
			}
		});
		panel.add(button);
		
		panel.add(Box.createVerticalGlue());
		
		return panel;
	}
	
	public void showBoardMaker() {
		remove(welcomePanel);
		
		add(tb,BorderLayout.NORTH);
		add(bp,BorderLayout.CENTER);
		
		revalidate();
	}
	
	public BoardPanel bp() {
		return bp;
	}
	
	public BoardMakerTabbedBar tb() {
		return tb;
	}
}
