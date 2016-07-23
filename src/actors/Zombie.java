package actors;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import core.AI;
import core.MobileObject;

public class Zombie extends MobileObject implements AI {

	public Zombie(Point2D cartPos, Dimension2D size, BufferedImage pic) {
		super(cartPos, size, pic);
	}

}
