package ai;

import java.lang.Math;

public class MiniMax extends Thread
{
	private board.State state;
	public int bestMove;
	public boolean runSearch;
	public MiniMax(board.State currentState)
	{
		state=currentState;
		for (int i=0; i<state.width; i++)
		{
			if (state.board[i][state.height-1]==0)
			{
				bestMove=i;
				break;
			}
		}
		runSearch=true;
	}
	public void run()
	{
		int max=1000000, maxMove=-1, moveVal=1000000;
		for (int i=1; runSearch; i++)
		{
			for (int j=0; j<state.width && runSearch; j++)
			{
				if (state.board[j][state.height-1]!=0) continue;
				moveVal=alphabeta(board.State.addPiece(state, 1, j),i,-1000000,1000000,1);
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
		if (!runSearch) return 0;
		int val=gameState.evaluate(player);
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
		}
		if (player==2)
		{
			for (int i=0; i<gameState.width; i++)
			{
				beta=Math.min(beta, alphabeta(board.State.addPiece(gameState, player, i), maxDepth-1, alpha, beta, 1));
				if (beta<=alpha) break;
			}
			return beta;
		}
		return 1;
	}
}
