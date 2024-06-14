package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class aboutMenu extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("About Hangman Game");

        // Credits text
        Text creditsText = new Text("Hangman Game\n\nDeveloped by:\n- Developer 1\n- Developer 2\n- Developer 3\n- Developer 4");
        creditsText.setStyle("-fx-font-size: 16px; -fx-text-alignment: center; -fx-fill: white; -fx-font-weight: bold;");

        // Back button to return to the main menu
        Button backButton = new Button("Back to Main Menu");
        backButton.getStyleClass().add("menu-button");
        backButton.setOnAction(e -> showMainMenu(primaryStage));

        VBox layout = new VBox(20);
        layout.getChildren().addAll(creditsText, backButton);
        layout.setAlignment(Pos.CENTER);

        BorderPane mainLayout = new BorderPane();
        mainLayout.setCenter(layout);
        mainLayout.getStyleClass().add("menu-root");

        Scene scene = new Scene(mainLayout, 800, 600);
        scene.getStylesheets().add(getClass().getResource("aboutMenu.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showMainMenu(Stage primaryStage) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

