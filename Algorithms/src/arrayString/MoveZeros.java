package arrayString;


 /*Given an array nums, write a function to move all 0's to the end of it while maintaining the relative order of the non-zero elements.

For example, given nums = [0, 1, 0, 3, 12], after calling your function, nums should be [1, 3, 12, 0, 0].

Note:

    You must do this in-place without making a copy of the array.
    Minimize the total number of operations.*/

public class MoveZeros 
{
	 public void moveZeroes(int[] nums) {
	        
		 	int len = nums.length;
		 	int start = 0;
		 	int end = len;
		 	
		 	for ( int i = start; i < end; i ++ )
		 	{
		 		if ( nums[i] != 0  )
		 		{
		 			if ( i > start )
		 			{
		 				for ( int j = i; j > start; j -- )
			 			{
			 				int tmp = nums[j];
			 				nums[j] = nums[j - 1];
			 				nums[j - 1] = tmp;
			 			}
		 			}
		 				
		 			start ++ ;
		 		}
		 		
		 	}
		 
	    }
	
}