package ai;
import java.io.*;

public class ConnectN 
{
	public static int width, height, numToWin, playerNumber, timeLimit, move;
    public static void main (String args[]) throws Exception 
    {
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
        playerNumber = Integer.parseInt(gameConfig[3]) + 1;
        timeLimit = Integer.parseInt(gameConfig[4]);
        board.State state=new board.State(width,height,numToWin);
        
        boolean myMove = (playerNumber==1);

        while (true) 
        {
            if (myMove) 
            {
            	MiniMax minimax=new MiniMax(state);
            	minimax.start();
            	Thread.sleep((long)((float)timeLimit-0.25)*1000);
            	move=minimax.getBestMove();
            	minimax.runSearch=false;
            	state=board.State.addPiece(state, 1, move);
                // send move
                System.out.println(String.valueOf(move));
                System.out.flush();
            } 
            else 
            {
                // read move
                move = Integer.parseInt(input.readLine());
                // check for end
                if (move < 0) break;
                state=board.State.addPiece(state, 2, move);
            }
            //System.out.println();
            //board.State.printState(state);
            myMove=!myMove;
        }
    }
}

