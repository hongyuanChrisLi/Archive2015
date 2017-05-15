package arrayString.medianSorted;

import java.util.Arrays;
import java.util.LinkedList;


public class MedianFinder {
	
	private int[] a_ar;
	private int[] b_ar;
	
	public MedianFinder (String a, String b) {
		
		a_ar = GetIntArray(a);
		b_ar = GetIntArray(b);
		
		//System.out.println(Arrays.toString(b_ar));
		
	}


	private int[] GetIntArray (String str) {
		
		if (str.trim().equals("")) {
			return new int[0];
		}
		
		String[] tmp = str.split(",");
		//System.out.println(Arrays.toString(tmp));
		int len = tmp.length;
		int[] int_ar = new int[len];
		
		for ( int i = 0; i < len; i++ ) {
			try {
				int_ar[i] = Integer.parseInt(tmp[i].trim());
			}catch (NumberFormatException nfe) {};
		}
		
		return int_ar;
	}
	
	
	public void Run () {
		
		int len = ( a_ar.length + b_ar.length ) / 2 + 1;
		int mol = ( a_ar.length + b_ar.length + 1 ) % 2;
		int idx_a = 0;
		int idx_b = 0;
		LinkedList<Integer> median_q = new LinkedList<Integer>();
		
		for ( int i = 0; i < len; i ++) {
			
			if ( idx_a < a_ar.length && idx_b < b_ar.length) {
				
				// Pick the smaller value among two ascending arrays. 
				
				if ( a_ar[idx_a] < b_ar[idx_b] ) {
					median_q.addLast(a_ar[idx_a]);
					idx_a ++;
				} else {
					median_q.addLast(b_ar[idx_b]);
					idx_b ++;
				}
				
			}else if ( idx_a < a_ar.length ) {
				
				median_q.addLast(a_ar[idx_a]);
				idx_a ++;
				
			}else if ( idx_b < b_ar.length ) {
				
				median_q.addLast(b_ar[idx_b]);
				idx_b ++;
			} else {
				System.out.println( "Error!" );
				return;
			}
			
			if ( mol != 0  && median_q.size() > 2) {
				median_q.removeFirst();
			}
			
			System.out.println( "idx_a: " + idx_a +", idx_b: " + idx_b+ ", median_q: " + median_q.getLast() );
		}
		
		float median;
		
		if ( mol ==  0) {
			median = (float) median_q.getLast();
		}else{
			median = (float) (median_q.getFirst() + median_q.getLast()) / 2.0f;
		}
		
		System.out.println(median);
	}
	
	
}