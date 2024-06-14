package application;

import javafx.application.Application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import java.util.Random;

public class PVPHangmanGame extends Application {
    private String wordToGuess;
    private String hint;
    private int incorrectGuesses;
    private int currentPlayer;
    private int[] playerPoints = new int[2];
    private int currentRound = 1;
    private Canvas canvas;
    private Label wordLabel;
    private TextField guessInput;
    private Label hintLabel;
    private Label messageLabel;
    private Label roundLabel;
    private Label scoreLabel;
    private char[] currentGuess;
    private ComboBox<String> categoryComboBox;
    private boolean wordSet = false;

    private static final int MAX_INCORRECT_GUESSES = 10;
    private static final int TOTAL_ROUNDS = 5;
    private BorderPane layout;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hangman Game");

        VBox menuLayout = new VBox(20);
        menuLayout.setAlignment(Pos.CENTER);

        categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll(Categories.getCategories());
        categoryComboBox.setValue("Animals"); // Default category

        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> flipCoin(primaryStage));
        styleButton(startButton);

        menuLayout.getChildren().addAll(categoryComboBox, startButton);
        Scene menuScene = new Scene(menuLayout, 600, 600);
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    public void flipCoin(Stage primaryStage) {
    	VBox flipLayout = new VBox(20);
    	flipLayout.setAlignment(Pos.CENTER);
    	
    	Label flipLabel = new Label("Flipping a coin to decide who starts...");
    	flipLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
    	flipLabel.setTextFill(Color.WHITE);
    	
    	flipLayout.getChildren().add(flipLabel);
        flipLayout.setStyle("-fx-background-color: #2c3e50;");
        
    	Scene flipScene = new Scene(flipLayout, 600, 600);
    	Stage flipStage = new Stage();
    	flipStage.setScene(flipScene);
    	flipStage.show();
    	
    	Random random = new Random();
    	currentPlayer = random.nextBoolean() ? 1 : 2;
    	
    	Label resultLabel = new Label("Player " + currentPlayer + " will start!");
        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        resultLabel.setTextFill(Color.YELLOW);

        Button continueButton = new Button("Continue");
        styleButton(continueButton);
        continueButton.setOnAction(e -> {
        	flipStage.close();
        	startGame(primaryStage);
        });

        flipLayout.getChildren().addAll(resultLabel, continueButton);
    }
    
    public void startGame(Stage primaryStage) {
        layout = new BorderPane();
        canvas = new Canvas(400, 400);

        initializeLabels();
        
        Button guessButton = createGuessButton();

        VBox topLayout = new VBox(20);
        topLayout.getChildren().addAll(roundLabel, wordLabel, canvas, messageLabel, scoreLabel, hintLabel);
        topLayout.setAlignment(Pos.CENTER);

        HBox bottomLayout = new HBox(20);
        bottomLayout.setAlignment(Pos.CENTER);
        bottomLayout.getChildren().addAll(guessInput, guessButton);

        StackPane root = new StackPane(layout);
        root.setStyle("-fx-background-color: #2c3e50;");

        layout.setTop(topLayout);
        layout.setBottom(bottomLayout);

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        initializeGameState();
        messageLabel.setText("Player " + currentPlayer + " starts!");
    }
    
    private void initializeLabels() {
    	 wordLabel = new Label();
         wordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
         wordLabel.setTextFill(Color.WHITE);

         hintLabel = new Label("Hint: ");
         hintLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
         hintLabel.setTextFill(Color.YELLOW);

         guessInput = new TextField();
         guessInput.setPromptText("Enter a letter");
         guessInput.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
         guessInput.setStyle("-fx-background-radius: 10; -fx-padding: 10;");

         messageLabel = new Label("Player " + currentPlayer + " starts!");
         messageLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
         messageLabel.setTextFill(Color.RED);

         roundLabel = new Label("Round: 1");
         roundLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
         roundLabel.setTextFill(Color.WHITE);

         scoreLabel = new Label("Player 1 : 0 | Player 2 : 0");
         scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
         scoreLabel.setTextFill(Color.WHITE);
    }
    
    private Button createGuessButton() {
    	Button guessButton = new Button("Guess");
        styleButton(guessButton);
        guessButton.setOnAction(e -> makeGuess());

        guessInput.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                guessButton.fire();
            }
        });
        return guessButton;
    }

    private void initializeGameState() {
        String category = categoryComboBox.getValue();
        String[] wordsWithHints = Categories.getWordsWithHints(category);
        Random random = new Random();
        int index = random.nextInt(wordsWithHints.length);
        String wordWithHint = wordsWithHints[index];
        String[] parts = wordWithHint.split(": ");
        wordToGuess = parts[0].trim();
        hint = parts[1].trim();

        currentGuess = new char[wordToGuess.length()];
        for (int i = 0; i < wordToGuess.length(); i++) {
            currentGuess[i] = '_';
        }
        incorrectGuesses = 0;
        wordSet = true;
        messageLabel.setText("New word to guess!");
        wordLabel.setText(getCurrentGuessDisplay());
        hintLabel.setText("Hint: " + hint);
        drawHangman();
    }

    private void makeGuess() {
        if (!wordSet) return;

        String input = guessInput.getText().trim().toLowerCase();
        guessInput.clear();

        if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
            char guessedLetter = input.charAt(0);

            if (wordToGuess.indexOf(guessedLetter) < 0) {
                incorrectGuesses++;
                drawHangman();

                if (incorrectGuesses >= MAX_INCORRECT_GUESSES) {
                	messageLabel.setText("Player " + currentPlayer + " has been hanged! The word was: " + wordToGuess);
                	switchPlayer();
                    displayRoundEnd();
                    return;
                }
                switchPlayer();
            } else {
                updateWordLabel(guessedLetter);
                if (isWordGuessed()) {
                	messageLabel.setText("Player " + currentPlayer + " guessed the word! The word was: " + wordToGuess);
                    playerPoints[currentPlayer - 1]++;
                    displayRoundEnd();
                    return;
                }
                messageLabel.setText("Correct guess! Player " + currentPlayer + ", you can continue.");
            }
        } else {
            messageLabel.setText("Please enter a single letter.");
        }
    }


    private void updateWordLabel(char guessedLetter) {
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == guessedLetter) {
                currentGuess[i] = guessedLetter;
            }
        }
        wordLabel.setText(getCurrentGuessDisplay());
    }

    private boolean isWordGuessed() {
        for (char c : currentGuess) {
            if (c == '_') {
                return false;
            }
        }
        return true;
    }

    private String getCurrentGuessDisplay() {
        StringBuilder display = new StringBuilder();
        for (char c : currentGuess) {
            display.append(c).append(' ');
        }
        return display.toString().trim();
    }

    private void drawHangman() {
    	GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.WHITE);
        gc.setLineWidth(2.0);
        gc.setStroke(Color.WHITE);
        // Base of the gallows
        gc.strokeLine(50, 250, 150, 250);

        // Vertical post
        if (incorrectGuesses > 0) gc.strokeLine(100, 50, 100, 250);

        // Horizontal beam
        if (incorrectGuesses > 1) gc.strokeLine(100, 50, 200, 50);

        // Rope
        if (incorrectGuesses > 2) gc.strokeLine(200, 50, 200, 70);

        // Head
        if (incorrectGuesses > 3) gc.strokeOval(180, 70, 40, 40);

        // Body
        if (incorrectGuesses > 4) gc.strokeLine(200, 110, 200, 180);

        // Left arm
        if (incorrectGuesses > 5) gc.strokeLine(200, 130, 170, 100);

        // Right arm
        if (incorrectGuesses > 6) gc.strokeLine(200, 130, 230, 100);

        // Left leg
        if (incorrectGuesses > 7) gc.strokeLine(200, 180, 170, 210);

        // Right leg
        if (incorrectGuesses > 8) gc.strokeLine(200, 180, 230, 210);

        // Eyes
        if (incorrectGuesses > 9) {
            gc.strokeLine(190, 80, 195, 85);
            gc.strokeLine(195, 80, 190, 85);
            gc.strokeLine(205, 80, 210, 85);
            gc.strokeLine(210, 80, 205, 85);
        }
    }

    private void switchPlayer() {
        currentPlayer = currentPlayer == 1 ? 2 : 1;
        messageLabel.setText("Incorrect guess! Player " + currentPlayer + ", it's your turn.");
    }

    private void displayRoundEnd() {
    	if (currentRound > TOTAL_ROUNDS || incorrectGuesses >= MAX_INCORRECT_GUESSES) {
            displayGameOver();
        } else {
            currentRound++;
            roundLabel.setText("Round: " + currentRound);
            scoreLabel.setText("Player 1: " + playerPoints[0] + " | Player 2: " + playerPoints[1]);
            wordSet = false;
            initializeGameState();
            messageLabel.setText("Player " + currentPlayer + " starts!");
        }
    }
    
    private void displayGameOver() {
    	guessInput.clear();
        guessInput.setDisable(true);

        String winner;
        if (playerPoints[0] > playerPoints[1]) {
            winner = "Player 1 wins!";
        } else if (playerPoints[0] < playerPoints[1]) {
            winner = "Player 2 wins!";
        } else {
            winner = "It's a tie!";
        }

        Label winnerLabel = new Label(winner);
        winnerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        winnerLabel.setTextFill(Color.YELLOW);

        Button mainMenuButton = new Button("Main Menu");
        Button playAgainButton = new Button("Play Again");

        mainMenuButton.setOnAction(e -> returnToMainMenu((Stage) mainMenuButton.getScene().getWindow()));
        playAgainButton.setOnAction(e -> resetForNewGame());

        styleButton(mainMenuButton);
        styleButton(playAgainButton);

        HBox endGameOptions = new HBox(20, mainMenuButton, playAgainButton);
        endGameOptions.setAlignment(Pos.CENTER);

        VBox gameOverLayout = new VBox(20, winnerLabel, endGameOptions);
        gameOverLayout.setAlignment(Pos.CENTER);

        layout.setBottom(gameOverLayout);
    }

    private void resetForNewGame() {
    	guessInput.setDisable(false);

        Button guessButton = new Button("Guess");
        guessButton.setOnAction(e -> makeGuess());

        styleButton(guessButton);

        HBox bottomLayout = new HBox(20);
        bottomLayout.setAlignment(Pos.CENTER);
        bottomLayout.getChildren().addAll(guessInput, guessButton);

        layout.setBottom(bottomLayout);

        playerPoints = new int[2];
        currentRound = 1;
        currentPlayer = 1;
        roundLabel.setText("Round: 1");
        scoreLabel.setText("Player 1: 0 | Player 2: 0");

        flipCoin((Stage) guessButton.getScene().getWindow());
    }

    private void returnToMainMenu(Stage stage) {
        MainMenu mainMenu = new MainMenu();
        try {
            mainMenu.start(stage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void styleButton(Button button) {
        button.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        button.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 10; -fx-padding: 10;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-background-radius: 10; -fx-padding: 10;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 10; -fx-padding: 10;"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
