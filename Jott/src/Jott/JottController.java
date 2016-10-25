package Jott;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * <p>This Class is dedicated for handling all actions and events in Jott.
 * @author bnp26
 *
 */
public class JottController {

	@FXML //fx:id="newPageButton"
	private Button newPageButton;
	
	public void viewAllNotebooks(ActionEvent ae) {
		
	}
	
	public void selectNote(ActionEvent ae) {
		
	}
	
	public void createNewNote(ActionEvent ae) {
		//when the "Create New Note" button is clicked, logic here is executed.
		System.out.println("clicked");
	}
	
}
