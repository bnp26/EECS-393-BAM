package Jott;

import javafx.scene.layout.HBox;

import java.util.*;
import java.util.function.Consumer;

public class Word implements Iterable<Glyph>{

	private Glyph first;
	private Word previous;
	private Word next;

    private LinkedList<Letter> wordLetters;

	private int length;
	private boolean isHighlighted;
	
	private HBox letterHBox;

	public Word(Glyph first) {
		this.first = first;
	}

	public Word(Letter first, Word previous, Word next) {
        wordLetters = new LinkedList<Letter>();
        wordLetters.add(first);
        this.first = first;
		this.previous = previous;
		this.next = next;
	}

	public Word(String str) {
        ArrayList<Letter> letters = new ArrayList<Letter>();
        for(int x = 0; x < str.length(); x++) {
            Letter letter;
            if(x == 0) {
                letter = new Letter(str.charAt(x));
                letters.add(letter);
            } else {
                letter = new Letter(str.charAt(x), letters.get(x - 1));
                letters.add(letter);
            }
        }
        this.first = letters.get(0);
        this.previous = null;
        this.next = null;
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
