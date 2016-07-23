package core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import panels.InfoPanel;
import panels.editor.ConstructionToolsPanel;

/**
 * Über diese Klasse wird alles gezeichnet, was für die Visualisierung des Spieles
 * notwendig ist. Die Klasse besteht nur aus einem JPanel und zeichnet auf Anforderung
 * alles neu. Die virtuelle Kamera wird auch über diese Klasse erzeugt und kann bei Bedarf 
 * abgefragt werden. Alle notwendigen Informationen zum Zeichnen der Oberfläche werden
 * über die {@link GameLogic} erfasst.
 * @author Stefan
 *
 */
public class GamePanel extends JPanel {
	private GameLogic gameLogic;
	private InfoPanel infoPanel;
	private ConstructionToolsPanel toolsPanel;
	private Camera camera;
	private BufferedImage bImage;
	private Graphics2D gdb;
	
	/**
	 * Erzeugt ein neues GamePanel. Es zeichnet alle Objekte des Spieles.
	 * @param width ist die breite des Panels.
	 * @param height ist die Höhe des Panels.
	 */
	public GamePanel(int width, int height) {
		gameLogic = new GameLogic(this);
		this.camera = gameLogic.getCamera();
		
		this.addMouseMotionListener(gameLogic.getMouseListener());
		this.addMouseListener(gameLogic.getMouseListener());
		
		this.addKeyListener(gameLogic.getKeyListener());
		
		this.setLayout(null);
		this.setSize(width, height);
		this.setDoubleBuffered(true);
		
		bImage = new BufferedImage(this.getWidth(), this.getHeight(),
	            BufferedImage.TYPE_INT_RGB);
		
		gdb = bImage.createGraphics();
		
		//infoPanel = new InfoPanel();
		//this.add(infoPanel);
		
		//toolsPanel = new ConstructionToolsPanel(width, height, gameLogic.getPicLoader());
		//this.add(toolsPanel);
		
		this.setFocusable(true);
	}
	
	public InfoPanel getInfoPanel() {
		return this.infoPanel;
	}
	
	public ConstructionToolsPanel getConstrucionToolsPanel() {
		return this.toolsPanel;
	}
	
	private void paintGrid(Graphics2D g) {
		Tile activeTile = null;
		
		for(int y = 0; y < GameLogic.sizeY; y++) {
			for(int x = 0; x < GameLogic.sizeX; x++) {
				if(gameLogic.getTile(x, y).isActive()) {
					activeTile = gameLogic.getTile(x, y);
				} else {
					gameLogic.getTile(x, y).draw(g);
				}
			}
		}
			
		//paintLandscape(g, 0);
		
		if(activeTile != null) {
			activeTile.draw(g);
			
			if(activeTile.isInhabited()) {
				activeTile.getResident().draw(g);
			}
		}
	}
	
	
	private void paintLandscape(Graphics2D g, int layer) {
		for(int y = 0; y < GameLogic.sizeY; y++) {
			for(int x = 0; x < GameLogic.sizeX; x++) {
				Tile tile = gameLogic.getTile(x, y);
				
				if(tile.isInhabited() && !tile.isActive()) {
					///tile.getResident().draw(g);
				}
			}
		}
	}
	
	private void paintStrings(Graphics2D g) {
		g.setColor(Color.red);
		
		for(int y = 0; y < GameLogic.sizeY; y++) {
			for(int x = 0; x < GameLogic.sizeX; x++) {
				// Ebene des Tile-Objektes
				int z = gameLogic.getTile(x, y).getLayer();
				
				
				if(gameLogic.getTile(x, y).isActive()) {
					g.drawString("X: " + x + " | Y: " + y + " | Z: " + z, 10, 15);
				}
			}
		}
		
		g.drawString("Camera: X: " + (int) camera.getX() + " Y: " + (int) camera.getY(), 10, 30);
		g.drawString("FPS: " + gameLogic.getFPS(), 10, 45);
		
		g.drawString("North", 980, 190);
		g.drawString("East", 980, 620);
		g.drawString("South", 290, 620);
		g.drawString("West", 290, 190);
		
		g.drawLine(700, 43, 870, 130);
		g.drawString("X-Axis", 785, 80);
		
		g.drawLine(650, 43, 480, 130);
		g.drawString("Y-Axis", 540, 80);
		
		g.drawString("Facing: " + gameLogic.getPlayer().facing.toString(), 10, 60);
		
	}
	
	private void paintObjects(Graphics2D g) {
		ArrayList<GameObject> list = gameLogic.getObjectList();
		
		for(GameObject obj : list) {
			obj.draw(g);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//super.paintComponent(g);
		
		g.drawImage(bImage, 0, 0, null);
		
		gdb.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gdb.setColor(Color.GRAY);
		gdb.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		paintGrid(gdb);
		paintStrings(gdb);
		paintObjects(gdb);
		
		g.dispose();
	}
	
	
	
}
