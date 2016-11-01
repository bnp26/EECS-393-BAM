package Jott;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextInputDialog;
/**
 * <p>This Class is dedicated for handling all actions and events in Jott.
 * @author bnp26
 *
 */
public class JottController {

	private NotebooksPane notebooksPane;
	private PagesPane pagesPane;
	private TextInputDialog newPageDialog;
	
	@FXML //fx:id = "pagesVBox"
	private VBox pagesVBox;
		
	@FXML //fx:id="newPageButton"
	private Button newPageButton;
	
	public JottController() {
		this.notebooksPane = null;
		this.pagesPane = null;
	}
	
	public JottController(NotebooksPane notebooksPane, PagesPane pagesPane) {
		this.notebooksPane = notebooksPane;
		this.pagesPane = pagesPane;
	}
	
	public void viewAllNotebooks(ActionEvent ae) {
		
	}
	
	public void selectPage(ActionEvent ae) {
		
	}
	
	public void createNewNotebook(ActionEvent ae) {
		System.out.println("trying to create a new notebook");
	}
	
	public void createNewPage(ActionEvent ae) {
		//when the "Create New Note" button is clicked, logic here is executed.
		newPageDialog = new TextInputDialog();
		newPageDialog.setTitle("Create New Page");
		newPageDialog.setHeaderText("Enter your new pages name");
		
		String newPageName;
		Optional<String> result = newPageDialog.showAndWait();
		
		if(result.isPresent()) {
			newPageName = new String(result.get());
			
			addNewPage(newPageName);
		}
		
		System.out.println("clicked");
		updateTitle(ae);
	}
	
	private boolean addNewPage(String name) {
		
		if(name == null)
		{
			name = "new page";
		}
		
		//creates a new page object
		Page page = new Page(name);

		//creates the new page button
		Button newPage = createPageButton(name);

		//finds the number of children in the pagesVBox
		int vBoxSize = pagesVBox.getChildrenUnmodifiable().size();
		
		page.setButton(newPage); 
		//adds the new button to the pagesVBox but puts it always at the end of the list but above the add new page button
		pagesVBox.getChildren().add(vBoxSize-1, newPage);
		
		pagesPane.addPage(page);
		
		
		newPage.setVisible(true);
		return true;
	}

	private Button createPageButton(String name) {
		
		Button newPage = new Button();
		//setting up the new page button
		newPage.setText(name);
		
		newPage.getStyleClass().add("jott_page_item");
		
		return newPage;
	}
	
	
	public void updateTitle(ActionEvent ae) {
		//when a new page or notebook is made/loaded, we should update the window's title
		//TODO: pull names of currently selected notebook/page in the form "Notebookname - Pagename"
		Stage stage = Stage.class.cast(Control.class.cast(ae.getSource()).getScene().getWindow());
		stage.setTitle("Dynamically added title");
	}
}
