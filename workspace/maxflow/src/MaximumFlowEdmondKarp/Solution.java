package MaximumFlowEdmondKarp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;


class Node
{
	
	static Node sink;
	static Node source;
	static HashMap<Integer,Node>  intVsNode = new HashMap<Integer,Node>();
	static int noOfedges;
	int number;
	HashMap<Node,Integer> children ;
	boolean whetherVisited ;
	Node prev;
	static LinkedList<Node> bfsQ = new LinkedList<Node>();
	static LinkedList<Node> path = new LinkedList<Node>();
	Node(int i)
	{
		number = i;
		intVsNode.put(i, this);
		children = new HashMap<Node,Integer>();
		prev =null;
		whetherVisited = false;
	}
	void addChild(Node child,int capacity)
	{
		this.children.put(child, capacity);
	}
	public static void Initialise(int s ,int t)
	{
		source = intVsNode.get(s);
		sink = intVsNode.get(t);
		noOfedges = t;
		
	}
	public String toString()
	{
		StringBuilder sb = null ;
		sb.append(this.number);
		return sb.toString();
	}
	public static int maxFlow()
	{
		int maxFlow = 0;
		
		
		while(source.children.size() != 0)
		{
			Node n1 = null;
			Node n2 = null;
			int minPath = 101;
			source.whetherVisited = true;
			source.prev = null;
			bfsQ.addLast(source);
			Node backTrack = null;
			Node backTrack1 = null;
			bfs:
			while(!bfsQ.isEmpty())
			{
				n1 = bfsQ.removeFirst();
				
				
				Set<Entry<Node, Integer>> entrySet = n1.children.entrySet();
				for(Entry<Node, Integer> entry : entrySet)
				{
					n2 = entry.getKey();
					
					if( n2.whetherVisited == false)
					{
						bfsQ.add(n2);
						n2.whetherVisited = true;
						n2.prev = n1;
						if(n2 == sink)
							break bfs;
						
					}
					
				}
			}
				
			 backTrack = sink.prev;
				 backTrack1 = sink;
				 if(backTrack == null)
				 {
					 break;
				 }
				
				while(backTrack != null)
				{
					path.addFirst(backTrack1);
					if(backTrack.children.get(backTrack1) < minPath)
					{
						minPath = backTrack.children.get(backTrack1);
					}
					backTrack1 = backTrack;
					backTrack = backTrack.prev;
				}
				path.addFirst(backTrack1);
				
				maxFlow = maxFlow + minPath;
				
				/* changing edge weights*/
				for(int i=0; i< path.size()-1;i++)
				{
					n1 = path.get(i);
					n2 = path.get(i+1);
					n1.whetherVisited = false;
					n1.prev = null;
					
					if (n1.children.get(n2) - minPath  <= 0)
					{
						n1.children.remove(n2);
					}
					else
					{
						n1.children.put(n2, n1.children.get(n2) - minPath);
					}
					
					if(n2.children.containsKey(n1))
					{
						n2.children.put(n1,n2.children.get(n1)+minPath);
					}
					else
						n2.children.put(n1, minPath);
				}
				n2.prev = null;
				n2.whetherVisited = false;
				
				for(int i = 1; i <= noOfedges;i++)
				{
					n1 = intVsNode.get(i);
					n1.prev = null;
					n1.whetherVisited = false;
				}
				bfsQ.clear();
				path.clear();
			}
		
		
		return maxFlow;
		
	}
}
public class Solution {
public static void main(String[] args) {
	Scanner inputFromUser = new Scanner(System.in);
	int nJunctions = inputFromUser.nextInt();
	int nEdges = inputFromUser.nextInt();
	int s,d,capacity;
	Node n2 = null;
	Node n1=null;
	
	for(int i = 0;i < nEdges;i++)
	{
		s = inputFromUser.nextInt();
		d = inputFromUser.nextInt();
		capacity = inputFromUser.nextInt();
		if(!Node.intVsNode.containsKey(s))
		{
			n1 = new Node(s);
			Node.intVsNode.put(s,n1);
		}
		else
		{
			n1 = Node.intVsNode.get(s);
		}
		
		if(!Node.intVsNode.containsKey(d))
		{
			n2 = new Node(d);
			Node.intVsNode.put(d,n2);
		}
		else
		{
		n2= Node.intVsNode.get(d);
		}
		
		n1.addChild(n2, capacity);
	}
	Node.Initialise(1, nJunctions);
	int max = Node.maxFlow();
	System.out.println(max);
}
}
