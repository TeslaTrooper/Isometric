package core;

import handlers.GameKeyListener;
import handlers.GameMouseListener;
import handlers.InputHandler;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.SwingUtilities;

import enums.Direction;
import enums.EventType;
import enums.Keys;
import obstacles.*;
import panels.*;
import panels.editor.ConstructionToolsPanel;
import panels.editor.LandscapeObjectsPanel;
import actors.*;

/**
 * Diese Klasse stellt die Steuerung des gesamten Programms dar. Sie steuert alle 
 * logisch definierten Abl�ufe und nimmt Benutzereingaben entgegen und verarbeitet diese.
 * Die Klasse hat auch teilweise die Aufgabe der Speicherung und der Verwaltung 
 * aller anderen Objekte, die f�r den Ablauf des Programms notwendig sind. Mithilfe
 * dieser Klasse kann jedes Objekt, welches am Ablauf beteiligt ist erfasst und 
 * ausgewertet werden.
 * @author Stefan
 *
 */
public class GameLogic {
	
	/**
	 * Dieser Wert gibt die Anzahl an Tiles in x-Richtung an, die in dieser Spielwelt
	 * exestieren.
	 */
	public static final int sizeX = 50;
	
	
	
	/**
	 * Dieser Wert gibt die Anzahl an Tiles in y-Richtung an, die in dieser Spielwelt
	 * exestieren.
	 */
	public static final int sizeY = 50;
	
	
	
	/**
	 * Mithilfe dieser Werte wird der Anfang des ersten Feldes der Spielwelt festgelgt.
	 */
	private int offsetX = 370, offsetY = -270;
	
	
	
	/**
	 * Dieses zweidimensionale Array enth�lt das gesamte Spielfeld, welches nur aus
	 * {@link Tile} Objekten besteht.
	 */
	private Tile[][] grid = new Tile[sizeX][sizeY];
	
	
	
	/**
	 * In dieser Liste werden alle im Spiel existierenden Objekte abgelegt.
	 */
	private ArrayList<GameObject> objs;
	
	
	// Hier sind weitere Variablen
	private GameThread		thread;
	private PicLoader 		picLoader;
	private GamePanel 		gamePanel;
	private InputHandler 	inputHandler;
	private IsoTranslator 	translator;
	private Camera 			camera;
	
	
	
	/**
	 * �ber diese Methode wird ein neues Objekt erzeugt. Dieses Objekt steuert dann nach
	 * seiner Erzeugung den Spielablauf, nimmt Benutzereingaben entgegen und verarbeitet
	 * diese.
	 * @param gamePanel ist die Oberfl�che, auf der das gesamte Spiel visuell dargstellt
	 * wird. Diese Referenz wird ben�tigt, damit die Oberfl�che und die Steuerungseinheit
	 * eine bidirektionale Assoziation haben.
	 */
	public GameLogic(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		picLoader = new PicLoader();
		
		camera = new Camera();
		
		objs = new ArrayList<GameObject>();
		
		objs.add(new Player(new Point2D.Double(offsetX, offsetY), null, null));

		inputHandler = new InputHandler();
		
		setupLandscape();
		
		translator = new IsoTranslator(grid, this);
		
		thread = new GameThread(this);
		thread.enable();
		thread.start();
	}
	
	private void setupLandscape() {
		for(int y = 0; y < sizeY; y++) {
			for(int x = 0; x < sizeX; x++) {
				Random rand = new Random();
				int i = rand.nextInt(16);
				
				grid[x][y] = new Tile(offsetX, offsetY, 64, 32, picLoader.dirt[i]);
				offsetX += 32;
			}
			
			offsetX = 370;
			offsetY += 32;
		}
	}
	
	
	private void handleTileActivities(int mX, int mY, EventType eventType) {
		// Deaktiviere alle tiles
		for(int y = 0; y<grid.length; y++) {
			for(int x = 0; x<grid.length; x++) {
				grid[x][y].deActivate();
			}
		}
		
		for(int y = 0; y<grid.length; y++) {
			for(int x = 0; x<grid.length; x++) {
				if(false) { // Bedingung, ob mx und my in polygon liegen
					grid[x][y].activate();
					
					//if(mouseListener.isEventType(EventType.MOUSE_CLICKED)) {
					if(eventType == EventType.MOUSE_CLICKED) {
						ConstructionToolsPanel cPnl = gamePanel.getConstrucionToolsPanel();
						LandscapeObjectsPanel pnl = gamePanel.getConstrucionToolsPanel().getLandscapeObjectsPanel();
						
						if(cPnl.panelStates[0]) {
							if(pnl.isTexture) {
								grid[x][y].setPic(gamePanel.getConstrucionToolsPanel().getLandscapeObjectsPanel().currPic);
							} else if(pnl.isBush) {
								grid[x][y].setResident(new Bush(pnl.currPic));
							} else if(pnl.isStone) {
								grid[x][y].setResident(new Stone(pnl.currPic));
							} else if(pnl.isCliff) {
								grid[x][y].setResident(new Cliff(pnl.currPic));
							} else if(pnl.isDeco) {
								grid[x][y].setResident(new Decoration(pnl.currPic));
							}
						} else if(cPnl.panelStates[1]) {
							
						} else if(cPnl.panelStates[2]) {
							grid[x][y].changeLayer(cPnl.getTerrainPanel().direction);
						}
					}
					
					// Wenn ein Tile gefunden wurde, welches vom user angeklickt wurde,
					// dann kann die Methode hier abbrechen
					return;
					//configInfoPanel(grid[x][y], eventType);
				}
			}
		}
	}
	
	private void checkMouseEvents() {
		// Wenn im InputHandler keinerlei Events regisrtiert wurden,
		// dann wird auch keine weitere Operation durchgef�hrt.
		// --
		// Falls ein Event regisrtiert wurde, dann wird dieses behandelt.
		if(inputHandler.getMouseEvent() != null) {
			handleTileActivities(inputHandler.getMousePos().x, inputHandler.getMousePos().y, inputHandler.getMouseEvent());
		}
	}
	
	private void rotatePlayer() {
		// Holt sich das Player-Objekt
		Player ply = this.getPlayer();
		
		if(inputHandler.getKeyStatus() && ply != null) {
			// Dreht den Player, sofern eine Taste gedr�ckt wurde und ein Player existiert
			ply.rotateTo(inputHandler.getKeyEventTypes());
		} else {
			ply.rotateTo(null);
		}
	}
	
	private void checkCollisions() {
		checkObstacleCollisions();
	}
	
	private void checkObstacleCollisions() {
		Player ply = this.getPlayer();
		
		Point p = new Point((int) ply.getIsoPos().getX(), (int) ply.getIsoPos().getY());
		
		for(int y = 0; y<grid.length; y++) {
			for(int x = 0; x<grid[y].length; x++) {
				// Wenn das Tile ein Hinderniss hat
				if(grid[x][y].isInhabited()) {
					// Kollisionsrechtecke werden geladen
					Rectangle2D[] rects = grid[x][y].getRects();
					
					// Kollision wird festgestellt
					for(int i = 0; i<rects.length; i++) {
						if(rects[i].contains(p)) {
							// Index des Kollisionsrechtecks wird Spieler mitgeteilt
							ply.forbiddenAreaIndex = i;
						}
					}
					
					// Wenn Spieler nun in Hinderniss hineinlaufen m�chte
					//if(grid[x][y].contains(p)) {
						// Spieler wird mitgeteilt, dass er sich im verbotenen Bereich befindet
						//ply.isInsideForbiddenArea = true;
					//} else {
						// Spieler wird mitgeteilt, dass er au�erhalb des verbotenen Bereiches is
						//ply.isInsideForbiddenArea = false;
					//}
				}
			}
		}
	}
	
	private void moveObjects() {
		for(GameObject obj : objs) { // Durchl�uft alle GameObjects
			if(obj instanceof MobileObject) { // Falls dieses eine Instanz von MobileObject ist
				((MobileObject) obj).move();
			}
		}
	}
	
	
	private void moveCamera() {
		if(inputHandler.getCamKeyStatus()) {
			camera.move(inputHandler.getCamKeyEventTypes());
		} else {
			camera.clearStatus();
		}
	}

	
	private void updateScreen() {
		// Durch diesen Aufruf werden die Tiles in Abh�ngigkeit der aktuellen Kameraposition
		// als "Block" dargestellt, falls dies notwendig ist
		// Ohne diesen Aufruf wird nur die obere Fl�che des Tiles gezeichnet.
		this.translator.translate(this.camera.getCameraStatus());
		
		// Erzwingt das Neuzeichnen des JPanels
		// !?! Wird irgendwie in seperatem Thread durchgef�hrt !?!; 
		// => Deswegen exisitert Problem mit Flimmern.
		this.gamePanel.repaint();
		//this.gamePanel.paintImmediately(0, 0, this.gamePanel.getWidth(), this.gamePanel.getHeight());
	}
	
	/**
	 * �ber diese Methode wird der gesamte Ablauf des Programms gesteuert. Dazu geh�ren
	 * die Verarbeitung von Benutzereingaben, die Berechnung der Spiellogik und 
	 * die Ausgabe wesentlicher Informationen auf dem Bildschirm.
	 */
	public void doGameLogic() {
		/* 
		 * Diese Anweisung beweist, dass der repaint des JPanels scheinbar
		 * in einem von Swing seperatem Thread durchgef�hrt wird.
		 * Mit dieser Anweisung verschwindet das Flimmern, jedoch ist der Programmablauf
		 * stark gebremst, obwohl n�chster gameloop erst nach der Beendigung von repaint
		 * stattfinden d�rfte.
		 * => Ein Teil des repaints und der darauffolgende gameloop finden zur gleichen Zeit statt.
		 */
		if(true) {
			inputHandler.checkInputs();
			
			checkMouseEvents();
			
			rotatePlayer();
			
			moveObjects();
			
			moveCamera();
			
			checkCollisions();
			
	        updateScreen();
		}
		
	}
	
	
	
	/*
	 * ################################################
	 * # Getter-setter Methoden sind hier aufgelistet #
	 * ################################################
	 */
	
	
	/**
	 * Gibt ein bestimmtes {@link Tile} aus der Spielwelt zur�ck.
	 * @param x ist der x-Index des Tile-Objektes. Der Wertebereich liegt zwischen
	 * 0 und {@link #sizeX}.
	 * @param y ist der y-Index des Tile-Objektes. Der Wertebereich liegt zwischen
	 * 0 und {@link #sizeY}.
	 * @return Tile-Objekt wird zur�ckgegeben, sofern x -und y Index einen
	 * g�ltigen Bereich haben. Ansonsten wird das Tile mit dem Index 
	 * <code>0|0</code> zur�ckgegeben.
	 */
	public Tile getTile(int x, int y) {
		Tile tile;
		
		if(x >= sizeX || y >= sizeY) {
			tile = grid[0][0];
			return tile;
		} else {
			tile = grid[x][y];
			return tile;
		}
	}
	
	public int getFPS() {
		return thread.getFPS();
	}
	
	
	/**
	 * @return Gibt den Listener f�r die Maus zur�ck.
	 */
	public GameMouseListener getMouseListener() {
		return this.inputHandler.getMouseListener();
	}
	
	/**
	 * @return Gibt den Listener f�r die Tastatur zur�ck.
	 */
	public GameKeyListener getKeyListener() {
		return this.inputHandler.getKeyListener();
	}
	
	/**
	 * Diese Methode liefert alle Objekte vom Typ {@link GameObject}, die sich aktuell 
	 * im Speicher befinden.
	 * Dazu z�hlen sowohl {@link MobileObject}s, also auch {@link SolidObject}s.
	 * @return Gibt alle Objekte in einer {@link ArrayList} zur�ck.
	 */
	public ArrayList<GameObject> getObjectList() {
		return this.objs;
	}
	
	
	/**
	 * �ber diese Methode kann das {@link Player}-Objekt ermitelt werden, sofern eines Exestiert.
	 * @return Gibt das Player-Objekt zur�k.
	 */
	public Player getPlayer() {
		// Geht das GameObject Array durch und sucht das Player-Objekt
		for(GameObject obj : objs) {
			// Pr�ft, ob das aktulle Objekt ein Player-Objekt ist
			if(obj instanceof Player) {
				return (Player) obj;
			}
		}
		
		throw new NullPointerException("Es wurde kein Spielerobjekt gefunden!");
	}
	
	public PicLoader getPicLoader() {
		PicLoader tmp = this.picLoader;
		
		return tmp;
	}
	
	public Camera getCamera() {
		return this.camera;
	}
}
