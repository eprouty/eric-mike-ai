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
			
			}
		}
		
		return 0;
	}
}

