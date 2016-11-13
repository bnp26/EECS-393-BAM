package Jott;

/**
 * Created by bnp26 on 11/13/16.
 */
public abstract class Glyph {

    private Location location;
    private Glyph previous;
    private Glyph next;

    public Glyph() {
        location = null;
        previous = null;
        next = null;
    }

    public Glyph(Location loc) {
        location = loc;
        next = null;
        previous = null;
    }

    public Glyph(Location loc, Glyph next, Glyph previous) {
        location = loc;
        next = next;
        previous = previous;
    }

    public Glyph getNext() {
        return next;
    }

    public Glyph setNext(Glyph newLetter) {
        Glyph oldNext = next;
        next = newLetter;

        return oldNext;
    }

    public Glyph getPrevious() {
        return next;
    }

    public Glyph setPrevious(Glyph newLetter) {
        Glyph oldPrevious = previous;
        previous = newLetter;

        return oldPrevious;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        location = loc;
    }
}
