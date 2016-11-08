package Jott;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;

public class Letter {

	private char value;
	private Letter previous;
	private Letter next;
	private Location location;
	
	private Label letterLabel;
	
	public Letter(char val) {
		value = val;
		setUpLabel(value);
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

	private void setUpLabel(char val) {
        String str = new String();
        str += val;
        letterLabel = new Label();
        letterLabel.setPrefSize(5, 5);
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
