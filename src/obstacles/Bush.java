package obstacles;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import constants.BushConst;
import core.SolidObject;

public class Bush extends SolidObject {
	public Bush(BufferedImage pic) {
		super(new Point2D.Double(0, 0), new Dimension(64, 32), pic);
	}
}