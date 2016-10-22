package Jott;

public class Letter {

	private char value;
	private Letter previous;
	private Letter next;
	
	public Letter(char val) {
		value = val;
	}
	
	public Letter(char val, Letter next) {
		value = val;
		this.next = next;
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
}
