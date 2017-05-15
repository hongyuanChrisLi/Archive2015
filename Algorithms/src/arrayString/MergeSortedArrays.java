package arrayString;

/*Given two sorted integer arrays nums1 and nums2, 
merge nums2 into nums1 as one sorted array.

Note:
You may assume that nums1 has enough space 
(size that is greater or equal to m + n) 
to hold additional elements from nums2. 
The number of elements initialized in nums1 and nums2 are m and n respectively.

Assume both arrays are sorted in ascending order
*
*/

public class MergeSortedArrays
{
	public void merge(int[] nums1, int m, int[] nums2, int n) {
        
		int len = m + n;
		int im = m - 1, in = n - 1;
		
		for ( int j = len - 1; j >= 0; j -- )
		{
			
			if ( im >= 0 && in >=0 )
			{

				if ( nums1[im] >= nums2[in] )
				{
					nums1[j] = nums1[im];
					im --;
				}
				else
				{
					nums1[j] = nums2[in];
					in --;
				}
			}
			else if ( im >= 0 )
			{
				nums1[j] = nums1[im];
				im --;
			}
			else if ( in >= 0 )
			{
				nums1[j] = nums2[in];
				in--;
			}
					
		}
		
    }
}