package arrayString;

/*Given an array of integers, find if the array contains any duplicates. 
Your function should return true if any value appears at least twice in the array, 
and it should return false if every element is distinct. */

import java.util.HashMap;

public class ContainsDuplicates
{
	public boolean containsDuplicate(int[] nums) {
		
		int len = nums.length;
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer> ();
		
		for ( int i = 0; i < len; i++ )
		{
			if (map.containsKey(nums[i]))
				return true;
			else
				map.put( nums[i], 1);
		}
		
		return false;
    }
}