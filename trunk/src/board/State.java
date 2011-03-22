package board;

public class State
{
	public int width;
	public int height;
	public int[][] board;
	public State(int width, int height)
	{
		this.width=width;
		this.height=height;
		board=new int[width][height];
		for (int i=0; i<width; i++)
		{
			for (int j=0; j<height; j++)
			{
				board[i][j]=0;
			}
		}
	}
	
	public static State addPiece(State current, int player, int x){
		for (int i = 0; i < current.height; i++){
			if (current.board[x][i] == 0){
				current.board[x][i] = player;
				break;
			}
		}
		
		return current;
	}
	
	public int evaluate(){
		int best;
		
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				switch(board[x][y]){
				case 1:
					movesHeuristic(x, y);
					break;
				case 0:
					y = height;
				}
			}
		}
		
		return 0;
	}
	
	private int movesHeuristic(int x, int y){
		int best = 0;
		for (int dir = 0; dir < 4; dir++){
			best = Math.max(best, checkDirection(dir, x, y));
		}
		return best;
	}
	
	private int checkDirection(int dir, int x, int y){
		int movesNeeded = ConnectN.numToWin - 1;
		
		switch (dir){
		case 0:
			for (int i = 1; i < movesNeeded; i++){
				if (board[x][y+i] == 1){
					movesNeeded--;
				} else {
					break;
				}
			}
			break;
		case 1:
			for (int i = 1; i < movesNeeded; i++){
				if (board[x+i][y+i] == 1){
					movesNeeded--;
				} else {
					break;
				}
			}
			break;
		case 2:
			for (int i = 1; i < movesNeeded; i++){
				if (board[x+i][y] == 1){
					movesNeeded--;
				} else {
					break;
				}
			}
			break;
		case 3:
			for (int i = 1; i < movesNeeded; i++){
				if (board[x+i][y-i] == 1){
					movesNeeded--;
				} else {
					break;
				}
			}
			break;
		}
		return movesNeeded;
	}
}

