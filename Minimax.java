import java.util.ArrayList;

public class Minimax {

//NOT USED ANYMORE. AB AND H MINIMAX USED!!!
	public static void main(String[]args) {
		String[][] board = new String[4][4];
		Player p = new Player("b");
		State s = new State(board, p);
		s.initialState();
		s.fillMap();

		System.out.println(minimax(p, s).toString());
	}

	//terminal function 
	public static boolean terminalTest(Player p, State s) {
		s.utility = 0;

		if(p.getPlayer().equals("b") || p.getPlayer().equals("B")) {// b is maximizer
			if(s.isTerminal(p, s) == true) { 
				s.utility = 1;
				return true;
			}
		}
		if(p.getPlayer().equals("w") || p.getPlayer().equals("W")) {// w is minimizer
			if(s.isTerminal(p, s) == true) {
				s.utility = -1;
				return true;
			}
		}

		return false;
	}
	
	public int heuristic(String [][] board) {
		int countb=0;
		int countw=0;
		int countbk=0;
		int countwk = 0;
		for(int i = 0;i<board.length;i++) {
			for(int j = 0;j<board.length;j++) {
				if(board[i][j].equals("b")) {
					countb++;
				}
				if(board[i][j].equals("B")) {
					countbk++;
				}	
				if(board[i][j].equals("w")) {
					countw++;
				}
				if(board[i][j].equals("W")){
					countwk++;
				}
				
			}
		}
		
		int pieces = 0;
		if(board.length == 4){
			pieces = 2;
		}
		else if(board.length == 8){
			pieces = 12;
		}
		return ((Math.abs(countb - pieces)) * 
				(-10) + Math.abs(countw - pieces) * (10) + countbk * 20 + countwk * (-20));
		
	}



	//minimax decider
	public static State minimax(Player p, State s) {
		if (p.getPlayer().equals("w")) { //min
			return getMin(p, s);
		} else { //max
			return getMax(p, s);
		}

	}
	public static State getMax(Player p, State s) {
		if (terminalTest(p, s)) {
			return s;
		}
		ArrayList<Action> moves = s.getAllMoves(p, s);
		ArrayList<State> children = s.getAllStates(moves, s);
		int maxUtil = Integer.MIN_VALUE;
		State maxNode = null;
		for (State c : children) {
			int util = getMin(switchPlayer(p), c).utility;
			if (util >= maxUtil) {
				maxUtil = util;
				maxNode = c;
			}

		}
		return maxNode;
	}

	public static State getMin(Player p, State s) {
		if (terminalTest(p, s)) {
			return s;
		}
		ArrayList<Action> moves = s.getAllMoves(p, s);
		ArrayList<State> children = s.getAllStates(moves, s);
		int minUtil = Integer.MAX_VALUE;
		State minNode = null;
		for (State c : children) {
			int util = getMax(switchPlayer(p), c).utility;
			if (util <= minUtil) {
				minUtil = util;
				minNode = c;
			}

		}
		return minNode;
	}


	public static Player switchPlayer(Player p) {
		if (p.getPlayer().equals("w")) {
			return new Player("b");
		} else {
			return new Player("w");
		}
	}
}