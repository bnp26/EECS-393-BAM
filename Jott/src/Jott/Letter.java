package Jott;

import javafx.scene.control.Label;

public class Letter extends Glyph{

	private char value;
	
	private Label letterLabel;
	
	public Letter(char val) {
		super();
		value = val;
		setUpLabel(value);
	}
	
	public Letter(char val, Glyph previous) {
		super();
		value = val;
		this.setPrevious(previous);
	}

	public Letter(char val, Glyph next, Glyph previous) {
		super();
		value = val;
		this.setNext(next);
		this.setPrevious(previous);
	}
	
	public Letter(char val, Location loc) {
		value = val;
	}
	
	public Letter(char val, int line, int word, int letter) {
		value = val;
	}

	private void setUpLabel(char val) {
        String str = new String();
        str += val;
        letterLabel = new Label();
        letterLabel.setPrefSize(5, 5);
    }
	
	public char getValue() {
		return value;
	}
	
	public void setValue(char newValue) {
		value = newValue;
	}
}
