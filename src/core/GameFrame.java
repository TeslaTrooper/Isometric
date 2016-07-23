package core;

import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JFrame;

/**
 * Dieses Objekt repr�sentiert einen Frame. In diesem Frame wird alles f�r das 
 * Programm notwendige angezeigt.
 * @author Stefan
 *
 */
public class GameFrame extends JFrame {

	
	
	/**
	 * Gibt an, ob der Frame in voller Aufl�sung gestartet werden soll.
	 */
	private final boolean winMode = true;
	
	
	
	/**
	 * Rechteck f�r niedrige Aufl�sung. Nur wirksam wenn {@link #winMode} auf <code>true</code> ist.
	 */
	private Rectangle lowRes = new Rectangle(640, 480);
	
	
	
	/**
	 *
	 * Rechteck f�r hohe Aufl�sung. Nur wirksam wenn {@link #winMode} auf <code>true</code> ist.
	 */
	private Rectangle highRes = new Rectangle(1024, 700);
	
	
	
	/**
	 * Ist die Framegr��e. Nur wirksam wenn {@link #winMode} auf <code>true</code> ist.
	 */
	private final Rectangle winSize = highRes;
	
	
	
	/**
	 * Rechteck f�r komplette Bildschirmaufl�sung. Nur wirksam wenn {@link #winMode} auf <code>false</code> ist.
	 */
	private final Rectangle fullSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
	
	private int realWidth, realHeight;
	
	/**
	 * Erzeugt ein neues Frame.
	 */
	public GameFrame() {
		super("Desert Survival");
		
		if(winMode)
			this.setSize(winSize.width, winSize.height);
		else
			this.setSize(fullSize.width-10, fullSize.height-10);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(10,5);
		this.setResizable(false);
		this.setVisible(true);
		
		Insets insets = this.getInsets();
		realWidth = this.getWidth() - (insets.left + insets.right);
		realHeight = this.getHeight() - (insets.top + insets.bottom);
		this.add(new GamePanel(realWidth, realHeight));
	}
}
