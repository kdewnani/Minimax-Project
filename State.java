import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class State {
	String[][] board;
	Character[] letter = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
	Character[] number = {'1', '2', '3', '4', '5', '6', '7', '8'};
	HashMap<Character, Integer> hRows = new HashMap<>();
	HashMap<Character, Integer> hColumns = new HashMap<>();
	static int counter = 0;
	static int n = 0;
	String[][] temp;
	static String computerToken;

	static ArrayList<State> allmovesArray;

	public static final String
	blank = " ",
	white = "w",
	whiteK = "W",
	black = "b",
	blackK = "B";

	Player p, hp;
	public static int utility;


	public State(String[][] board, Player p) {
		this.p = p;
		this.board = board;
		this.utility = utility;
		this.computerToken = computerToken;

	}
	public static void setComputerToken(Player p) {
		if(p.getPlayer().equals("w")) {
			computerToken= "w";
		}
		else {
			computerToken= "b";
		}
	}

	public String[][] getBoard() {
		return board;
	}

	public void setBoard(String[][] board) {
		this.board = board;
	}


	public String[][] copyBoard(String[][] board) {
		String[][] newBoard = new String[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {

				newBoard[i][j] = board[i][j];

			}
		}

		return newBoard;
	}


	//gets all moves for the state
	public ArrayList<Action> getAllMoves(Player p, State s) {
		ArrayList<Action> moves = new ArrayList<Action>();  // Moves will be stored in this list.
		String[][] newBoard = copyBoard(s.getBoard());
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n; col++) {
				if (canJumpAI(new Action(p, row, col,row+2, col+2),newBoard))
					moves.add(new Action(p,row, col, row+2, col+2));
				if (canJumpAI(new Action(p, row, col, row-2, col+2),newBoard))
					moves.add(new Action(p,row, col, row-2, col+2));
				if (canJumpAI(new Action(p, row, col, row+2, col-2),newBoard))
					moves.add(new Action(p,row, col, row+2, col-2));
				if (canJumpAI(new Action(p, row, col,row-2, col-2),newBoard))
					moves.add(new Action(p,row, col, row-2, col-2));
			}
		}

		if (moves.size() == 0) {
			for (int row = 0; row < n; row++) {
				for (int col = 0; col < n; col++) {
					if (canMoveAI(new Action(p,row,col,row+1,col+1), newBoard))
						moves.add(new Action(p,row,col,row+1,col+1));
					if (canMoveAI(new Action(p,row,col,row-1,col+1), newBoard))
						moves.add(new Action(p,row,col,row-1,col+1));
					if (canMoveAI(new Action(p,row,col,row+1,col-1), newBoard))
						moves.add(new Action(p,row,col,row+1,col-1));
					if (canMoveAI(new Action(p,row,col,row-1,col-1), newBoard))
						moves.add(new Action(p,row,col,row-1,col-1));
				}
			}
		}


		return moves;
	}

	public ArrayList<State> getAllStates(ArrayList<Action> moves, State s){
		ArrayList<State> states = new ArrayList<State>();
		//temp = copyBoard(board);
		for(Action a : moves) {
			String[][] tempBoard = copyBoard(s.getBoard());
			State tempState = new State(tempBoard, p);
			//newBoard = temp;
			//System.out.println(a.toString());
			states.add(applyActionAI(a, tempState));

		}
		return states;
	}

	//apply action for state
	public State applyActionAI(Action a, State s) {
		Player p= a.getPlayer();
		int fromRow= a.getFromRow();
		int fromCol= a.getFromCol();
		int toRow= a.getToRow();
		int toCol= a.getToCol();
		String[][] board = s.getBoard();

		if(canMove(a)) {
			board[a.getToRow()][a.getToCol()] = board[a.getFromRow()][a.getFromCol()]; // Move the piece.
			board[a.getFromRow()][a.getFromCol()] = " ";


		}

		if(canJump(a)) {
			//System.out.println("true");
			board[toRow][toCol] = board[fromRow][fromCol]; // Move the piece.
			board[fromRow][fromCol] = " ";
			int jumpRow = (fromRow + toRow) / 2; // Row of the jumped piece.
			int jumpCol = (fromCol + toCol) / 2; // Column of the jumped piece.
			board[jumpRow][jumpCol] = blank;

		}
		if (toRow == 0 && board[toRow][toCol] == white) {
			board[toRow][toCol] = whiteK;  // White piece becomes a king.
		}
		if (toRow == n-1 && board[toRow][toCol] == black) {
			board[toRow][toCol] = blackK;  // Black piece becomes a king.
		} 
		return new State(board,p);
	}



	//RANDOM AI NOT USED ANYMORE!!!!!!
	public Action findMove(Player p, State s) {//random AI

		ArrayList<Action> actions = new ArrayList<Action>();

		Action toReturn = new Action(p, -1, -1, -1, -1);



		actions = getAllMoves(p, s);



		int upperLimit = actions.size();

		Random random = new Random();

		int pick = random.nextInt(upperLimit);


		toReturn = actions.get(pick);

		return toReturn;

	}

	//terminal test
	public static boolean isTerminal(Player p, State s) {
		if(p.getPlayer().equals("b")) {
			for(int i = 0;i<n;i++) {
				for(int j = 0;j<n;j++) {
					if(s.getBoard()[i][j].equals("w")||s.getBoard()[i][j].equals("W"))
						return false;
				}
			}
		}else {
			for(int i = 0;i<n;i++) {
				for(int j = 0;j<n;j++) {
					if(s.getBoard()[i][j].equals("b")||s.getBoard()[i][j].equals("B"))
						return false;
				}
			}
		}
		return true;
	}




	public void switchPlayer() {
		if(hp.getPlayer().equals("w")) {
			hp.setPlayer("b");
		}else if(hp.getPlayer().equals("b")) {
			hp.setPlayer("w");
		}
	}


	//initializes
	public void initialState() {
		n = board.length;
		for(int i = 0;i<n;i++){
			for(int j = 0;j<n;j++){
				if(i%2!=j%2){
					if(i<(n-2)/2){
						board[i][j] = black;
					}else
						if(i>=n-(n-2)/2){
							board[i][j] = white;
						}else{
							board[i][j] = blank;
						}
				}else if(board[i][j] == null){
					board[i][j] = blank;
				}
			}
		}
	}

	//gets the next state 
	public State nextState(Action a) {
		Player p= a.getPlayer();
		int fromRow= a.getFromRow();
		int fromCol= a.getFromCol();
		int toRow= a.getToRow();
		int toCol= a.getToCol();

		if(canMove(a)) {
			board[a.getToRow()][a.getToCol()] = board[a.getFromRow()][a.getFromCol()]; // Move the piece.
			board[a.getFromRow()][a.getFromCol()] = " ";
		}

		if(canJump(a)) {
			//System.out.println("true");
			board[toRow][toCol] = board[fromRow][fromCol]; // Move the piece.
			board[fromRow][fromCol] = " ";
			int jumpRow = (fromRow + toRow) / 2; // Row of the jumped piece.
			int jumpCol = (fromCol + toCol) / 2; // Column of the jumped piece.
			board[jumpRow][jumpCol] = blank;

		}
		if (toRow == 0 && board[toRow][toCol] == white) {
			board[toRow][toCol] = whiteK;  // White piece becomes a king.
		}
		if (toRow == n-1 && board[toRow][toCol] == black) {
			board[toRow][toCol] = blackK;  // Black piece becomes a king.
		} 
		updateCounter();
		temp = copyBoard(board);
		//copyPlayer(p);
		//System.out.println(p.getPlayer());
		return new State(board, p);
	}

	public void updateCounter() {
		counter++;
		System.out.println("Move counter: " +counter);
	}

	public void updateBoard(State s) {
		for(int i = 0;i<board.length;i++) {
			for(int j = 0;j<board.length;j++) {
				board[i][j] = s.getBoard()[i][j];
			}
		}
	}

	public boolean isEmpty(String piece) {
		if(piece.equals(" ")) {
			return true;
		}
		else{
			return false;
		}
	}
	public void fillMap() {
		for(int i = 0;i<letter.length;i++){
			hRows.put(letter[i], i);
		}
		for(int i = 0;i<number.length;i++){
			hColumns.put(number[i], i);
		}
	}

	//gets input from human and sets a location to move
	public Action getHumanInput(Player p, String input) {
		char[] convert = input.toCharArray();
		int[] values = new int[convert.length];
		int fromRow = 0, fromCol = 0, toRow = 0, toCol = 0;
		for(int i = 0;i<convert.length;i++){
			if(hRows.containsKey(convert[i])){
				values[i] = hRows.get(convert[i]);
			}
			else if(hColumns.containsKey(convert[i])){
				values[i] = hColumns.get(convert[i]);
			}
		}
		fromRow = values[0];
		fromCol = values[1];
		char type = convert[2];
		toRow = values[3];
		toCol = values[4];

		return new Action(p, type, fromRow, fromCol, toRow, toCol);
	}

	//checks if its valid input from human
	public boolean isValidInput(Player p, String input) {
		if(input.length()!=5) {
			return false;
		}
		char[] convert = input.toCharArray();
		if(n==4) {
			if((int)convert[0]<65 || (int)convert[0] > 68 || (int)convert[1] < 49 || (int)convert[1] > 52 ||
					(int)convert[3]<65 || (int)convert[3] > 68 || (int)convert[4] < 49 || (int)convert[4] > 52 ||
					(convert[2] != '-'&&convert[2] != 'x')) {
				System.out.println("Invalid input, please try again: ");
				return false;
			}
		}else if(n==8) {
			if((int)convert[0]<65 || (int)convert[0] > 72 || (int)convert[1] < 49 || (int)convert[1] > 56 ||
					(int)convert[3]<65 || (int)convert[3] > 72 || (int)convert[4] < 49 || (int)convert[4] > 56 ||
					(convert[2] != '-'&&convert[2] != 'x')) {
				System.out.println("Invalid input, please try again: ");
				return false;
			}
		}
		int[] values = new int[convert.length];
		int fromRow = 0, fromCol = 0, toRow = 0, toCol = 0;
		for(int i = 0;i<convert.length;i++){
			if(hRows.containsKey(convert[i])){
				values[i] = hRows.get(convert[i]);
			}
			else if(hColumns.containsKey(convert[i])){
				values[i] = hColumns.get(convert[i]);
			}
		}
		fromRow = values[0];
		fromCol = values[1];
		toRow = values[3];
		toCol = values[4];
		if (!canMove(new Action(p,fromRow,fromCol,toRow,toCol))&&
				!canJump(new Action(p, fromRow, fromCol,toRow, toCol))) {
			System.out.println("Invalid input, please try again: ");
			return false;
		}
		return true;
	}

	//checks multiple valid input 
	public boolean isValidInputMultiple(Player p, String input) {
		if(input.length()!=5) {
			return false;
		}
		char[] convert = input.toCharArray();
		if(n==4) {
			if((int)convert[0]<65 || (int)convert[0] > 68 || (int)convert[1] < 49 || (int)convert[1] > 52 ||
					(int)convert[3]<65 || (int)convert[3] > 68 || (int)convert[4] < 49 || (int)convert[4] > 52 ||
					(convert[2] != '-'&&convert[2] != 'x')) {
				return false;
			}
		}else if(n==8) {
			if((int)convert[0]<65 || (int)convert[0] > 72 || (int)convert[1] < 49 || (int)convert[1] > 56 ||
					(int)convert[3]<65 || (int)convert[3] > 72 || (int)convert[4] < 49 || (int)convert[4] > 56 ||
					(convert[2] != '-'&&convert[2] != 'x')) {
				return false;
			}
		}
		int[] values = new int[convert.length];
		int fromRow = 0, fromCol = 0, toRow = 0, toCol = 0;
		for(int i = 0;i<convert.length;i++){
			if(hRows.containsKey(convert[i])){
				values[i] = hRows.get(convert[i]);
			}
			else if(hColumns.containsKey(convert[i])){
				values[i] = hColumns.get(convert[i]);
			}
		}
		fromRow = values[0];
		fromCol = values[1];
		toRow = values[3];
		toCol = values[4];
		if (!canJump(new Action(p, fromRow, fromCol,toRow, toCol))) {
			System.out.println("Invalid input, please try again: ");
			return false;
		}
		return true;
	}

	public boolean isWin(Player p) {
		String player = "", player2 = "";
		if(p.getPlayer().equals("b")) {
			player = "black";
			player2 = "white";
			for(int i = 0;i<board.length;i++) {
				for(int j = 0;j<board.length;j++) {
					if(board[i][j].equals("w")||board[i][j].equals("W")) {
						return false;
					}
				}
			}
		}else if(p.getPlayer().equals("w")) {
			player = "White";
			player2= "Black";
			for(int i = 0;i<board.length;i++) {
				for(int j = 0;j<board.length;j++) {
					if(board[i][j].equals("b")||board[i][j].equals("B")) {
						return false;
					}
				}
			}
		}
		System.out.println(player+" Wins since there are no "+player2+" pieces on the board.");
		return true;
	}

	//checks if anymore moves are possible
	public boolean isPossible(Player p, State s) {
		ArrayList<Action> moves = new ArrayList<Action>();  // Moves will be stored in this list.
		String[][] newBoard = copyBoard(s.getBoard());
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < n; col++) {
				if (canJumpAI(new Action(p, row, col,row+2, col+2),newBoard))
					moves.add(new Action(p,row, col, row+2, col+2));
				if (canJumpAI(new Action(p, row, col, row-2, col+2),newBoard))
					moves.add(new Action(p,row, col, row-2, col+2));
				if (canJumpAI(new Action(p, row, col, row+2, col-2),newBoard))
					moves.add(new Action(p,row, col, row+2, col-2));
				if (canJumpAI(new Action(p, row, col,row-2, col-2),newBoard))
					moves.add(new Action(p,row, col, row-2, col-2));
			}
		}

		if (moves.size() == 0) {
			for (int row = 0; row < n; row++) {
				for (int col = 0; col < n; col++) {
					if (canMoveAI(new Action(p,row,col,row+1,col+1), newBoard))
						moves.add(new Action(p,row,col,row+1,col+1));
					if (canMoveAI(new Action(p,row,col,row-1,col+1), newBoard))
						moves.add(new Action(p,row,col,row-1,col+1));
					if (canMoveAI(new Action(p,row,col,row+1,col-1), newBoard))
						moves.add(new Action(p,row,col,row+1,col-1));
					if (canMoveAI(new Action(p,row,col,row-1,col-1), newBoard))
						moves.add(new Action(p,row,col,row-1,col-1));
				}
			}
		}
		if(moves.size()==0) {
			return false;
		}else {
			return true;
		}
	}

	//chhecks if an action is possible
	public boolean canMove(Action a) {
		Player player= a.getPlayer();
		int r1= a.getFromRow();
		int c1= a.getFromCol();
		int r2= a.getToRow();
		int c2= a.getToCol();

		if(player.getPlayer().equals("w")) {
			if(board[r1][c1].equals("w")) {
				if((r1>=0&&r1<=n-1)&&(c1>=0&&c1<=n-1)&&(r2>=0&&r2<=n-1)&&(c2>=0&&c2<=n-1)) {
					if(isEmpty(board[r2][c2])) {
						if((r2==r1-1&&c2==c1+1)||((r2==r1-1&&c2==c1-1))) {
							return true;
						}
					}
				}
			}else if(board[r1][c1].equals("W")) {
				if((r1>=0&&r1<=n-1)&&(c1>=0&&c1<=n-1)&&(r2>=0&&r2<=n-1)&&(c2>=0&&c2<=n-1)) {
					if(isEmpty(board[r2][c2])) {
						if((r2==r1+1&&c2==c1+1)||((r2==r1+1&&c2==c1-1))||(r2==r1-1&&c2==c1+1)||((r2==r1-1&&c2==c1-1))) {
							return true;
						}
					}
				}
			}
		}else if(player.getPlayer().equals("b")) {
			if(board[r1][c1].equals("b")) {
				if((r1>=0&&r1<=n-1)&&(c1>=0&&c1<=n-1)&&(r2>=0&&r2<=n-1)&&(c2>=0&&c2<=n-1)) {
					if(isEmpty(board[r2][c2])) {
						if((r2==r1+1&&c2==c1+1)||((r2==r1+1&&c2==c1-1))) {
							return true;
						}
					}
				}
			}else if(board[r1][c1].equals("B")) {
				if((r1>=0&&r1<=n-1)&&(c1>=0&&c1<=n-1)&&(r2>=0&&r2<=n-1)&&(c2>=0&&c2<=n-1)) {
					if(isEmpty(board[r2][c2])) {
						if((r2==r1+1&&c2==c1+1)||((r2==r1+1&&c2==c1-1))||(r2==r1-1&&c2==c1+1)||((r2==r1-1&&c2==c1-1))) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	//tests if the AI can move
	private boolean canMoveAI(Action a, String[][] board) {
		Player player= a.getPlayer();
		int r1= a.getFromRow();
		int c1= a.getFromCol();
		int r2= a.getToRow();
		int c2= a.getToCol();

		if(player.getPlayer().equals("w")) {
			if(board[r1][c1].equals("w")) {
				if((r1>=0&&r1<=n-1)&&(c1>=0&&c1<=n-1)&&(r2>=0&&r2<=n-1)&&(c2>=0&&c2<=n-1)) {
					if(isEmpty(board[r2][c2])) {
						if((r2==r1-1&&c2==c1+1)||((r2==r1-1&&c2==c1-1))) {
							return true;
						}
					}
				}
			}else if(board[r1][c1].equals("W")) {
				if((r1>=0&&r1<=n-1)&&(c1>=0&&c1<=n-1)&&(r2>=0&&r2<=n-1)&&(c2>=0&&c2<=n-1)) {
					if(isEmpty(board[r2][c2])) {
						if((r2==r1+1&&c2==c1+1)||((r2==r1+1&&c2==c1-1))||(r2==r1-1&&c2==c1+1)||((r2==r1-1&&c2==c1-1))) {
							return true;
						}
					}
				}
			}
		}else if(player.getPlayer().equals("b")) {
			if(board[r1][c1].equals("b")) {
				if((r1>=0&&r1<=n-1)&&(c1>=0&&c1<=n-1)&&(r2>=0&&r2<=n-1)&&(c2>=0&&c2<=n-1)) {
					if(isEmpty(board[r2][c2])) {
						if((r2==r1+1&&c2==c1+1)||((r2==r1+1&&c2==c1-1))) {
							return true;
						}
					}
				}
			}else if(board[r1][c1].equals("B")) {
				if((r1>=0&&r1<=n-1)&&(c1>=0&&c1<=n-1)&&(r2>=0&&r2<=n-1)&&(c2>=0&&c2<=n-1)) {
					if(isEmpty(board[r2][c2])) {
						if((r2==r1+1&&c2==c1+1)||((r2==r1+1&&c2==c1-1))||(r2==r1-1&&c2==c1+1)||((r2==r1-1&&c2==c1-1))) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	//tests if I can jump
	public boolean canJump(Action a) {
		Player player= a.getPlayer();
		int r1= a.getFromRow();
		int c1= a.getFromCol();
		int r2= a.getToRow();
		int c2= a.getToCol();

		if(player.getPlayer().equals("w")) {
			//System.out.println("1");
			if(board[r1][c1].equals("w")) {
				if((r1>=0&&r1<=n-1)&&(c1>=0&&c1<=n-1)&&(r2>=0&&r2<=n-1)&&(c2>=0&&c2<=n-1)&& r2<r1) {
					if(Math.abs(r1-r2)==2&&Math.abs(c1-c2)==2) {
						if(isEmpty(board[r2][c2])) {
							if(board[(r1+r2)/2][(c1+c2)/2].equals("b")||board[(r1+r2)/2][(c1+c2)/2].equals("B")) {
								return true;
							}
						}
					}
				}
			}
			else if(board[r1][c1].equals("W")) {
				if((r1>=0&&r1<=n-1)&&(c1>=0&&c1<=n-1)&&(r2>=0&&r2<=n-1)&&(c2>=0&&c2<=n-1)) {
					if(Math.abs(r1-r2)==2&&Math.abs(c1-c2)==2) {
						if(isEmpty(board[r2][c2])) {
							if(board[(r1+r2)/2][(c1+c2)/2].equals("b")||board[(r1+r2)/2][(c1+c2)/2].equals("B")) {
								return true;
							}
						}
					}
				}
			}
		}
		if(player.getPlayer().equals("b")) {
			if((r1>=0&&r1<=n-1)&&(c1>=0&&c1<=n-1)&&(r2>=0&&r2<=n-1)&&(c2>=0&&c2<=n-1)&&r2>r1) {
				if(board[r1][c1].equals("b")) {
					if(Math.abs(r1-r2)==2&&Math.abs(c1-c2)==2) {
						if(isEmpty(board[r2][c2])) {
							if(board[(r1+r2)/2][(c1+c2)/2].equals("w")||board[(r1+r2)/2][(c1+c2)/2].equals("W")) {
								return true;
							}
						}
					}
				}
			}
			else if(board[r1][c1].equals("B")) {
				if((r1>=0&&r1<=n-1)&&(c1>=0&&c1<=n-1)&&(r2>=0&&r2<=n-1)&&(c2>=0&&c2<=n-1)) {
					if(Math.abs(r1-r2)==2&&Math.abs(c1-c2)==2) {
						if(isEmpty(board[r2][c2])) {
							if(board[(r1+r2)/2][(c1+c2)/2].equals("w")||board[(r1+r2)/2][(c1+c2)/2].equals("W")) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	//tests is an AI can jump
	private boolean canJumpAI(Action a, String[][] board) {
		Player player= a.getPlayer();
		int r1= a.getFromRow();
		int c1= a.getFromCol();
		int r2= a.getToRow();
		int c2= a.getToCol();

		if(player.getPlayer().equals("w")) {
			//System.out.println("1");
			if(board[r1][c1].equals("w")) {
				if((r1>=0&&r1<=n-1)&&(c1>=0&&c1<=n-1)&&(r2>=0&&r2<=n-1)&&(c2>=0&&c2<=n-1)&& r2<r1) {
					if(isEmpty(board[r2][c2])) {
						if(board[(r1+r2)/2][(c1+c2)/2].equals("b")||board[(r1+r2)/2][(c1+c2)/2].equals("B")) {
							return true;
						}
					}
				}
			}
			else if(board[r1][c1].equals("W")) {
				if((r1>=0&&r1<=n-1)&&(c1>=0&&c1<=n-1)&&(r2>=0&&r2<=n-1)&&(c2>=0&&c2<=n-1)) {
					if(isEmpty(board[r2][c2])) {
						if(board[(r1+r2)/2][(c1+c2)/2].equals("b")||board[(r1+r2)/2][(c1+c2)/2].equals("B")) {
							return true;
						}
					}
				}
			}
		}
		if(player.getPlayer().equals("b")) {
			if((r1>=0&&r1<=n-1)&&(c1>=0&&c1<=n-1)&&(r2>=0&&r2<=n-1)&&(c2>=0&&c2<=n-1)&&r2>r1) {
				if(board[r1][c1].equals("b")) {
					if(isEmpty(board[r2][c2])) {
						if(board[(r1+r2)/2][(c1+c2)/2].equals("w")||board[(r1+r2)/2][(c1+c2)/2].equals("W")) {
							return true;
						}
					}
				}
			}
			else if(board[r1][c1].equals("B")) {
				if((r1>=0&&r1<=n-1)&&(c1>=0&&c1<=n-1)&&(r2>=0&&r2<=n-1)&&(c2>=0&&c2<=n-1)) {
					if(isEmpty(board[r2][c2])) {
						if(board[(r1+r2)/2][(c1+c2)/2].equals("w")||board[(r1+r2)/2][(c1+c2)/2].equals("W")) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	// checks if multiple jumps are possible
	public boolean multipleJumps(Action a) {
		Player player= a.getPlayer();
		int r1= a.getFromRow();
		int c1= a.getFromCol();
		int r2= a.getToRow();
		int c2= a.getToCol();

		if(player.getPlayer().equals("w")) {
			//System.out.println("1");
			if(board[r1][c1].equals("w")) {
				if((r1>=0&&r1<=n-1)&&(c1>=0&&c1<=n-1)&&(r2>=0&&r2<=n-1)&&(c2>=0&&c2<=n-1)&& r2<r1) {
					if(isEmpty(board[r2][c2])) {
						if(board[(r1+r2)/2][(c1+c2)/2].equals("b")||board[(r1+r2)/2][(c1+c2)/2].equals("B")) {
							return true;
						}
					}
				}
			}
			else if(board[r1][c1].equals("W")) {
				if((r1>=0&&r1<=n-1)&&(c1>=0&&c1<=n-1)&&(r2>=0&&r2<=n-1)&&(c2>=0&&c2<=n-1)) {
					if(isEmpty(board[r2][c2])) {
						if(board[(r1+r2)/2][(c1+c2)/2].equals("b")||board[(r1+r2)/2][(c1+c2)/2].equals("B")) {
							return true;
						}
					}
				}
			}
		}
		if(player.getPlayer().equals("b")) {
			if((r1>=0&&r1<=n-1)&&(c1>=0&&c1<=n-1)&&(r2>=0&&r2<=n-1)&&(c2>=0&&c2<=n-1)&&r2>r1) {
				if(board[r1][c1].equals("b")) {
					if(isEmpty(board[r2][c2])) {
						if(board[(r1+r2)/2][(c1+c2)/2].equals("w")||board[(r1+r2)/2][(c1+c2)/2].equals("W")) {
							return true;
						}
					}
				}
			}
			else if(board[r1][c1].equals("B")) {
				if((r1>=0&&r1<=n-1)&&(c1>=0&&c1<=n-1)&&(r2>=0&&r2<=n-1)&&(c2>=0&&c2<=n-1)) {
					if(isEmpty(board[r2][c2])) {
						if(board[(r1+r2)/2][(c1+c2)/2].equals("w")||board[(r1+r2)/2][(c1+c2)/2].equals("W")) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public String toString(){
		String toDisplay = " ";
		for(int i = 0;i<n;i++){
			toDisplay += " "+number[i];
		}
		toDisplay+="\n";
		for(int i = 0;i<n;i++) {
			toDisplay += "---";
		}
		toDisplay+="\n";
		for(int i = 0;i<n;i++){
			toDisplay+=letter[i];
			for(int j = 0;j<n;j++){
				if(board[i][j] == " ") {
					toDisplay +="| ";
				}
				else {
					toDisplay +="|" + board[i][j];
				}
			} 
			toDisplay +="|";
			toDisplay+="\n";
			for(int k = 0;k<n;k++) {
				toDisplay += "---";
			}
			toDisplay+="\n";
		}
		return toDisplay;

	}
}