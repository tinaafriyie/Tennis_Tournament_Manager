
Grand Slam Tennis Tournament Management System
Project Overview
This Java application simulates and manages Grand Slam tennis tournaments (Australian Open, Roland Garros, Wimbledon, US Open). It implements a complete tournament system with 128 players per category, 7 rounds of matches, realistic tennis rules, and comprehensive statistics tracking.

Features
    Four Grand Slam Tournaments: Australian Open, Roland Garros, Wimbledon, US Open
    Complete Match Simulation: Realistic tennis scoring, serves, rallies, aces, and faults
    Player Management: 
                128 men's and women's singles players with rankings and statistics
    Tournament Progression: 
                7 rounds from first round to finals
    Statistics Tracking: 
                Match/set/game/point statistics, aces, double faults, break points
    Referee System:
                 Experienced referees with dispute resolution
    Spectator Simulation: 
                Dynamic crowd reactions based on match intensity
    Console Interface: 
                User-friendly menu system for tournament management


Class Structure
Core Classes
Person (Base Class)
Attributes: birth name, common name, first name, nickname, birth date, place of birth, date of death, nationality, height, weight
Methods: age calculation, display name handling
Immutable: Birth name, first name, birth date, place of birth, nationality

Player (extends Person)
Additional Attributes: playing hand, sponsor, ranking, coach, gender, attire color
Statistics: matches/sets/games/points won/lost, aces, double faults, break points
Methods:
    changeAttireColor(): Updates and announces attire changes
    updateRanking(): Adjusts ranking based on match results
    encourage(): Player self-encouragement
    disputeCall(): Challenge referee decisions
    getStatsSummary(): Comprehensive statistics display

Referee (extends Person)
Attributes: experience years, reputation, matches officiated
Methods:
    Score announcements (game, set, match)
    Fault announcements (foot fault, double fault, net, out)
    resolveDispute(): Handles player challenges
    Penalty system (warnings, point penalties, game penalties)

Spectator (extends Person)
Attributes: seat number, ticket price, tournament round, gender
Distinctive Features:
Men: shirt color (changes)
Women: glasses
Both: hats (not distinctive)
Behaviors: applaud, cheer, boo, sleep (based on match excitement)

Match Structure Classes
Playable Interface (NEW!)
Defines contract for all playable components
Methods: play(), isComplete(), getWinner()
Implemented by: Game, Set, Match
Benefits:
    Polymorphism support
    Consistent API across components
    Easy to add new playable types

Rally
Represents a single point exchange
Features:
    First and second serve attempts
    Fault detection (foot faults, double faults)
    Ace detection
    Rally simulation with realistic shot exchange
    Intensity calculation for spectator reactions

Game
Represents a game in a set
Scoring: 0, 15, 30, 40, Advantage, Deuce
Features:
    Regular games
    Tiebreak games (first to 7 with 2-point margin)
    Server alternation in tiebreaks

Set
Represents a set in a match
Win Conditions:
First to 6 games with 2-game advantage
Tiebreak at 6-6 (except deciding set)
Features:
    Deciding set handling
    Server alternation between games
    Game history tracking

Match
Represents a complete tennis match
Categories: Men's Singles (best of 5 sets), Women's Singles (best of 3 sets)
Features:
    Automatic match progression
    Step-by-step mode for interactive play
    ATP/WTA ranking points
    Match summary and statistics
    Winner/loser tracking

Tournament Management
Tournament
Attributes: Grand Slam type, year, surface, city
Features:
    Automatic player generation (128 per category)
    Referee generation
    Spectator generation (varies by round)
    Match scheduling and progression
    Seven rounds: First Round → Second Round → Third Round → Round of 16 → Quarterfinals → Semifinals → Finals

Tournament statistics:
Total spectators
Average spectators per match
Balls used
Glasses and hats sold
Championship tracking

TournamentManager
Console-based user interface
Features:
    Tournament creation and configuration
    Automatic or round-by-round play
    Player statistics viewing
    Match details and summaries
    Player search functionality
    Individual match testing

Technical Implementation
Design Patterns Used
Inheritance: Person → Player, Referee, Spectator
Composition: Match contains Sets, Set contains Games, Game contains Rallies
Encapsulation: Private attributes with public getters/setters
Enums: Hand, Gender, Category, GrandSlam, Outcome
Collections: Lists and Maps for managing players, matches, and tournament structure

Exception Handling
Input validation for all constructors
IllegalArgumentException for invalid parameters
Try-catch blocks in user interface
Null checks throughout
Boundary validation (rankings, ages, scores)
Java Standards Compliance


Naming Conventions:
Classes: PascalCase (e.g., TournamentManager)
Methods: camelCase (e.g., playCurrentRound())
Variables: camelCase (e.g., currentServer)
Constants: UPPER_SNAKE_CASE (e.g., ROUND_NAMES)
Visibility Modifiers: Proper use of private, public, protected
Final Keywords: Used for immutable attributes
Documentation: Comprehensive JavaDoc comments

How to Run
Compilation
bash
    javac *.java
Execution
bash
    java TournamentManager

Menu Options
    Create New Tournament: Select Grand Slam, year, and configure tournament
    Run Tournament: Play all rounds automatically or round-by-round
    View Tournament Details: See tournament configuration and progress
    View Player Statistics: Browse player rankings and performance
    View Match Details: Examine completed match results
    Test Individual Match: Run a single exhibition match
    Exit: Close the application

Example Usage
Creating a Tournament
Select Grand Slam:
1. Australian Open
2. Roland Garros
3. Wimbledon
4. US Open
Enter choice: 1
Enter year: 2024
How many referees? 15
Playing a Round
The system automatically:

Generates spectators based on round importance
Assigns referees to matches
Simulates complete matches with realistic scoring
Tracks all statistics
Advances winners to next round
Viewing Statistics
Players statistics include:

Win-loss record
Sets, games, and points won/lost
Aces and double faults
Break point conversion rate

Current ranking
Key Features Implementation
Realistic Tennis Rules
Proper serve rules (2 attempts, faults)
Tennis scoring system (0, 15, 30, 40, Deuce, Advantage)
Set scoring (6 games with 2-game margin or tiebreak)
Match scoring (best of 3 for women, best of 5 for men)
Server alternation

Statistics Tracking
Individual player career statistics
Match-level statistics
Tournament-wide metrics
Real-time updates during matches
Dynamic Elements
Random serve success based on realistic percentages
Rally length variation
Ace probability
Spectator reactions based on match intensity
Referee dispute resolution

File Structure
├── Person.java
├── Player.java
├── Referee.java
├── Spectator.java
├── Rally.java
├── Game.java
├── Set.java
├── Match.java
├── Tournament.java
├── TournamentManager.java
└── README.md

Future Enhancements
Possible additions:

Doubles categories
    Junior categories
    Weather delays
    Player injuries
    Prize money tracking
    Media coverage simulation
    Historical data persistence
    GUI interface
    Network play
    Author Notes


This project demonstrates:

Object-oriented programming principles
Inheritance and composition
Exception handling
User interaction design
Statistical tracking
Complex system simulation
Clean code practices
Java standard compliance



