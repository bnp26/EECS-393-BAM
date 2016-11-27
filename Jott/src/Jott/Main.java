package Jott;
	
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.*;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			MongoDB mongodb = new MongoDB();
			mongodb.insertLine("Notebook1", "page 1", 3, "abcdefg");
			System.out.println(mongodb.getLine("Notebook1", "page 1", 3));

			HBox page = (HBox) FXMLLoader.load(Main.class.getResource("static/JottPrototype.fxml"));

            Region secondRegion = getPagePaneRegion(page);
			Scene scene = new Scene(page,900, 600);

			NotebooksPane notebooksPane = new NotebooksPane();
			PagesPane pagesPane = new PagesPane();

			JottController controller = new JottController(notebooksPane, pagesPane);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void setUpGUI() {
		HBox mainWindow = new HBox();
		VBox primaryVBox, secondaryVBox, trinaryVBox, pagesVBox;

		primaryVBox = new VBox();
		secondaryVBox = new VBox();
		trinaryVBox = new VBox();

		mainWindow.getChildren().add(primaryVBox);
		primaryVBox.getChildren().add(secondaryVBox);
		secondaryVBox.getChildren().add(trinaryVBox);

		ComboBox<Notebook> notebookComboBox = new ComboBox<Notebook>();
		Notebook initNotebook = new Notebook("My First Notebook");
		notebookComboBox.getItems().add(initNotebook);

		trinaryVBox.getChildren().add(notebookComboBox);

		ScrollPane pagesScrollPane = new ScrollPane();
		pagesVBox = new VBox();

	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public Region getPagePaneRegion(Region region)
	{
		Region newRegion = new Region();
		while(region.getChildrenUnmodifiable().get(0).getClass() == Region.class) {
			System.out.println("trying to find Region");
		}
		return newRegion;
	}
}
/*
Notebook 
	-name (unique)

Page
	-name (also unique)
	-notebook (Foreign Key)

Line
	-linenum
	-linestr
	-Page (Forgein Key)


//Notebook <- Page (page has a foreign key to Notebook
//Page <- Line (Line has a foreign key to PAge)
*/
