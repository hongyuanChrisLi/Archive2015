package arrayString.RotateArray;

import java.util.Arrays;

// Intermediate Array

public class RotateArray01 {
	
	private static final int MAX_LEN = 15;
	private int base_a[]  = new int[MAX_LEN] ;
	private int k;
	private int n;
	
	public RotateArray01 (int n, int k) throws InterruptedException {
		this.k = k;
		this.n = n;
		
		if ( n > MAX_LEN || n < 1) 
			throw new InterruptedException( "Array size should be in (1, 15]" ) ;
		
		for ( int i = 0; i < n ; ++ i) {
			base_a[i] = i + 1;
		}
	
	}

	public void Run () {
		
		int inter_a [] = new int[n];
		
		// This method resizes the array. Can't be used. 
		//inter_a = Arrays.copyOfRange(base_a, n - k, n - 1);
		
		for (int i = 0; i < k ; i ++ ) {
			System.out.println(i);
			inter_a[i] = base_a[n - k + i];
		}
		
		
		for ( int i = k ; i < n ; i ++ ) {
			System.out.println ( i );
			inter_a[i] = base_a[i - k];
		}
		
		base_a = Arrays.copyOf(inter_a, n);
		
		System.out.println (Arrays.toString(base_a));
	}
	
	
}