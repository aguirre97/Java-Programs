/*
Alexandra Aguirre
NID: al373180
4-11-17
COP3503
*/

import java.io.*;
import java.util.*;

public class RunLikeHell
{
    public static int maxGain(int [] blocks)
    {	
        return maxGainDPFancy(blocks);
    }

	// dynamic programming
	public static int maxGainDPFancy(int [] blocks)
	{
		// length 4 b/c only need the last 3 stored values
		int [] max = new int [4];

		// start at 2 to begin by looking at the previous 2 elements, then move forward
		for(int i = 2; i <= blocks.length + 1; i++)
							// max of taking or not taking
							// mod by 3 so you only keep the most recent 3 elements
			max[i%3] = Math.max(blocks[i-2] + max[(i-2)%3], max[(i-1)%3]);

			// max value is stored here
		return max[(blocks.length + 1) % 3];
	}

	/* ************ NOTE: These functions were just practice to getting the DP solution ************ */

	// regular top-down recursion
    private static int maxGain(int [] blocks, int start)
	{
		//if there is no more blocks return 0
		if (blocks.length - start <= 0)
			return 0;
		
		// return the max of either taking it or not taking it
		return Math.max(
		//taking it 
		blocks[start] + maxGain(blocks, start + 2),
		//not taking it
		maxGain(blocks, start + 1)
		);
	}

	// memoization
	public static int maxGainMemo(int [] blocks)
	{
		HashMap<Integer, Integer> memo = new HashMap<>();
		return maxGainMemo(blocks, 0, memo);
	}


	// avoid computing same result twice, memoization
	private static int maxGainMemo(int [] blocks, int start, HashMap<Integer, Integer> memo)
	{
		if(blocks.length - start <= 0)
			return 0;
		
		Integer result = memo.get(start);
		
		if(result != null)
			return result;

		result = Math.max(
			// taking it
			blocks[start] + maxGainMemo(blocks, start + 2, memo), 
			// not taking it
			maxGainMemo(blocks, start + 1, memo)
			);
		
		memo.put(start, result);

		return result;
	}

	// dynamic programming
	public static int maxGainDP(int [] blocks)
	{
		int [] max = new int [blocks.length+3];

		for(int i = 2; i <= blocks.length + 1; i++)
							// max of taking or not taking
			max[i] = Math.max(blocks[i-2] + max[i-2], max[i-1]);

		return max[blocks.length + 1];
	}

    public static double difficultyRating()
    {
		return 2.5;
    }

    public static double hoursSpent()
    {
		return 3;
    }
}