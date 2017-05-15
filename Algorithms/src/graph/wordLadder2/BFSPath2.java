package graph.wordLadder2;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;

import graph.wordLadder2.Node2;

public class BFSPath2 {
	
	private String[] tokens;
	private String start;
	private String end;
	private Node2[] nodes;
	private Hashtable<String, Node2> node_hash = new Hashtable<String,Node2> ();
	
	
	public BFSPath2 (String in, String start, String end) {
	
		tokens = in.replaceAll("\\s", "").split(",");
		System.out.println(Arrays.toString(tokens));
		
		this.start = start;
		this.end = end;
		nodes = new Node2[tokens.length];
		BuildGraph();
	}
	
	
	private void BuildGraph () {
		
		int item_num = tokens.length;
		
		for ( int i = 0; i < item_num; i++ ) {
			nodes[i] =  new Node2(tokens[i]);
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
			nodes[i].InitChildIterator();
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
		
		LinkedList<Node2> proc_queue = new LinkedList<Node2>();
		Hashtable<Node2, Boolean> visited_node_hash = new Hashtable<Node2, Boolean> ();
		node_hash.get(start).SetMinDist(0);
		proc_queue.add(node_hash.get(start));
		
		while( ! proc_queue.isEmpty() ) {
			Node2 cur_node = proc_queue.removeFirst();
			//System.out.println("Here: " + cur_node.GetValue());
			if (visited_node_hash.containsKey(cur_node)) {
				continue;
			}
			
			visited_node_hash.put(cur_node, true);
			while(cur_node.HasNextChild()) {
				Node2 child_node = cur_node.NextChild();
				//System.out.println(child_node.GetValue());
				int dist = cur_node.GetDist() + 1;
				
				if ( dist < child_node.GetDist()) {
					child_node.SetMinDist(dist);
					child_node.InitNearParents(cur_node);
				}else if (dist == child_node.GetDist()) {
					child_node.AddNearParent(cur_node);
				}
				proc_queue.add(child_node);			
			}
			
			//proc_queue.removeFirst();
		}
		
		/*System.out.println(node_hash.get(end).GetDist() + 1);	
		Node2 trace_node = node_hash.get(end);
		int path_len = trace_node.GetDist() + 1;*/
		
		/*String[] path = new String[path_len];

		for (int i = path_len - 1 ; i >= 0 ; i--) {
			
			path[i] = trace_node.GetValue();
			trace_node = trace_node.GetPreNode();
		}
		
		System.out.println(Arrays.toString(path));*/
		
		
		PrintAllShortestPaths();
	}
	
	private void PrintAllShortestPaths () {
		
		LinkedList<Node2> nodes_stack = new LinkedList<Node2> ();
		
		Node2 end_node = node_hash.get(end);
		nodes_stack.addFirst(end_node);
		
		int path_len = end_node.GetDist() + 1;
		String[] path = new String[path_len]; 
		int idx = path_len = 1;
		
		while ( ! nodes_stack.isEmpty()){
			
			Node2 trace_node = nodes_stack.removeFirst();
			
			if ( trace_node.GetValue().equals(start)) {
				path[idx] = trace_node.GetValue();
				System.out.println(Arrays.toString(path));
				continue;
			}
			
			trace_node.InitNearParenetInterator();
			
			while(trace_node.HasNextNearParent()) {
				nodes_stack.addFirst(trace_node.NextNearParent());
			}
			
			int cur_dist =  trace_node.GetDist();
			
			if (cur_dist > idx ) {
				
				idx = cur_dist;
			}
			
			path[idx] = trace_node.GetValue();
			idx -- ;
		}
	
	}
	
}