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
}

