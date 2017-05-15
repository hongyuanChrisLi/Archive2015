package main;

/*import arrayString.RotateArray.RotateArray01;
import arrayString.RotateArray.RotateArray02Bubble;
import arrayString.RotateArray.RotateArray03Map;
import arrayString.RotateArray.RotateArray04Reversal;
import arrayString.isomorphicStrings.Eval;
import graph.wordLadder.BFSPath;
import graph.wordLadder2.BFSPath2;*/



import java.util.List;
import java.util.Arrays;
/*import arrayString.SortedInputRangeSummary;
import arrayString.RotateArray.RotateArray01;
import arrayString.medianSorted.MedianFinder;;
*/

import arrayString.MergeSortedArrays;
import arrayString.MajorityElement;

public class TestMain {
	
	public static void main (String[] argv) throws InterruptedException {
		
		int[] nums = {};
		MajorityElement me = new MajorityElement();
		System.out.println(me.majorityElement(nums));
		
		
		/*RotateArray01 prac1 = new RotateArray01(7, 3);
		prac1.Run();*/
		
		
		/*MergeSortedArrays msa = new MergeSortedArrays();
		int[] nums1a = {3,4,6,10,11,12,15,19};
		int[] nums2 = {};
		int m = nums1a.length;
		int n = nums2.length;
		int[] nums1 = new int[m+n];
		System.arraycopy(nums1a, 0, nums1, 0, m);
		
		msa.merge(nums1, m, nums2, n);
		
		System.out.println(Arrays.toString(nums1));*/
		
		/*RotateArray02Bubble prac2 = new RotateArray02Bubble(7, 3);
		prac2.Run();*/
		
		/*RotateArray03Map prac03 = new RotateArray03Map(7, 24);
		prac03.Run();*/
		
		/*RotateArray04Reversal prac04 = new RotateArray04Reversal (7, 3);
		prac04.Run();*/
		
		/*Eval prac05 = new Eval("egg", "aid");
		prac05.Run();*/
		
		/*BFSPath prac06 = new BFSPath ("hot,  dot,   dog, lot, log, hit, cog", "hit", "cog");
		prac06.Run();*/
		
		/*BFSPath2 prac07 = new BFSPath2 ("hot,  dot, dog, lot, log, hit, cog, fot, fog, dit, dig", "dig", "fot");
		prac07.Run();*/
		
		/*MedianFinder prac08 = new MedianFinder("2,3, 6, 7, 10", "1, 4, 8,9, 11");
		prac08.Run();*/
		
		/*SortedInputRangeSummary sirs = new SortedInputRangeSummary();
		int[] nums = {0,1,2,4,5,7};
		List<String> res = sirs.summaryRanges(nums);
		System.out.println(res.toString());*/
	}
	
}

