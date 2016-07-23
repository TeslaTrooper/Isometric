package enums;

/**
 * In dieser Enumeration werden die Tasten aufgelistet, die auch tatsächlich im Programm
 * verwendet werden.
 * @author Stefan
 *
 */
public enum Keys {
	
	KEY_UP (Direction.UP, 0),
	KEY_RIGHT (Direction.RIGHT, 1),
	KEY_DOWN (Direction.DOWN, 2),
	KEY_LEFT (Direction.LEFT, 3),
	KEY_W (Direction.DOWN, 4),
	KEY_A (Direction.RIGHT, 5),
	KEY_S (Direction.UP, 6),
	KEY_D (Direction.LEFT, 7);
	
	private final int id;
	private Direction direction;
	
	private Keys(Direction direction, int id) {
		this.direction = direction;
		this.id = id;
	}

	public int getID() {
		return this.id;
	}
	
	public Direction getDirectionFromKey() {
		return this.direction;
	}

}
