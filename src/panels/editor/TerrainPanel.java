package panels.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import enums.Direction;

public class TerrainPanel extends JPanel implements ActionListener {
	
	private JButton btnLayerUp, btnLayerDown;
	
	public boolean tileUp = false;
	public boolean tileDown = false;
	
	public Direction direction = Direction.UP;
	
	public TerrainPanel(int width, int height) {
		this.setLayout(null);
		this.setSize(width, height);
		this.setLocation(5, 25);
		this.setBackground(Color.blue);
		
		btnLayerUp = new JButton("Hoch");
		btnLayerUp.setBounds(5, 20, 70, 20);
		btnLayerUp.addActionListener(this);
		this.add(btnLayerUp);
		
		btnLayerDown = new JButton("Runter");
		btnLayerDown.setBounds(80, 20, 80, 20);
		btnLayerDown.addActionListener(this);
		this.add(btnLayerDown);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.black);
		g.drawString("Terrain", 5, 15);
		g.drawLine(3, 17, this.getWidth()-3, 17);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnLayerUp) {
			direction = Direction.UP;
		} else if(e.getSource() == btnLayerDown) {
			direction = Direction.DOWN;
		}
	}
}
