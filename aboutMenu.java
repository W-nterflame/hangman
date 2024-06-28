	package application;
	
	import javafx.animation.TranslateTransition;
	import javafx.application.Application;
	import javafx.geometry.Pos;
	import javafx.scene.Scene;
	import javafx.scene.control.Button;
	import javafx.scene.layout.BorderPane;
	import javafx.scene.layout.HBox;
	import javafx.scene.layout.StackPane;
	import javafx.scene.text.Text;
	import javafx.stage.Stage;
	import javafx.util.Duration;
	
	/**
	 * The aboutMenu class sets up and displays the "About" menu of the Hangman game.
	 * It provides information about the developers of the game and includes an animated credits text.
	 */
	public class aboutMenu extends Application {
	
	    /**
	     * The start method is the main entry point for JavaFX applications.
	     * It sets up the "About" menu scene and displays it.
	     * 
	     * @param primaryStage The primary stage for this application.
	     */
	    @Override
	    public void start(Stage primaryStage) {
	        primaryStage.setTitle("About Hangman Game");
	
	        // Credits text
	        Text creditsText = new Text("Hangman Game\n\nDeveloped by:\nGroup 3");
	        creditsText.setStyle("-fx-font-size: 22px; -fx-font-family: 'Century Gothic'; -fx-font-weight: bold; -fx-fill: white; -fx-text-alignment: center;");
	
	        StackPane creditsPane = new StackPane(creditsText);
	        creditsPane.setPrefSize(800, 600);
	
	        // Animated scrolling credits
	        TranslateTransition scrollCredits = new TranslateTransition(Duration.seconds(10), creditsText);
	        scrollCredits.setFromY(creditsPane.getHeight() / 10 + 100);
	        scrollCredits.setToY(-creditsPane.getHeight() / 10 - 100);
	        scrollCredits.setCycleCount(TranslateTransition.INDEFINITE);
	        scrollCredits.setAutoReverse(true);
	        scrollCredits.play();
	
	        // Back button
	        Button backButton = new Button("Back to Main Menu");
	        backButton.getStyleClass().add("menu-button");
	        backButton.setOnAction(e -> showMainMenu(primaryStage));
	
	        HBox bottomLeftBox = new HBox();
	        bottomLeftBox.getChildren().add(backButton);
	        bottomLeftBox.setAlignment(Pos.BOTTOM_LEFT);
	        bottomLeftBox.setStyle("-fx-padding: 10px;");
	
	        // Main layout
	        BorderPane mainLayout = new BorderPane();
	        mainLayout.setCenter(creditsPane);
	        mainLayout.setBottom(bottomLeftBox);
	        mainLayout.getStyleClass().add("menu-root");
	
	        Scene scene = new Scene(mainLayout, 800, 600);
	        scene.getStylesheets().add(getClass().getResource("mainmenu.css").toExternalForm());
	
	        primaryStage.setScene(scene);
	        primaryStage.show();
	    }
	
	    /**
	     * Navigates back to the main menu by launching the MainMenu class.
	     * 
	     * @param primaryStage The primary stage for this application.
	     */
	    private void showMainMenu(Stage primaryStage) {
	        MainMenu mainMenu = new MainMenu();
	        mainMenu.start(primaryStage);
	    }
	
	    /**
	     * The main method is the standard entry point for Java applications.
	     * It calls the launch method to start the JavaFX application.
	     * 
	     * @param args Command-line arguments.
	     */
	    public static void main(String[] args) {
	        launch(args);
	    }
	}
