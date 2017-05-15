package arrayString.RotateArray;

import java.util.Arrays;

// Bubble sort has performance issue in this case. 

public class RotateArray02Bubble{
	
	private static final int MAX_LEN = 15;
	private int[] base_a ;
	private int k;
	private int n;
	
	public RotateArray02Bubble (int n, int k) throws InterruptedException {
		this.k = k;
		this.n = n;
		
		if ( n > MAX_LEN || n < 1) 
			throw new InterruptedException( "Array size should be in (1, 15]" ) ;
		
		base_a = new int[n];
		
		
		for ( int i = 0; i < n ; ++ i) {
			base_a[i] = i + 1;
		}
	
	}

	public void Run () {
		
		for (int i = 0; i < k; i ++ ) {
			
			for (int j = n - 1 ; j > 0; j -- ) {
				
				int tmp_val = base_a[j];
				base_a[j] = base_a[j - 1];
				base_a[j-1] = tmp_val;
			}
		}
		
		System.out.println (Arrays.toString(base_a));
	}
	
	
}