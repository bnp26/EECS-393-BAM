package Jott;

import javafx.scene.layout.HBox;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

public class Word implements Iterable<Glyph>{

	private Glyph first;
	private Word previous;
	private Word next;

	private int length;
	private boolean isHighlighted;
	
	private HBox letterHBox;

	public Word(Glyph first) {
		this.first = first;
	}

	public Word(Letter first, Word previous, Word next) {
		this.first = first;
		this.previous = previous;
		this.next = next;
	}

    public Iterator<Glyph> iterator() {
        return new WordIterator();
    }

    private class WordIterator implements Iterator<Glyph> {

        private Glyph current;

        public WordIterator () {
            current = Word.this.first;
        }

        @Override
        public boolean hasNext() {
            return current.getNext() != null;
        }

        @Override
        public Glyph next() {
            current = current.getNext();
            return current.getNext();
        }
    }

	public Word getNext(){
        return next;
    }

    public Word getPrevious(){
        return previous;
    }

	private void toggleItalics() {
		
	}
	
	private void toggleBold() {}
	
	public void highlight() {}
}
