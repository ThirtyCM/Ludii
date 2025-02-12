package app.boardMaker.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import app.boardMaker.maker.Maker;



public class BoardMakerMenuListener implements ActionListener
{
	private Maker maker;
	
	public BoardMakerMenuListener(Maker maker) {
		super();
		this.maker = maker;
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		JMenuItem source = (JMenuItem) e.getSource();
		System.out.println(source.getText());
		
		switch (source.getText())
		{
		case "Compile (NYI)":
			break;

		default:
			break;
		}
	}

}
