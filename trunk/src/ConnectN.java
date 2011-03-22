import java.io.*;

public class ConnectN 
{
    public static void main (String args[]) throws Exception 
    {
        int width, height, numToWin, playerNumber, timeLimit, move;

        // use BufferedReader for easy reading
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        // send player name
        System.out.println("player-x");
        System.out.flush();

        // read game config
        String [] gameConfig = input.readLine().split(" ");
        height = Integer.parseInt(gameConfig[0]);
        width = Integer.parseInt(gameConfig[1]);
        numToWin = Integer.parseInt(gameConfig[2]);
        playerNumber = Integer.parseInt(gameConfig[3]);
        timeLimit = Integer.parseInt(gameConfig[4]);
        
        boolean myMove = (playerNumber==0);

        while (true) 
        {
            if (myMove) 
            {
            	MiniMax minimax=new MiniMax();
            	minimax.start();
            	Thread.sleep((long)((float)timeLimit-0.5)*1000);
            	move=MiniMax.bestMove;
            	minimax.stop();
                // send move
                // System.out.println(String.valueOf(move));
                System.out.flush();
            } 
            else 
            {
                // read move
                move = Integer.parseInt(input.readLine());

                // check for end
                if (move < 0) break;
            }

            myMove=!myMove;
        }
    }
}
class MiniMax extends Thread
{
	public static int bestMove;
	public void run()
	{
	}
	
}
class State
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

