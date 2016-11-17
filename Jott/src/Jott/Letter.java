package Jott;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class Letter extends Glyph{

	private char value;

	public Letter(char val) {
		super();
		value = val;
        Label newLabel = setUpLabel(value);
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

	private Label setUpLabel(char val) {
        Label newLabel = new Label();
        newLabel.setText(new String(val+""));
        return newLabel;
    }
	
	public char getValue() {
		return value;
	}
	
	public void setValue(char newValue) {
		value = newValue;
	}
}
