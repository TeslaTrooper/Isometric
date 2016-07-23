package core;

import java.util.ArrayList;

import enums.Keys;

/**
 * ‹ber die Kamera kann ermittelt werden, welcher Ausschnitt des Spielfeldes angezeigt,
 * bzw gezeichnet werden soll. Auﬂerdem beinhaltet die Kamera weitere Eigenschaften, wie
 * Zoom und Sichtweite.
 * @author Stefan
 *
 */
public class Camera {
	
	private double x;
	private double y;
	private double speed;
	
	private Vector2[] vecs;
	private Vector2 movement;
	private boolean cameraMoved;
	
	/**
	 * These constants are used to declare the direction in which the camera should move.
	 */
	public static final int TO_POSITIVE = -1, TO_NEGATIVE = 1;
	
	/**
	 * These constants are used to declare on which axis the camera should move.
	 */
	public static final int X_AXIS = 0, Y_AXIS = 1;
	
	// Diese Wert werden sp‰ter implementiert
	private double zoomFactor;
	private double fov;
	
	public Camera() {
		speed = 2.0;
		vecs = new Vector2[2];
		movement = new Vector2(0.0, 0.0);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public boolean getCameraStatus() {
		return this.cameraMoved;
	}
	
	public void clearStatus() {
		this.cameraMoved = false;
	}
	
	public void lookAt(int x, int y) {
		
	}
	
	public void move(ArrayList<Keys> events) {
		vecs[0] = null;
		vecs[1] = null;
		
		int ctr = 0;
		if(events.size() > 0) {
			this.cameraMoved = true;
			
			for(Keys k : events) {
				vecs[ctr] = k.getDirectionFromKey().getVelocityFromDirection();
				ctr++;
				
				movement.x = vecs[0].x;
				movement.y = vecs[0].y;
				
				if(vecs[1] != null) {
					movement.x += vecs[1].x;
					movement.y += vecs[1].y;
				}
				
				movement.norm();
				movement.sclr(speed);
			}
		} else {
			movement.reset();
		}
		
		this.x += movement.x;
		this.y += movement.y;
	}
}
