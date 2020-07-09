import java.util.ArrayList;

public class H_Minimax {
	//terminal function 
	public static boolean terminalTest(Player p, State s) {
		
		ArrayList<Action> moves = s.getAllMoves(p, s);
		ArrayList<State> children = s.getAllStates(moves, s);
		
		
		s.utility = 0;

		if((p.getPlayer().equals("b") || p.getPlayer().equals("B"))) {// b is maximizer
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

		if(children.size() == 0) return true;
		

		return false;
	}

	//heurestic function
	public static int heuristic(String [][] board) {
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
		int h = ((Math.abs(countb - pieces)) * 
				(-10) + Math.abs(countw - pieces) * (10) + countbk * 20 + countwk * (-20));
		return h;
		
	}


	static int m = 0;

	
	//Minimax decider
	public static State minimax(Player p, State s) {
		System.out.println("Thinking...\nPerforming the most intelligent move!\n");
		if(s.board.length == 8) {
			m = 3;
		} else {
			m = 6;
		}
		if (p.getPlayer().equals("w")) { //min
			return getMin(p, s, 0);
		} else { //max
			return getMax(p, s, 0);
		}

		

	}
	public static State getMax(Player p, State s, int depth) {
		if (terminalTest(p, s) || depth > m) {
			
			return s;
		}
		ArrayList<Action> moves = s.getAllMoves(p, s);
		ArrayList<State> children = s.getAllStates(moves, s);
		int maxUtil = Integer.MIN_VALUE;
		State maxNode = null;
		for (State c : children) {
			int util = heuristic(getMin(switchPlayer(p), c, depth+1).board);
			if (util >= maxUtil) {
				maxUtil = util;
			
				maxNode = c;
			}

		}
		
		return maxNode;
	}

	public static State getMin(Player p, State s, int depth) {
		if (terminalTest(p, s) || depth > m) {
			return s;
		}
		ArrayList<Action> moves = s.getAllMoves(p, s);
		ArrayList<State> children = s.getAllStates(moves, s);
		int minUtil = Integer.MAX_VALUE;
		State minNode = null;
		
		for (State c : children) {
			int util = heuristic(getMax(switchPlayer(p), c, depth+1).board);
			if (util <= minUtil) {
				minUtil = util;
			
				minNode = c;
			
			}

		}
	
		return minNode;
	}

//switchplayer
	public static Player switchPlayer(Player p) {
		if (p.getPlayer().equals("w")) {
			return new Player("b");
		} else {
			return new Player("w");
		}
	}
}