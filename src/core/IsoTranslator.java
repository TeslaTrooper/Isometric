package core;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * - bildet tiles als block in isometrischer perspektive ab
 * - umrisse der tiles sollen konfiguriert werden
 * - 
 * @author Stefan
 *
 */
public class IsoTranslator {
	
	private Tile[][] grid;
	private Camera cam;
	private boolean cameraMoved;
	private GameLogic logic;
	
	public IsoTranslator(Tile[][] grid, GameLogic logic) {
		this.grid = grid;
		this.logic = logic;
		this.cam = this.logic.getCamera();
	}
	
	
	private void translateTileIntoBlock() {
		for(int y = 0; y < grid.length; y++) {
			for(int x = 0; x < grid[y].length; x++) {
				Tile tile = grid[x][y];
				
				// Wenn das aktuelle Tile ein update erfordert
				// 
				// Diese Abfrage verbessert die Performance des Programms,
				// denn die Berechnungen der Seitenwände werden nur bei Bedarf durchgeführt
				// und nicht für jeden gameloop
				if(tile.isUpdateRequired() || cameraMoved) {
					// Seitenwände werden dann für dieses Teil berechnet
					configWalls(x, y);
					
					// Danach werden für alle anliegenden Tiles auch die Seitenwände
					// berechnet, sofern es ein anliegendes Tile gibt
					// (Ausnahmen bilden Tiles, die am Rand der Welt sind
					if(y < grid.length-1) {
						configWalls(x, y+1);
					}
					
					if(x < grid.length-1) {
						configWalls(x+1, y);
					}
					
					if(x > 0) {
						configWalls(x-1, y);
					}
					
					if(y > 0) {
						configWalls(x, y-1);
					}
					
					
					configOutlines(tile);
				}
				
				if(cam.getCameraStatus()) {
					adjustTileToCameraPos(x, y);
				}
			}
		}
	}
	
	private void configWalls(int x, int y) {
		// Die Bedingung für die leftWall wird überprüft
		// Wenn ein anliegendes Tile exisiert und dieses dann ein niedriegeren Layer hat
		// oder wenn das Tile am Rand ist, und es somit kein anliegendes Tile mehr gibt
		if(y < grid.length-1 && grid[x][y+1].getLayer() < grid[x][y].getLayer() || y == grid.length-1) {
			configLeftWall(x, y);
		} else {
			grid[x][y].drawLeftWall = false;
		}
		
		// Analoges Vorgehen auch für rightWall
		if(x < grid.length-1 && grid[x+1][y].getLayer() < grid[x][y].getLayer() || x == grid.length-1) {
			configRightWall(x, y);
		} else {
			grid[x][y].drawRightWall = false;
		}
	}
	
	
	private void configLeftWall(int x, int y) {
//		Point2D[] points = new Point2D[4];
//		Tile tile = grid[x][y];
//		
//		// Eckpunkte der leftWall werden berechnet
//		for(int i = 3; i>1; i--) {
//			double pX = tile.getPoints()[i].getX();
//			double pY;
//			
//			if(y < grid.length-1) {
//				pY = tile.getPoints()[i].getY()+((tile.getLayer()-grid[x][y+1].getLayer())*tile.getSize().getHeight());
//			} else {
//				pY = tile.getPoints()[i].getY()+(tile.getLayer()*tile.getSize().getHeight());
//			}
//			
//			points[3-i] = new Point2D.Double(pX, pY);
//		}
//		
//		points[2] = new Point2D.Double(tile.getPoints()[2].getX(), tile.getPoints()[2].getY());
//		points[3] = new Point2D.Double(tile.getPoints()[3].getX(), tile.getPoints()[3].getY());
//		
//		// leftWall wird dann erzeugt und flag zum Zeichnen wird gesetzt
//		tile.createWall(points, tile.leftWall);
//		tile.drawLeftWall = true;
	}
	
	
	private void configRightWall(int x, int y) {
//		Point2D[] points = new Point2D[4];
//		Tile tile = grid[x][y];
//		
//		for(int i = 2; i>0; i--) {
//			double pX = tile.getPoints()[i].getX();
//			double pY;
//			
//			if(x < grid.length-1) {
//				pY = tile.getPoints()[i].getY()+((tile.getLayer()-grid[x+1][y].getLayer())*tile.getSize().getHeight());
//			} else {
//				pY = tile.getPoints()[i].getY()+(tile.getLayer()*tile.getSize().getHeight());
//			}
//			
//			points[2-i] = new Point2D.Double(pX, pY);
//		}
//		
//		points[2] = new Point2D.Double(tile.getPoints()[1].getX(), tile.getPoints()[1].getY());
//		points[3] = new Point2D.Double(tile.getPoints()[2].getX(), tile.getPoints()[2].getY());
//		
//		tile.createWall(points, tile.rightWall);
//		tile.drawRightWall = true;
	}
	
	
	
	private void adjustTileToCameraPos(int x, int y) {
		grid[x][y].updateCameraPos((int) cam.getX(), (int) cam.getY());
		logic.getPlayer().updateCameraPos((int) cam.getX(), (int) cam.getY());
	}
	
	
	
	
	
	
	
	
	private void configOutlines(Tile tile) {
		
	}
	
	
	public void translate(boolean cameraMoved) {
		this.cameraMoved = cameraMoved;
		translateTileIntoBlock();
	}

}
