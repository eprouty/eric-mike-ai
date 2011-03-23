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
				moveVal=alphabeta(board.State.addPiece(state, 1, j),i,-(state.width*state.height+1),(state.width*state.height+1),1);
				if (i==1) System.out.print(moveVal+" ");
				if (moveVal<max) maxMove=j;
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
				beta=Math.max(beta, alphabeta(board.State.addPiece(gameState, player, i), maxDepth-1, alpha, beta, 2));
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
