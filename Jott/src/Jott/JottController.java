package Jott;

import java.util.LinkedList;
import java.util.Optional;

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
	private TextInputDialog newPageDialog;
	private Page page;
	
	@FXML //fx:id = "pagesVBox"
	private VBox pagesVBox;
		
	@FXML //fx:id="newPageButton"
	private Button newPageButton;

    @FXML //fx:id="mainFlowPane"
    private FlowPane mainFlowPane;

    @FXML //fx:id="pageScrollPane"
    private ScrollPane pageScrollPane;

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
	
	public void createNewNotebook(ActionEvent ae) {
		System.out.println("trying to create a new notebook");
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
		
		if(result.isPresent()) {
			newPageName = new String(result.get());
			
			if(pagesPane.hasPage(newPageName)) 
				System.out.println("this notebook already has a page by this name");
			else	
				addNewPage(newPageName);
		}
		
		System.out.println("clicked");
		System.out.println(pagesPane.toString());
		updateTitle(ae);
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
            if(!firstClick)
			    mainFlowPane.getChildren().remove(0, 1);
            Cursor cursor = new Cursor();
            selectedPage.setCursor(cursor);
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
                cursor.insertLetter(ke.getCharacter().toCharArray()[0]);
                System.out.println("letter = " + ke.getCharacter().toCharArray()[0]);
        }
    }

	private boolean addNewPage(String name) {
		
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
        pagesPane.selectPage(page);
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
