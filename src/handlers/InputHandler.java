package handlers;

import java.awt.Point;
import java.util.ArrayList;

import enums.Direction;
import enums.EventType;
import enums.Keys;

public class InputHandler {
	
	private GameKeyListener keyListener;
	private GameMouseListener mouseListener;
	
	private Point mousePos;
	
	private EventType mouseEventType;
	private ArrayList<Keys> keyEventTypes;
	private ArrayList<Keys> camEventTypes;
	
	private boolean keysChanged = false;
	private boolean camKeysChanged = false;
	
	public InputHandler() {
		this.keyListener = new GameKeyListener();
		this.mouseListener = new GameMouseListener();
		
		mousePos = new Point();
		keyEventTypes = new ArrayList<>(2);
		camEventTypes = new ArrayList<>(2);
		
	}
	
	private void checkMouseEvents() {
		this.mouseEventType = null;
		
		// Prüft, ob die linke Maustaste gedrückt wurde
		if(mouseListener.isMouseLeftClicked()) {
			mousePos = mouseListener.getMousePos();
			
			this.mouseEventType = EventType.MOUSE_CLICKED;
		}
		
		// Prüft, ob die Maus bewegt wurde
		if(mouseListener.isMouseMoved()) {
			mousePos = mouseListener.getMousePos();
			
			this.mouseEventType = EventType.MOUSE_MOVED;
		}
	}
	
	public void checkKeyEvents() {
		keyEventTypes.clear();
		camEventTypes.clear();
		keysChanged = false;
		camKeysChanged = false;
		
		if(keyListener.checkKey(Keys.KEY_UP)) {
			this.keyEventTypes.add(Keys.KEY_UP);
			keysChanged = true;
		}
		if(keyListener.checkKey(Keys.KEY_RIGHT)) {
			this.keyEventTypes.add(Keys.KEY_RIGHT);
			keysChanged = true;
		}
		if(keyListener.checkKey(Keys.KEY_DOWN)) {
			this.keyEventTypes.add(Keys.KEY_DOWN);
			keysChanged = true;
		}
		if(keyListener.checkKey(Keys.KEY_LEFT)) {
			this.keyEventTypes.add(Keys.KEY_LEFT);
			keysChanged = true;
		}
		if(keyListener.checkKey(Keys.KEY_A)) {
			this.camEventTypes.add(Keys.KEY_A);
			this.camKeysChanged = true;
		}
		if(keyListener.checkKey(Keys.KEY_S)) {
			this.camEventTypes.add(Keys.KEY_S);
			this.camKeysChanged = true;
		}
		if(keyListener.checkKey(Keys.KEY_W)) {
			this.camEventTypes.add(Keys.KEY_W);
			this.camKeysChanged = true;
		}
		if(keyListener.checkKey(Keys.KEY_D)) {
			this.camEventTypes.add(Keys.KEY_D);
			this.camKeysChanged = true;
		}
	}
	
	
	
	public GameMouseListener getMouseListener() {
		return this.mouseListener;
	}
	
	public GameKeyListener getKeyListener() {
		return this.keyListener;
	}
	
	
	public Point getMousePos() {
		return new Point(mousePos.x, mousePos.y);
	}
	
	public EventType getMouseEvent() {
		return this.mouseEventType;
	}
	
	public boolean getKeyStatus() {
		return this.keysChanged;
	}
	
	public boolean getCamKeyStatus() {
		return this.camKeysChanged;
	}
	
	public ArrayList<Keys> getKeyEventTypes() {
		ArrayList<Keys> tmp = new ArrayList<Keys>(5);
		for(Keys k : this.keyEventTypes) {
			tmp.add(k);
		}
		
		return tmp;
	}
	
	public ArrayList<Keys> getCamKeyEventTypes() {
		ArrayList<Keys> tmp = new ArrayList<Keys>(5);
		for(Keys k : this.camEventTypes) {
			tmp.add(k);
		}
		
		return tmp;
	}
	
	
	
	
	public void checkInputs() {
		checkMouseEvents();
		checkKeyEvents();
	}

}
