package core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enums.Direction;
import enums.Keys;

/**
 * Diese Klasse fasst alle Objekte mit den Eigenschaften eines beweglichen Objektes 
 * zusammen. Unter einem mobilen, oder beweglichen Objekt versteht man ein Objekt, 
 * das zur Laufzeit seine Position ändern kann. Desweiteren hat ein <code>MobileObject</code>
 * eine Blickrichtung. Diese Richtung gibt an, welcher Himmelsrichtung das Objekt 
 * zugewandt ist. Diese Eigenschaft ist notwendig, da ein <code>MobileObject</code>
 * auch zur Laufzeit sein Orientierung ändern kann.
 * @author Stefan
 *
 */
public abstract class MobileObject extends GameObject {
	
	
	/**
	 * In dieser Variable wird die aktuelle Blickrichtung des Objektes gespeichert.
	 * Die Richtung ist vom Typ {@link Direction}.
	 */
	public Direction facing = Direction.NONE;
	
	
	
	/**
	 * In Abhängigkeit von diesem {@link Vector2} bewegt sich das Objekt.
	 */
	private Vector2 movement;
	
	
	
	/**
	 * In dem Array werden die Geschwindigkeitskomponenten in x bzw. in y-Richtung
	 * abgelegt.
	 */
	private Vector2[] vecs = new Vector2[2];
	
	
	
	/**
	 * Gibt an, wie viele Pixel pro loop bewegt werden soll
	 */
	private final double speed = 2.0;
	
	
	
	public int forbiddenAreaIndex = 0;
	public boolean isInsideForbiddenArea = false;
	
	
	
	/**
	 * Erzeugt ein neues Objekt vom Typ {@link MobileObject}.
	 * @param cartPos ist die x und y Koordinate in kartesischer Angabe.
	 * @param size ist die Größe des Objektes.
	 * @param pic ist die Textur, die für dieses Objekt verwendet werden soll.
	 */
	public MobileObject(Point2D cartPos, Dimension2D size, BufferedImage pic) {
		super(cartPos, size, pic);
		
		movement = new Vector2(0.0, 0.0);
	}
	
	/**
	 * Über diese Methode wird das Objekt in die entsprechende Richtung gedreht, in die
	 * es sich bewegen soll. Anschließend werden die Vektoren für die Bewegung berechnet.
	 * Bei Aufruf dieser Methode wird die Position des Objektes <b>nicht</b> verändert.
	 * @param events stellt eine Liste von Tasteneingaben dar, mit denen die 
	 * Vektoren für die Bewegung bestimmt werden können.
	 */
	public void rotateTo(ArrayList<Keys> events) {
		// Bewegungsvektor löschen
		movement.reset();
		// Neue Vektoren anlegen
		vecs[0] = new Vector2(0.0, 0.0);
		vecs[1] = new Vector2(0.0, 0.0);
		
		// Wenn es events gibt und die Liste min. 1 Element besitzt
		if(events != null && (events.size() > 0 && events.size() < 3)) {
			// Nun wird durch die Liste der events iteriert
			for(int i = 0; i<events.size(); i++) {
				// Definiert über event den aktuellen Vektor
				vecs[i] = events.get(i).getDirectionFromKey().getVelocityFromDirection();
				
				// Dieser Vektor wird dann zum Gesamtvektor
				movement.x += vecs[i].x;
				movement.y += vecs[i].y;
				
				// Vektor auf die Geschwindigkeit des Objektes skalieren
				movement.norm();
				movement.sclr(speed);
				
				facing = Direction.getDirectionFromVelocity(movement);
			}
		} else {
			stop();
		}
	}
	
	/**
	 * Über diese Methode wird das Objekt um den aktuellen Richtungsvektor verschoben.
	 */
	public void move() {
		Point2D oldPos = super.getCartPos();
		
		if(!isInsideForbiddenArea) {
			this.setCartPos(oldPos.getX()+movement.x, oldPos.getY()+movement.y);
		} else {
			handleObstacleCollision(oldPos);
		}
	}
	
	public void handleObstacleCollision(Point2D oldPos) {
		// Linke obere Ecke oder rechte untere Ecke
		if(forbiddenAreaIndex == 0 || forbiddenAreaIndex == 2) {
			this.setCartPos(oldPos.getX(), oldPos.getY()+movement.y);
		}
		
		// Rechte obere Ecke oder linke untere Ecke
		if(forbiddenAreaIndex == 1 || forbiddenAreaIndex == 3) {
			this.setCartPos(oldPos.getX()+movement.x, oldPos.getY());
		}
	}
	
	/**
	 * Bei Aufruf dieser Methode beendet das Objekt seine aktuelle Bewegung.
	 */
	public void stop() {
		movement.reset();
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillOval((int) super.getIsoPos().getX(), (int) super.getIsoPos().getY(), 10, 10);
	}

}
