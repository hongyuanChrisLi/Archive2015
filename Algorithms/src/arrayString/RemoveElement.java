package arrayString;


//Given an array and a value, remove all instances of that value in place 
//and return the new length.
//The order of elements can be changed. 
//It doesn't matter what you leave beyond the new length.

public class RemoveElement
{
	public int removeElement(int[] nums, int val) 
	{
		int len = nums.length;
        int end_idx = len - 1;
        
        for ( int i = 0; i < len; i++ )
        {
        	
        	// Find the last element that is not the given val.
        	while ( end_idx > i  &&  nums[ end_idx ] == val )
        	{
        		end_idx --;
        	}
        	
        	
        	// Termination Condition
        	if ( i >= end_idx )
        	{
        		// One element may haven't been checked
        		if ( nums[end_idx] == val)
        	    {
        	        end_idx --;
        	    }
        	    break;
        	}
        	
        	
        	// Swap
        	if ( nums[i] == val )
        	{
        		nums[i] = nums[end_idx];
        		nums[end_idx] = val;
        		end_idx -- ;
        	}
        	
        }
        
        return end_idx + 1;
    }

}