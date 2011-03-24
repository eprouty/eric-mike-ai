package board;

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
	public static void printState(State state)
	{
		for (int y=state.height-1; y>=0; y--)
		{
			for (int x=0; x<state.width; x++)
			{
				System.out.print(state.board[x][y]==0 ? " " : (state.board[x][y]==1?"0":"@"));
			}
			System.out.println();
		}
		System.out.println();
		System.out.flush();
	}
	public static State addPiece(State current, int player, int x){
		State state=new State(current.width, current.height, current.numToWin);
		for (int i=0; i<state.width; i++)
		{
			for (int y=0; y<state.height; y++)
			{
				state.board[i][y]=current.board[i][y];
			}
		}
		for (int i = 0; i < state.height; i++){
			if (state.board[x][i] == 0){
				state.board[x][i] = player;
				break;
			}
		}
		
		return state;
	}
	
	public int evaluate(int player){
		int best = 1000000, vertScore, horizScore, upRightScore, downRightScore;
		
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				vertScore=checkVert(x,y,player);
				horizScore=checkRight(x,y,player);
				upRightScore=checkUpRightDiag(x,y,player);
				downRightScore=checkDownRightDiag(x,y,player);
				best=Math.min(best, Math.min(vertScore, Math.min(horizScore, Math.min(upRightScore, downRightScore))));
			}
		}
		
		return best;
	}
	private int checkVert(int x, int y, int player)
	{
		
		int score=0;
		for (int i=0; i<numToWin; i++)
		{
			if (y+i==height) return 1000000;
			if (board[x][y+i]==0) score++;
			else if (board[x][y+i]==player) continue;
			else return 1000000;
		}
		
		return score;
	}
	private int checkRight(int x, int y, int player)
	{
		int score=0;
		for (int i=0; i<numToWin; i++)
		{
			if (x+i==width) return 1000000;
			if (board[x+i][y]==0)
			{
				score+=1+y-getColumnHeight(x+i);
			}
			else if (board[x+i][y]==player) continue;
			else return 1000000;
		}
		
		return score;
	}
	private int checkUpRightDiag(int x, int y, int player)
	{
		int score=0;
		for (int i=0; i<numToWin; i++)
		{
			if (x+i==width) return 1000000;
			if (y+i==height) return 1000000;
			if (board[x+i][y+i]==0)
			{
				score+=1+i+y-getColumnHeight(x+i);
			}
			else if (board[x+i][y+i]==player) continue;
			else return 1000000;
		}
		return score;
	}
	private int checkDownRightDiag(int x, int y, int player)
	{
		int score=0;
		for (int i=0; i<numToWin; i++)
		{
			if (x+i==width) return 1000000;
			if (y-i==-1) return 1000000;
			if (board[x+i][y-i]==0)
			{
				
				score+=1+i+y-getColumnHeight(x+i);
			}
			else if (board[x+i][y-i]==player) continue;
			else return 1000000;
		}
		return score;
	}

	private int getColumnHeight(int col)
	{
		int height=0;
		for (int i=0; i<height; i++)
		{
			if (board[col][i]!=0) height++;
			else break;
		}
		return height;
	}
}
