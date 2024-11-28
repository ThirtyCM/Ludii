package app.boardMaker.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;



public class BoardMakerMenuListener implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e)
	{
		JMenuItem source = (JMenuItem) e.getSource();
		System.out.println(source.getText());
	}

}
