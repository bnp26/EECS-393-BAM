package Jott;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputDialog;

public class NotebooksPane {
	private HashMap<String, Notebook> notebooks;
	
	private Notebook selectedNotebook;
	
	public TextInputDialog newNotebookDialog;

	public NotebooksPane() {
		//here add all the notebooks in the mongoDB database to the notebooks array list.
		//this is initializing for now an empty array of notebooks
		notebooks = new HashMap<String, Notebook>();
	}

	public ArrayList<Notebook> getNotebooks() {
		ArrayList<Notebook> notebooksArrayList = new ArrayList<Notebook>();

		notebooksArrayList.addAll(notebooks.values());

		return notebooksArrayList;
	}

	public Notebook createNewNotebook(String name) {
		Notebook newNotebook = new Notebook(name);
		this.notebooks.put(name, newNotebook);
		return newNotebook;
	}

	public void selectNotebook(String name) {
		Notebook notebook = this.notebooks.get(name);
		if(notebook == null) {
			System.out.print("could not find notebook");
			return;
		}

		selectedNotebook = notebook;
	}

	public Notebook getSelectedNotebook() {
		return selectedNotebook;
	}
	
}
