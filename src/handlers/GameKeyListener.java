package handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import enums.Keys;

public class GameKeyListener implements KeyListener {
	private int numKeys = 8; // Anzahl verwendeter Keys
	
	private boolean[] keys = new boolean[numKeys]; // Array zum Prüfen welcher der Tasten gedrückt sind
	
	/**
	 * Diese Methode liefert zurück, ob eine bestimmte Taste vom user gedrückt wurde.
	 * @param key ist die Taste, nach der geprüft werden soll.
	 * Gültige Übergaben sind Tasten aus der {@link Keys} Enum.
	 * @return gibt einen boolschen Wert zurück.
	 */
	public boolean checkKey(Keys key) {
		return keys[key.getID()];
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP:
			keys[Keys.KEY_UP.getID()] = true;
			break;
		case KeyEvent.VK_RIGHT:
			keys[Keys.KEY_RIGHT.getID()] = true;
			break;
		case KeyEvent.VK_DOWN:
			keys[Keys.KEY_DOWN.getID()] = true;
			break;
		case KeyEvent.VK_LEFT:
			keys[Keys.KEY_LEFT.getID()] = true;
			break;
		case KeyEvent.VK_W:
			keys[Keys.KEY_W.getID()] = true;
			break;
		case KeyEvent.VK_A:
			keys[Keys.KEY_A.getID()] = true;
			break;
		case KeyEvent.VK_S:
			keys[Keys.KEY_S.getID()] = true;
			break;
		case KeyEvent.VK_D:
			keys[Keys.KEY_D.getID()] = true;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP:
			keys[Keys.KEY_UP.getID()] = false;
			break;
		case KeyEvent.VK_RIGHT:
			keys[Keys.KEY_RIGHT.getID()] = false;
			break;
		case KeyEvent.VK_DOWN:
			keys[Keys.KEY_DOWN.getID()] = false;
			break;
		case KeyEvent.VK_LEFT:
			keys[Keys.KEY_LEFT.getID()] = false;
			break;
		case KeyEvent.VK_W:
			keys[Keys.KEY_W.getID()] = false;
			break;
		case KeyEvent.VK_A:
			keys[Keys.KEY_A.getID()] = false;
			break;
		case KeyEvent.VK_S:
			keys[Keys.KEY_S.getID()] = false;
			break;
		case KeyEvent.VK_D:
			keys[Keys.KEY_D.getID()] = false;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

}
