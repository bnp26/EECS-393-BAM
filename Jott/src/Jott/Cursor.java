package Jott;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Cursor {

    private Location location;
    private Rectangle cursorImage;

    // Cursor constructor defining the size and shape
    public Cursor() {
        cursorImage = new Rectangle(1.25, 15.0,Paint.valueOf("BLACK"));
        setCursorTransition();
        // Initialy the cursor starts from the position 0,0 (top left of the document)
        location = new Location(0,0);
    }

    // getLocation for receiving the position of the cursor
    public Location getLocation() {
        return location;
    }

    public Rectangle getCursorImage() {
        return cursorImage;
    }

    // Set location for cursor
    private void setLocation(Location loc) {
        location = loc;
    }

    // Moving the image of the cursor to the new location
    public void move(Location loc) {
        this.setLocation(loc);
        moveImage(loc);
    }
	
    // Move method for creating a Location with arguments line & letter
    // moveImage called for the Location instance
    public void move(int line, int letter){
        Location newLoc = new Location(line, letter);
        this.setLocation(newLoc);
        moveImage(new Location(line, letter));
    }

    
    private void moveImage(Location newLoc) {
        // Setting the X,Y coordinates for the new Location of the cursor
        this.cursorImage.setX(newLoc.getXPixelValue());
        this.cursorImage.setY(newLoc.getYPixelValue());

        this.cursorImage.setVisible(true);
        setCursorTransition();
        this.cursorImage.setVisible(true);
        // For debugging the position of the cursor
        System.out.println("cursor should be at: ("+this.getLocation().getXPixelValue() + ", " + this.getLocation().getYPixelValue()+")");
        System.out.println("cursor is at: ("+cursorImage.xProperty().get() + ", " + cursorImage.yProperty().get()+")");
    }

    private void setCursorTransition() {
        // Give a smooth fade for the cursor image
        FadeTransition ft = new FadeTransition(Duration.millis(600), this.cursorImage);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        // Have this fade transition always
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();
    }

	//Make Page Create the cursor.

}
