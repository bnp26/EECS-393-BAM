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
import javafx.scene.layout.VBox;

public class NotebooksPane {
	private HashMap<String, Notebook> notebooks;
	private MongoDB mdb;
	private Notebook selectedNotebook;
	
	public TextInputDialog newNotebookDialog;

	public NotebooksPane() {
		//here add all the notebooks in the mongoDB database to the notebooks array list.
		//this is initializing for now an empty array of notebooks
		notebooks = new HashMap<String, Notebook>();
		mdb = new MongoDB();

		ArrayList<String> notebooks = mdb.getNotebooks();

		for(String notebook:notebooks) {
			if(notebook.equals("admin") || notebook.equals("local") || notebook.equals("Create New Notebook"))
				continue;
			createNewNotebook(notebook);
		}
	}

	public ArrayList<Notebook> getNotebooks() {
		ArrayList<Notebook> notebooksArrayList = new ArrayList<Notebook>();

		notebooksArrayList.addAll(notebooks.values());

		return notebooksArrayList;
	}

	public Notebook getNotebook(String notebookStr) {
		return notebooks.get(notebookStr);
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

		Notebook oldNotebook = selectedNotebook;
		selectedNotebook = notebook;

		VBox pagePane = oldNotebook.getPagesPane().getPages().get(0).getVBox();

		pagePane.getChildren().remove(0, pagePane.getChildren().size());

		for(Page page : selectedNotebook.getPagesPane().getPages()) {
			pagePane.getChildren().add(page.getButton());
		}

		selectedNotebook.getPagesPane().getPages().get(0).selectPage();
	}

	public Notebook getSelectedNotebook() {
		return selectedNotebook;
	}
	
}
