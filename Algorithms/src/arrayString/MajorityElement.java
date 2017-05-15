package arrayString;

/*Given an array of size n, find the majority element. 
The majority element is the element that appears more than  n/2  times.

You may assume that the array is non-empty and the majority element always 
exist in the array.*/

import java.util.HashMap;

public class MajorityElement
{
	 public int majorityElement(int[] nums) {
		 
		 int len = nums.length;
		 
		 if ( len == 0 )
			 return 0;
		 
		 HashMap<Integer, Integer> map = new HashMap<Integer, Integer> ();
		 
		 for ( int i = 0; i < len; i++)
		 {
			 if ( map.containsKey(nums[i]))
				 map.put(nums[i], map.get(nums[i]) + 1);
			 else
				 map.put(nums[i], 1);
		 }
	        
		 int maj_key = nums[0];
		 int max_cnt = 0;
		 
		 for (int key: map.keySet())
		 {
			 if ( map.get(key) > max_cnt )
			 {
				 max_cnt = map.get(key);
				 maj_key = key;
			 }
		 }
		 
		 return maj_key;
	    }
}