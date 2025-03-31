package app.boardMaker.window.TabbedPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import app.boardMaker.maker.Maker;

public class FuncButtonListener implements ActionListener
{
	private Maker maker;
	
	public FuncButtonListener(Maker maker) {
		this.maker = maker;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (maker.getGame() == null) {
			JOptionPane.showMessageDialog(maker.getFrame(), "Functions can only be applied on boards.", "Inane error", JOptionPane.ERROR_MESSAGE);
		} else {
			String func = e.getActionCommand();
			// TODO Auto-generated method stub
			System.out.println(func);
			
			switch (func)
			{
			case "Dual":
				maker.computeDual();
				break;

			default:
				break;
			}
		}
	}

}
