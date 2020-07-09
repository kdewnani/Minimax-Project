import java.util.HashMap;


public class Action {
	Player player;
	int fromRow, fromCol, toRow, toCol;
	char type;
	public Action(Player player, int fromRow, int fromCol, int toRow, int toCol) {
		this.player = player;
		this.fromCol = fromCol;
		this.fromRow = fromRow;
		this.toRow = toRow;
		this.toCol = toCol;
	}
	public Action(Player player, char type, int fromRow, int fromCol, int toRow, int toCol) {
		this.player = player;
		this.fromCol = fromCol;
		this.fromRow = fromRow;
		this.toRow = toRow;
		this.toCol = toCol;
		this.type = type;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public int getFromRow() {
		return fromRow;
	}
	public void setFromRow(int fromRow) {
		this.fromRow = fromRow;
	}
	public int getFromCol() {
		return fromCol;
	}
	public void setFromCol(int fromCol) {
		this.fromCol = fromCol;
	}
	public int getToRow() {
		return toRow;
	}
	public void setToRow(int toRow) {
		this.toRow = toRow;
	}
	public int getToCol() {
		return toCol;
	}
	public void setToCol(int toCol) {
		this.toCol = toCol;
	}
	public Character getType() {
		return type;
	}
	public void setType(char type) {
		this.type = type;
	}
	public String toString(){
		System.out.println("d");
		return player + " From Row: " + fromRow + "FromCol: " + fromCol + "ToRow: " + toRow + "ToCol: " + toCol;
	}
	
}