package core;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * Über diese Klasse werden alle Bilder aus dem tileset definiert, initialisert und
 * externe Klassen können bei Bedarf auf diese Bilder zugreifen. Die Initialisierung
 * aller Bilder erfolgt einmalig beim Erzeugen des Objektes. Deshalb sollte auch immer 
 * nur ein Objekt existieren, da ansonsten viel Speicher benötigt wird.
 * @author Stefan
 *
 */
public class PicLoader {
	
	private BufferedImage tileSetDesert;
	
	public BufferedImage[] dirt = new BufferedImage[16];
	public BufferedImage[] cobbleStone = new BufferedImage[16];
	public BufferedImage[] bush = new BufferedImage[16];
	public BufferedImage[] stone = new BufferedImage[4];
	public BufferedImage[] water = new BufferedImage[16];
	public BufferedImage[] waterCliffs = new BufferedImage[24];
	public BufferedImage[] decoration1 = new BufferedImage[8];
	public BufferedImage cliff1, cliff2;
	public BufferedImage cliffOuterCorner;
	public BufferedImage cliffLeftCurve;
	public BufferedImage cliffRightCurve;
	public BufferedImage cliffToUpLeft;
	public BufferedImage cliffToUpRight;
	public BufferedImage cliffTopCorner;
	public BufferedImage cliffInnerCorner;
	public BufferedImage cliffSmall1;
	public BufferedImage cliffSmall2;
	public BufferedImage cliffSmallOuterCorner;
	public BufferedImage cliffSmallRightCorner;
	public BufferedImage cliffSmallLeftCorner;
	public BufferedImage cliffSmall1Behind;
	public BufferedImage cliffSmall2Behind;
	public BufferedImage cliffSmallCornerBehind;
	public BufferedImage cliffSmallInnerCornerRight;
	public BufferedImage cliffSmallInnerCornerLeft;
	public BufferedImage cliffSmallInnerCornerLeftReversed;
	public BufferedImage cliffSmallInnerCornerRightReversed;
	
	public PicLoader() {
		initAll();
	}
	
	public void initAll() {
		tileSetDesert = initPic("tileset_desert.png");
		cliff1 = tileSetDesert.getSubimage(0, 64, 64, 96);
		cliff2 = tileSetDesert.getSubimage(64, 64, 64, 96);
		cliffOuterCorner = tileSetDesert.getSubimage(256, 158, 64, 96);
		cliffLeftCurve = tileSetDesert.getSubimage(192, 160, 64, 96);
		cliffRightCurve = tileSetDesert.getSubimage(64, 160, 64, 96);
		cliffTopCorner = tileSetDesert.getSubimage(128, 160, 64, 96);
		cliffToUpLeft = tileSetDesert.getSubimage(192, 64, 64, 96);
		cliffToUpRight = tileSetDesert.getSubimage(128, 64, 64, 96);
		cliffInnerCorner = tileSetDesert.getSubimage(768, 64, 64, 96);
		
		cliffSmall1 = tileSetDesert.getSubimage(0, 704, 64, 64);
		cliffSmall2 = tileSetDesert.getSubimage(64, 704, 64, 64);
		cliffSmallOuterCorner = tileSetDesert.getSubimage(0, 768, 64, 64);
		cliffSmallRightCorner = tileSetDesert.getSubimage(192, 768, 64, 64);
		cliffSmallLeftCorner = tileSetDesert.getSubimage(64, 768, 64, 64);
		cliffSmall1Behind = tileSetDesert.getSubimage(128, 704, 64, 64);
		cliffSmall2Behind = tileSetDesert.getSubimage(192, 704, 64, 64);
		cliffSmallCornerBehind = tileSetDesert.getSubimage(128, 768, 64, 64);
		cliffSmallInnerCornerRight = tileSetDesert.getSubimage(832, 704, 64, 64);
		cliffSmallInnerCornerLeft = tileSetDesert.getSubimage(896, 704, 64, 64);
		cliffSmallInnerCornerLeftReversed = tileSetDesert.getSubimage(768, 704, 64, 64);
		cliffSmallInnerCornerRightReversed = tileSetDesert.getSubimage(960, 704, 64, 64);
		
		initDirtArray();
		initCobbleStoneArray();
		initBushArray();
		initStoneArray();
		initWaterArray();
		initWaterCliffsArray();
		initDecorations();
	}
	
	private void initDirtArray() {
		for(int i = 0; i < dirt.length; i++) {
			dirt[i] = tileSetDesert.getSubimage(i*64, 0, 64, 32);
		}
	}
	
	private void initCobbleStoneArray() {
		for(int i = 0; i < cobbleStone.length; i++) {
			cobbleStone[i] = tileSetDesert.getSubimage(i*64, 32, 64, 32);
		}
	}
	
	private void initBushArray() {
		for(int i = 0; i < bush.length; i++) {
			bush[i] = tileSetDesert.getSubimage(i*64, 352, 64, 32);
		}
	}
	
	private void initStoneArray() {
		for(int i = 0; i < stone.length; i++) {
			stone[i] = tileSetDesert.getSubimage(i*64, 439, 64, 32);
		}
	}
	
	private void initWaterArray() {
		for(int i = 0; i < water.length; i++) {
			water[i] = tileSetDesert.getSubimage(i*64, 608, 64, 32);
		}
	}
	
	private void initWaterCliffsArray() {
		int x = 0;
		int y = 480;
		
		for(int i = 0; i < waterCliffs.length; i++) {
			waterCliffs[i] = tileSetDesert.getSubimage(x, y, 64, 64);
			x+=64;
			
			if(i == 15) {
				x = 0;
				y += 64;
			}
		}
	}
	
	private void initDecorations() {
		for(int i = 0; i < decoration1.length; i++) {
			decoration1[i] = tileSetDesert.getSubimage(i*64, 288, 64, 32);
		}
	}
	
	
	
	
	
	
	
	private BufferedImage initPic(String path) {
		BufferedImage source = null;
		
		URL picURL = this.getClass().getClassLoader().getResource(path);
		
		try {
			source = ImageIO.read(picURL);
		} catch(IOException ex) {
			ex.printStackTrace();
		};
		
		return source;
	}
	
	private BufferedImage cropOut(int x, int y, int width, int height) {
		return tileSetDesert.getSubimage(x, y, width, height);
	}
}
