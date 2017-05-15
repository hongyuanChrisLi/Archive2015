package arrayString.RotateArray;

import java.util.Arrays;

public class RotateArray04Reversal{
	
	private static final int MAX_LEN = 15;
	private int[] base_a ;
	private int k;
	private int n;
	
	public RotateArray04Reversal (int n, int k) throws InterruptedException {
		this.k = k;
		this.n = n;
		
		if ( n > MAX_LEN || n < 1) 
			throw new InterruptedException( "Array size should be in (1, 15]" ) ;
		
		base_a = new int[n];
		
		
		for ( int i = 0; i < n ; ++ i) {
			base_a[i] = i + 1;
		}
	}
	
	public void Run ( ) {
		
		Reverse(0, n-k-1);
		Reverse(n-k, n-1);
		Reverse(0, n-1);
		
		System.out.println(Arrays.toString(base_a));
		
	}
	
	private void Reverse ( int start, int end) {
		
		while (start < end) {
			int tmp_val = base_a[start];
			base_a[start] = base_a[end];
			base_a[end] = tmp_val;
			
			start ++;
			end --;
		}
		
	}
}
