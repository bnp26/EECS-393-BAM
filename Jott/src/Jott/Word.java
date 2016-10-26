package Jott;

import javafx.scene.layout.HBox;

public class Word {

	private Letter first;
	private Word previous;
	private Word next;
	private int length;
	private boolean isHighlighted;
	
	private HBox letterHBox;
	
	public Word(Letter first) {
		this.first = first;
	}
	
	public Word(Letter first, Word previous, Word next) {
		this.first = first;
		this.previous = previous;
		this.next = next;
	}
	
	private void toggleItalics() {
		
	}
	
	private void toggleBold() {}
	
	public void highlight() {}
}
