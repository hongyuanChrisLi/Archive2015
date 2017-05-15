package arrayString;

//Given a non-negative number represented as an array of digits, 
//plus one to the number.
//The digits are stored such that 
//the most significant digit is at the head of the list.

public class PlusOne
{
	public int[] plusOne(int[] digits) {
        
		int len = digits.length;
		boolean all_9_flag = true;
		int[] digits_more;
		
		// handle empty array 
		if ( len == 0 )
		{
			
			digits_more = new int[1];
			digits_more[0]=1;
			return digits_more;
		}
		
		// identify all 9 situation
		for ( int i = 0; i < len; i++ )
		{
			if ( digits[i] != 9 )
			{
				all_9_flag = false;
				break;
			}
		}
		

		if ( all_9_flag )
		{
			// Handle all 9 situtation. New array needs to be created with length increment. 
			digits_more = new int[len + 1];
			
			digits_more[0] = 1;
			
			for ( int i = 1; i < len + 1; i ++)
			{
				digits_more[i] = 0;
			}
			
			return digits_more;
		}
		else
		{
			// Regular situation. 
			for ( int i = len - 1; i >= 0; i -- )
			{
				int tmp_val = digits[i] + 1;
				
				if ( tmp_val == 10 )
					digits[i] = 0;
				else
				{
					digits[i] = tmp_val;
					break;
				}
			}
		}
		
		return digits;
    }
	
}