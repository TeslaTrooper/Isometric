package core;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enums.Direction;

/**
 * Diese Klasse definiert Eigenschaften und Verfahrensweisen zur Darstellung einer
 * "Bodenplatte" in Isometrischer Perspektive. Aus diesen Platten (Tiles) besteht 
 * die Spielwelt ({@link GameLogic#grid}). Ein Tile kann eine beliebige Textur, sowie Position annehmen. 
 * Ein Tile kann andere Objekte, die auf diesem Tile platziert werden, verwalten.
 * Ein Tile geh�rt zu den {@link SolidObject}s.
 * @author Stefan
 *
 */
public class Tile extends SolidObject {
	
	
	
	/**
	 * Dieses Objekt stellt den "Bewohner" dieses Tile-Objektes dar. Wenn bspw. eine Pflanze auf diesem
	 * Tile platziert wird, dann ist diese der Bewohner dieses Tiles.
	 */
	private SolidObject resident;
	
	
	
	/**
	 * Diese Variable gibt an, ob das Tile vom User aktiviert wurde.
	 */
	private boolean isActive = false;
	
	
	
	/**
	 * Mit dieser Farbe wird der Umriss des Polygons gezeichnet.
	 */
	private final Color frameClr = Color.CYAN;
	
	
	
	/**
	 * Mit dieser Farbe wird die linke Wand gezeichnet.
	 */
	private final Color LEFT_WALL_COLOR = Color.DARK_GRAY;
	
	
	
	/**
	 * Mit dieser Farbe wird die rechte Wand gezeichnet.
	 */
	private final Color RIGHT_WALL_COLOR = Color.BLACK;
	
	
	
	/**
	 * Gibt an, ob dieses Tile-Objekt mit Umriss gezeichnet werden soll.
	 */
	private boolean debug = false;
	
	
	
	/**
	 * Diese Variable gibt an, ob dieses Tile-Objekt ein update ben�tigt.
	 */
	private boolean updateRequired;
	
	
	// Das sind die Rechtecke, zur Erkennung in welcher Ecke sich ein Objekt relativ
	// zum Teil befindet
	// rect1 - linke obere Ecke
	// rect2 - rechte obere Ecke
	// rect3 - rechte untere Ecke
	// rect4 - linke untere Ecke
	private Rectangle2D[] rects = new Rectangle2D[4];
	
	// Mithilfe dieser Polygone werden die Seitenw�nde des Polygons gezeichnet
	public Polygon leftWall;
	public Polygon rightWall;
	
	
	
	public boolean drawLeftWall = false;
	public boolean drawRightWall = false;
	
	
	/**
	 * Diese Methode konstruiert ein neues Tile-Objekt mit den �bergebenen Eigenschaften.
	 * @param cartX ist die kartesische x-Koordinate.
	 * @param cartY ist die kartesische y-Koordinate.
	 * @param width ist die Breite des Tiles. �blicherweise betr�gt die Breite 64px, da das
	 * tileset so ausgelegt ist.
	 * @param height ist die H�he des Tiles. �blicherweise betr�gt die H�he 32px, da das
	 * tileset so ausgelegt ist.
	 * @param pic ist die Textur, die das Tile annehmen soll. Diese Textur kann �ber
	 * {@link PicLoader} ausgew�hlt werden.
	 */
	public Tile(int cartX, int cartY, int width, int height, BufferedImage pic) {
		super(new Point2D.Double(cartX, cartY), new Dimension(width, height), pic);
	}
	
	
	/**
	 * Diese Methode konstruiert ein neues Tile-Objekt mit den �bergebenen Eigenschaften.
	 * @param cartX ist die kartesische x-Koordinate.
	 * @param cartY ist die kartesische y-Koordinate.
	 * @param width ist die Breite des Tiles. �blicherweise betr�gt die Breite 64px, da das
	 * tileset so ausgelegt ist.
	 * @param height ist die H�he des Tiles. �blicherweise betr�gt die H�he 32px, da das
	 * tileset so ausgelegt ist.
	 * @param pic ist die Textur, die das Tile annehmen soll. Diese Textur kann �ber
	 * {@link PicLoader} ausgew�hlt werden.
	 * @param debug gibt an, ob der Umriss des Tiles gezeichnet werden soll.
	 */
	public Tile(int cartX, int cartY, int width, int height, BufferedImage pic, boolean debug) {
		super(new Point2D.Double(cartX, cartY), new Dimension(width, height), pic);
		this.debug = debug;
	}
	
	
	private void initPoints() {
		//this.reset(); // L�scht in Polygon alle zuvor hinzugef�gten Punkte
		
		// Die Positionen der 4 Eckpunkte werden berechnet
		// Oben
//		this.addPoint((int) (getIsoPos().getX()+(getSize().getWidth()/2)), (int) getIsoPos().getY());
//		// Rechts
//		this.addPoint((int) (getIsoPos().getX()+getSize().getWidth()), (int) (getIsoPos().getY()+(getSize().getHeight()/2)));
//		// Unten
//		this.addPoint((int) (getIsoPos().getX()+(getSize().getWidth()/2)), (int) (getIsoPos().getY()+getSize().getHeight()));
//		// Links
//		this.addPoint((int) (getIsoPos().getX()), (int) (getIsoPos().getY()+(getSize().getHeight()/2)));
	}
	
	private void initRects() {
		rects[0] = new Rectangle2D.Double(getIsoPos().getX(), getIsoPos().getY(), getSize().getWidth()/2, getSize().getHeight()/2);
		rects[1] = new Rectangle2D.Double(getIsoPos().getX()+getSize().getWidth()/2, getIsoPos().getY(), getSize().getWidth()/2, getSize().getHeight()/2);
		rects[2] = new Rectangle2D.Double(getIsoPos().getX()+getSize().getWidth()/2, getIsoPos().getY()+getSize().getHeight()/2, getSize().getWidth()/2, getSize().getHeight()/2);
		rects[3] = new Rectangle2D.Double(getIsoPos().getX(), getIsoPos().getY()+getSize().getHeight()/2, getSize().getWidth()/2, getSize().getHeight()/2);
	}
	
	/**
	 * �ber diese Methode wird ein neuer Resident f�r dieses Tile-Objekt definiert. 
	 * Nur Objekte vom Typ <code>ImmobileObject</code> k�nnen als Resident definiert werden.
	 * Mithilfe der Methode {@link #getResident()} kann dann der Resident, der sich 
	 * aktuell auf diesem Tile befindet, ermittelt werden.
	 * @param resident ist das neue Objekt, das auf diesem Tile wohnt.
	 */
	public void setResident(SolidObject resident) {
		embedResident(resident);
	}
	
	private void embedResident(SolidObject resident) {
		double tHeight = this.getSize().getHeight();
		double rHeight = resident.getSize().getHeight();
		
		double deltaHeight = rHeight - tHeight;
		
		resident.setCartPos(this.getCartPos().getX()-deltaHeight, this.getCartPos().getY()-deltaHeight);
		
		this.resident = resident;
	}
	
	/**
	 * Diese Methode liefert den aktuellen Resident dieses Tile-Objektes. Ein Resident 
	 * kann mithilfe der {@link #setResident(SolidObject)} Methode definiert werden.
	 * @return Gibt aktuellen Resident als <code>ImmobileObject</code> zur�ck.
	 */
	public SolidObject getResident() {
		return this.resident;
	}
	
	/**
	 * Diese Methode wird f�r eine genauere Kollisionserkennung ben�tigt. Sie gibt die
	 * Hitboxes zur�ck, mit denen dann erkannt werden kann, wo ein Objekt mit dem Tile
	 * kollidiert ist.
	 * @return gibt 4 Hitboxes zur�ck als <code>Rectangle2D</code>. Das Array ist 
	 * folgenderma�en codiert:<br>
	 * Index 0: Linke-obere Hitbox<br> Die verbleibenden 3 Boxes sind dann im Uhrzeigersinn
	 * angeordnet.
	 */
	public Rectangle2D[] getRects() {
		return rects.clone();
	}
	
	/**
	 * Diese Methode aktualisiert die 4 Eckpunkte {@link #points} des Polygons 
	 * und den {@link #resident} dieses Tile-Objektes, sofern dieser existiert.
	 */
	public void reCalculatePolygon() {
		initPoints();
		
		if(this.isInhabited()) {
			this.setResident(resident);
		}
	}
	
	/**
	 * Hebt das Tile-Objekt hervor, indem der Umriss gezeichnet wird. 
	 * <br> 
	 * Der Status des Tiles kann mit {@link #isActive()} abgefragt werden. <br>
	 * Zum Komplementieren des Zustandes existiert die Methode {@link #deActivate()}.
	 */
	public void activate() {
		this.isActive = true;
	}
	
	/**
	 * L�sst den Umriss der durch die {@link #activate()} Methode entstanden ist, 
	 * verschwinden.
	 */
	public void deActivate() {
		this.isActive = false;
	}
	
	/**
	 * Mithilfe dieser Methode kann ermittelt werden, ob das Tile �ber die 
	 * Methode {@link #activate()} aktiviert wurde.
	 * @return true, wenn das Tile aktiv ist.
	 */
	public boolean isActive() {
		return this.isActive;
	}
	
	/**
	 * Diese Methode wird verwendet, um Informationen �ber den Resident zu erhalten.
	 * Sie ermittelt, ob es ein Resident auf diesem Tile gibt. Ein Tile-Objekt erh�lt 
	 * einen Resident durch den Aufruf der {@link #setResident(SolidObject)} Methode.
	 * @return true, wenn ein Resident auf diesem Objekt existiert.
	 */
	public boolean isInhabited() {
		return this.getResident() != null;
	}
	
	/**
	 * Diese Methode gibt Informationen dar�ber, ob dieses Objekt in Bezug auf dessen
	 * Darstellung in der Spielwelt, neu berechnet werden muss.
	 * @return <code>true</code>, wenn das Tile-Objekt neu berechnet werden muss.
	 */
	public boolean isUpdateRequired() {
		boolean tmp = this.updateRequired;
		this.updateRequired = false;
		
		return tmp;
	}
	
	
	/**
	 * Mithilfe dieser Methode l�sst sich ein <code>Tile</code> Objekt entweder
	 * nach oben, oder nach unten verschieben.
	 * @param direction gibt die Verschiebungsrichtung an.
	 */
	public void changeLayer(Direction direction) {
		// Ermittelt aktuelle kartesische Koordianten
		double cartX = super.getCartPos().getX();
		double cartY = super.getCartPos().getY();
		
		if(direction == Direction.UP) {
			// Verschiebt das Tile-Objekt um eine H�heneinheit (32px) nach oben
			super.setCartPos(cartX-(1*32), cartY-(1*32));
		} else if(direction == Direction.DOWN) {
			// Verschiebt das Tile-Objekt um eine H�heneinheit (32px) nach unten
			super.setCartPos(cartX+(1*32), cartY+(1*32));
		}
		
		super.setLayer(direction);
		
		// Wenn dieses Tile-Objekt einen Resident besitzt, dann muss dieser
		// ebenfalls mit nach oben verschoben werden
		if(this.isInhabited()) {
			this.embedResident(this.getResident());
		}
		
		// Die 4 Eckpunkte m�ssen nach der Verschiebung neu berechnet werden
		reCalculatePolygon();
		
		this.updateRequired = true;
	}
	
	
	public void createWall(Point2D[] points, Polygon poly) {
		if(poly != null) {
			poly.reset();
			
			for(Point2D point : points) {
				poly.addPoint((int) point.getX(), (int) point.getY());
			}
		}
	}
	
	@Override
	public void updateCameraPos(int camX, int camY) {
		super.updateCameraPos(camX, camY);
		reCalculatePolygon();
	}
	
	
	/**
	 * Beim Aufruf dieser Methode werden alle Seitenw�nde gezeichnet, die f�r die 
	 * perspektifisch korrekte Darstellung notwendig sind.
	 * @param g ist der Grafikkontext, der f�r die Zeichenoperation zust�ndig ist.
	 */
	public void drawWalls(Graphics2D g) {
		if(this.drawLeftWall) {
			g.setColor(LEFT_WALL_COLOR);
			g.fillPolygon(leftWall);
		}
		
		if(this.drawRightWall) {
			g.setColor(RIGHT_WALL_COLOR);
			g.fillPolygon(rightWall);
		}
	}
	
	
	private void drawBorders(Graphics2D g) {
		// Zeichnet den Rand des Tiles, wenn der user dieses aktiviert
		if (isActive || debug) {
			g.setColor(frameClr);

			// Linie von oben nach rechts
			g.drawLine((int) (getIsoPos().getX()+(getSize().getWidth()/2)), (int) getIsoPos().getY(), 
					(int) (getIsoPos().getX()+getSize().getWidth()), (int) (getIsoPos().getY()+(getSize().getHeight()/2)));
			// Linie von Rechts nach unten
			g.drawLine((int) (getIsoPos().getX()+getSize().getWidth()), (int) (getIsoPos().getY()+(getSize().getHeight()/2)), 
					(int) (getIsoPos().getX()+(getSize().getWidth()/2)), (int) (getIsoPos().getY()+getSize().getHeight()));
			// Linie von unten nach links
			g.drawLine((int) (getIsoPos().getX()+(getSize().getWidth()/2)), (int) (getIsoPos().getY()+getSize().getHeight()), 
					(int) (getIsoPos().getX()), (int) (getIsoPos().getY()+(getSize().getHeight()/2)));
			// Linie von links nach oben
			g.drawLine((int) (getIsoPos().getX()), (int) (getIsoPos().getY()+(getSize().getHeight()/2)), 
					(int) (getIsoPos().getX()+(getSize().getWidth()/2)), (int) getIsoPos().getY());
		}
	}
	
	/**
	 * Diese Methode zeichnet das <code>Tile</code>-Objekt, ohne Seitenw�nde. Folglich wird beim
	 * Aufruf dieser Methode nur das Polygon (die Oberseite des W�rfels) gezeichnet.
	 * @param g ist der Grafikkontext, der f�r das Zeichnen des Objektes verwendet
	 * werden soll.
	 */
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		
		drawBorders(g);
		//drawWalls(g);
	}
}
