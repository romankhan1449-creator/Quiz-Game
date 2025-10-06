import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.*;

public class QuizApp extends Application {
    
    private Stage primaryStage;
    private String playerName = "";
    private String selectedSubject = "";
    private String selectedDifficulty = "";
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int timeRemaining = 30;
    private Timeline timer;
    private List<Question> currentQuestions;
    private QuestionBank questionBank;
    
    private Label timerLabel;
    private Label questionLabel;
    private RadioButton[] optionButtons;
    private ToggleGroup optionsGroup;
    private Button submitButton;
    
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setTitle("Quiz Master - MCQ Test Application");
        
        questionBank = new QuestionBank();
        showWelcomeScreen();
        
        primaryStage.show();
    }
    
    private void showWelcomeScreen() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #667eea 0%, #764ba2 50%, #f093fb 100%);");
        
        VBox centerBox = new VBox(40);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(50));
        
        Label mainTitle = new Label("QUIZ MASTER");
        mainTitle.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 68));
        mainTitle.setTextFill(Color.WHITE);
        mainTitle.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 20, 0.6, 0, 6);");
        
        Label subtitle = new Label("MCQ Test Application");
        subtitle.setFont(Font.font("Georgia", FontWeight.BOLD, 28));
        subtitle.setTextFill(Color.rgb(255, 223, 0));
        subtitle.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 12, 0.4, 0, 4);");
        
        Region line = new Region();
        line.setMaxWidth(400);
        line.setMinHeight(3);
        line.setStyle("-fx-background-color: linear-gradient(to right, transparent, #FFD700, transparent);");
        
        VBox featureBox = new VBox(15);
        featureBox.setAlignment(Pos.CENTER);
        featureBox.setMaxWidth(500);
        featureBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.18); " +
                           "-fx-background-radius: 25px; " +
                           "-fx-padding: 30px; " +
                           "-fx-border-color: rgba(255, 255, 255, 0.4); " +
                           "-fx-border-radius: 25px; " +
                           "-fx-border-width: 2px;");
        
        Label featureTitle = new Label("Test Your Knowledge");
        featureTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 22));
        featureTitle.setTextFill(Color.WHITE);
        
        String[] features = {
            "✓ 4 Challenging Subjects",
            "✓ 3 Difficulty Levels",
            "✓ 15 Random Questions",
            "✓ 30 Second Timer per Question"
        };
        
        for (String feature : features) {
            Label featureLabel = new Label(feature);
            featureLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 17));
            featureLabel.setTextFill(Color.rgb(240, 240, 255));
            featureBox.getChildren().add(featureLabel);
        }
        
        VBox inputBox = new VBox(15);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setMaxWidth(350);
        
        Label namePrompt = new Label("Enter Your Name to Begin:");
        namePrompt.setFont(Font.font("Verdana", FontWeight.BOLD, 19));
        namePrompt.setTextFill(Color.WHITE);
        
        TextField nameField = new TextField();
        nameField.setPromptText("Your Name");
        nameField.setMaxWidth(300);
        nameField.setStyle("-fx-font-size: 19px; " +
                          "-fx-padding: 14px; " +
                          "-fx-background-radius: 12px; " +
                          "-fx-border-radius: 12px; " +
                          "-fx-background-color: white; " +
                          "-fx-text-fill: #333;");
        
        Button startButton = new Button("START TEST");
        startButton.setFont(Font.font("Impact", FontWeight.BOLD, 22));
        startButton.setPrefSize(260, 60);
        startButton.setStyle("-fx-background-color: linear-gradient(to bottom, #FF6B6B, #EE5A6F); " +
                           "-fx-text-fill: white; " +
                           "-fx-background-radius: 35px; " +
                           "-fx-border-color: #C92A2A; " +
                           "-fx-border-width: 3px; " +
                           "-fx-border-radius: 35px; " +
                           "-fx-cursor: hand; " +
                           "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 12, 0.6, 0, 6);");
        
        startButton.setOnMouseEntered(e -> startButton.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #FF8787, #FF6B6B); " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 35px; " +
            "-fx-border-color: #C92A2A; " +
            "-fx-border-width: 3px; " +
            "-fx-border-radius: 35px; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.7), 18, 0.8, 0, 9); " +
            "-fx-scale-x: 1.08; " +
            "-fx-scale-y: 1.08;"));
        
        startButton.setOnMouseExited(e -> startButton.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #FF6B6B, #EE5A6F); " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 35px; " +
            "-fx-border-color: #C92A2A; " +
            "-fx-border-width: 3px; " +
            "-fx-border-radius: 35px; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 12, 0.6, 0, 6);"));
        
        startButton.setOnAction(e -> {
            playerName = nameField.getText().trim();
            if (playerName.isEmpty()) {
                showAlert("Error", "Please enter your name to continue!");
            } else {
                showRulesScreen();
            }
        });
        
        inputBox.getChildren().addAll(namePrompt, nameField, startButton);
        
        centerBox.getChildren().addAll(mainTitle, subtitle, line, featureBox, inputBox);
        
        root.setCenter(centerBox);
        
        Scene scene = new Scene(root, 900, 700);
        primaryStage.setScene(scene);
    }
    
    private void showRulesScreen() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #00b09b 0%, #96c93d 100%);");
        
        Label titleLabel = new Label("GAME RULES");
        titleLabel.setFont(Font.font("Impact", FontWeight.BOLD, 45));
        titleLabel.setTextFill(Color.WHITE);
        
        VBox rulesBox = new VBox(15);
        rulesBox.setAlignment(Pos.CENTER_LEFT);
        rulesBox.setMaxWidth(600);
        rulesBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95); -fx-padding: 35px; -fx-background-radius: 20px;");
        
        String[] rules = {
            "✓ Choose a subject and difficulty level",
            "✓ You will get 15 random questions",
            "✓ Each question has 30 seconds timer",
            "✓ You MUST select an answer to proceed",
            "✓ Results will be shown at the end",
            "✓ You need 50% marks to pass",
            "✓ No negative marking"
        };
        
        for (String rule : rules) {
            Label ruleLabel = new Label(rule);
            ruleLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 19));
            ruleLabel.setTextFill(Color.BLACK);
            rulesBox.getChildren().add(ruleLabel);
        }
        
        Button continueButton = new Button("I UNDERSTAND - LET'S GO!");
        continueButton.setFont(Font.font("Verdana", FontWeight.BOLD, 19));
        continueButton.setStyle("-fx-font-size: 19px; -fx-background-color: #3498db; -fx-text-fill: white; " +
                              "-fx-padding: 15px 35px; -fx-background-radius: 25px; -fx-cursor: hand;");
        continueButton.setOnAction(e -> showSubjectSelection());
        
        root.getChildren().addAll(titleLabel, rulesBox, continueButton);
        
        Scene scene = new Scene(root, 900, 700);
        primaryStage.setScene(scene);
    }
    
    private void showSubjectSelection() {
        VBox root = new VBox(25);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #8e2de2 0%, #4a00e0 100%);");
        
        Label titleLabel = new Label("SELECT SUBJECT");
        titleLabel.setFont(Font.font("Impact", FontWeight.BOLD, 45));
        titleLabel.setTextFill(Color.WHITE);
        
        HBox subjectsBox = new HBox(20);
        subjectsBox.setAlignment(Pos.CENTER);
        
        String[] subjects = {"Math", "Science", "History", "Geography"};
        String[] emojis = {"🔢", "🔬", "📜", "🌍"};
        
        for (int i = 0; i < subjects.length; i++) {
            Button subjectBtn = createSubjectButton(subjects[i], emojis[i]);
            subjectsBox.getChildren().add(subjectBtn);
        }
        
        root.getChildren().addAll(titleLabel, subjectsBox);
        
        Scene scene = new Scene(root, 900, 700);
        primaryStage.setScene(scene);
    }
    
    private Button createSubjectButton(String subject, String emoji) {
        VBox btnBox = new VBox(10);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setStyle("-fx-background-color: white; -fx-padding: 30px; -fx-background-radius: 20px; -fx-cursor: hand;");
        btnBox.setMinWidth(150);
        
        Label emojiLabel = new Label(emoji);
        emojiLabel.setFont(Font.font(50));
        
        Label textLabel = new Label(subject);
        textLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 19));
        
        btnBox.getChildren().addAll(emojiLabel, textLabel);
        
        Button invisibleBtn = new Button();
        invisibleBtn.setGraphic(btnBox);
        invisibleBtn.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        
        invisibleBtn.setOnMouseEntered(e -> btnBox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 30px; " +
                                                           "-fx-background-radius: 20px; -fx-cursor: hand;"));
        invisibleBtn.setOnMouseExited(e -> btnBox.setStyle("-fx-background-color: white; -fx-padding: 30px; " +
                                                          "-fx-background-radius: 20px; -fx-cursor: hand;"));
        
        invisibleBtn.setOnAction(e -> {
            selectedSubject = subject;
            showDifficultySelection();
        });
        
        return invisibleBtn;
    }
    
    private void showDifficultySelection() {
        VBox root = new VBox(25);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #fc4a1a 0%, #f7b733 100%);");
        
        Label titleLabel = new Label("SELECT DIFFICULTY");
        titleLabel.setFont(Font.font("Impact", FontWeight.BOLD, 45));
        titleLabel.setTextFill(Color.WHITE);
        
        HBox difficultyBox = new HBox(20);
        difficultyBox.setAlignment(Pos.CENTER);
        
        String[] difficulties = {"EASY", "MEDIUM", "HARD"};
        String[] colors = {"#27ae60", "#f39c12", "#c0392b"};
        
        for (int i = 0; i < difficulties.length; i++) {
            Button diffBtn = createDifficultyButton(difficulties[i], colors[i]);
            difficultyBox.getChildren().add(diffBtn);
        }
        
        root.getChildren().addAll(titleLabel, difficultyBox);
        
        Scene scene = new Scene(root, 900, 700);
        primaryStage.setScene(scene);
    }
    
    private Button createDifficultyButton(String difficulty, String color) {
        Button btn = new Button(difficulty);
        btn.setFont(Font.font("Impact", FontWeight.BOLD, 26));
        btn.setPrefSize(190, 110);
        btn.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-background-radius: 20px; -fx-cursor: hand;");
        
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; " +
                                               "-fx-background-radius: 20px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 15, 0, 0, 0);"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-background-radius: 20px;"));
        
        btn.setOnAction(e -> {
            selectedDifficulty = difficulty;
            startQuiz();
        });
        
        return btn;
    }
    
    private void startQuiz() {
        if (!questionBank.getQuestions().containsKey(selectedSubject)) {
            showAlert("Error", "Invalid subject selected!");
            return;
        }
        currentQuestions = questionBank.getRandomQuestions(selectedSubject, 15);
        if (currentQuestions.isEmpty()) {
            showAlert("Error", "No questions available!");
            return;
        }
        currentQuestionIndex = 0;
        score = 0;
        showQuestion();
    }
    
    private void showQuestion() {
        // Stop previous timer if exists
        if (timer != null) {
            timer.stop();
        }
        
        // Check if quiz is complete
        if (currentQuestionIndex >= currentQuestions.size()) {
            showResultScreen();
            return;
        }
        
        Question q = currentQuestions.get(currentQuestionIndex);
        
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #134e5e 0%, #71b280 100%);");
        
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(20));
        topBar.setSpacing(20);
        topBar.setStyle("-fx-background-color: rgba(0, 0, 0, 0.35);");
        
        Label playerLabel = new Label("Player: " + playerName);
        playerLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 19));
        playerLabel.setTextFill(Color.WHITE);
        
        Label subjectLabel = new Label("Subject: " + selectedSubject);
        subjectLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 19));
        subjectLabel.setTextFill(Color.WHITE);
        
        Label questionNumLabel = new Label("Question " + (currentQuestionIndex + 1) + "/" + currentQuestions.size());
        questionNumLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 19));
        questionNumLabel.setTextFill(Color.WHITE);
        
        timerLabel = new Label("Time: 30s");
        timerLabel.setFont(Font.font("Impact", FontWeight.BOLD, 26));
        timerLabel.setTextFill(Color.rgb(255, 255, 102));
        
        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        
        topBar.getChildren().addAll(playerLabel, spacer1, subjectLabel, questionNumLabel, spacer2, timerLabel);
        
        VBox questionBox = new VBox(30);
        questionBox.setAlignment(Pos.CENTER);
        questionBox.setPadding(new Insets(40));
        
        questionLabel = new Label(q.getQuestion());
        questionLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 24));
        questionLabel.setTextFill(Color.WHITE);
        questionLabel.setWrapText(true);
        questionLabel.setMaxWidth(700);
        questionLabel.setAlignment(Pos.CENTER);
        
        VBox optionsBox = new VBox(15);
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setMaxWidth(600);
        
        optionsGroup = new ToggleGroup();
        optionButtons = new RadioButton[4];
        
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new RadioButton(q.getOptions()[i]);
            optionButtons[i].setToggleGroup(optionsGroup);
            optionButtons[i].setFont(Font.font("Segoe UI", FontWeight.NORMAL, 19));
            optionButtons[i].setTextFill(Color.WHITE);
            optionButtons[i].setStyle("-fx-background-color: rgba(255, 255, 255, 0.25); -fx-padding: 16px; " +
                                     "-fx-background-radius: 12px;");
            optionsBox.getChildren().add(optionButtons[i]);
        }
        
        submitButton = new Button("SUBMIT ANSWER");
        submitButton.setFont(Font.font("Impact", FontWeight.BOLD, 20));
        submitButton.setDisable(true);
        submitButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-padding: 16px 45px; " +
                            "-fx-background-radius: 30px; -fx-opacity: 0.6;");
        
        // Enable submit button when an option is selected
        optionsGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                submitButton.setDisable(false);
                submitButton.setStyle("-fx-background-color: linear-gradient(to bottom, #e74c3c, #c0392b); " +
                                    "-fx-text-fill: white; -fx-padding: 16px 45px; " +
                                    "-fx-background-radius: 30px; -fx-cursor: hand; -fx-opacity: 1.0;");
            }
        });
        
        submitButton.setOnAction(e -> {
            timer.stop();
            checkAnswer();
        });
        
        questionBox.getChildren().addAll(questionLabel, optionsBox, submitButton);
        
        root.setTop(topBar);
        root.setCenter(questionBox);
        
        Scene scene = new Scene(root, 900, 700);
        primaryStage.setScene(scene);
        
        startTimer();
    }
    
    private void startTimer() {
        timeRemaining = 30;
        timerLabel.setText("Time: " + timeRemaining + "s");
        timerLabel.setTextFill(Color.rgb(255, 255, 102));
        
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeRemaining--;
            timerLabel.setText("Time: " + timeRemaining + "s");
            
            if (timeRemaining <= 10) {
                timerLabel.setTextFill(Color.rgb(255, 99, 71));
            }
            
            if (timeRemaining <= 0) {
                timer.stop();
                // Time out - move to next question without checking answer (no score)
                currentQuestionIndex++;
                Platform.runLater(() -> showQuestion());
            }
        }));
        
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }
    
    private void checkAnswer() {
        Question q = currentQuestions.get(currentQuestionIndex);
        RadioButton selected = (RadioButton) optionsGroup.getSelectedToggle();
        
        // Only check if something is selected
        if (selected != null && selected.getText().equals(q.getCorrectAnswer())) {
            score++;
        }
        
        currentQuestionIndex++;
        Platform.runLater(() -> showQuestion());
    }
    
    private void showResultScreen() {
        // Stop timer if still running
        if (timer != null) {
            timer.stop();
        }
        
        VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        
        int totalQuestions = currentQuestions.size();
        int percentage = (score * 100) / totalQuestions;
        boolean passed = percentage >= 50;
        
        root.setStyle(passed ? "-fx-background-color: linear-gradient(to bottom, #00b09b 0%, #96c93d 100%);" :
                              "-fx-background-color: linear-gradient(to bottom, #c0392b 0%, #e74c3c 100%);");
        
        Label resultLabel = new Label(passed ? "CONGRATULATIONS!" : "BETTER LUCK NEXT TIME!");
        resultLabel.setFont(Font.font("Impact", FontWeight.BOLD, 50));
        resultLabel.setTextFill(Color.WHITE);
        
        Label nameLabel = new Label(playerName);
        nameLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 32));
        nameLabel.setTextFill(Color.WHITE);
        
        VBox scoreBox = new VBox(10);
        scoreBox.setAlignment(Pos.CENTER);
        scoreBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95); -fx-padding: 35px; -fx-background-radius: 20px;");
        
        Label scoreLabel = new Label("Your Score: " + score + "/" + totalQuestions);
        scoreLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        scoreLabel.setTextFill(Color.BLACK);
        
        Label percentageLabel = new Label("Percentage: " + percentage + "%");
        percentageLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 24));
        percentageLabel.setTextFill(Color.BLACK);
        
        Label statusLabel = new Label(passed ? "PASSED" : "FAILED");
        statusLabel.setFont(Font.font("Impact", FontWeight.BOLD, 26));
        statusLabel.setTextFill(passed ? Color.rgb(39, 174, 96) : Color.rgb(192, 57, 43));
        
        scoreBox.getChildren().addAll(scoreLabel, percentageLabel, statusLabel);
        
        HBox buttonsBox = new HBox(20);
        buttonsBox.setAlignment(Pos.CENTER);
        
        Button playAgainBtn = new Button("PLAY AGAIN");
        playAgainBtn.setFont(Font.font("Impact", FontWeight.BOLD, 20));
        playAgainBtn.setStyle("-fx-font-size: 20px; -fx-background-color: #27ae60; -fx-text-fill: white; " +
                            "-fx-padding: 14px 35px; -fx-background-radius: 25px; -fx-cursor: hand;");
        playAgainBtn.setOnAction(e -> {
            currentQuestionIndex = 0;
            score = 0;
            showSubjectSelection();
        });
        
        Button exitBtn = new Button("EXIT");
        exitBtn.setFont(Font.font("Impact", FontWeight.BOLD, 20));
        exitBtn.setStyle("-fx-font-size: 20px; -fx-background-color: #c0392b; -fx-text-fill: white; " +
                       "-fx-padding: 14px 35px; -fx-background-radius: 25px; -fx-cursor: hand;");
        exitBtn.setOnAction(e -> Platform.exit());
        
        buttonsBox.getChildren().addAll(playAgainBtn, exitBtn);
        
        root.getChildren().addAll(resultLabel, nameLabel, scoreBox, buttonsBox);
        
        Scene scene = new Scene(root, 900, 700);
        primaryStage.setScene(scene);
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

class Question {
    private String question;
    private String[] options;
    private String correctAnswer;
    
    public Question(String question, String[] options, String correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
    
    public String getQuestion() { return question; }
    public String[] getOptions() { return options; }
    public String getCorrectAnswer() { return correctAnswer; }
}

class QuestionBank {
    private Map<String, List<Question>> questions;
    
    public QuestionBank() {
        questions = new HashMap<>();
        initializeQuestions();
    }
    
    public Map<String, List<Question>> getQuestions() {
        return questions;
    }
    
    private void initializeQuestions() {
        List<Question> mathQuestions = new ArrayList<>();
        mathQuestions.add(new Question("What is 15 + 27?", 
            new String[]{"42", "52", "32", "62"}, "42"));
        mathQuestions.add(new Question("What is 8 × 7?", 
            new String[]{"54", "56", "64", "48"}, "56"));
        mathQuestions.add(new Question("What is the square root of 144?", 
            new String[]{"10", "11", "12", "13"}, "12"));
        mathQuestions.add(new Question("What is 100 ÷ 4?", 
            new String[]{"20", "25", "30", "35"}, "25"));
        mathQuestions.add(new Question("What is 2³?", 
            new String[]{"6", "8", "9", "12"}, "8"));
        mathQuestions.add(new Question("What is 50% of 200?", 
            new String[]{"50", "75", "100", "150"}, "100"));
        mathQuestions.add(new Question("What is the value of π (pi) approximately?", 
            new String[]{"2.14", "3.14", "4.14", "5.14"}, "3.14"));
        mathQuestions.add(new Question("What is 9²?", 
            new String[]{"72", "81", "90", "99"}, "81"));
        mathQuestions.add(new Question("What is 15 - 8?", 
            new String[]{"5", "6", "7", "8"}, "7"));
        mathQuestions.add(new Question("How many sides does a hexagon have?", 
            new String[]{"4", "5", "6", "7"}, "6"));
        mathQuestions.add(new Question("What is 3 × 13?", 
            new String[]{"36", "39", "42", "45"}, "39"));
        mathQuestions.add(new Question("What is 72 ÷ 9?", 
            new String[]{"6", "7", "8", "9"}, "8"));
        mathQuestions.add(new Question("What is 5 + 5 × 5?", 
            new String[]{"25", "30", "50", "55"}, "30"));
        mathQuestions.add(new Question("What is the sum of angles in a triangle?", 
            new String[]{"90°", "180°", "270°", "360°"}, "180°"));
        mathQuestions.add(new Question("What is 20% of 50?", 
            new String[]{"5", "10", "15", "20"}, "10"));
        mathQuestions.add(new Question("What is 11 × 11?", 
            new String[]{"111", "121", "131", "141"}, "121"));
        mathQuestions.add(new Question("What is the perimeter of a square with side 5?", 
            new String[]{"10", "15", "20", "25"}, "20"));
        mathQuestions.add(new Question("What is 64 ÷ 8?", 
            new String[]{"6", "7", "8", "9"}, "8"));
        mathQuestions.add(new Question("What is 2 × 2 × 2 × 2?", 
            new String[]{"8", "12", "16", "20"}, "16"));
        mathQuestions.add(new Question("What is 1000 - 1?", 
            new String[]{"99", "999", "9999", "1001"}, "999"));
        mathQuestions.add(new Question("How many minutes are in 2 hours?", 
            new String[]{"60", "90", "120", "150"}, "120"));
        mathQuestions.add(new Question("What is 7 × 8?", 
            new String[]{"48", "54", "56", "64"}, "56"));
        mathQuestions.add(new Question("What is half of 88?", 
            new String[]{"40", "42", "44", "46"}, "44"));
        mathQuestions.add(new Question("What is 15 × 3?", 
            new String[]{"35", "40", "45", "50"}, "45"));
        mathQuestions.add(new Question("What is the area of a rectangle 5×4?", 
            new String[]{"9", "18", "20", "25"}, "20"));
        
        List<Question> scienceQuestions = new ArrayList<>();
        scienceQuestions.add(new Question("What is H2O?", 
            new String[]{"Oxygen", "Hydrogen", "Water", "Carbon"}, "Water"));
        scienceQuestions.add(new Question("What planet is known as the Red Planet?", 
            new String[]{"Venus", "Mars", "Jupiter", "Saturn"}, "Mars"));
        scienceQuestions.add(new Question("What gas do plants absorb from the atmosphere?", 
            new String[]{"Oxygen", "Nitrogen", "Carbon Dioxide", "Hydrogen"}, "Carbon Dioxide"));
        scienceQuestions.add(new Question("What is the center of an atom called?", 
            new String[]{"Electron", "Proton", "Nucleus", "Neutron"}, "Nucleus"));
        scienceQuestions.add(new Question("What is the speed of light?", 
            new String[]{"300,000 km/s", "150,000 km/s", "450,000 km/s", "600,000 km/s"}, "300,000 km/s"));
        scienceQuestions.add(new Question("What is the chemical symbol for Gold?", 
            new String[]{"Go", "Gd", "Au", "Ag"}, "Au"));
        scienceQuestions.add(new Question("How many bones are in the human body?", 
            new String[]{"186", "206", "226", "246"}, "206"));
        scienceQuestions.add(new Question("What is the largest organ in the human body?", 
            new String[]{"Heart", "Brain", "Liver", "Skin"}, "Skin"));
        scienceQuestions.add(new Question("What is the boiling point of water?", 
            new String[]{"50°C", "100°C", "150°C", "200°C"}, "100°C"));
        scienceQuestions.add(new Question("What is DNA short for?", 
            new String[]{"Deoxyribonucleic Acid", "Dynamic Nucleic Acid", "Dual Nuclear Acid", "None"}, "Deoxyribonucleic Acid"));
        scienceQuestions.add(new Question("Which planet is closest to the Sun?", 
            new String[]{"Venus", "Earth", "Mercury", "Mars"}, "Mercury"));
        scienceQuestions.add(new Question("What is the process of water changing to vapor?", 
            new String[]{"Condensation", "Evaporation", "Precipitation", "Sublimation"}, "Evaporation"));
        scienceQuestions.add(new Question("What is the hardest natural substance?", 
            new String[]{"Gold", "Iron", "Diamond", "Platinum"}, "Diamond"));
        scienceQuestions.add(new Question("How many hearts does an octopus have?", 
            new String[]{"1", "2", "3", "4"}, "3"));
        scienceQuestions.add(new Question("What is the largest planet in our solar system?", 
            new String[]{"Saturn", "Jupiter", "Neptune", "Uranus"}, "Jupiter"));
        scienceQuestions.add(new Question("What vitamin does the sun provide?", 
            new String[]{"Vitamin A", "Vitamin B", "Vitamin C", "Vitamin D"}, "Vitamin D"));
        scienceQuestions.add(new Question("What is the chemical formula for salt?", 
            new String[]{"NaCl", "KCl", "CaCl", "MgCl"}, "NaCl"));
        scienceQuestions.add(new Question("How many teeth does an adult human have?", 
            new String[]{"28", "30", "32", "34"}, "32"));
        scienceQuestions.add(new Question("What is the most abundant gas in Earth's atmosphere?", 
            new String[]{"Oxygen", "Carbon Dioxide", "Nitrogen", "Hydrogen"}, "Nitrogen"));
        scienceQuestions.add(new Question("What is the powerhouse of the cell?", 
            new String[]{"Nucleus", "Ribosome", "Mitochondria", "Chloroplast"}, "Mitochondria"));
        scienceQuestions.add(new Question("What force keeps us on the ground?", 
            new String[]{"Magnetism", "Gravity", "Friction", "Tension"}, "Gravity"));
        scienceQuestions.add(new Question("What is the smallest unit of life?", 
            new String[]{"Atom", "Molecule", "Cell", "Organ"}, "Cell"));
        scienceQuestions.add(new Question("How many chromosomes do humans have?", 
            new String[]{"23", "46", "48", "50"}, "46"));
        scienceQuestions.add(new Question("What is the study of weather called?", 
            new String[]{"Geology", "Meteorology", "Astronomy", "Biology"}, "Meteorology"));
        scienceQuestions.add(new Question("What is the freezing point of water?", 
            new String[]{"0°C", "32°C", "-10°C", "10°C"}, "0°C"));
        
        List<Question> historyQuestions = new ArrayList<>();
        historyQuestions.add(new Question("Who was the first President of USA?", 
            new String[]{"Thomas Jefferson", "George Washington", "Abraham Lincoln", "John Adams"}, "George Washington"));
        historyQuestions.add(new Question("In which year did World War II end?", 
            new String[]{"1943", "1944", "1945", "1946"}, "1945"));
        historyQuestions.add(new Question("Who discovered America?", 
            new String[]{"Marco Polo", "Christopher Columbus", "Vasco da Gama", "Ferdinand Magellan"}, "Christopher Columbus"));
        historyQuestions.add(new Question("Who was known as the Iron Lady?", 
            new String[]{"Indira Gandhi", "Margaret Thatcher", "Angela Merkel", "Queen Elizabeth"}, "Margaret Thatcher"));
        historyQuestions.add(new Question("In which year did India gain independence?", 
            new String[]{"1945", "1946", "1947", "1948"}, "1947"));
        historyQuestions.add(new Question("Who built the Taj Mahal?", 
            new String[]{"Akbar", "Shah Jahan", "Aurangzeb", "Jahangir"}, "Shah Jahan"));
        historyQuestions.add(new Question("Who was the first man on the moon?", 
            new String[]{"Buzz Aldrin", "Neil Armstrong", "Yuri Gagarin", "John Glenn"}, "Neil Armstrong"));
        historyQuestions.add(new Question("What year did the Titanic sink?", 
            new String[]{"1910", "1911", "1912", "1913"}, "1912"));
        historyQuestions.add(new Question("Who painted the Mona Lisa?", 
            new String[]{"Michelangelo", "Leonardo da Vinci", "Raphael", "Donatello"}, "Leonardo da Vinci"));
        historyQuestions.add(new Question("Which ancient wonder is still standing?", 
            new String[]{"Hanging Gardens", "Great Pyramid of Giza", "Colossus of Rhodes", "Lighthouse of Alexandria"}, "Great Pyramid of Giza"));
        historyQuestions.add(new Question("Who was the first Emperor of Rome?", 
            new String[]{"Julius Caesar", "Augustus", "Nero", "Caligula"}, "Augustus"));
        historyQuestions.add(new Question("When did the Berlin Wall fall?", 
            new String[]{"1987", "1988", "1989", "1990"}, "1989"));
        historyQuestions.add(new Question("Who wrote the Declaration of Independence?", 
            new String[]{"George Washington", "Benjamin Franklin", "Thomas Jefferson", "John Adams"}, "Thomas Jefferson"));
        historyQuestions.add(new Question("In which year did World War I start?", 
            new String[]{"1912", "1913", "1914", "1915"}, "1914"));
        historyQuestions.add(new Question("Who was the first woman Prime Minister of Britain?", 
            new String[]{"Margaret Thatcher", "Theresa May", "Queen Victoria", "Elizabeth II"}, "Margaret Thatcher"));
        historyQuestions.add(new Question("What was the name of the ship that brought Pilgrims to America?", 
            new String[]{"Santa Maria", "Mayflower", "Discovery", "Endeavour"}, "Mayflower"));
        historyQuestions.add(new Question("Who was assassinated in 1963?", 
            new String[]{"Martin Luther King Jr.", "John F. Kennedy", "Robert Kennedy", "Malcolm X"}, "John F. Kennedy"));
        historyQuestions.add(new Question("Which civilization built Machu Picchu?", 
            new String[]{"Aztec", "Maya", "Inca", "Olmec"}, "Inca"));
        historyQuestions.add(new Question("What year did the Cold War end?", 
            new String[]{"1989", "1990", "1991", "1992"}, "1991"));
        historyQuestions.add(new Question("Who was the longest reigning British monarch?", 
            new String[]{"Victoria", "Elizabeth II", "George III", "Edward VII"}, "Elizabeth II"));
        historyQuestions.add(new Question("Where did the Renaissance begin?", 
            new String[]{"France", "Spain", "Italy", "Greece"}, "Italy"));
        historyQuestions.add(new Question("Who invented the telephone?", 
            new String[]{"Thomas Edison", "Alexander Graham Bell", "Nikola Tesla", "Guglielmo Marconi"}, "Alexander Graham Bell"));
        historyQuestions.add(new Question("What was the capital of the Byzantine Empire?", 
            new String[]{"Rome", "Athens", "Constantinople", "Alexandria"}, "Constantinople"));
        historyQuestions.add(new Question("Who led the French Revolution?", 
            new String[]{"Napoleon", "Robespierre", "Louis XVI", "Marie Antoinette"}, "Robespierre"));
        historyQuestions.add(new Question("In which year did Pakistan gain independence?", 
            new String[]{"1945", "1946", "1947", "1948"}, "1947"));
        
        List<Question> geoQuestions = new ArrayList<>();
        geoQuestions.add(new Question("What is the capital of France?", 
            new String[]{"London", "Berlin", "Paris", "Rome"}, "Paris"));
        geoQuestions.add(new Question("Which is the largest ocean?", 
            new String[]{"Atlantic", "Indian", "Arctic", "Pacific"}, "Pacific"));
        geoQuestions.add(new Question("What is the longest river in the world?", 
            new String[]{"Amazon", "Nile", "Yangtze", "Mississippi"}, "Nile"));
        geoQuestions.add(new Question("Which country has the most population?", 
            new String[]{"India", "USA", "China", "Indonesia"}, "China"));
        geoQuestions.add(new Question("What is the smallest continent?", 
            new String[]{"Europe", "Australia", "Antarctica", "South America"}, "Australia"));
        geoQuestions.add(new Question("What is the capital of Japan?", 
            new String[]{"Beijing", "Seoul", "Tokyo", "Bangkok"}, "Tokyo"));
        geoQuestions.add(new Question("Which country is known as the Land of Rising Sun?", 
            new String[]{"China", "Japan", "Korea", "Thailand"}, "Japan"));
        geoQuestions.add(new Question("What is the tallest mountain in the world?", 
            new String[]{"K2", "Kangchenjunga", "Mount Everest", "Makalu"}, "Mount Everest"));
        geoQuestions.add(new Question("Which desert is the largest in the world?", 
            new String[]{"Gobi", "Sahara", "Arabian", "Kalahari"}, "Sahara"));
        geoQuestions.add(new Question("What is the capital of Australia?", 
            new String[]{"Sydney", "Melbourne", "Canberra", "Brisbane"}, "Canberra"));
        geoQuestions.add(new Question("How many continents are there?", 
            new String[]{"5", "6", "7", "8"}, "7"));
        geoQuestions.add(new Question("Which country has the Eiffel Tower?", 
            new String[]{"Italy", "Spain", "France", "Germany"}, "France"));
        geoQuestions.add(new Question("What is the largest country by area?", 
            new String[]{"Canada", "China", "USA", "Russia"}, "Russia"));
        geoQuestions.add(new Question("Which river flows through Egypt?", 
            new String[]{"Amazon", "Nile", "Congo", "Niger"}, "Nile"));
        geoQuestions.add(new Question("What is the capital of Canada?", 
            new String[]{"Toronto", "Vancouver", "Montreal", "Ottawa"}, "Ottawa"));
        geoQuestions.add(new Question("Which ocean is the smallest?", 
            new String[]{"Arctic", "Atlantic", "Indian", "Southern"}, "Arctic"));
        geoQuestions.add(new Question("What is the Great Barrier Reef located near?", 
            new String[]{"Australia", "USA", "Brazil", "Indonesia"}, "Australia"));
        geoQuestions.add(new Question("Which country is home to the kangaroo?", 
            new String[]{"New Zealand", "Australia", "South Africa", "India"}, "Australia"));
        geoQuestions.add(new Question("What is the capital of Italy?", 
            new String[]{"Venice", "Milan", "Rome", "Florence"}, "Rome"));
        geoQuestions.add(new Question("Which continent is the Sahara Desert in?", 
            new String[]{"Asia", "Africa", "Australia", "South America"}, "Africa"));
        geoQuestions.add(new Question("What is the largest island in the world?", 
            new String[]{"Greenland", "New Guinea", "Borneo", "Madagascar"}, "Greenland"));
        geoQuestions.add(new Question("Which city is known as the Big Apple?", 
            new String[]{"Los Angeles", "Chicago", "New York", "Miami"}, "New York"));
        geoQuestions.add(new Question("What is the capital of Spain?", 
            new String[]{"Barcelona", "Madrid", "Valencia", "Seville"}, "Madrid"));
        geoQuestions.add(new Question("Which country has a maple leaf on its flag?", 
            new String[]{"USA", "Canada", "Australia", "New Zealand"}, "Canada"));
        geoQuestions.add(new Question("What is the smallest country in the world?", 
            new String[]{"Monaco", "Vatican City", "San Marino", "Liechtenstein"}, "Vatican City"));
        
        questions.put("Math", mathQuestions);
        questions.put("Science", scienceQuestions);
        questions.put("History", historyQuestions);
        questions.put("Geography", geoQuestions);
    }
    
    public List<Question> getRandomQuestions(String subject, int count) {
        List<Question> allQuestions = questions.getOrDefault(subject, new ArrayList<>());
        if (allQuestions.isEmpty()) {
            return new ArrayList<>();
        }
        List<Question> selectedQuestions = new ArrayList<>(allQuestions);
        Collections.shuffle(selectedQuestions);
        return selectedQuestions.subList(0, Math.min(count, selectedQuestions.size()));
    }
}