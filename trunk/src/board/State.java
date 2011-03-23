package board;
import ai.ConnectN;

public class State
{
	public int width;
	public int height;
	public int numToWin;
	public int[][] board;
	public State(int width, int height, int numToWin)
	{
		this.width=width;
		this.height=height;
		this.numToWin=numToWin;
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
		int best = 1000000, cur=0;
		
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				switch(board[x][y]){
				case 1:
					cur=movesHeuristic(x, y);
					if (cur<=best) best=cur;
					break;
				default:
					break;
				}
			}
		}
		
		return best;
	}
	
	private int movesHeuristic(int x, int y){
		int best = 1000000, current=0;
		int sentinel=numToWin;
		for (int i=1; i<numToWin; i++)
		{
			if (x+i==width)
			{
				current=1000000;
				break;
			}
			for (int j=0; j<height;j++)
			{
				if (y-j==-1) break;
				if (board[x+i][y-j]==0) current++;
				else j=height;
			}
		}
		if (current<best) best=current;
		current=0;
		for (int i=1; i<numToWin; i++)
		{
			if (y+i==height)
			{
				current=1000000;
				break;
			}
			if (x+i==width)
			{
				current=1000000;
				break;
			}
			if (board[x][y+i]!=0)
			{
				current=1000000;
				break;
			}
			for (int j=0; j<height;j++)
			{
				if (y+j==height)
				{
					current=1000000;
					break;
				}
				if (board[x+i][y+j]==0) current++;
				else j=height;
			}
		}
		if (current<best) best=current;
		current=0;
		for (int i=1; i<numToWin; i++)
		{
			if (y+i==height)
			{
				current=1000000;
				break;
			}
			if (x+i==width)
			{
				current=1000000;
				break;
			}
			if (board[x+i][y+i]!=0)
			{
				current=1000000;
				break;
			}
			for (int j=0; j<height;j++)
			{
				if (y+i-j==-1)
				{
					break;
				}
				if (board[x+i][y+i-j]==0) current++;
				else j=height;
			}
		}
		if (current<best) best=current;
		current=0;
		for (int i=1; i<numToWin; i++)
		{
			if (y-i==-1)
			{
				current=1000000;
				break;
			}
			if (x+i==width)
			{
				current=1000000;
				break;
			}
			if (board[x+i][y-i]!=0)
			{
				current=1000000;
				break;
			}
			for (int j=0; j<height;j++)
			{
				if (y-i-j==-1) break;
				if (board[x+i][y-i-j]==0) current++;
				else j=height;
			}
		}
		if (current<best) best=current;
		return best;
	}
	
	private int checkDirection(int dir, int x, int y){
		int movesNeeded = ConnectN.numToWin - 1;
		
		switch (dir){
		case 0:
			for (int i = 1; i < movesNeeded; i++){
				if (y+i==height) break;
				if (board[x][y+i] == 1){
					movesNeeded--;
				} else {
					break;
				}
			}
			break;
		case 1:
			for (int i = 1; i < movesNeeded; i++){
				if ((y+i==height || x+i==width)) break;
				if (board[x+i][y+i] == 1){
					movesNeeded--;
				} else {
					break;
				}
			}
			break;
		case 2:
			for (int i = 1; i < movesNeeded; i++){
				if ((y==height || x+i==width)) break;
				if (board[x+i][y] == 1){
					movesNeeded--;
				} else {
					break;
				}
			}
			break;
		case 3:
			for (int i = 1; i < movesNeeded; i++){
				if ((y-i==-1 || x+i==width)) break;
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

