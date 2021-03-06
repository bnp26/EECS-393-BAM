package Jott;

import java.lang.reflect.Array;
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

    private boolean toggleControl = false;
    private boolean toggleShift = false;
    private boolean toggleBold = false;
    private boolean toggleItalics = false;
    private boolean toggleUnderline = false;
    
    private StringBuilder highlitedString = new StringBuilder();
    private String currentlyCoppied = null;

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
        if(pageAnchorPane.getChildren().contains(highlightedShape))
            pageAnchorPane.getChildren().remove(highlightedShape);

        highlightedShape = new Polygon ();

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
    }

    public void mouseReleased(MouseEvent me) {
        double xLoc = me.getX();
        double yLoc = me.getY();

        Location loc = new Location();
        loc = loc.pixelsToLocation(xLoc, yLoc);

        highlightEnd = loc;
        System.out.println("HighlightedShape: " + highlightedShape.toString());
    }

    public void mouseDragged(MouseEvent me) {
        Page currentPage = pagesPane.getSelectedPage();

        double xLoc = me.getX();
        double yLoc = me.getY();

        Location loc = new Location();
        loc = loc.pixelsToLocation(xLoc, yLoc);
        Line firstLine = currentPage.getLines().get(highlightStart.getLineNum());
        Line lastLine = currentPage.getLines().get(loc.getLineNum());

        highlitedString.delete(0, highlitedString.length());

        if((highlightStart.getLetterNum() > loc.getLetterNum() && firstLine.getLineNum() == lastLine.getLineNum())) {
            highlitedString.append(firstLine.getLineValue().substring(loc.getLetterNum(), highlightStart.getLetterNum()));
        } else if (highlightStart.getLetterNum() < loc.getLetterNum() && firstLine.getLineNum() == lastLine.getLineNum()) {
            highlitedString.append(firstLine.getLineValue().substring(highlightStart.getLetterNum(), loc.getLetterNum()));
        }
        else if(firstLine.getLineNum() <= lastLine.getLineNum()) {
            for(int x = firstLine.getLineNum(); x <= lastLine.getLineNum(); x++) {
                if(x == firstLine.getLineNum())
                    highlitedString.append(currentPage.getLines().get(x).getLineValue().substring(highlightStart.getLetterNum()));
                else if(x == lastLine.getLineNum())
                    highlitedString.append(currentPage.getLines().get(x).getLineValue().substring(0, loc.getLetterNum()));
                else
                    highlitedString.append(currentPage.getLines().get(x).getLineValue());
            }
        }
        else {
            for(int x = firstLine.getLineNum(); x >= lastLine.getLineNum(); x--) {
                if(x == firstLine.getLineNum())
                    highlitedString.append(currentPage.getLines().get(x).getLineValue().substring(highlightStart.getLetterNum()));
                else if(x == lastLine.getLineNum())
                    highlitedString.append(currentPage.getLines().get(x).getLineValue().substring(0, loc.getLetterNum()));
                else
                    highlitedString.append(currentPage.getLines().get(x).getLineValue());
            }
        }

        System.out.println(highlitedString);

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
        System.out.println("Main VBox Height: " + this.mainVBox.getHeight());
        System.out.println("Main VBox Width: " + this.mainVBox.getWidth());
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

//            //FIX THIS, REMOVES THE LINES FROM THE FLOW PANE
//            if (!pageAnchorPane.getChildrenUnmodifiable().contains(cursor.getCursorImage())) {
//                pageAnchorPane.getChildren().add(cursor.getCursorImage());
//            }
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
                    moveCursor(updatedLoc);
                    break;
                }
            case DOWN:
                if(lineNum == lines.size()-1) {
                    break;
                }
                else {
                    lineNum += 1;
                    Location updatedLoc = new Location(lineNum, letterNum);
                    moveCursor(updatedLoc);
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
                moveCursor(updatedLocation);
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
                moveCursor(updatedLoc);
                System.out.println(ke.getCode().toString());
                break;
            case ENTER:
                //gotta add instances if there is a bullet point and such
                String newLineValue = "";
                if(lines.get(lineNum).isBulleted() && lines.get(lineNum).getLineValue().substring(lines.get(lineNum).getLineValue().length()-3).equals(" - ")) {
                    lines.get(lineNum).setLineValue("");
                    lines.get(lineNum).toggleBullet();
                    moveCursor(lineNum, 0);
                    lines.get(lineNum).updateLabel();
                    break;
                }
                else if(lines.get(lineNum).isBulleted()) {
                    selectedPage.createNewLine(lineNum+1);
                    for(int x = 0; x < lines.get(lineNum).getBulletTier()-1; x++) {
                        newLineValue+="  ";
                    }
                    newLineValue+=" - ";
                    lines.get(lineNum + 1).setLineValue(newLineValue);
                    lines.get(lineNum + 1).updateLabel();
                } else {
                    selectedPage.createNewLine(lineNum+1);
                }
                lineNum+=1;
                letterNum=0+newLineValue.length();
                moveCursor(lineNum, letterNum);
                break;
            case BACK_SPACE:
                if(letterNum == 3 && lines.get(lineNum).isBulleted()) {
                    Location loc1 = new Location(lineNum, 0);
                    Location loc2 = new Location(lineNum, 1);
                    Location loc3 = new Location(lineNum, 2);

                    lines.get(lineNum).removeLetter(loc3);
                    lines.get(lineNum).removeLetter(loc2);
                    lines.get(lineNum).removeLetter(loc1);

                    letterNum -= 3;
                    moveCursor(lineNum, letterNum);
                }else if(lines.get(lineNum).isBulleted() && lines.get(lineNum).getLineValue().charAt(letterNum-2) == '-') {
                    Location loc1 = new Location(lineNum, 0);
                    Location loc2 = new Location(lineNum, 1);

                    lines.get(lineNum).removeLetter(loc2);
                    lines.get(lineNum).removeLetter(loc1);
                }
                else if(letterNum == 0 && lineNum != 0) {
                    lines.remove(lineNum);
                    lineNum -= 1;
                    letterNum = lines.get(lineNum).getLineValue().length() - 1;
                    moveCursor(lineNum, letterNum);
                }
                else if(!(lineNum == 0 && letterNum == 0)){
                    Line line = lines.get(lineNum);

                    line.removeLetter(cursor.getLocation());
                    moveCursor(lineNum, letterNum-1);
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
                toggleShift=true;
                break;
            case TAB:
                System.out.println("pressed " + ke.getCode().toString());
                for(int x = 1; x < 5; x++){
                    if(lines.get(lineNum).getLineValue().length() >= 40) {
                        moveCursor(lineNum + 1, 0);
                    }
                    lines.get(lineNum).insertLetter(cursor.getLocation(), ' ');
                    moveCursor(lineNum, letterNum+x);
                }
                break;
            case DIGIT1:
                num = toggleSymbols == true ? '!' : '1';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                moveCursor(lineNum, letterNum);
                break;
            case DIGIT2:
                num = toggleSymbols == true ? '@' : '2';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                moveCursor(lineNum, letterNum);
                break;
            case DIGIT3:
                num = toggleSymbols == true ? '#' : '3';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                moveCursor(lineNum, letterNum);
                break;
            case DIGIT4:
                num = toggleSymbols == true ? '$' : '4';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                moveCursor(lineNum, letterNum);
                break;
            case DIGIT5:
                num = toggleSymbols == true ? '%' : '5';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                moveCursor(lineNum, letterNum);
                break;
            case DIGIT6:
                num = toggleSymbols == true ? '^' : '6';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                moveCursor(lineNum, letterNum);
                break;
            case DIGIT7:
                num = toggleSymbols == true ? '&' : '7';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                moveCursor(lineNum, letterNum);
                break;
            case DIGIT8:
                num = toggleSymbols == true ? '*' : '8';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                moveCursor(lineNum, letterNum);
                System.out.println("layout bounds anchor pane: "+mainVBox.getLayoutBounds().getHeight() + ", " + mainVBox.getLayoutBounds().getWidth());
                System.out.println("label size: " + lines.get(lineNum).getLabel().getLayoutBounds().getHeight() + ", " + lines.get(lineNum).getLabel().getLayoutBounds().getWidth());

                break;
            case DIGIT9:
                num = toggleSymbols == true ? '(' : '9';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                moveCursor(lineNum, letterNum);
                break;
            case DIGIT0:
                num = toggleSymbols == true ? ')' : '0';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                moveCursor(lineNum, letterNum);
                break;
            case COMMA:
                num = toggleSymbols == true ? '<' : ',';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                moveCursor(lineNum, letterNum);
                break;
            case PERIOD:
                num = toggleSymbols == true ? '>' : '.';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                moveCursor(lineNum, letterNum);
                break;
            case QUOTE:
                num = toggleSymbols == true ? '\"' : '\'';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                moveCursor(lineNum, letterNum);
                break;
            case SEMICOLON:
                num = toggleSymbols == true ? ':' : ';';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                moveCursor(lineNum, letterNum);
                break;
            case BACK_QUOTE:
                num = toggleSymbols == true ? '~' : '`';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                moveCursor(lineNum, letterNum);
                break;
            case SLASH:
                num = toggleSymbols == true ? '?' : '/';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                moveCursor(lineNum, letterNum);
                break;
            case BACK_SLASH:
                num = toggleSymbols == true ? '|' : '\\';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                moveCursor(lineNum, letterNum);
                break;
            case BRACELEFT:
                num = toggleSymbols == true ? '{' : '[';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                moveCursor(lineNum, letterNum);
                break;
            case BRACERIGHT:
                num = toggleSymbols == true ? '}' : ']';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                moveCursor(lineNum, letterNum);
                break;
            case MINUS:
                num = toggleSymbols == true ? '_' : '-';
                if(num == '-' && letterNum == 0) {
                    lines.get(lineNum).insertLetter(cursor.getLocation(), ' ');
                    lines.get(lineNum).insertLetter(cursor.getLocation(), num);
                    lines.get(lineNum).insertLetter(cursor.getLocation(), ' ');
                    lines.get(lineNum).toggleBullet();
                    letterNum += 3;
                    moveCursor(lineNum, letterNum);
                } else {
                    lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                    letterNum += 1;
                    moveCursor(lineNum, letterNum);
                }
                break;
            case EQUALS:
                num = toggleSymbols == true ? '+' : '=';
                lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), num);
                letterNum+=1;
                moveCursor(lineNum, letterNum);
                break;
            case SPACE:
                if(toggleControl && lines.get(lineNum).isBulleted()) {
                    lines.get(cursor.getLocation().getLineNum()).insertLetter(new Location(cursor.getLocation().getLineNum(), 0) , ' ');
                    lines.get(cursor.getLocation().getLineNum()).insertLetter(new Location(cursor.getLocation().getLineNum(), 0) , ' ');
                    letterNum += 2;
                    moveCursor(lineNum, letterNum);
                } else if(toggleShift && lines.get(lineNum).isBulleted() && !lines.get(lineNum).getLineValue().substring(0,3).equals(" - ")) {
                    Location loc1 = new Location(lineNum, 0);
                    lines.get(lineNum).removeLetter(loc1);
                    lines.get(lineNum).removeLetter(loc1);
                    letterNum-=2;
                    moveCursor(lineNum, letterNum);
                } else {
                    lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), ' ');
                    letterNum += 1;
                    moveCursor(lineNum, letterNum);
                }
                break;
            case CONTROL:
                toggleControl = true;
                break;
            default:
                if(toggleControl) {
                    // IF B - Bold
                    // IF C - Copy
                    // IF I - Italic
                    // IF U - Underline
                    // IF V - Paste
                    // IF SPACE - Tab Bullet Point
                    if(ke.getCode().getName().equals("B")) {
                        if(toggleBold) {
                            System.out.println("THE CONTROL IS HELD DOWN" + ke.getCode().getName());
                            System.out.println("ToggleBold to false");
                            toggleBold = false;
                        }
                        else {
                            System.out.println("THE CONTROL IS HELD DOWN" + ke.getCode().getName());
                            System.out.println("ToggleBold to true");
                            toggleBold = true;
                        }
                    }
                    else if (ke.getCode().getName().equals("I")) {
                        toggleItalics = true;
                    }
                    else if(ke.getCode().getName().equals("C")) {
                        currentlyCoppied = highlitedString.toString();
                    }
                    else if(ke.getCode().getName().equals("X")) {
                        
                        currentlyCoppied = highlitedString.toString();
                        letterNum -= currentlyCoppied.length();
                        moveCursor(lineNum, letterNum);

                        for(int i = 0; i<=currentlyCoppied.length(); i++) {
                            if(highlightStart != highlightEnd)
                                lines.get(cursor.getLocation().getLineNum()).removeLetter(highlightStart);
                        }
                        
                        letterNum -= 1;
                        moveCursor(lineNum, letterNum);
                    }
                    else if(ke.getCode().getName().equals("V")) {
                        for(int i = 0; i<currentlyCoppied.length(); i++) {
                            lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), currentlyCoppied.charAt(currentlyCoppied.length() - i - 1));
                            letterNum += 1;
                        }                  
                        /*
                        for(char character:currentlyCoppied.toCharArray()) {
                            lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), character);
                            letterNum += 1;
                        }
                        */
                        moveCursor(lineNum, letterNum);
                    }
                }
                else {
                    char letter = toggleCaps == true ? ke.getCode().getName().toUpperCase().charAt(0) : ke.getCode().getName().toLowerCase().charAt(0);
                    lines.get(cursor.getLocation().getLineNum()).insertLetter(cursor.getLocation(), letter);
                    letterNum+=1;
                    moveCursor(lineNum, letterNum);
                    System.out.println("letter = " + ke.getCode().toString());
                    System.out.println("letter = " + ke.getCode().getName().toString());
                    break;
                }
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
                toggleShift = false;
                break;
            case CONTROL:
                toggleControl = false;
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
        pagesVBox.getChildren().add(0, newPage);
		
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
        pagesPane.getSelectedPage().deselectPage();
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

        MongoDB mdb = new MongoDB();
        this.notebooksPane = new NotebooksPane(pagesVBox);

		ArrayList<String> notebooks = mdb.getNotebooks();

        createNotebooksFromDB(notebooks);

        for(int x = 0; x < notebooksPane.getNotebooks().size(); x++) {
            Notebook current = notebooksPane.getNotebooks().get(x);

            ArrayList<String> pages = mdb.getPages(current.toString());
            ArrayList<Page> pagesArrayList = createAndAddPagesToNotebook(current, pages);

            for(Page page:pagesArrayList) {
                long pageLength = mdb.getPageLength(current.toString(), page.getName());

                for(int y = 1; y <= pageLength; y++) {
                    Line line = new Line(y-1);
                    line.setLineValue(mdb.getLine(current.toString(), page.getName(), y));
                    page.getLines().add(line);
                }
            }
        }
        this.pagesPane = new PagesPane();
    }

    private void createNotebooksFromDB(ArrayList<String> notebooks) {
        for(String notebook:notebooks) {
            if(notebook.equals("admin") || notebook.equals("local") || notebook.equals("Create New Notebook"))
                continue;
            createNewNotebook(notebook);
            System.out.println("Notebook that was created: " + notebooksPane.getNotebook(notebook));
        }
    }

    private ArrayList<Page> createAndAddPagesToNotebook(Notebook notebook, ArrayList<String> pages) {
        ArrayList<Page> pagesArrayList = new ArrayList();

        for(String pageStr:pages) {
            if(pageStr.equals("-**BLANK**-") || pageStr.equals("system.indexes"))
                continue;

            Page page = notebook.getPagesPane().createNewPage(pageStr);

            notebook.getPagesPane().addPage(page);
            pagesArrayList.add(page);
        }

        return pagesArrayList;
    }

    private void moveCursor(Location loc) {
        Page page = pagesPane.getSelectedPage();
        page.getCursor().move(loc);
        if(pageAnchorPane.getChildren().contains(highlightedShape)) {
            pageAnchorPane.getChildren().remove(highlightedShape);
            highlightedShape = new Polygon();
        }
    }

    private void moveCursor(int lineNum, int letterNum) {

        Page page = pagesPane.getSelectedPage();
        page.getCursor().move(lineNum, letterNum);
        if(pageAnchorPane.getChildren().contains(highlightedShape)) {
            pageAnchorPane.getChildren().remove(highlightedShape);
            highlightedShape = new Polygon();
        }
    }
}
