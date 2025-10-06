# 🎯 Quiz Master - MCQ Test Application

![JavaFX](https://img.shields.io/badge/JavaFX-23-orange.svg)
![Java](https://img.shields.io/badge/Java-23-blue.svg)
![License](https://img.shields.io/badge/License-MIT-green.svg)

A professional JavaFX-based quiz application with multiple subjects, difficulty levels, and an interactive timer system. Test your knowledge across Math, Science, History, and Geography with 25 questions per subject.

## 📋 Table of Contents
- [Features](#features)
- [Screenshots](#screenshots)
- [Installation](#installation)
- [How to Play](#how-to-play)
- [Game Rules](#game-rules)
- [Project Structure](#project-structure)
- [Technical Details](#technical-details)
- [Future Enhancements](#future-enhancements)
- [Contributing](#contributing)

## ✨ Features

- **4 Subject Categories**
  - Mathematics
  - Science
  - History
  - Geography

- **3 Difficulty Levels**
  - Easy
  - Medium
  - Hard

- **Interactive Gameplay**
  - 15 randomly selected questions per game
  - 30-second countdown timer for each question
  - Real-time timer with visual feedback
  - Clean and intuitive user interface

- **Professional Design**
  - Modern gradient backgrounds
  - Smooth animations and transitions
  - Responsive button hover effects
  - Glass morphism effects

- **Score System**
  - Instant scoring after quiz completion
  - Percentage calculation
  - Pass/Fail status (50% passing marks)
  - Option to replay or exit

## 📸 Screenshots

### Welcome Screen
Professional landing page with player name input and attractive design elements.

### Rules Screen
Clear explanation of game rules before starting the quiz.

### Subject Selection
Choose from 4 different subjects with emoji icons.

### Difficulty Selection
Select Easy, Medium, or Hard difficulty levels.

### Question Screen
Interactive quiz interface with timer, question counter, and multiple-choice options.

### Results Screen
Final score display with percentage, pass/fail status, and replay options.

## 🚀 Installation

### Prerequisites
- Java Development Kit (JDK) 23 or higher
- JavaFX SDK (if not included with JDK)

### Setup Instructions

1. **Clone or Download the Repository**
```bash
git clone https://github.com/yourusername/quiz-master.git
cd quiz-master
```

2. **Compile the Code**
```bash
javac QuizApp.java
```

3. **Run the Application**
```bash
java QuizApp
```

### For JavaFX Setup (if needed)
If JavaFX is not included in your JDK:

1. Download JavaFX SDK from [Gluon](https://gluonhq.com/products/javafx/)
2. Extract the SDK
3. Compile with module path:
```bash
javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml QuizApp.java
```
4. Run with module path:
```bash
java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml QuizApp
```

## 🎮 How to Play

1. **Launch the Application**
   - Run `java QuizApp` from the command line

2. **Enter Your Name**
   - Type your name in the welcome screen
   - Click "START TEST" button

3. **Read the Rules**
   - Review game rules carefully
   - Click "I UNDERSTAND - LET'S GO!"

4. **Select Subject**
   - Choose from Math, Science, History, or Geography

5. **Choose Difficulty**
   - Select Easy, Medium, or Hard level

6. **Answer Questions**
   - Read each question carefully
   - Select one answer from four options
   - Watch the 30-second timer
   - Click "SUBMIT ANSWER" to proceed

7. **View Results**
   - Check your final score
   - See your percentage and pass/fail status
   - Choose to play again or exit

## 📖 Game Rules

- You will receive **15 random questions** from your chosen subject
- Each question has a **30-second time limit**
- Questions have **4 multiple-choice options**
- Select one answer and submit before time runs out
- **No negative marking** for incorrect answers
- You need **50% marks (8/15)** to pass
- Once submitted, you cannot change your answer
- Results are displayed at the end of the quiz

## 📁 Project Structure

```
quiz-master/
│
├── QuizApp.java              # Main application file
├── README.md                 # Project documentation
└── LICENSE                   # MIT License file
```

### Code Structure

**Main Classes:**
- `QuizApp` - Main application class extending JavaFX Application
- `Question` - Data model for quiz questions
- `QuestionBank` - Database containing all questions for each subject

**Key Methods:**
- `showWelcomeScreen()` - Professional landing page
- `showRulesScreen()` - Display game rules
- `showSubjectSelection()` - Subject selection interface
- `showDifficultySelection()` - Difficulty level selection
- `showQuestion()` - Display quiz questions with timer
- `showResultScreen()` - Final score and results

## 🔧 Technical Details

### Technologies Used
- **Language:** Java 23
- **Framework:** JavaFX
- **UI Components:** VBox, HBox, BorderPane, Button, Label, RadioButton
- **Animations:** Timeline for countdown timer
- **Styling:** Inline CSS with gradient backgrounds

### Question Database
- **Total Questions:** 100 (25 per subject)
- **Subjects:** Math, Science, History, Geography
- **Format:** MCQ with 4 options each
- **Selection:** Random 15 questions per game

### Timer System
- **Duration:** 30 seconds per question
- **Visual Feedback:** Color changes to red at 10 seconds
- **Auto-submit:** Question auto-submits when timer reaches 0

### Scoring System
- **Correct Answer:** +1 point
- **Wrong Answer:** 0 points (no negative marking)
- **Passing Marks:** 50% (8 out of 15 questions)

## 🎨 Design Features

- **Gradient Backgrounds:** Modern color schemes for different screens
- **Shadow Effects:** Professional depth and elevation
- **Hover Animations:** Interactive button feedback
- **Responsive Layout:** Clean spacing and alignment
- **Color-coded Elements:** 
  - Green for pass
  - Red for fail
  - Yellow for timer warning

## 🔮 Future Enhancements

- [ ] Add more subjects (Programming, General Knowledge)
- [ ] Implement difficulty-based question filtering
- [ ] Add sound effects and background music
- [ ] Save high scores and leaderboard
- [ ] Add question explanations after quiz
- [ ] Implement user profiles and progress tracking
- [ ] Add hints or lifelines feature
- [ ] Export results to PDF
- [ ] Multi-language support
- [ ] Online multiplayer mode

## 🤝 Contributing

Contributions are welcome! Here's how you can help:

1. Fork the repository
2. Create a new branch (`git checkout -b feature/improvement`)
3. Make your changes
4. Commit your changes (`git commit -am 'Add new feature'`)
5. Push to the branch (`git push origin feature/improvement`)
6. Create a Pull Request

### Areas for Contribution
- Adding more questions to existing subjects
- Creating new subject categories
- Improving UI/UX design
- Adding new features from the enhancement list
- Bug fixes and performance improvements
- Documentation improvements

## 🎓 Learning Objectives

This project demonstrates:
- JavaFX application development
- Event-driven programming
- UI/UX design principles
- Data structure implementation (HashMap, ArrayList)
- Timer and animation handling
- User input validation
- Dynamic content generation
- Object-oriented programming concepts

## 📧 Support

For questions or issues, please open an issue on the GitHub repository.

---

**Made with Java and JavaFX**

**Happy Learning!**
