package Jott;

import java.util.LinkedList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class Page {
	
	private String name;
	private LinkedList<Line> lines;
	private Cursor cursor;
    private FlowPane flowPane;

	private Button pageButton;
	
	public Page(String name, FlowPane flowPane) {
		this.name = name;
		lines = new LinkedList<Line>();
		Cursor cursor = new Cursor(new Location(0, 0));
        lines.add(new Line());

        //setting up flow pane event listeners
        this.flowPane = flowPane;
        this.flowPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch(keyEvent.getCode()){
                    case UP:
                        break;
                    case DOWN:
                        break;
                    case LEFT:
                        break;
                    case RIGHT:
                        break;
                    case ENTER:
                        break;
                    case BACK_SPACE:
                        break;
                    case DELETE:
                        break;
                    case SHIFT:
                        break;
                    case TAB:
                        break;
                }
            }
        });
	}

	public void setButton(Button button){
		this.pageButton = button;
	}

	public Button getButton() {
		return pageButton;
	}

	public String getName() {
		return name;
	}

	public void setName(String newName) {
		name = newName;
	}

	public void setFlowPane(FlowPane pane) {
        this.flowPane = pane;
    }

    public FlowPane getFlowPane() {
        return flowPane;
    }

	public LinkedList<Line> getLines() {
		return lines;
	}

	public Cursor getCursor() {
		return cursor;
	}

	public void setCursor(Cursor cursor) {
		this.cursor = cursor;
	}

	public void addLine() {
        int lettersSize = 60;
		Line line = new Line();

		if(cursor == null) {
            System.out.println("cursor is null");
            cursor = new Cursor(new Location(0, 0));
        }
        cursor.insertLetter('a');
        for(int x = 0; x < 120; x++) {
            cursor.insertLetter('a');
        }
	}
	
	public boolean removeLine(int lineNum) {
		if(lines.get(lineNum) == null) {
			return false;
		}
		else {
			lines.remove(lineNum);
			return true;
		}
	}

	public void selectPage() {
        pageButton.getStyleClass().add("jott_current_page_item");
        Image imageDecline = new Image(getClass().getResourceAsStream("static/ic_label_black_24dp_2x.png"));
        pageButton.setGraphic(new ImageView(imageDecline));
        pageButton.getGraphic().setScaleX(0.5);
        pageButton.getGraphic().setScaleY(0.5);
    }

    public void deselectPage() {
        pageButton.getStyleClass().remove("jott_current_page_item");
        pageButton.setGraphic(new ImageView());
    }
}
