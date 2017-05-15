package arrayString;

/*Follow up for "Unique Paths":
Now consider if some obstacles are added to the grids. 
How many unique paths would there be?
An obstacle and empty space is marked as 1 and 0 respectively in the grid.
For example,
There is one obstacle in the middle of a 3x3 grid as illustrated below.
[
  [0,0,0],
  [0,1,0],
  [0,0,0]
]
The total number of unique paths is 2.*/

public class UniquePathsII
{
	public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        
		int m = obstacleGrid.length;
		int n = obstacleGrid[0].length;
		
		if ( m == 0 || n == 0) return 0;
		
		if ( n == 1 )
		{
			for ( int i = 0 ; i < m; i++ )
			{
				if ( obstacleGrid[i][0] == 1 )
					return 0;
			}
				
			return 1;
		}
		
		if ( m == 1 )
		{
			for ( int j = 0 ; j < n; j++ )
			{
				if ( obstacleGrid[0][j] == 1 )
					return 0;
			}
			return 1;
		}
		
		int[][] dp = new int[m][n];
		
		int m_val = 1;
		for ( int i = m -1; i >=0 ; i -- )
		{
			if ( obstacleGrid[i][n-1] == 1 )
				m_val = 0;
			
			dp[i][n-1] = m_val;			
		}
		
		
		int n_val = 1;
		for ( int j = n -1; j >= 0; j -- )
		{
			if ( obstacleGrid[m-1][j] == 1)
				n_val = 0;
			
			dp[m-1][j] = n_val;
		}
		
		
		for ( int i = m -2; i >=0 ; i -- )
			for ( int j = n -2; j >= 0 ; j --)
			{
				if (obstacleGrid[i][j] == 1 )
					dp[i][j] = 0;
				else
					dp[i][j] = dp[i+1][j] + dp[i][j+1];
			}
				
		
		return dp[0][0];
    }
}