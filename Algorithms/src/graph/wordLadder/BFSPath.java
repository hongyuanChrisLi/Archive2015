package graph.wordLadder;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;

import graph.wordLadder.Node;

public class BFSPath {
	
	private String[] tokens;
	private String start;
	private String end;
	private Node[] nodes;
	private Hashtable<String, Node> node_hash = new Hashtable<String,Node> ();
	
	
	public BFSPath (String in, String start, String end) {
	
		tokens = in.replaceAll("\\s", "").split(",");
		System.out.println(Arrays.toString(tokens));
		
		this.start = start;
		this.end = end;
		nodes = new Node[tokens.length];
		BuildGraph();
	}
	
	
	private void BuildGraph () {
		
		int item_num = tokens.length;
		
		for ( int i = 0; i < item_num; i++ ) {
			nodes[i] =  new Node(tokens[i]);
			node_hash.put(tokens[i], nodes[i]);
		}
		
		for ( int i = 0 ; i < item_num; i++ ) {
			for ( int j = 0; j < item_num; j++ ) {
				
				if ( nodes[i].GetValue().equals(tokens[j])){
					continue;
				}
				
				if (IsNeighbor(nodes[i].GetValue(), tokens[j])){
					nodes[i].AddChild(node_hash.get(tokens[j]));
					
					//System.out.println(tokens[j] + " is added to " + nodes[i].GetValue());
				}
	
			}
			nodes[i].CreateIterator();
		}		
	}
	
	private boolean IsNeighbor (String astr, String bstr) {
		
		int len = astr.length();
		int same = 0;
		for (int i = 0; i < len; i++ ) {
			if (astr.charAt(i) == bstr.charAt(i))
				same ++;
		}
		
		return same == len - 1;
	}
	
	public void Run () {
		
		LinkedList<Node> proc_queue = new LinkedList<Node>();
		Hashtable<Node, Boolean> visited_node_hash = new Hashtable<Node, Boolean> ();
		node_hash.get(start).SetMinDist(0);
		proc_queue.add(node_hash.get(start));
		
		while( ! proc_queue.isEmpty() ) {
			Node cur_node = proc_queue.removeFirst();
			//System.out.println("Here: " + cur_node.GetValue());
			if (visited_node_hash.containsKey(cur_node)) {
				continue;
			}
			
			visited_node_hash.put(cur_node, true);
			while(cur_node.HasNext()) {
				Node child_node = cur_node.Next();
				//System.out.println(child_node.GetValue());
				int dist = cur_node.GetDist() + 1;
				
				if ( dist < child_node.GetDist()) {
					child_node.SetMinDist(dist);
					child_node.SetPreNode(cur_node);
				}
				proc_queue.add(child_node);			
			}
			
			//proc_queue.removeFirst();
		}
		
		//System.out.println(node_hash.get(end).GetDist() + 1);	
		Node trace_node = node_hash.get(end);
		int path_len = trace_node.GetDist() + 1;
		
		String[] path = new String[path_len];

		for (int i = path_len - 1 ; i >= 0 ; i--) {
			
			path[i] = trace_node.GetValue();
			trace_node = trace_node.GetPreNode();
		}
		
		System.out.println(Arrays.toString(path));
	}
	
}