package enums;

import core.Vector2;


/**
 * In dieser Enumeration werden alle Bewegungsrichtungen aufgelistet, die ein Objekt haben kann.
 * Jede Bewegungrichtung hat einen eigenen Richtungsvektor.
 * @author Stefan
 *
 */
public enum Direction {
	
	UP (new Vector2(-1.0, -1.0)),
	RIGHT (new Vector2(1.0, -1.0)),
	DOWN (new Vector2(1.0, 1.0)),
	LEFT (new Vector2(-1.0, 1.0)),
	
	DOWN_RIGHT (new Vector2(1.0, 0.0)),
	DOWN_LEFT (new Vector2(0.0, 1.0)),
	UP_LEFT (new Vector2(-1.0, 0.0)),
	UP_RIGHT (new Vector2(0.0, -1.0)),
	
	NONE (new Vector2(0.0, 0.0));
	
	private Vector2 vector;
	
	private Direction(Vector2 vec) {
		this.vector = vec;
	}
	
	
	/**
	 * �ber diese Methode kann der Richtungsvektor in Abh�ngigkeit der Richtung ermittelt werden.
	 * @return Gibt einen {@link Vector2} zur�ck.
	 */
	public Vector2 getVelocityFromDirection() {
		return vector;
	}
	
	
	
	/**
	 * �ber diese Methode kann die Richtung in Abh�ngigkeit des �bergebenen Vektors ermittelt werden.
	 * @param v ist der {@link Vector2}, f�r den die Richtung bestimmt werden soll.
	 * @return Gibt die passende {@link Direction} zur�ck. Der R�ckgabewert kann <b>niemals</b>
	 * <code>null</code> werden.
	 */
	public static Direction getDirectionFromVelocity(Vector2 v) throws NullPointerException {
		if(v != null) {
			int x = (int) v.x;
			int y = (int) v.y;
			
			if(x > 0 && y > 0) {
				return DOWN;
			} else if(x > 0 && y < 0) {
				return RIGHT;
			} else if(x < 0 && y < 0) {
				return UP;
			} else if(x < 0 && y > 0) {
				return LEFT;
			} else if(x > 0 && y == 0) {
				return DOWN_RIGHT;
			} else if(x == 0 && y > 0) {
				return DOWN_LEFT;
			} else if(x < 0 && y == 0) {
				return UP_LEFT;
			} else {
				return UP_RIGHT;
			}
		} else {
			throw new NullPointerException("Der �bergebene Wert ist null");
		}
	}
}
