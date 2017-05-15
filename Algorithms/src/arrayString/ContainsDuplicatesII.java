package arrayString;


/*Given an array of integers and an integer k, 
 * find out whether there are two distinct indices i and j in the array 
 * such that nums[i] = nums[j] and the difference between i and j is at most k. . */

import java.util.HashMap;

public class ContainsDuplicatesII
{
	 public boolean containsNearbyDuplicate(int[] nums, int k) 
	 {
	 
		 int len = nums.length;
		 HashMap<Integer, Integer> map = new HashMap<Integer, Integer> ();
		 
		 for ( int i = 0 ; i < len; i++ )
		 {
			 if ( map.containsKey(nums[i]) && i - map.get(nums[i]) <= k )
			 {
					 return true;
			 }
			 else
				 map.put(nums[i], i);
		 }
		 
		 return false;
	  }
}