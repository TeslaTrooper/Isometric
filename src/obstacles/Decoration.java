package obstacles;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import core.SolidObject;

public class Decoration extends SolidObject {

	public Decoration(BufferedImage pic) {
		super(new Point2D.Double(0, 0), new Dimension(pic.getWidth(), pic.getHeight()), pic);
	}

}
