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
	 * Gibt an, welche Art von Event ausgelöst wurde. Mögliche Events sind Events
	 * aus der Enumeration {@link EventType}. Mithilfe der Methode {@link #isEventType(EventType)}
	 * kann ein Event geprüft werden.
	 */
	public EventType type;
	
	/**
	 * Über diese Methode kann ermittelt werden, ob der user die linke Maustaste
	 * gedrückt hat. Außerdem wird nach jeder Abfrage die flag zurückgesetzt, 
	 * damit keine falschen Events erkannt werden. 
	 * @return gibt <code>true</code> zurück, wenn der user seit der letzten Abfrage
	 * die linke Maustaste gedrückt hat.
	 */
	public boolean isMouseLeftClicked() {
		boolean tmp = mouseLeftClicked;
		mouseLeftClicked = false;
		
		this.type = EventType.MOUSE_CLICKED;
		
		return tmp;
	}
	
	/**
	 * Über diese Methode kann ermittelt werden, ob der user die Maus bewegt hat. 
	 * Außerdem wird nach jeder Abfrage die flag zurückgesetzt, 
	 * damit keine falschen Events erkannt werden. 
	 * @return gibt <code>true</code> zurück, wenn der user seit der letzten Abfrage
	 * die linke Maustaste gedrückt hat.
	 */
	public boolean isMouseMoved() {
		boolean tmp = mouseMoved;
		mouseMoved = false;
		
		this.type = EventType.MOUSE_MOVED;
		
		return tmp;
	}
	
	/**
	 * Mithilfe dieser Methode kann geprüft werden, ob das erwartete Event dem 
	 * tatsächlichen Event entspricht. Diese Events stammen aus {@link EventType}.
	 * @param expected ist das Event, welches man überprüfen möchte, ob dieses zutrifft.
	 * @return Gibt einen <code>boolschen</code> Wert zurück, der den Wahrheitsgehalt
	 * hinsichtlich der äquivalenz der beiden Events beinhaltet.
	 */
	public boolean isEventType(EventType expected) {
		return expected == this.type;
	}
	
	private void updateMousePos(MouseEvent e) {
		this.mX = e.getX();
		this.mY = e.getY();
	}
	
	/**
	 * Über diese Methode kann die Position der Maus abgefragt werden. Die Position gibt
	 * <b>nicht</b> die tatsächliche Bildposition an, sondern sie stellt eine Punkt dar,
	 * der relativ zum Panel liegt, auf dem der Listener hinzugefügt wurde.
	 * @return gibt die Mausposition als {@link Point} zurück.
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
