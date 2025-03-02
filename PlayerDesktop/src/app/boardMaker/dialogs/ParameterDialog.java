package app.boardMaker.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import app.boardMaker.maker.Maker;
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
				JPanel tmp = (JPanel) Class.forName("app.boardMaker.panels."+tiling+"Panel").getConstructor(JDialog.class,Maker.class).newInstance(this,maker);
				tmp.setPreferredSize(new Dimension(400,500));
				p.add(tmp,BorderLayout.WEST);
				p.add(new JSeparator(SwingConstants.VERTICAL),BorderLayout.CENTER);
				// Add preview panel
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
