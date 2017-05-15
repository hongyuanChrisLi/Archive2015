package arrayString.RotateArray;

import java.util.Arrays;

// This algorithm has loopholes. Even n introduces problem. 
//[1,2,3,4,5,6]
//2


public class RotateArray03Map{
	
	private static final int MAX_LEN = 15;
	private int[] base_a ;
	private int k;
	private int n;
	
	public RotateArray03Map (int n, int k) throws InterruptedException {
		this.k = k;
		this.n = n;
		
		if ( n > MAX_LEN || n < 1) 
			throw new InterruptedException( "Array size should be in (1, 15]" ) ;
		
		if ( k< 1) 
			throw new InterruptedException( "move number should be positive" ) ;
		else if (k > n ) {
			this.k =  k % n ;
		}
		
		base_a = new int[n];
		
		
		for ( int i = 0; i < n ; ++ i) {
			base_a[i] = i + 1;
		}
	
	}

	public void Run () {
		
		final int src_idx = 0;
		int des_idx = 0;
		int count = n;
		
		System.out.println(k);
		while ( count -- > 1 ) 
		{
			
			if ( des_idx < n - k ) {
				des_idx = des_idx + k;
			}else {
				des_idx = des_idx - ( n - k );
			}
		
			//System.out.println("src: " + src_idx + ", des: " + des_idx);
			
			int tmp_val = base_a [des_idx];
			base_a[des_idx] = base_a[src_idx];
			base_a[src_idx] = tmp_val;
			
			
			//System.out.println (Arrays.toString(base_a));
		}
		
		System.out.println (Arrays.toString(base_a));
	}
		
}