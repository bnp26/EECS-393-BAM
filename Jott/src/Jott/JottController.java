package Jott;

import java.awt.*;
import java.net.URL;
import java.util.*;

import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.TextInputDialog;
/**
 * <p>This Class is dedicated for handling all actions and events in Jott.
 * @author bnp26
 *
 */
public class JottController implements Initializable {

	private NotebooksPane notebooksPane;
	private PagesPane pagesPane;
	private TextInputDialog newPageDialog, newNotebookDialog;
	private Page page;
	private MongoDB mongoDB;
	
	@FXML //fx:id = "pagesVBox"
	private VBox pagesVBox;

	@FXML //fx:id="newPageButton"
	private JFXButton newPageButton;

    @FXML //fx:id="mainVBox"
    private VBox mainVBox;

    @FXML //fx:id="pageScrollPane"
    private ScrollPane pageScrollPane;

    @FXML //fx:id="notebooksComboBox"
    private ComboBox notebooksComboBox;

    @FXML //fx:id="pageMainPane"
    private AnchorPane pageAnchorPane;

    private boolean firstClick = false;
    private boolean toggleCaps = false;
    private boolean toggleSymbols = false;

    private String highlitedString = "";
    private Location highlightStart = null;
    private Location highlightEnd = null;

    private Polygon highlightedShape = new Polygon ();

    public JottController() {
        this.notebooksPane = null;
        this.pagesPane = null;
    }

    public JottController(NotebooksPane notebooksPane, PagesPane pagesPane) {
		this.notebooksPane = notebooksPane;
		this.pagesPane = pagesPane;
	}

	public PagesPane getPagesPane() {
	    return this.pagesPane;
    }

    public NotebooksPane getNotebooksPane() {
        return this.notebooksPane;
    }

    public void setPagesPane(PagesPane pagesPane) {
        this.pagesPane = pagesPane;
    }

    public void mousePressed(MouseEvent me) {
        double xLoc = me.getX();
        double yLoc = me.getY();

        Location loc = new Location();
        loc = loc.pixelsToLocation(xLoc, yLoc);

        highlightStart = loc;

        double startXPixel = highlightStart.getXPixelValue();
        double startYPixel = highlightStart.getYPixelValue();

        double startXPixel2 = startXPixel + 2.0;
        double startYPixel2 = startYPixel + 15.0;

        highlightedShape.getPoints().addAll(new Double[] {
                startXPixel, startYPixel,
                startXPixel2, startYPixel2
        });
        if(!pageAnchorPane.getChildren().contains(highlightedShape))
            pageAnchorPane.getChildren().add(highlightedShape);

        highlitedString = "";
    }

    public void mouseReleased(MouseEvent me) {
        double xLoc = me.getX();
        double yLoc = me.getY();

        Location loc = new Location();
        loc = loc.pixelsToLocation(xLoc, yLoc);

        highlightEnd = loc;

        pageAnchorPane.getChildren().remove(highlightedShape);
        highlightedShape = new Polygon ();
    }

    public void mouseDragged(MouseEvent me) {
        Page currentPage = pagesPane.getSelectedPage();

        double xLoc = me.getX();
        double yLoc = me.getY();

        Location loc = new Location();
        loc = loc.pixelsToLocation(xLoc, yLoc);
        Line firstLine = currentPage.getLines().get(highlightStart.getLineNum());
        Line lastLine = currentPage.getLines().get(loc.getLineNum());

        if(firstLine.getLineNum() < lastLine.getLineNum()) {
            for(int x = firstLine.getLineNum(); x <= lastLine.getLineNum(); x++) {
                if(x == firstLine.getLineNum())
                    highlitedString += currentPage.getLines().get(x).getLineValue().substring(highlightStart.getLetterNum());
                else if(x == lastLine.getLineNum())
                    highlitedString += currentPage.getLines().get(x).getLineValue().substring(0, loc.getLetterNum());
                else
                    highlitedString += currentPage.getLines().get(x).getLineValue();
            }
        }

        double startXPixel = highlightStart.getXPixelValue();
        double startYPixel = highlightStart.getYPixelValue();

        double startXPixel2 = startXPixel + 2.0;
        double startYPixel2 = startYPixel + 15.0;

        //this is the top left corner
        double endXPixel = loc.getXPixelValue();
        double endYPixel = loc.getYPixelValue();

        double endXPixel2 = endXPixel + 2.0;
        double endYPixel2 = endYPixel + 15.0;

        highlightedShape.getPoints().remove(0, highlightedShape.getPoints().size());

        int longestLine = 0;

        for(Line line : pagesPane.getSelectedPage().getLines()) {
            if(longestLine < line.getLineValue().length()) {
                longestLine = line.getLineValue().length();
            }
        }
        System.out.println("(" + startXPixel + ", " + startYPixel + ")");
        System.out.println("(" + startXPixel2 + ", " + startYPixel2 + ")");
        System.out.println("(" + 0.0 + ", " + startYPixel2 + ")");
        System.out.println("(" + 0.0 + ", " + endYPixel2 + ")");
        System.out.println("(" + endXPixel2 + ", " + endYPixel2 + ")");
        System.out.println("(" + endXPixel + ", " + endYPixel + ")");
        System.out.println("(" + 8.40625 * longestLine + ", " + endYPixel + ")");
        System.out.println("(" + 8.40625 * longestLine + ", " + startYPixel + ")");
        if(loc.getLineNum() != highlightStart.getLineNum()) {
            //this is going above
            Double pointsArray[] = new Double[16];
            ArrayList<Double> points = new ArrayList<Double>();

            pointsArray[0] = startXPixel; //top left of cursor starting loc
            pointsArray[1] = startYPixel;

            pointsArray[2] = startXPixel2; //bottom left of cursor starting loc
            pointsArray[3] = startYPixel2;

            pointsArray[4] = 0.0;          //left most edge of page at bottom of the cursors height
            pointsArray[5] = startYPixel2;

            pointsArray[6] = 0.0;          //left most edge of page at bottom of current loc's height
            pointsArray[7] = endYPixel2;

            pointsArray[8] = endXPixel2;
            pointsArray[9] = endYPixel2;

            pointsArray[10] = endXPixel;
            pointsArray[11] = endYPixel;

            pointsArray[12] = pageAnchorPane.getWidth();
            pointsArray[13] = endYPixel;

            pointsArray[14] = pageAnchorPane.getWidth();
            pointsArray[15] = startYPixel;

            highlightedShape.getPoints().addAll(pointsArray);

        }
        else {
            //this is the top left corner


            highlightedShape.getPoints().addAll(new Double[] {
                    startXPixel, startYPixel,
                    startXPixel2, startYPixel2,
                    endXPixel2, endYPixel2,
                    endXPixel, endYPixel
            });

            Color color = Color.rgb(175, 238, 238, 0.45);

            highlightedShape.setFill(color);
            highlightedShape.setVisible(true);
        }

        Page selectedPage = pagesPane.getSelectedPage();

//        Line draggedLine = selectedPage.getLines().get(loc.getLineNum());
//        char letter = draggedLine.getLineValue().charAt(loc.getLetterNum());
    }

	public void initializeComboBox() {
        refreshNotebooksComboBox();
        notebooksComboBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object oldValue, Object newValue) {
                String oldStr = (String)oldValue;
                String newStr = (String)newValue;

                System.out.println(ov.getValue());
                if(newStr != null && newStr.equals("Create New Notebook")) {
                    Notebook newestNotebook;

                    newNotebookDialog = new TextInputDialog();
                    newNotebookDialog .setHeaderText("Enter the name of your new Notebook!");

                    String newNotebookName;
                    Optional<String> result = newNotebookDialog .showAndWait();

                    if(result.isPresent()) {
                        newNotebookName = new String(result.get());

                        if(pagesPane.hasPage(newNotebookName))
                            System.out.println("this notebook already has a page by this name");
                        else {
                            createNewNotebook(newNotebookName);
                            refreshNotebooksComboBox();
                        }
                    }
                    refreshNotebooksComboBox();
                }
                else {
                    notebooksPane.selectNotebook((String)newValue);
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

            if (pagesPane.hasPage(newPageName))
                System.out.println("this notebook already has a page by this name");
            else
                newPage = addNewPage(newPageName);
            updateTitle(ae);

            Cursor cursor = new Cursor();
            newPage.setCursor(cursor);
            if (!firstClick) {
                mainVBox.getChildren().remove(0, 1);
                newPage.setVBox(mainVBox);
                firstClick = true;
            }

            pagesPane.selectPage(newPage);

            pageAnchorPane.requestFocus();
            newPage.getCursor().move(0, 0);
            newPage.getCursor().getCursorImage().setVisible(true);
            pageAnchorPane.getChildren().add(newPage.getCursor().getCursorImage());
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
        if(selectedPage == null) {
		    return;
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
        pageAnchorPane.requestFocus();
	}

    public void keyPressed(KeyEvent ke) {

        Page selectedPage = pagesPane.getSelectedPage();
        Cursor cursor = selectedPage.getCursor();

        int lineNum = cursor.getLocation().getLineNum();
        int letterNum = cursor.getLocation().getLetterNum();

        LinkedList<Line> lines = selectedPage.getLines();
        char num;

        System.out.println("code = " + ke.getCode().toString());
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
                if(letterNum == 0 && lineNum != 0) {
                    lineNum-=1;
                    //need to fix lines before this starts to work
                    letterNum = lines.get(lineNum).getLineValue().length() - 1;
                }
                else {
                    letterNum-=1;
                }

                Location updatedLocation = new Location(lineNum, letterNum);
                cursor.move(updatedLocation);
                pageAnchorPane.setFocusTraversable(true);
                System.out.println(ke.getCode().toString());
                break;
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
                selectedPage.createNewLine(lineNum+1);
                lineNum+=1;
                break;
            case BACK_SPACE:
                if(letterNum == 0 && lineNum != 0) {
                    lines.remove(lineNum);
                    lineNum -= 1;
                    letterNum = lines.get(lineNum).getLineValue().length() - 1;
                    cursor.move(lineNum, letterNum);
                }
                else if(!(lineNum == 0 && letterNum == 0)){
                    Line line = lines.get(lineNum);

                    line.removeLetter(cursor.getLocation());
                    cursor.move(lineNum, letterNum-1);
                }
                break;
            case CAPS:
                toggleCaps();
                break;
            case DELETE:
                System.out.println("pressed " + ke.getCode().toString());
                break;
            case SHIFT:
                toggleCaps();
                toggleSymbols();
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
            case DIGIT1:
                num = toggleSymbols == true ? '!' : '1';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                cursor.move(lineNum, letterNum);
                break;
            case DIGIT2:
                num = toggleSymbols == true ? '@' : '2';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                cursor.move(lineNum, letterNum);
                break;
            case DIGIT3:
                num = toggleSymbols == true ? '#' : '3';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                cursor.move(lineNum, letterNum);
                break;
            case DIGIT4:
                num = toggleSymbols == true ? '$' : '4';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                cursor.move(lineNum, letterNum);
                break;
            case DIGIT5:
                num = toggleSymbols == true ? '%' : '5';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                cursor.move(lineNum, letterNum);
                break;
            case DIGIT6:
                num = toggleSymbols == true ? '^' : '6';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                cursor.move(lineNum, letterNum);
                break;
            case DIGIT7:
                num = toggleSymbols == true ? '&' : '7';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                cursor.move(lineNum, letterNum);
                break;
            case DIGIT8:
                num = toggleSymbols == true ? '*' : '8';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                cursor.move(lineNum, letterNum);
                break;
            case DIGIT9:
                num = toggleSymbols == true ? '(' : '9';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                cursor.move(lineNum, letterNum);
                break;
            case DIGIT0:
                num = toggleSymbols == true ? ')' : '0';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                cursor.move(lineNum, letterNum);
                break;
            case MINUS:
                num = toggleSymbols == true ? '_' : '-';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                cursor.move(lineNum, letterNum);
                break;
            case EQUALS:
                num = toggleSymbols == true ? '+' : '=';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                cursor.move(lineNum, letterNum);
                break;
            case SPACE:
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), ' ');
                letterNum+=1;
                cursor.move(lineNum, letterNum);
                break;
            default:
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
                toggleSymbols();
                break;
        }
    }

    private void toggleCaps() {
        toggleCaps = !toggleCaps;
    }

    private void toggleSymbols() {
        toggleSymbols = !toggleSymbols;
    }

    private void insertNewLine(int start) {
        Page selectedPage = pagesPane.getSelectedPage();
        selectedPage.createNewLine(start);
        selectedPage.addLinesToPage();
    }

	private Page addNewPage(String name) {
		
		if(name == null)
		{
			name = "new page";
		}
		
		//creates a new page object
		Page page = new Page(name, mainVBox);

		//creates the new page button
		JFXButton newPage = createPageButton(name);
		
		page.setButton(newPage); 
		//adds the new button to the pagesVBox but puts it always at the end of the list but above the add new page button
        pagesVBox.getChildren().add(newPage);
		
		pagesPane.addPage(page);

        newPage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pagesPane.selectPage(page);
                System.out.println("selected page: " + page.getName());
            }
        });
        Rectangle cursor = pagesPane.getSelectedPage().getCursor().getCursorImage();
        int cursorImageIndex = pageAnchorPane.getChildren().indexOf(cursor);
        if(cursorImageIndex != -1) {
            pageAnchorPane.getChildren().remove(cursorImageIndex);
        }
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
        notebooksComboBox.getSelectionModel().select(newNotebook.toString());
        return newNotebook;
    }

    public void refreshNotebooksComboBox() {
        ArrayList<Notebook> myNotebooks = notebooksPane.getNotebooks();
        if(notebooksComboBox.getItems().size() > 0)
            notebooksComboBox.getItems().remove(0, notebooksComboBox.getItems().size());

        for(Notebook current : myNotebooks) {
            notebooksComboBox.getItems().add(current.toString());
        }
        notebooksComboBox.getItems().set(notebooksComboBox.getItems().size()-1, "Create New Notebook");
    }

	private JFXButton createPageButton(String name) {
		
		JFXButton newPage = new JFXButton();
		//setting up the new page button
		newPage.setText(name);
		
		newPage.getStyleClass().add("jott_page_item");
		
		return newPage;
	}

	private void addLineToPage(Line line) {
        this.mainVBox.getChildren().add(line.getLabel());
    }
	
	
	public void updateTitle(ActionEvent ae) {
		//when a new page or notebook is made/loaded, we should update the window's title
		//TODO: pull names of currently selected notebook/page in the form "Notebookname - Pagename"
		Stage stage = Stage.class.cast(Control.class.cast(ae.getSource()).getScene().getWindow());
		stage.setTitle("Dynamically added title");
	}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.notebooksPane = new NotebooksPane(pagesVBox);
        this.pagesPane = new PagesPane();
    }
}
