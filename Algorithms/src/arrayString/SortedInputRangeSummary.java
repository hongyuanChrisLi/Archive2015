package arrayString;


import java.util.ArrayList;
import java.util.List;


public class SortedInputRangeSummary
{
	List<String> range = new ArrayList<String>();
	
	public List<String> summaryRanges(int[] nums) 
	{
        
		int len = nums.length;

		try 
		{
			if ( len == 0 )
			{
				throw new IllegalArgumentException("Int Array should contain some value"); 
			}
		}
		catch(IllegalArgumentException e)
		{
			System.err.println("Exception: " + e.getMessage());
		}
		
		
		if ( len == 1 )
		{
			addToRange(nums[0], nums[0]);
			return range;
		}
		else 
		{
			int start = nums[0];
			int end = start;
			
			for ( int i = 1; i < len ; i++) {
				
				
				if ( nums[i] - nums[i-1] == 1 )
				{
					end = nums[i];
				}
				else
				{
					addToRange(start, end);
					start = nums[i];
					end = start;	
				}
				

				if ( i == len -1 )
				{
					
					addToRange(start, end);
				}
				
			}
			
		}
		
		return range;

    }
	
	
	private void addToRange ( int start, int end )
	{
		if (start == end )
		{
			range.add( Integer.toString(start) );
		}
		else
		{
			range.add( Integer.toString(start) + "->" + Integer.toString(end) );
		}
	}
	
	/*public static void main () {
		
	}
	*/
}