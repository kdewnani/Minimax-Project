import java.util.ArrayList;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static String player = "", player2 = "", move = "";
	static Scanner scan = new Scanner(System.in);
	public static void main(String[]args) {

		System.out.println("Would you like to play black(b) or white(w)?");
		player = scan.nextLine();
		while(!player.equals("b")&& !player.equals("w")) {
			System.out.println("Would you like to play black(b) or white(w)?");
			player = scan.nextLine();
		}
		System.out.println("Okay, you're "+player);
		Player p = new Player(player);

		if(player.equalsIgnoreCase("w")) {
			player2 = "b";
		}else {
			player2 = "w";
		}
		Player p2 = new Player(player2);

		System.out.println("Please select the size of the board (either 4 for 4x4 checkers or 8 for 8x8 "
				+ "checkers.");
		System.out.println("Black Starts First");
		int n = scan.nextInt();
		while(n!=4&&n!=8) {
			System.out.println("Please select the size of the board (either 4 for 4x4 checkers or 8 for 8x8 "
					+ "checkers.");
			System.out.println("Black Starts First");
			n = scan.nextInt();
		}
		
		String[][] board = new String[n][n];
		State s = new State(board, p);
		s.initialState();
		s.fillMap();
		System.out.println(s.toString());
		State next = null;
		
		

		
		
		System.out.println("Please select your opponent AI: \n1. Heuristic Minimax AI\n2. AB Minimax AI");
		int opp = scan.nextInt();

		if(opp == 1) {
			H_minimaxAI(board, s, next, p, p2, n);
		}else if(opp == 2) {
			AB_minimaxAI(board, s, next, p, p2, n);
		}
		
	}
	public static void AB_minimaxAI(String[][] board, State s,State next, Player p, Player p2, int n) {
		AB_Minimax node = new AB_Minimax();
		while(true) {
			if(player.equalsIgnoreCase("b")) {
				while(true) {
					System.out.println("Black's Turn! Make a Move: ");
					move = scan.nextLine();
					while(s.isValidInput(p, move) == false) {
						move = scan.nextLine();

					}
					Action human = s.getHumanInput(p, move);
					next = s.nextState(human);
					System.out.println(s.toString());// p is b, p2 is w
					if(s.isWin(p)) {
						System.exit(0);
					}
					if(!s.isPossible(p2,s)) {//no more moves for white
						System.out.println("No more moves possible! Game over!");
						System.exit(0);
						
					}

					String[][] hboard = s.copyBoard(next.board);
					next = node.minimax(p2, next);
					s.updateBoard(next);
					System.out.println(s.toString());
					if(s.isWin(p2)) {
						System.exit(0);
					}
					if(!s.isPossible(p,s)) {//no more moves for black
						System.out.println("No more moves possible! Game over!");
						System.exit(0);
						
					}
					if(n==4) {
						if(s.counter >=10) {
							System.out.println("It's a tie! Game over!");
							System.exit(0);
						}
					}else if(n==8) {
						if(s.counter >=30) {
							System.out.println("It's a tie! Game over!");
							System.exit(0);
						}
					}
				}
			}else if(player.equalsIgnoreCase("w")) {
				next = new State(board, p);
				while(true){
					String[][] hboard = s.copyBoard(next.board);
					next = node.minimax(p2, next);
					s.updateBoard(next);
					System.out.println(s.toString());
					if(s.isWin(p2)) {
						System.exit(0);
					}
					if(!s.isPossible(p,s)) {//no more moves for white
						System.out.println("No more moves possible! Game over!");
						System.exit(0);
						
					}
					System.out.println("White's Turn! Make a Move: ");
					move = scan.nextLine();
					while(s.isValidInput(p, move) == false) {
						move = scan.nextLine();

					}
					Action human = s.getHumanInput(p, move);
					next = s.nextState(human);
					System.out.println(s.toString());
					if(s.isWin(p)) {
						System.exit(0);
					}
					if(s.isPossible(p2,s)==false) {//no more moves for black
						System.out.println("No more moves possible! Game over!");
						System.exit(0);
						
					}
					if(n==4) {
						if(s.counter >=10) {
							System.out.println("It's a tie! Game over!");
							System.exit(0);
						}
					}else if(n==8) {
						if(s.counter >=30) {
							System.out.println("It's a tie! Game over!");
							System.exit(0);
						}
					}
				}
			}
		}
	}
	
	public static void H_minimaxAI(String[][] board, State s,State next, Player p, Player p2, int n) {
		H_Minimax node = new H_Minimax();
		while(true) {
			if(player.equalsIgnoreCase("b")) {
				while(true) {
					System.out.println("Black's Turn! Make a Move: ");
					move = scan.nextLine();
					while(s.isValidInput(p, move) == false) {
						move = scan.nextLine();

					}
					Action human = s.getHumanInput(p, move);
					next = s.nextState(human);
					System.out.println(s.toString()); // p is b, p2 is w
					
					if(s.isWin(p)) {
						System.exit(0);
					}
				
					if(!s.isPossible(p2,s)) {//no more moves for white
						System.out.println("No more moves possible! Game over!");
						System.exit(0);
						
					}
					System.out.println("White's turn!");
					String[][] hboard = s.copyBoard(next.board);
					next = node.minimax(p2, next);
					s.updateBoard(next);
					System.out.println(s.toString());
					if(s.isWin(p2)) {
						System.exit(0);
					}
					if(n==4) {
						if(s.counter >=10) {
							System.out.println("It's a tie! Game over!");
							System.exit(0);
						}
					}else if(n==8) {
						if(s.counter >=30) {
							System.out.println("It's a tie! Game over!");
							System.exit(0);
						}
					}
				
					if(!s.isPossible(p,s)) {//no more moves for black
						System.out.println("No more moves possible! Game over!");
						System.exit(0);
						
					}
				}
			}else if(player.equalsIgnoreCase("w")) {
				next = new State(board, p);
				while(true){
					System.out.println("Black's turn!");
					String[][] hboard = s.copyBoard(next.board);
					next = node.minimax(p2, next);// p is w, p2 is black
					s.updateBoard(next);
					System.out.println(s.toString());
					
					if(s.isWin(p2)) {
						System.exit(0);
					}
					
					if(!s.isPossible(p,s)) {//no more moves for white
						System.out.println("No more moves possible! Game over!");
						System.exit(0);
						
					}
					System.out.println("White's Turn! Make a Move: ");
					move = scan.nextLine();
					while(s.isValidInput(p, move) == false) {
						move = scan.nextLine();

					}
					Action human = s.getHumanInput(p, move);
					next = s.nextState(human);
					System.out.println(s.toString());
					if(s.isWin(p)) {
						System.exit(0);
					}
					if(n==4) {
						if(s.counter >=10) {
							System.out.println("It's a tie! Game over!");
							System.exit(0);
						}
					}else if(n==8) {
						if(s.counter >=30) {
							System.out.println("It's a tie! Game over!");
							System.exit(0);
						}
					}
					
					if(s.isPossible(p2,s)==false) {//no more moves for black
						System.out.println("No more moves possible! Game over!");
						System.exit(0);		
					}
						
				}
			}
		}
	}
}