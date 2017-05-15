package arrayString;

import java.util.ArrayList;
import java.util.List;


//Given an index k, return the kth row of the Pascal's triangle.
//For example, given k = 3,
//Return [1,3,3,1].
//Note:
//Could you optimize your algorithm to use only O(k) extra space? 

class PascalTriangleII 
{
	 public List<Integer> getRow(int rowIndex) {
		 List<Integer> row = new ArrayList<Integer>();
		 int rownum;
		 
		 if ( rowIndex < 1)
			 rownum = 1;
		 else 
			 rownum = rowIndex + 1;
		 
		 row.add(1);
		 
		 for ( int i = 1 ; i < rownum ; i++ )
		 {
			 row.add(0);
			 
			 for ( int j = i ; j > 0 ; j-- )
			 {
				 row.set(j, row.get(j) + row.get(j-1));
			 }
		 }
		
		 return row;
	 }
}