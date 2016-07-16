package skiplist;

import java.util.Scanner;
import java.util.Vector;
class Node {
	Integer data;
	Integer level;
	Node down;
	Node right;
	public Node(Integer v,Integer l,Node d,Node r) {
		data=v;
		level=l;
		down=d;
		right=r;
	}
	public void setDown(Node n){
		down=n;
	}
	public void setRight(Node n){
		right=n;
	}
}

public class Solution {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input 	= new Scanner(System.in);
		Vector<Node> headList= new Vector<Node>();
		Vector<Node> currprocess= new Vector<Node>();
		Integer searchNode;
		Node prev=null,curr = null,prevtrack;
		Integer noofNodes,nooftracks;
		Integer loopi,loopj,currnodeval,currlevel;
		noofNodes=input.nextInt();
		nooftracks=input.nextInt();
		for(loopi=0;loopi<nooftracks;loopi++) {
			prev=new Node(-1, loopi,prev, null);
			headList.add(prev);
			currprocess.add(prev);
		}	
		for(loopi=0;loopi<noofNodes;loopi++) {
			currnodeval=input.nextInt();
			currlevel=input.nextInt();
			prev=null;
			for(loopj=0;loopj<currlevel;loopj++) {
				prev=new Node(currnodeval, loopj,prev, null);
				currprocess.get(loopj).right=prev;
				currprocess.set((int)loopj,prev);
			}
		}
		searchNode=input.nextInt();
		for(prevtrack=prev=curr=headList.get(nooftracks-1).right;;){
			if(curr.data==searchNode) {
				//if(prevtrack.data!=prev.data)
					System.out.println(prevtrack.level+1+" "+prevtrack.data+" "+curr.data);
				break;
			}
			else if(curr.data<searchNode){
				prev=curr;
				curr=curr.right;
			}
			else {
				if(prevtrack.data!=prev.data)
					System.out.println(prevtrack.level+1+" "+prevtrack.data+" "+prev.data);
				prev=prev.down;
				curr=prev;
				prevtrack=prev;
			}				
		}
		
	}

}
