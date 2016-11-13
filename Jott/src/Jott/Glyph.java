package Jott;

/**
 * Created by bnp26 on 11/13/16.
 */
public abstract class Glyph {

    private Location location;
    private Letter previous;
    private Letter next;

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

    public Glyph(Location loc, Letter next, Letter previous) {
        location = loc;
        next = next;
        previous = previous;
    }

    public Letter getNext() {
        return next;
    }

    public Letter setNext(Letter newLetter) {
        Letter oldNext = next;
        next = newLetter;

        return oldNext;
    }

    public Letter getPrevious() {
        return next;
    }

    public Letter setPrevious(Letter newLetter) {
        Letter oldPrevious = previous;
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
