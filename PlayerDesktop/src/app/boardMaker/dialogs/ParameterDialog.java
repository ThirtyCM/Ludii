package app.boardMaker.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import app.boardMaker.maker.Maker;
import app.boardMaker.panels.ParameterPanel;
import app.boardMaker.tools.PreviewPanel;
import app.boardMaker.window.TabbedPane.BoardTiling;

public class ParameterDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
	
	public ParameterDialog(Maker maker) {
		super();
				
		setContentPane(createParameterPanel(maker));
		setTitle("Board features");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(800,500));
		setResizable(false);
		setModalityType(DEFAULT_MODALITY_TYPE);
		
		requestFocus();
		pack();
		setLocationRelativeTo(rootPane);
		setVisible(true);
	}
	
	/**
	 * Create panel of board parameter dialog
	 * @return board parameter panel
	 */
	public JPanel createParameterPanel(Maker maker) {
		JPanel panel = new JPanel(new BorderLayout());
		
		JTabbedPane tPane = new JTabbedPane();
		for (BoardTiling tiling : BoardTiling.values()) {
			try {
				JPanel p = new JPanel(new BorderLayout());
				PreviewPanel prev = new PreviewPanel(maker);
				ParameterPanel tmp = (ParameterPanel) Class.forName("app.boardMaker.panels."+tiling+"Panel").getConstructor(JDialog.class,Maker.class,PreviewPanel.class).newInstance(this,maker,prev);
				prev.setParams(tmp);
				tmp.setPreferredSize(new Dimension(400,500));
				prev.setPreferredSize(new Dimension(400,500));
				p.add(tmp,BorderLayout.WEST);
				p.add(prev,BorderLayout.EAST);
				tPane.addTab(tiling.name(), p);
			}
			catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException
						| ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		panel.add(tPane,BorderLayout.CENTER);
		
		return panel;
	}
}
