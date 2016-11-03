package Jott;

import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputDialog;

public class NotebooksPane implements Initializable{
	private HashMap<String, Notebook> notebooks;
	
	private Notebook selectedNotebook;
	
	public TextInputDialog newNotebookDialog;
	
	@FXML	//fx:id="notebookxComboBox"
	private ComboBox<Notebook> notebooksComboBox;
	
	public NotebooksPane() {
		//here add all the notebooks in the mongoDB database to the notebooks array list.
		//this is initializing for now an empty array of notebooks
		notebooks = new HashMap<String, Notebook>();
		
		newNotebookDialogSetup();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		assert notebooksComboBox != null : "fx:id=\"notebooksComboBox\" was not injected: check your FXML file 'jottPrototype.fxml'.";
		
		notebooksComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Notebook>(){

			@Override
			public void changed(ObservableValue<? extends Notebook> selected, Notebook oldNotebook, Notebook newNotebook) {
				if(newNotebook.equals("Create New Notebook"))
				{
					String newNotebookName;
					Optional<String> result = newNotebookDialog.showAndWait();
					
					if(result.isPresent()) {
						newNotebookName = new String(result.get());
						
						Notebook createdNotebook = new Notebook(newNotebookName);
						
						notebooks.put(newNotebookName, createdNotebook);
					}
				}
				
				if(oldNotebook != null && newNotebook != null && oldNotebook.compareTo(newNotebook) != 0){
					selectedNotebook = notebooks.get(newNotebook);
				}
			}
		});
	}
	
	public void createNewNotebook(String name) {
		Notebook newNotebook = new Notebook(name);
	}
	
	public void updatePane() {
		
	}
	
	private void newNotebookDialogSetup()
	{
		//setup new notebook dialog box details
		newNotebookDialog = new TextInputDialog();
		
		newNotebookDialog.setTitle("Create New Notebook");
		newNotebookDialog.setHeaderText("Enter The Name of Your New Notebook");
	}
	
	
}
