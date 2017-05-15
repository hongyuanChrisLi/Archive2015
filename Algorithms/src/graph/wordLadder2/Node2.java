package graph.wordLadder2;

import java.util.LinkedList;
import java.util.ListIterator;

public class Node2 {
	
	private String value;
	private int min_dist = Integer.MAX_VALUE;
	private LinkedList<Node2> near_parents = new LinkedList<Node2> ();
	private LinkedList<Node2> children = new LinkedList<Node2>() ;
	private ListIterator<Node2> it_child;
	private ListIterator<Node2> it_near_parent;
	
	public Node2( String value ) {
		
		this.value = value;
	}
	
	public int GetDist () {
		return min_dist;
	}
	
	public void InitNearParenetInterator () {
		it_near_parent = near_parents.listIterator();
	}
	
	public void InitNearParents( Node2 parent) {
		near_parents = new LinkedList<Node2> ();
		AddNearParent ( parent );
	}
	
	public void AddNearParent ( Node2 parent ) {
		near_parents.add(parent);
	}
	
	public boolean HasNextNearParent() {
		return it_near_parent.hasNext();
	}
	
	public Node2 NextNearParent () {
		return it_near_parent.next();
	}
	
	public void SetMinDist (int dist) {
		min_dist = dist;
	}
	
	public String GetValue () {
		return value;
	}

	public void InitChildIterator() {
		it_child = children.listIterator();
	}
	
	public void AddChild (Node2 child) {
		children.add(child);
	}
	
	public Node2 NextChild() {
		return it_child.next();
	}

	public boolean HasNextChild () {
		return it_child.hasNext();
	}
	
	public boolean IsParent () {
		return ! children.isEmpty();
	}

}