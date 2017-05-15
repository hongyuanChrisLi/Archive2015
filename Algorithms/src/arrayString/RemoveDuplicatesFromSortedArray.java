package arrayString;

/*
Given a sorted array, remove the duplicates in place such that 
each element appear only once and return the new length.

Do not allocate extra space for another array,
 you must do this in place with constant memory.

For example,
Given input array nums = [1,1,2],

Your function should return length = 2, 
with the first two elements of nums being 1 and 2 respectively. 
It doesn't matter what you leave beyond the new length.

*/ 


public class RemoveDuplicatesFromSortedArray 
{
	// Similar to Bubble sort
	 public int removeDuplicates(int[] nums) 
	 {
		 int len = nums.length;
		 
		 if ( len == 0 )
		 {
			 return 0;
		 }
		 
		 int cur_val = nums[0];
		 int cur_idx = 1;
		 int end_idx = len -1;
		 
		 while ( cur_idx <= end_idx )
		 {
			 
			 // Move current element to the end of new string
			 if ( nums[cur_idx] == cur_val )
			 {
				 
				 for ( int j = cur_idx; j < end_idx ; j++)
				 {
					 int tmp_val = nums[j];
					 nums[j] = nums[j+1];
					 nums[j+1] = tmp_val;
				 }
				 end_idx --;
			 }
			 else
			 {
				 // update current value and move index forward
				 cur_val = nums[cur_idx];
				 cur_idx ++;
			 }
				
		 }
		 
		 return end_idx + 1;
		 
	 }

}