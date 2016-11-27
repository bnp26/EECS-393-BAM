package Jott;

import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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

    @FXML //fx:id="pageMainPane"
    private AnchorPane pageAnchorPane;

    private boolean firstClick = false;
    private boolean toggleCaps = false;

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

        pagesPane.selectPage(newPage);

        Cursor cursor = new Cursor();
        newPage.setCursor(cursor);
        if(!firstClick) {
            mainFlowPane.getChildren().remove(0, 1);
            newPage.setFlowPane(mainFlowPane);
            firstClick = true;
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

            Cursor cursor;

            if (selectedPage.getCursor() == null)
                cursor = new Cursor();
            else
                cursor = selectedPage.getCursor();

            //FIX THIS, REMOVES THE LINES FROM THE FLOW PANE
            if (!pageAnchorPane.getChildrenUnmodifiable().contains(cursor.getCursorImage())) {
                pageAnchorPane.getChildren().add(cursor.getCursorImage());
            }
            cursor.move(loc);
        }

		if(selectedPage.getLines().size() < loc.getLineNum()) {
			System.out.println("adding cursor to the last line");
			loc.setLineNum(selectedPage.getLines().size()-1);
		}
		else if(selectedPage.getLines().size() < loc.getLineNum()+5){
            Line line = new Line(selectedPage.getLines().size());
            selectedPage.getLines().add(line);
            addLineToPage(line);
        }
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
                    cursor.move(updatedLoc);
                    break;
                }
            case DOWN:
                if(lineNum == lines.size()-1) {
                    break;
                }
                else {
                    lineNum += 1;
                    Location updatedLoc = new Location(lineNum, letterNum);
                    cursor.move(updatedLoc);
                    break;
                }
            case LEFT:
                if(letterNum == 0) {
                    lineNum-=1;
                    //need to fix lines before this starts to work
                    Location updatedLoc = new Location(lineNum, lines.get(lineNum).getLineValue().length()-1);
                    cursor.move(updatedLoc);
                    break;
                }
                else {
                    letterNum -= 1;
                    Location updatedLoc = new Location(lineNum, letterNum);
                    cursor.move(updatedLoc);
                    break;
                }
            case RIGHT:
                if (letterNum == lines.get(lineNum).getLineValue().length()-1 && lines.get(lineNum+1) != null) {
                    letterNum = 0;
                    lineNum +=1;
                }
                else {
                    letterNum+=1;
                }
                Location updatedLoc = new Location(lineNum, letterNum);
                cursor.move(updatedLoc);
                System.out.println(ke.getCode().toString());
                break;
            case ENTER:
                //gotta add instances if there is a bullet point and such
                if(lines.get(lineNum+1) != null) {
                    insertNewLine(lineNum+1);
                }
                cursor.move(lineNum+1, 0);
                System.out.println(ke.getCode().toString());
                break;
            case BACK_SPACE:
                if(letterNum < lines.get(lineNum).getLineValue().length()-1) {
                    Line line = lines.get(lineNum);
                    String lineVal = line.getLineValue();
                    lineVal = lineVal.substring(0, letterNum) + lineVal.substring(letterNum + 1);
                    letterNum -= 1;
                    //line.setLineValue(lineVal);
                } else if(letterNum == 0) {
                    lines.remove(lineNum);
                    lineNum -=1;
                    letterNum = lines.get(lineNum).getLineValue().length()-1;
                }
                else {
                    Line line = lines.get(lineNum);
                    String lineVal = line.getLineValue();
                    lineVal = lineVal.substring(0, letterNum);
                    //line.setLineValue(lineVal);
                }
                cursor.move(lineNum, letterNum);
                    break;
            case CAPS:
                toggleCaps();
                break;
            case DELETE:
                System.out.println("pressed " + ke.getCode().toString());
                break;
            case SHIFT:
                toggleCaps();
                break;
            case TAB:
                System.out.println("pressed " + ke.getCode().toString());
                for(int x = 1; x < 5; x++){
                    if(lines.get(lineNum).getLineValue().length() >= 40) {
                        cursor.move(lineNum + 1, 0);
                    }
                    lines.get(lineNum).insertLetter(cursor.getLocation(), ' ');
                    cursor.move(lineNum, letterNum+x);
                }
                break;
            default:
                cursor.getLocation().getLineNum();
                char letter = toggleCaps == true ? ke.getCode().getName().toUpperCase().charAt(0) : ke.getCode().getName().toLowerCase().charAt(0);
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), letter);
                letterNum+=1;
                cursor.move(lineNum, letterNum);
                System.out.println("letter = " + ke.getCode().toString());
                System.out.println("letter = " + ke.getCode().getName().toString());
                break;
        }
    }

    public void keyReleased(KeyEvent ke) {
        Page selectedPage = pagesPane.getSelectedPage();
        Cursor cursor = selectedPage.getCursor();

        LinkedList<Line> lines = selectedPage.getLines();
        switch(ke.getCode()){
            case SHIFT:
                toggleCaps();
                break;
        }
    }

    private void toggleCaps() {
        toggleCaps = !toggleCaps;
    }

    private void insertNewLine(int start) {
        Page selectedPage = pagesPane.getSelectedPage();
        LinkedList<Line> lines = selectedPage.getLines();

        Line newLine = new Line(start);
        lines.add(newLine);

        for(int x = lines.size()-2; x >= start; x--) {
            Line linePlaceHolder = lines.get(x);
            lines.set(x, newLine);
            lines.set(x-1, linePlaceHolder);
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

	private void addLineToPage(Line line) {
        this.mainFlowPane.getChildren().add(line.getLabel());
    }
	
	
	public void updateTitle(ActionEvent ae) {
		//when a new page or notebook is made/loaded, we should update the window's title
		//TODO: pull names of currently selected notebook/page in the form "Notebookname - Pagename"
		Stage stage = Stage.class.cast(Control.class.cast(ae.getSource()).getScene().getWindow());
		stage.setTitle("Dynamically added title");
	}
}
