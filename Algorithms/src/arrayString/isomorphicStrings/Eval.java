package arrayString.isomorphicStrings;

import java.util.Hashtable;

public class Eval {
	
	private char[] array_a;
	private char[] array_b;
	
	public Eval (String a, String b){
		this.array_a = a.toLowerCase().toCharArray();
		this.array_b = b.toLowerCase().toCharArray();
	}
	
	
	public void Run () {
		if ( array_a.length != array_b.length) {
			
			System.out.println("Not Isomorphic");
			return;
		}
		
		int len = array_a.length;
		Hashtable<Character, Character> char_map = new Hashtable<Character,Character>();
		
		for ( int i = 0 ; i < len; i ++ ) {
			
			if ( char_map.containsKey(array_a[i]) ){
				
				if ( char_map.get(array_a[i]).charValue() == array_b[i] )
					continue;
				else {
					System.out.println("Not Isomorphic");
					return;
				}
					
			}
			else {
				char_map.put(array_a[i], array_b[i]);
			}
		}
		
		System.out.println("Isomorphic");
	}
	
	
	/*public void Run () {
		
		if ( array_a.length != array_b.length) {
			
			System.out.println("Not Isomorphic");
			return;
		}
		
		int len = array_a.length;
		
		for ( int i = 0 ; i < len ; i ++ ) {
			
			int diff_a = (int) array_a[i] - (int) array_a[0];
			int diff_b = (int) array_b[i] - (int) array_b[0];
			
			if (diff_a - diff_b != 0 ) {
				System.out.println("Not Isomorphic: No." + i + " Str A: " + array_a[i] + ", Str B: " + array_b[i]);
				return;
			}
			
		}
		
		System.out.println("Stings are Isomorphic");
	}*/
	
	
}