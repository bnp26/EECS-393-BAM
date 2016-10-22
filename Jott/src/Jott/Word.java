package Jott;

public class Word {

	private Letter first;
	private Word previous;
	private Word next;
	private int length;
	private boolean isHighlighted;
	
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
