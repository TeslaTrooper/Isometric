package panels.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;

import core.GameLogic;
import core.PicLoader;

/**
 * Diese Klasse hat die Aufgabe alle Objekte, die zur Kategorie Landschaft gehören,
 * für den user übersichtlich darzustellen. Objekte der Klasse werden als {@link JPanel} 
 * auf dem Bildschirm dargestellt. Dieses Panel ist ein Bestandteil von {@link ConstructionToolsPanel}.
 * Außerdem kann der user Objekte aus dieser Liste auswählen und diese anschließend auf 
 * dem Spielfeld ({@link GameLogic#grid}) platzieren.
 * @author Stefan
 *
 */
@SuppressWarnings("serial")
public class LandscapeObjectsPanel extends JPanel implements ActionListener, MouseMotionListener, MouseListener {
	
	private PicLoader picLoader;
	
	private JButton btnScrollUp, btnScrollDown;
	private JButton btnDirt, btnCobbleStone, btnBush, btnStone, btnWater, btnWaterCliffs, btnDecoration;
	
	
	private int height;
	
	private BufferedImage[] pics;
	private Rectangle[] rects;
	
	private int mX, mY;
	
	private int currIndex = 0;
	public BufferedImage currPic;
	
	public boolean isTexture = true, isBush = false, isStone = false, isCliff = false, isDeco = false;
	
	public LandscapeObjectsPanel(int width, int height, PicLoader picLoader) {
		this.setLayout(null);
		this.setSize(width-10, height*3);
		this.setLocation(5, 25);
		this.setBackground(Color.green);
		
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		
		this.picLoader = picLoader;
		this.height = height;
		
		btnDirt = new JButton("Dirt");
		btnDirt.setBounds(5, 20, 70, 20);
		btnDirt.addActionListener(this);
		this.add(btnDirt);
		
		btnCobbleStone = new JButton("Boulders");
		btnCobbleStone.setBounds(80, 20, 85, 20);
		btnCobbleStone.addActionListener(this);
		this.add(btnCobbleStone);
		
		btnBush = new JButton("Bush");
		btnBush.setBounds(170, 20, 70, 20);
		btnBush.addActionListener(this);
		this.add(btnBush);
		
		btnStone = new JButton("Stone");
		btnStone.setBounds(5, 45, 70, 20);
		btnStone.addActionListener(this);
		this.add(btnStone);
		
		btnWater = new JButton("Water");
		btnWater.setBounds(80, 45, 70, 20);
		btnWater.addActionListener(this);
		this.add(btnWater);
		
		btnWaterCliffs = new JButton("WaterCliffs");
		btnWaterCliffs.setBounds(155, 45, 100, 20);
		btnWaterCliffs.addActionListener(this);
		this.add(btnWaterCliffs);

		btnDecoration = new JButton("Decoration");
		btnDecoration.setBounds(5, 70, 100, 20);
		btnDecoration.addActionListener(this);
		this.add(btnDecoration);
		
		btnScrollUp = new JButton("U");
		btnScrollUp.setBounds(this.getWidth()-60, 60, 50, 20);
		btnScrollUp.addActionListener(this);
		this.add(btnScrollUp);
		
		btnScrollDown = new JButton("D");
		btnScrollDown.setBounds(this.getWidth()-60, this.height-50, 50, 20);
		btnScrollDown.addActionListener(this);
		this.add(btnScrollDown);
		
		pics = picLoader.dirt;
		rects = new Rectangle[pics.length];
		currPic = pics[currIndex];
	}
	
	private void drawPics(Graphics g) {
		int num = pics.length;
		int x = 5;
		int y = 90;
		
		for(int i = 0; i<num; i++) {
			g.drawImage(pics[i], x, y, pics[i].getWidth(), pics[i].getHeight(), this);
			rects[i] = new Rectangle(x, y, pics[i].getWidth(), pics[i].getHeight());
			g.setColor(Color.BLACK);
			g.drawRect(rects[i].x, rects[i].y, rects[i].width, rects[i].height);
			
			if(i == currIndex) {
				g.setColor(Color.white);
				g.drawRect(rects[i].x, rects[i].y, rects[i].width, rects[i].height);
			}
			x+=64;
			
			if(x+pics[i].getWidth() > this.getWidth()-5) {
				x = 5;
				y+=pics[i].getHeight();
			}
		}
	}
	
	private void checkMousePos() {
		for(int i = 0; i<rects.length; i++) {
			if(rects[i].contains(new Point(mX, mY))) {
				currIndex = i;
				currPic = pics[currIndex];
			}
		}
	}
	
	private void setPics(BufferedImage[] pics) {
		this.pics = pics;
		rects = new Rectangle[pics.length];
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.black);
		g.drawString("Landschaft", 5, 15);
		g.drawLine(3, 17, this.getWidth()-3, 17);
		
		if(pics != null) {
			drawPics(g);
		}
	}
	
	private void resetAll() {
		isTexture = false;
		isBush = false;
		isStone = false;
		isCliff = false;
		isDeco = false;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnScrollDown) {
			btnScrollDown.setLocation(btnScrollDown.getX(), btnScrollDown.getY()+32);
			btnScrollUp.setLocation(btnScrollUp.getX(), btnScrollUp.getY()+32);
			this.setLocation(this.getX(), this.getY()-32);
		} else if(e.getSource() == btnScrollUp) {
			if(this.getY() < 25) {
				btnScrollUp.setLocation(btnScrollUp.getX(), btnScrollUp.getY()-32);
				btnScrollDown.setLocation(btnScrollDown.getX(), btnScrollDown.getY()-32);
				this.setLocation(this.getX(), this.getY()+32);
			}
		} else if(e.getSource() == btnDirt) {
			resetAll();
			isTexture = true;
			
			setPics(picLoader.dirt);
		} else if(e.getSource() == btnCobbleStone) {
			isTexture = true;
			isBush = false;
			isStone = false;
			isCliff = false;
			
			setPics(picLoader.cobbleStone);
		} else if(e.getSource() == btnBush) {
			resetAll();
			isBush = true;
			
			setPics(picLoader.bush);
		} else if(e.getSource() == btnStone) {
			resetAll();
			isStone = true;
			
			setPics(picLoader.stone);
		} else if(e.getSource() == btnWater) {
			resetAll();
			isTexture = true;
			
			setPics(picLoader.water);
		} else if(e.getSource() == btnWaterCliffs) {
			resetAll();
			isCliff = true;
			
			setPics(picLoader.waterCliffs);
		} else if(e.getSource() == btnDecoration) {
			resetAll();
			isDeco = true;
			
			setPics(picLoader.decoration1);
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.mX = e.getX();
		this.mY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		checkMousePos();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
