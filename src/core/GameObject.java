package core;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import enums.Direction;

/**
 * Diese Klasse fasst alle Objekte, mit denen interagiert werden kann, zusammen. Hier werden
 * die elementarsten Eigenschaften und Verfahrensweisen definiert, die alle anderen
 * Objekte ebenfalls besitzen. Diese Klasse erbt von {@link Polygon}, somit wird 
 * jedes Objekt des Programms aus einem Polygon abgeleitet.
 * @author Stefan
 *
 */
public abstract class GameObject {
	
	
	
	/**
	 * Das ist die aktuelle Position des Objektes auf dem Feld in <b>kartesischen</b> Koordianten.
	 */
	private Point2D cartPos;
	
	
	
	/**
	 * Dieses Bild wird f�r das Objekt verwendet.
	 */
	private BufferedImage pic;
	
	
	
	/**
	 * Breite und H�he des Objektes (Ma�e des Bildes)
	 */
	private Dimension2D size;
	
	
	
	/**
	 * In dieser Variable wird die aktuelle Ebene, auf der sich das Objekt befindet, gespeichert.
	 */
	private int layer;
	
	
	
	protected int cameraX, cameraY;
	
	/**
	 * Erzeugt ein neues <code>GameObject</code>. �ber diese Klasse werden die allgemeinsten
	 * Eigenschaften von Objekten dargestellt. Dazu z�hlen Position, Gr��e und die zu
	 * verwendende Textur.
	 * @param cartPos ist die Position des Objektes.
	 * @param size ist die Gr��e des Objektes.
	 * @param pic ist die Textur, die f�r dieses Objekt verwendet werden soll.
	 */
	public GameObject(Point2D cartPos, Dimension2D size, BufferedImage pic) {
		this.cartPos = cartPos;
		this.size = size;
		this.pic = pic;
	}
	
	/**
	 * Wandelt eine kartesische Koordinate in eine isometrische Koordinate um.
	 * @param cartPos ist die kartesische Koordinate, die umgewandelt werden soll
	 * @return Gibt die isometrische Koordinate als Point2D zur�ck.
	 */
	private Point2D twoDToIso(Point2D cartPos) {
		Point2D isoPos = new Point2D.Double();
		
		double x = cartPos.getX() - cartPos.getY();
		double y = (cartPos.getX() + cartPos.getY()) / 2;
		
		isoPos.setLocation(x, y);
		
		return isoPos;
	}
	
	/**
	 * Wandelt eine isometrische Koordinate in eine kartesische Koordinate um.
	 * @param isoPos ist die isometrische Koordinate, die umgewandelt werden soll.
	 * @return Gibt die kartesische Koordinate als Point2D zur�ck.
	 */
	private Point2D isoTo2D(Point2D isoPos) {
		  Point2D cartPos = new Point2D.Double();
		  
		  double x = (2 * isoPos.getY() + isoPos.getX()) / 2;
		  double y = (2 * isoPos.getY() - isoPos.getX()) / 2;
		  
		  cartPos.setLocation(x, y);
		  
		  return cartPos;
	}
	
	/**
	 * @return Gibt die aktuelle kartesische Position des Objektes zur�ck. Diese Position 
	 * entspricht nicht der Position auf dem Bildschirm, sondern repr�sentiert die Position 
	 * ohne isometrischer Transformation.
	 */
	public Point2D getCartPos() {
		return cartPos;
	}
	
	/**
	 * �ber diese Methode kann die Position des Objektes ge�ndert werden. Der �bergebene Wert
	 * stellt die neue Position dar.
	 * @param x ist die neue x-Koordinate.
	 * @param y ist die neue y-Koordinate.
	 */
	public void setCartPos(double x, double y) {
		cartPos = new Point2D.Double(x, y);
	}
	
	/**
	 * @return Gibt die aktuelle isometrische Position des Objektes zur�ck. Diese 
	 * Position entspricht der Position auf dem Bildschirm.
	 */
	public Point2D getIsoPos() {
		return twoDToIso(cartPos);
	}
	
	/**
	 * @return Gibt die aktuelle Gr��e des Objektes zur�ck.
	 */
	public Dimension2D getSize() {
		return size;
	}
	
	/**
	 * @return Gibt das Bild, das von dem Objekt verwendet wird, zur�ck.
	 */
	public BufferedImage getPic() {
		return this.pic;
	}
	
	/**
	 * �ber diese Methode kann die zu verwendende Textur ge�ndert werden.
	 * @param pic ist die neue Textur.
	 */
	public void setPic(BufferedImage pic) {
		this.pic = pic;
	}
	
	public void setLayer(Direction direction) {
		if(direction == Direction.UP) {
			this.layer++;
		} else if(direction == Direction.DOWN) {
			this.layer--;
		}
	}
	
	public int getLayer() {
		return this.layer;
	}
	
	public void updateCameraPos(int camX, int camY) {
		int deltaCamX = cameraX - camX;
		int deltaCamY = cameraY - camY;
		
		this.cameraX = camX;
		this.cameraY = camY;
		
		this.setCartPos(this.getCartPos().getX() - deltaCamX, this.getCartPos().getY() - deltaCamY);
	}
	
	/**
	 * �ber diese Methode kann das Objekt gezeichnet werden. Dazu wird ein Grafikkontext ben�tigt.
	 * @param g ist der Grafikkontext, der das Objekt zeichnet.
	 * gezeichnet wird.
	 */
	public abstract void draw(Graphics2D g);
}
