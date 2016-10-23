package Jott;

public class Letter {

	private char value;
	private Letter previous;
	private Letter next;
	private Location location;
	
	public Letter(char val) {
		value = val;
	}
	
	public Letter(char val, Letter next) {
		value = val;
		this.next = next;
	}
	
	public Letter(char val, Location loc) {
		value = val;
		location = loc;
	}
	
	public Letter(char val, int line, int word, int letter) {
		value = val;
		location = new Location(line, word, letter);
	}
	
	public Letter getNext() {
		return next;
	}
	
	public Letter getPrevious() {
		return previous;
	}
	
	public boolean setNext(Letter letter) {
		next = letter;
		return true;
	}
	
	public boolean setPrevious(Letter letter) {
		previous = letter;
		return true;
	}
	
	public char getValue() {
		return value;
	}
	
	public void setValue(char newValue) {
		value = newValue;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location loc) {
		location = loc;
	}
	
	public void setLocation(int line, int word, int letter) {
		location = new Location(line, word, letter);
	}
}
