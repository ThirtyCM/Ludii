package app.boardMaker.window.TabbedPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardButtonListener implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e)
	{
		System.out.println(e.getActionCommand());
	}

}
