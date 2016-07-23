package panels.editor;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class ItemsPanel extends JPanel {
	public ItemsPanel(int width, int height) {
		this.setLayout(null);
		this.setSize(width, height);
		this.setLocation(5, 25);
		this.setBackground(Color.pink);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.black);
		g.drawString("Items", 5, 15);
		g.drawLine(3, 17, this.getWidth()-3, 17);
	}
}
