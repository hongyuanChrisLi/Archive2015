package graph.wordLadder;

import java.util.LinkedList;
import java.util.ListIterator;

public class Node {
	
	private String value;
	private int min_dist = Integer.MAX_VALUE;
	private Node pre_node = null;
	private LinkedList<Node> children = new LinkedList<Node>() ;
	private ListIterator<Node> list_it;
	
	public Node( String value ) {
		
		this.value = value;
	}
	
	public void UpdateVertex (int dist, Node pre_node) {
		this.min_dist = dist;
		this.pre_node = pre_node;
		
	}
	
	public int GetDist () {
		return min_dist;
	}
	
	public Node GetPreNode () {
		return pre_node;
	}
	
	public void SetMinDist (int dist) {
		min_dist = dist;
	}
	
	public void SetPreNode ( Node pre ) {
		pre_node = pre;
	}
	
	public String GetValue () {
		return value;
	}

	public void AddChild (Node child) {
		children.add(child);
	}
	
	public Node Next() {
		return list_it.next();
	}

	public boolean HasNext () {
		return list_it.hasNext();
	}
	
	public boolean IsParent () {
		return ! children.isEmpty();
	}

	public void CreateIterator() {
		list_it = children.listIterator();
	}
}