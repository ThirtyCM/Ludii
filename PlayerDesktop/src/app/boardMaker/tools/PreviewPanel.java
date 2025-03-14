package app.boardMaker.tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import app.boardMaker.maker.Maker;
import app.boardMaker.panels.ParameterPanel;

public class PreviewPanel extends JPanel implements ActionListener, ChangeListener
{
	
	private ParameterPanel params;
	private Maker maker;
	
	public PreviewPanel(Maker maker) {
		this.maker = maker;
	}
	
	public void setParams(ParameterPanel params) {
		this.params = params;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		params.createBoard();
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		// TODO Auto-generated method stub
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		maker.drawPreview(g2d, getWidth(), getHeight(),params.getGraph());
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		// TODO Auto-generated method stub
		params.createBoard();
		repaint();
	}
	
	

}
