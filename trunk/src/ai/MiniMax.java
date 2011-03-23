package ai;

import board.State;
import java.lang.Math;

public class MiniMax extends Thread
{
	private board.State state;
	public static int bestMove;
	public MiniMax(board.State currentState)
	{
		state=currentState;
	}
	public void run()
	{
		int max=(state.width*state.height+1), maxMove=-1, moveVal=(state.width*state.height+1);
		for (int i=1; i<1000; i++)
		{
			for (int j=0; j<state.width; j++)
			{
				if (state.board[j][state.height-1]!=0) continue;
				moveVal=alphabeta(board.State.addPiece(state, 1, j),i,-1000000,1000000,1);
				if (i==1) System.out.print(moveVal+" ");
				if (moveVal<max)
				{
					maxMove=j;
					max=moveVal;
				}
			}
			bestMove=maxMove;
		}
	}
	public int getBestMove()
	{
		return bestMove;
	}
	public int alphabeta(board.State gameState, int maxDepth, int alpha, int beta, int player)
	{
		int val=gameState.evaluate();
		if (maxDepth==0 || val==0)
		{
			return val;
		}
		if (player==1)
		{
			for (int i=0; i<gameState.width; i++)
			{
				alpha=Math.max(alpha, alphabeta(board.State.addPiece(gameState, player, i), maxDepth-1, alpha, beta, 2));
				if (beta<=alpha) break;
			}
			return alpha;
			//minimize
		}
		if (player==2)
		{
			for (int i=0; i<gameState.width; i++)
			{
				beta=Math.min(beta, alphabeta(board.State.addPiece(gameState, player, i), maxDepth-1, alpha, beta, 1));
				if (beta<=alpha) break;
			}
			return beta;
			//maximize
		}
		return 1;
	}
}
/*
*/
