package Jott;

import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
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
	private TextInputDialog newPageDialog, newNotebookDialog;
	private Page page;
	
	@FXML //fx:id = "pagesVBox"
	private VBox pagesVBox;
		
	@FXML //fx:id="newPageButton"
	private Button newPageButton;

    @FXML //fx:id="mainFlowPane"
    private FlowPane mainFlowPane;

    @FXML //fx:id="pageScrollPane"
    private ScrollPane pageScrollPane;

    @FXML //fx:id="notebooksComboBox"
    private ComboBox notebooksComboBox;


    private boolean firstClick = false;

	public JottController() {
		this.notebooksPane = new NotebooksPane();
		this.pagesPane = new PagesPane();
	}
	
	public JottController(NotebooksPane notebooksPane, PagesPane pagesPane) {
		this.notebooksPane = notebooksPane;
		this.pagesPane = pagesPane;
	}
	
	public void viewAllNotebooks(ActionEvent ae) {
		
	}

	public void initializeComboBox() {
        notebooksComboBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object oldValue, Object newValue) {
                String oldStr = (String)oldValue;
                String newStr = (String)newValue;

                if(newStr.equals("Create New Notebook")) {
                    Notebook newestNotebook;

                    newNotebookDialog = new TextInputDialog();
                    newNotebookDialog .setTitle("Create New Notebook");
                    newNotebookDialog .setHeaderText("Enter the name of your new Notebook!");

                    String newNotebookName;
                    Optional<String> result = newNotebookDialog .showAndWait();

                    if(result.isPresent()) {
                        newNotebookName = new String(result.get());

                        if(pagesPane.hasPage(newNotebookName))
                            System.out.println("this notebook already has a page by this name");
                        else
                            newestNotebook = createNewNotebook(newNotebookName);
                    }
                }

            }
        });

    }

	public void moveCursor(Event e) {
	}

	public void createNewPage(ActionEvent ae) {
		//when the "Create New Note" button is clicked, logic here is executed.
		newPageDialog = new TextInputDialog();
		newPageDialog.setTitle("Create New Page");
		newPageDialog.setHeaderText("Enter your new pages name");
		
		String newPageName;
		Optional<String> result = newPageDialog.showAndWait();

        Page newPage = null;

		if(result.isPresent()) {
			newPageName = new String(result.get());
			
			if(pagesPane.hasPage(newPageName)) 
				System.out.println("this notebook already has a page by this name");
			else	
				newPage = addNewPage(newPageName);
		}
		
		System.out.println("clicked");
		System.out.println(pagesPane.toString());
		updateTitle(ae);

        Cursor cursor;

        if(pagesPane.getSelectedPage() == newPage) {
            cursor = new Cursor();
            newPage.setCursor(cursor);
            if(!firstClick) {
                mainFlowPane.getChildren().remove(0, 1);
                firstClick = true;
            }
        }

	}

	public void pageClicked(MouseEvent me) {
		//prints out the x and y coordinates of the click
		System.out.println(me.getX() + ", " + me.getY());

		double xLoc = me.getX();
		double yLoc = me.getY();
		Location loc = new Location();
		loc = loc.pixelsToLocation(xLoc, yLoc);

		Page selectedPage = pagesPane.getSelectedPage();

		if(selectedPage == null){
			System.out.println("page is null");
		}
		else {
            System.out.println("page is not null");
            selectedPage.setFlowPane(mainFlowPane);

            Cursor cursor;

            if(selectedPage.getCursor() == null)
                cursor = new Cursor();
            else
                cursor = selectedPage.getCursor();

            selectedPage.setCursor(cursor);
            if(!mainFlowPane.getChildren().contains(cursor.getLabel()))
                mainFlowPane.getChildren().add(cursor.getLabel());

		}

		if(selectedPage.getLines().size() < loc.getLineNum()) {
			System.out.println("adding cursor to the last line");
			loc.setLineNum(selectedPage.getLines().size()-1);
		}
        Cursor cursor = selectedPage.getCursor();
        if(cursor == null) {
            System.out.println("FUCK THIS SHIT. IT DOESN'T FIND THE CURSOR THAT WAS ALREADY INITIALIZED!");
        }
        else{
            cursor.setLocation(loc);
        }
        Line line = new Line();
        selectedPage.addLine();
        mainFlowPane.getChildren().add(line.getLabel());
	}

    public void keyPressed(KeyEvent ke) {

        Page selectedPage = pagesPane.getSelectedPage();
        Cursor cursor = selectedPage.getCursor();

        int lineNum = cursor.getLocation().getLineNum();
        int letterNum = cursor.getLocation().getLetterNum();

        LinkedList<Line> lines = selectedPage.getLines();

        switch(ke.getCode()){
            case UP:
                if(lineNum == 0) {
                    break;
                }
                else {
                    lineNum -= 1;
                    Location updatedLoc = new Location(lineNum, letterNum);
                    cursor.setLocation(updatedLoc);
                    break;
                }
            case DOWN:
                if(lineNum == lines.size()-1) {
                    break;
                }
                else {
                    lineNum += 1;
                    Location updatedLoc = new Location(lineNum, letterNum);
                    cursor.setLocation(updatedLoc);
                    break;
                }
            case LEFT:
                if(letterNum == 0) {
                    lineNum-=1;
                    Location updatedLoc = new Location(lineNum, lines.get(lineNum).getLineValue().length()-1);
                    cursor.setLocation(updatedLoc);
                    break;
                }
                else {
                    lineNum += 1;
                    Location updatedLoc = new Location(lineNum, letterNum);
                    cursor.setLocation(updatedLoc);
                    break;
                }
            case RIGHT:
                if (letterNum == lines.get(lineNum).getLineValue().length()-1) {

                }
                System.out.println(ke.getCode().toString());
                break;
            case ENTER:
                Line newLine = new Line();
                lines.add(newLine);
                break;
            case BACK_SPACE:
                cursor.backspace();
                break;
            case DELETE:
                cursor.delete();
                break;
            case SHIFT:
                break;
            case TAB:
                break;
            default:
                //newLine.insertLetter(cursor.getLocation(), ;
                System.out.println("letter = " + ke.getCharacter().toCharArray()[0]);
                System.out.println("letter = " + ke.getCode().getName().toCharArray()[0]);
        }
    }

	private Page addNewPage(String name) {
		
		if(name == null)
		{
			name = "new page";
		}
		
		//creates a new page object
		Page page = new Page(name, mainFlowPane);

		//creates the new page button
		Button newPage = createPageButton(name);

		//finds the number of children in the pagesVBox
		int vBoxSize = pagesVBox.getChildrenUnmodifiable().size();
		
		page.setButton(newPage); 
		//adds the new button to the pagesVBox but puts it always at the end of the list but above the add new page button
		pagesVBox.getChildren().add(vBoxSize-1, newPage);
		
		pagesPane.addPage(page);

        newPage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pagesPane.selectPage(page);
                System.out.println("selected page: " + page.getName());
            }
        });

        pagesPane.selectPage(page);
		newPage.setVisible(true);
		return page;
	}

    private Notebook createNewNotebook(String name) {

        if(name == null)
        {
            name = "new notebook";
        }

        Notebook newNotebook = notebooksPane.createNewNotebook(name);

        //finds the number of children in the pagesVBox
        int comboItemsSize = notebooksComboBox.getChildrenUnmodifiable().size();


        notebooksPane.selectNotebook(newNotebook.toString());
        notebooksComboBox.getItems().add(0, newNotebook.toString());
        refreshNotebooksComboBox();
        initializeComboBox();
        notebooksComboBox.getSelectionModel().select(newNotebook.toString());
        return newNotebook;
    }

    private void refreshNotebooksComboBox() {
        ArrayList<Notebook> myNotebooks = notebooksPane.getNotebooks();

        final ObservableList<String> notebooksArray = notebooksComboBox.getItems();

        for(Notebook current : myNotebooks) {
            if(!notebooksArray.contains(current.toString()) && !current.equals(notebooksPane.getSelectedNotebook()))
                notebooksComboBox.getItems().add(current.toString());
        }
        notebooksComboBox.getItems().set(notebooksComboBox.getItems().size()-1, "Create New Notebook");
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
