package core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import constants.Constants;

/**
 * Diese Klasse fasst alle Objekte zusammen, die die Eigenschaft eines nichbeweglichen
 * Objektes haben. Diese Objekte können zur Laufzeit also nicht ihre Position ändern und
 * sie können auch nicht durch äußere Einwirkungen bewegt werden. Diese Objekte
 * stellen lediglich Hindernisse, oder Fundstellen dar.
 * @author Stefan
 *
 */
public abstract class SolidObject extends GameObject {
	
	public SolidObject(Point2D cartPos, Dimension2D size, BufferedImage pic) {
		super(cartPos, size, pic);
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.RED);
		g.drawImage(super.getPic(), (int) super.getIsoPos().getX(), (int) super.getIsoPos().getY(), 
				(int) super.getSize().getWidth(), (int) super.getSize().getHeight(), null);
		
		
//		g.setColor(Color.RED);
//		g.fillRect((int) super.getIsoPos().getX(), (int) super.getIsoPos().getY(), 
//				(int) super.getSize().getWidth(), (int) super.getSize().getHeight());
//		g.setColor(Color.BLACK);
//		g.drawRect((int) super.getIsoPos().getX(), (int) super.getIsoPos().getY(), 
//				(int) super.getSize().getWidth(), (int) super.getSize().getHeight());
		
	}
}
