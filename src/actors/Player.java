package actors;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import core.MobileObject;

/**
 * Diese Klasse soll den Charakter, den der User beim Spielen einnimmt, repräsentieren.
 * Der Player kann per Maus und Tastatur gestuert werden.
 * @author Stefan
 *
 */
public class Player extends MobileObject {
	public Player(Point2D cartPos, Dimension2D size, BufferedImage pic) {
		super(cartPos, size, pic);
	}
}
