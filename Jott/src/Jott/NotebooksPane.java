package Jott;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
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

	private VBox pagesVBox;

	public TextInputDialog newNotebookDialog;

	public NotebooksPane(VBox pagesVBox) {
		//here add all the notebooks in the mongoDB database to the notebooks array list.
		//this is initializing for now an empty array of notebooks

		this.pagesVBox = pagesVBox;

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
		Notebook newNotebook = new Notebook(name, pagesVBox);
		this.notebooks.put(name, newNotebook);
		return newNotebook;
	}

	public void selectNotebook(String name) {
		Notebook notebook = this.notebooks.get(name);
		Notebook oldNotebook;
		VBox pagePane;

		if(notebook == null) {
			System.out.print("could not find notebook");
			return;
		}

		pagesVBox.getChildren().remove(0, pagesVBox.getChildren().size());

		if(selectedNotebook == null)
		{
			selectedNotebook = notebook;
		} else {
			oldNotebook = selectedNotebook;
			selectedNotebook = notebook;
		}

		for(Page page : selectedNotebook.getPagesPane().getPages()) {
			if(pagesVBox.getChildren().contains(page.getButton()))
			    continue;

			pagesVBox.getChildren().add(page.getButton());
		}

		if(selectedNotebook.getPagesPane().getPages().size()>0)
			selectedNotebook.getPagesPane().getPages().get(0).selectPage();
	}

	public Notebook getSelectedNotebook() {
		return selectedNotebook;
	}
	
}
