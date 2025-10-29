====================================
   Tennis Project - Java 1
   Authors: Thomas & TINAA
   Year: 2025
====================================

Project Overview
----------------
This project was created for the Java 1 course.  
The goal is to simulate a tennis tournament using Object-Oriented Programming.  
The program includes players, referees, spectators, exchanges, and tournaments.  

We worked in pairs.  
Some parts were made by me (Thomas), and others were done by my classmate.

---

Parts done by Thomas
--------------------
- **Person class**  
  Defines a basic person with attributes like name, first name, gender, birth date, nationality, height, and weight.  
  The class also includes a method to calculate age and another to change the last name in case of marriage.

- **Player class**  
  Inherits from Person.  
  Adds tennis-related information such as hand (right/left), sponsor, coach, outfit color, and ranking.  
  The player can also act as a spectator by implementing the Spectator interface.  
  Methods let the player motivate themselves, celebrate wins/losses, and change outfit color.

- **Spectator interface**  
  Contains actions for watching a match, applauding, and reacting to a player’s performance.  
  This interface is implemented by the Player class.

- **Exchange class**  
  Represents a rally between two players.  
  The user can type “fault”, “net”, or “correct” to simulate the result of a serve.  
  Randomly determines who wins the point and displays the result.  
  Includes simple error handling with try/catch for invalid inputs.

- **Referee class**  
  The referee announces game scores and handles disputes.  
  A random factor and referee “mood” decide if a player wins or loses a challenge.  

- **Tournament class**  
  Creates all players, referees, and spectators automatically.  
  Simulates the first round of matches with random pairs and random results.  
  Displays each match and announces the winners.

---

Parts done by [Classmate’s Name]
--------------------------------
(Write your sections here)
- Example: Match class  
- Example: Set or Score management  
- Example: Graphical display (if any)  
...

---

How to Run the Program
----------------------
1. Open the project in **NetBeans**.  
2. Open the file **main.java**.  
3. Click **Run File (▶)** to start the program.  
4. Follow the instructions in the console.  
   For example, when asked:
      Enter result of the serve (fault / net / correct)
   → type one of these words and press Enter.

---

JavaDoc
--------
To open the Java documentation:
1. Go to the folder **/dist/javadoc/** or **/javadoc/**.  
2. Open **index.html** in a web browser.  
3. You will see all the classes, methods, and descriptions.  

---

Project Structure
-----------------
TennisProject/
│
├── src/                → All source files (.java)
│    ├── Person.java
│    ├── Player.java
│    ├── Spectator.java
│    ├── Exchange.java
│    ├── Referee.java
│    ├── Tournament.java
│    └── main.java
│
├── dist/
│    ├── tennis.jar
│    └── javadoc/
│         └── index.html
│
└── README.txt

---

Notes
-----
- Project developed only with Java features learned in class.  
- No external libraries or AI tools were used.  
- Built and tested with **JDK 17** in **NetBeans**.  

