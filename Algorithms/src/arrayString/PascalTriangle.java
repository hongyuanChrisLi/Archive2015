package arrayString;


import java.util.List;
import java.util.ArrayList;

/*Given numRows, generate the first numRows of Pascal's triangle.

For example, given numRows = 5,
Return

[
     [1],
    [1,1],
   [1,2,1],
  [1,3,3,1],
 [1,4,6,4,1]
]
*/


public class PascalTriangle
{
	List<Integer> row_lst ;
	List<List<Integer>> matrix = new ArrayList<List<Integer>> ();
	
	public List<List<Integer>> generate(int numRows) {
		
		
		if ( numRows == 0 )
		{
			return matrix;
		}
		
		row_lst = new ArrayList<Integer> ();
		row_lst.add(1);
		matrix.add(row_lst);
		
		for ( int i = 1 ; i < numRows; i++ )
		{
			int row_len = i;
			row_lst = new ArrayList<Integer> ();
			
			for ( int j = 0; j < row_len + 1; j++)
			{
				row_lst.add(topLeftVal(i, j) + topRightVal(i,j));
			}
			
			matrix.add(row_lst);
		}
		
        return matrix;
    }
	
	private Integer topLeftVal (int row, int col)
	{
		int tl_row = row - 1;
		int tl_col = col - 1;
		
		if ( tl_row < 0 || tl_col < 0)
		{
			return 0;
		}
		
		return matrix.get(tl_row).get(tl_col);
	}
	
	private Integer topRightVal (int row, int col)
	{
		
		int tr_row = row - 1;
		int tr_col = col;
		
		if ( tr_row < 0 || col > tr_row )
		{	
			return 0;
		}
		
		return matrix.get(tr_row).get(tr_col);
	}
	
}