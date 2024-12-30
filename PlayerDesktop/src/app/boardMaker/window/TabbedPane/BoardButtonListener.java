package app.boardMaker.window.TabbedPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.boardMaker.maker.Maker;
import game.functions.dim.DimConstant;
import game.functions.graph.generators.basis.square.Square;

public class BoardButtonListener implements ActionListener
{
	
	private Maker maker;
	
	public BoardButtonListener(Maker maker) {
		super();
		
		this.maker = maker;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		System.out.println(e.getActionCommand());
		
		switch (e.getActionCommand()) {
			case "Square" : 
				maker.setGraphFunction(Square.construct(null,new DimConstant(3),null,null));
				break;
			default : System.out.println("Not yet.");
		}
	}

}
