package arrayString;

/*
 * A robot is located at the top-left corner of a m x n grid;

The robot can only move either down or right at any point in time. 
The robot is trying to reach the bottom-right corner of the grid
How many possible unique paths are there?
 */

public class UniquePaths 
{
	public int uniquePaths(int m, int n) 
	{
		
		// Dynamic Programming
		
		if ( m == 0 || n == 0) return 0;
		if ( m == 1 || n == 1) return 1;
		
		
		int[][] dp = new int[m][n];
		
		for ( int i = 0; i < m - 1 ; i ++  )
			dp[i][n-1] = 1;
		
		for ( int j = 0; j < n -1 ; j ++ )
			dp[m-1][j] = 1;
		
		for ( int i = m - 2; i >= 0 ; i --  )
			for ( int j = n - 2; j >= 0; j --)
				dp[i][j] = dp[i+1][j] + dp[i][j+1];
	
		return dp [0][0];
    }
	
	
	/*
	 * Time consuming. 
	 * private int dfs ( int m, int i, int n, int j, int k)
	{
		if ( i < 0 || j < 0 || i >= m || j >= n || k < 0 )
			return 0;
		
		if ( k == 0 && i == m - 1 && j == n - 1)
			return 1;
		
		int right = dfs (m, i, n, j + 1, k - 1);
		int down = dfs (m, i + 1, n, j, k - 1);
		
		return right + down;
	}*/
	
	
}