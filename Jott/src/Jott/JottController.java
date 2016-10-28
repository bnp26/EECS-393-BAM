package Jott;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
	
	public void selectPage(ActionEvent ae) {
		
	}
	
	public void createNewNotebook(ActionEvent ae) {
		System.out.println("trying to create a new notebook");
	}
	
	public void createNewPage(ActionEvent ae) {
		//when the "Create New Note" button is clicked, logic here is executed.
		System.out.println("clicked");
		updateTitle(ae);
	}
	
	public void updateTitle(ActionEvent ae) {
		//when a new page or notebook is made/loaded, we should update the window's title
		//TODO: pull names of currently selected notebook/page in the form "Notebookname - Pagename"
		Stage stage = Stage.class.cast(Control.class.cast(ae.getSource()).getScene().getWindow());
		stage.setTitle("Dynamically added title");
	}
}
