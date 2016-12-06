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

    public Cursor() {
        cursorImage = new Rectangle(2.0, 15.0,Paint.valueOf("BLACK"));
        setCursorTransition();
        location = new Location(0,0);
    }

	public Location getLocation() {
        return location;
    }

    public Rectangle getCursorImage() {
        return cursorImage;
    }

    private void setLocation(Location loc) {
        location = loc;
    }

	public void move(Location loc) {
        this.setLocation(loc);
        moveImage(loc);
	}
	
	public void move(int line, int letter){
        Location newLoc = new Location(line, letter);
        this.setLocation(newLoc);
        moveImage(new Location(line, letter));
	}

	private void moveImage(Location newLoc) {
        this.cursorImage.setX(newLoc.getXPixelValue());
        this.cursorImage.setY(newLoc.getYPixelValue());

        this.cursorImage.setVisible(true);
        setCursorTransition();
        this.cursorImage.setVisible(true);
        System.out.println("cursor should be at: ("+this.getLocation().getXPixelValue() + ", " + this.getLocation().getYPixelValue()+")");
        System.out.println("cursor is at: ("+cursorImage.xProperty().get() + ", " + cursorImage.yProperty().get()+")");
    }

	private void setCursorTransition() {
        FadeTransition ft = new FadeTransition(Duration.millis(600), this.cursorImage);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();
    }

	//Make Page Create the cursor.

}
