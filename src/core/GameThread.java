package core;

/**
 * Diese Klasse stellt den Haupt-Thread des Programms dar. �ber diese Klasse kann 
 * der Spielablauf beliebig pausiert, oder fortgesetzt werden.
 * @author Stefan
 */
public class GameThread extends Thread implements Runnable {
	
	
	
	/**
	 * Diese Varariable gibt an, ob die gameloop ausgf�hrt werden soll.
	 */
	private boolean isRunning = false;
	
	
	
	/**
	 * Objektreferenz auf GameLogic. Dies dient f�r die gegenseitige Assoziation.
	 */
	private GameLogic gameLogic;
	
	
	
	/**
	 * Dieser Wert gibt an, wie lange der Thread unterbrochen sein soll.<br>
	 * F�r <b>60 FPS</b> ist der Wert <b>16</b>. <br>F�r <b>30 FPS</b> ist der Wert <b>32</b>.
	 */
	private final int delay = 16;
	
	
	
	/**
	 * In dieser Variable wird die aktuelle Anzahl an Frames pro Sekunde abgelegt.
	 */
	private double fps = 0.0;
	
	
	
	
	/**
	 * Erzeugt ein neues Objekt. Standardm��ig ist der Thread nicht eingeschaltet.
	 * @param gameLogic ist die Referenz auf das existierende Objekt vom Typ {@link GameLogic}.
	 * Die Referenz wird ben�tigt, um eine bidirektionale Assoziation herzustellen.
	 */
	public GameThread(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
	}
	
	/**
	 * �ber diese Methode wird die gameloop ausgef�hrt.
	 */
	public void enable() {
		this.isRunning = true;
	}
	
	/**
	 * Deaktiviert die Ausf�hrung der gameLoop.
	 */
	public void disable() {
		this.isRunning = false;
	}
	
	/**
	 * @return Gibt die Anzahl an Programmdurchl�ufen pro Sekunde zur�ck.
	 */
	public int getFPS() {
		return (int) this.fps;
	}
	
	
	
	@Override
	public void run() {
		while(true) {
			if(isRunning) {
				long start = System.nanoTime();
				
				gameLogic.doGameLogic();
				try{
					Thread.sleep(delay);
				} catch(InterruptedException err){
					System.out.println("omg");
				}
				
				long end = System.nanoTime();
				fps = 1.0e9 / (end - start);
			}
		}
	}
}
