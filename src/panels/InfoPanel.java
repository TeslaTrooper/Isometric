package panels;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

import constants.Constants;

/**
 * Über diese Klasse werden unterschiedlichste Informationen visuell für den Benutzer
 * dargestellt. Das <code>InfoPanel</code> kann dann durch Übergabe einer Klasse vom Typ
 * {@link Constants} das Layout, das sich innerhalb des Panels befindet, 
 * konfigurieren.
 * @author Stefan
 *
 */
public class InfoPanel extends JPanel {
	
	public String header = "Header";
	public String description = "Desc";
	
	private boolean solid = false;
	
	private JButton btClose;
	private JButton btAction;
	
	public InfoPanel() {
		this.setLayout(null);
		this.setBackground(Color.BLACK);
		this.setLocation(10, 10);
		this.setSize(200, 200);
		
		btClose = new JButton("Close");
		btClose.setBounds(120, 10, 70, 20);
		btClose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				InfoPanel.this.release();
				InfoPanel.this.hideP();
			}
		});
		this.add(btClose);
		
		btAction = new JButton("Durchsuchen");
		btAction.setBounds(this.getWidth()-(120+5), this.getHeight()-(20+5), 120, 20);
		this.add(btAction);
	}
	
	public void showP() {
		if(!this.isVisible()) {
			this.setVisible(true);
		}
	}
	
	public void hideP() {
		if(this.isVisible() && !solid) {
			this.setVisible(false);
		}
	}
	
	public void moveTo(int x, int y) {
		if(!solid) {
			this.setLocation(x, y);
		}
	}
	
	public void attach() {
		this.solid = true;
	}
	
	public void release() {
		this.solid = false;
	}
	
	public void loadRes(Constants constants) {
		//this.header = constants.header;
		//this.description = constants.description;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.WHITE);
		g.drawString(header, 5, 15);
		
		g.drawString(description, 5, 40);
	}
}
