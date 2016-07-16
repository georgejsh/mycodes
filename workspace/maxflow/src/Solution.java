import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

class Node {
	Integer Id;
	Integer tempmax;
	Boolean visited;
//	Node prev;
//	Integer previ;
	ArrayList<Node> neighbours;
	ArrayList<Integer> capacity;
	public Node(Integer id) {
		Id = id;
		neighbours=new ArrayList<Node>();
		capacity=new ArrayList<Integer>();
		visited=new Boolean(false);
	}
	public void addEdge(Node n,Integer c) {
		neighbours.add(n);
		capacity.add(c);
	}
	
}
public class Solution {

	/**
	 * @param args
	 */
	static HashMap<Integer, Node> maptoNode = new HashMap<Integer, Node>();
	static Integer N;
	static ArrayList<Node> currentpath= new ArrayList<Node>();
	static Integer findpath(Node current,Integer maxC) {
		current.tempmax=maxC;
		//currentpath.add(current);
		//while(!currentpath.isEmpty()){
			//current=currentpath.get(0);
			//if(current.visited==true)
			//{
			//	currentpath.remove(0);
			//	continue;
			//}
			current.visited=true;
			if(current.Id==N)
			{
				Integer min=current.tempmax;
			//	while(current.prev!=null)
			//	{					
			//		current.prev.capacity.add(current.previ, current.prev.capacity.get(current.previ)-min);
			//		current.prev.capacity.remove(current.previ.intValue()+1);
			//		current=current.prev;
				//}
				return min;
				//break;
			}
			for(Integer loopneig=0;loopneig<current.neighbours.size();loopneig++) {
				Node curr=current.neighbours.get(loopneig);			
				if(current.capacity.get(loopneig)>0 && curr.visited!=true ) {//&& !currentpath.contains(curr)) {	
			//		curr.tempmax=Math.min(current.tempmax, current.capacity.get(loopneig));
			//		curr.prev=current;
			//		curr.previ=loopneig;
			//		currentpath.add(curr);
			        System.out.print(curr.Id+" ");
					Integer currmax=current.capacity.get(loopneig);
					Integer foundC=findpath(curr,Math.min(maxC, current.capacity.get(loopneig)));
                    current.capacity.remove((int)loopneig);
					current.capacity.add(loopneig,currmax-foundC );
					Integer currmaxrev=-1;
					for(Integer loopne=0;loopne<curr.neighbours.size();loopne++) {
                    //Integer locrev=curr.neighbours.find(curr);					
    					if(curr.neighbours.get(loopne).Id==current.Id) {
    					    System.out.print("a"+current.Id);
    						currmaxrev=curr.capacity.get(loopne);
    						curr.capacity.remove((int)loopne);		
    						curr.capacity.add(loopne,currmaxrev+foundC );
    						break;
    					}
    					
					}
					if(currmaxrev==-1){
    						curr.capacity.add(foundC );
                            curr.neighbours.add(current );
    					}
					return foundC;
				}			
			}
			//currentpath.remove(0);
		//}
		return 0;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input=new Scanner(System.in);
		Integer noofNodes,noofedges,loopi,nodea,nodeb,capab,maxFlow=0,loopj;
		noofNodes=input.nextInt();
		noofedges=input.nextInt();
		N=noofNodes;
		for(loopi=0;loopi<noofedges;loopi++) {
			nodea=input.nextInt();
			nodeb=input.nextInt();
			capab=input.nextInt();
			if(maptoNode.get(nodea)==null)
				maptoNode.put(nodea,new Node(nodea));
			if(maptoNode.get(nodeb)==null)
				maptoNode.put(nodeb,new Node(nodeb));
			maptoNode.get(nodea).addEdge(maptoNode.get(nodeb), capab);
		}
		while(true) {
			for(loopj=1;loopj<=N;loopj++){
				//	if(maptoNode.get(loopj)!=null)
							maptoNode.get(loopj).visited=false;
						//	System.out.println(loopj);
					//		for(Integer loopk=0;loopk<maptoNode.get(loopj).neighbours.size();loopk++)
				//				System.out.println(maptoNode.get(loopj).neighbours.get(loopk).Id+" " +maptoNode.get(loopj).capacity.get(loopk));
				}
			currentpath.clear();
			loopj=findpath(maptoNode.get(1), 10000);
			maxFlow+=loopj;
			if(loopj==0)
				break;
			
			System.out.println(maxFlow);
			
				
		}
		if(maxFlow>0)
		{	
			System.out.println("Yes");
			System.out.println(maxFlow);
		}
		else
			System.out.println("No");
			

	}

}
