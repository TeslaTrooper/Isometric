package core;

public class Vector2 {
	
	public double x;
	public double y;
	
	/**
	 * Erzeugt einen neuen Vektor der auf den Punkt x|y zeigt.
	 * @param x
	 * @param y
	 */
	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Diese Methode ermittelt die Länge des Vektors
	 * @return die Länge des Vektors als <code>double</code>.
	 */
	public double len() {
		return Math.sqrt(x*x+y*y);
	}
	
	/**
	 * Normiert den Vektor auf die Länge 1.
	 */
	public void norm() {
		double length = len();
		
		if(length == 0) {
			length = 1e-10;
		}
		x *= 1.0/length;
		y *= 1.0/length;
	}
	
	/**
	 * Diese Methode skaliert einen Vektor um einen beliebigen Wert.
	 * @param scalar ist der Wert, mit dem der Vektor skaliert weden soll.
	 */
	public void sclr(double scalar) {
		this.x *= scalar;
		this.y *= scalar;
	}
	
	public void add(Vector2 vec) {
		
	}
	
	public void reset() {
		this.x = 0.0;
		this.y = 0.0;
	}
}
