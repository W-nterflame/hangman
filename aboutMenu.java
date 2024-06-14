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

public class aboutMenu extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("About Hangman Game");

        Text creditsText = new Text("Hangman Game\n\nDeveloped by:\n- Developer 1\n- Developer 2\n- Developer 3\n- Developer 4");
        creditsText.getStyleClass().add("credits-text");

        StackPane creditsPane = new StackPane(creditsText);
        creditsPane.setPrefSize(800, 600);

        TranslateTransition scrollCredits = new TranslateTransition(Duration.seconds(10), creditsText);
        scrollCredits.setFromY(creditsPane.getHeight() / 2 + 100);
        scrollCredits.setToY(-creditsPane.getHeight() / 2 - 100);
        scrollCredits.setCycleCount(TranslateTransition.INDEFINITE);
        scrollCredits.setAutoReverse(false);
        scrollCredits.play();

        Button backButton = new Button("Back to Main Menu");
        backButton.getStyleClass().add("menu-button");
        backButton.setOnAction(e -> showMainMenu(primaryStage));

        HBox bottomLeftBox = new HBox();
        bottomLeftBox.getChildren().add(backButton);
        bottomLeftBox.setAlignment(Pos.BOTTOM_LEFT);
        bottomLeftBox.setStyle("-fx-padding: 10px;");

        BorderPane mainLayout = new BorderPane();
        mainLayout.setCenter(creditsPane);
        mainLayout.setBottom(bottomLeftBox);
        mainLayout.getStyleClass().add("menu-root");

        Scene scene = new Scene(mainLayout, 800, 600);
        scene.getStylesheets().add(getClass().getResource("mainmenu.css").toExternalForm());

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
