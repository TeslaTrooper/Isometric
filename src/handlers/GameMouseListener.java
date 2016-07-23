package handlers;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import enums.EventType;

public class GameMouseListener implements MouseMotionListener, MouseListener {
	private int mX, mY;
	
	public boolean mouseReleased = false;
	private boolean mouseMoved = false;
	private boolean mouseClicked = false;
	public boolean mouseLeftClicked = false;
	public boolean mouseRightClicked = false;
	
	/**
	 * Gibt an, welche Art von Event ausgel�st wurde. M�gliche Events sind Events
	 * aus der Enumeration {@link EventType}. Mithilfe der Methode {@link #isEventType(EventType)}
	 * kann ein Event gepr�ft werden.
	 */
	public EventType type;
	
	/**
	 * �ber diese Methode kann ermittelt werden, ob der user die linke Maustaste
	 * gedr�ckt hat. Au�erdem wird nach jeder Abfrage die flag zur�ckgesetzt, 
	 * damit keine falschen Events erkannt werden. 
	 * @return gibt <code>true</code> zur�ck, wenn der user seit der letzten Abfrage
	 * die linke Maustaste gedr�ckt hat.
	 */
	public boolean isMouseLeftClicked() {
		boolean tmp = mouseLeftClicked;
		mouseLeftClicked = false;
		
		this.type = EventType.MOUSE_CLICKED;
		
		return tmp;
	}
	
	/**
	 * �ber diese Methode kann ermittelt werden, ob der user die Maus bewegt hat. 
	 * Au�erdem wird nach jeder Abfrage die flag zur�ckgesetzt, 
	 * damit keine falschen Events erkannt werden. 
	 * @return gibt <code>true</code> zur�ck, wenn der user seit der letzten Abfrage
	 * die linke Maustaste gedr�ckt hat.
	 */
	public boolean isMouseMoved() {
		boolean tmp = mouseMoved;
		mouseMoved = false;
		
		this.type = EventType.MOUSE_MOVED;
		
		return tmp;
	}
	
	/**
	 * Mithilfe dieser Methode kann gepr�ft werden, ob das erwartete Event dem 
	 * tats�chlichen Event entspricht. Diese Events stammen aus {@link EventType}.
	 * @param expected ist das Event, welches man �berpr�fen m�chte, ob dieses zutrifft.
	 * @return Gibt einen <code>boolschen</code> Wert zur�ck, der den Wahrheitsgehalt
	 * hinsichtlich der �quivalenz der beiden Events beinhaltet.
	 */
	public boolean isEventType(EventType expected) {
		return expected == this.type;
	}
	
	private void updateMousePos(MouseEvent e) {
		this.mX = e.getX();
		this.mY = e.getY();
	}
	
	/**
	 * �ber diese Methode kann die Position der Maus abgefragt werden. Die Position gibt
	 * <b>nicht</b> die tats�chliche Bildposition an, sondern sie stellt eine Punkt dar,
	 * der relativ zum Panel liegt, auf dem der Listener hinzugef�gt wurde.
	 * @return gibt die Mausposition als {@link Point} zur�ck.
	 */
	public Point getMousePos() {
		return new Point(mX, mY);
	}

	
	
	
	
	
	
	
	
	@Override
	public void mouseDragged(MouseEvent arg0) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		updateMousePos(e);
		
		mouseMoved = true;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		mouseClicked = true;
		updateMousePos(e);
		
		if(e.getButton() == MouseEvent.BUTTON1) {
			mouseLeftClicked = true;
		} else if(e.getButton() == MouseEvent.BUTTON3) {
			mouseRightClicked = true;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

}
