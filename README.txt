Contributors: Kashish Dewnani (kdewnani)
I decided to learn AI since I would be taking that class next semester. I was always interested by this, therefore I decided to learn and implement one of the most basic algorithms as seen in this project:
Note: This is text-based checkers

Checkers:
The project was created on Eclipse.
Create a deafault java package. Add classes and run Main:

Running the Program:  Open the project on Eclipse. Run the main file. 
1. Enter “b” or “w” to select player (Only lowercase)
2. Enter 4 or 8 to choose board size
3. Choose 1 or 2 to choose the AI you want to play against
4. Enter moves like “A2-B1” (Note enter in uppercase only)

Input Format:
For moves you can use the syntax “A1-B2” (Note enter in uppercase only)
For jumps you can use the syntax “A1-C3”
Note: Sometimes if an invalid input is entered, the user is not prompted via a print statement to enter an input, hence just enter another input and it should work.


Rules:
Black starts first. You can only move diagonally and jump if there’s an opponent’s piece which it can capture. If you have a capture available, you must perform the jump.

Game Decision:
If the opponent has no more pieces on the board, you win!
If there are no possible moves left, the game ends.

Tie conditions: 
For 4x4 board: When move count exceeds 10 moves/ player.
For 8x8 board: When move count exceeds 30 moves/ player.

AI:
The agent uses adversarial search with minimax, heuristic minimax and Alpha-beta pruning Minimax.

Implementation of minimax: Minimax.java
Implementation of heuristic minimax and heuristic functions: H_Minimax.java 
Implementation of alpha-beta pruning: AB_Minimax.java






