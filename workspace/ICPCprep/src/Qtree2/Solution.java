package Qtree2;
import java.util.Scanner;
import java.util.Vector;
class Node {
	Node left;
	Node right;
	int lvalue;
	boolean color;
	Node() {
		left=null;
		right=null;
		lvalue=Integer.MAX_VALUE;
		color=false;
	}
}
class HLD {
	static int V = 300018;
	static Vector<Node> segtree = new Vector<>();     // segtrees
	static Vector<Vector<Integer>> adj = new Vector<>();     // adjacency list
	static int parent[]=new int[V], heavy[] =new int[V];
	//static int depth[]=new int[V];
			static int size[]=new int[V];  
	static int chain[]=new int[V], head[]=new int[V]; 
	static int chainid[]=new int[V], chainlength[]=new int[V];
    static Vector<Integer> q = new Vector<>();
    //static Vector<Integer> q2 = new Vector<>();
	
    static void DFS(int i)
	{
	    size[i] = 1;
	    for (int k=0; k<adj.get(i).size(); ++k)
	    {
	        int j = adj.get(i).get(k);
	        if (j == parent[i]) continue;
	 
	        parent[j] = i;
	  //      depth[j] = depth[i] + 1;
	 
	        DFS(j);
	 
	        size[i] += size[j];
	        if (heavy[i] == -1 || size[j] >= size[heavy[i]]) 
	            heavy[i] = j;
	    }
	}
	static void BFS(int i)
	{
		q.add(i);
		Integer curr,index=0;
		while(index<q.size()) {
			curr=q.get(index);
			size[curr] = 1;
		    for (int k=0; k<adj.get(curr).size(); ++k)
		    {
		        int j = adj.get(curr).get(k);
		        if (j == parent[curr]) continue;
		 
		        parent[j] = curr;
		//        depth[j] = depth[curr] + 1;
		        q.add(j);
		        //DFS(j);
		    }
		    index++;
		}
		index--;
		while(index>=0) {
			curr=q.get(index);
			    // curr=parent[curr];
			 for (int k=0; k<adj.get(curr).size(); ++k) {
				   int j = adj.get(curr).get(k);
				   if (j == parent[curr]) continue;
				   size[curr] += size[j];
			        if (heavy[curr] == -1 || size[j] > size[heavy[curr]]) 
			            heavy[curr] = j;
			   }
			 index--;
		   }
	    
	}
	static void makesegtree(Node s,int start,int end) {
		 if(start==end){
			 s.color=false;//colorids.get(start);
		 }
		 else {
			 s.left=new Node();
			 s.right=new Node();
			 //segt.add(segt.get(index).left);
			 //segt.get(index).left.index=segt.size()-1;
			 //segt.add(segt.get(index).right);
			 //segt.get(index).right.index=segt.size()-1;
			 makesegtree(s.left, start, (start+end)/2);
			 makesegtree(s.right, (start+end)/2+1,end);
			 
			 // segt.get(index).value=Math.min(segt.get(index).right.value, segt.get(index).left.value);
		 }
	 }
	/*static Integer searchtreer(Vector<Node> segt,int index,int start,int end,int qs,int qe) {
		if(start>qe || end<qs) 
			return Integer.MAX_VALUE;
		int mid=(start+end)/2;
		if(qs<=start && end<=qe)
			return segt.get(index).rvalue;
		Integer rindex=searchtreer(segt, segt.get(index).right.index, mid+1, end, qs, qe);
		if(rindex!=Integer.MAX_VALUE)
			return rindex;
		return 	searchtreer(segt, segt.get(index).left.index, start, mid, qs, qe);
	}*/
	static int searchtreel(Node s,int start,int end,int qs,int qe) {
		if(start>qe || end<qs) 
			return Integer.MAX_VALUE;
		int mid=(start+end)/2;
		if(qs<=start && end<=qe)
			return s.lvalue;
		int lindex=searchtreel(s.left, start, mid, qs, qe);
		if(lindex!=Integer.MAX_VALUE)
			return lindex;
		return 	searchtreel(s.right, mid+1, end, qs, qe);
	}
	static void update(Node s,int start,int end,int q,int id) {
		if(start>q || end<q)
			return ;
		int mid=(start+end)/2;
		if(start==q && end==q)
		{
			s.color=!s.color;
			 if(s.color==true) {
		//		 segt.get(index).rvalue=id;
				 s.lvalue=id;
			 }
			 else {
		//		 segt.get(index).rvalue=Integer.MAX_VALUE;
				 s.lvalue=Integer.MAX_VALUE;
			 }
			return ;
		}
		update(s.left, start, mid, q,id);
		
		update(s.right, mid+1, end, q,id);
		//	Integer rindex=segt.get(index).right.rvalue;
	/*	if(rindex!=Integer.MAX_VALUE)
			segt.get(index).rvalue= rindex;
		else
			segt.get(index).rvalue=segt.get(index).left.rvalue;
		*/Integer lindex=s.left.lvalue;
		if(lindex!=Integer.MAX_VALUE)
			s.lvalue= lindex;
		else
			s.lvalue=s.right.lvalue;
	}
	static void heavylight_DFS(int N)
	{
	    //memset(heavy, -1, sizeof(heavy));
	 
	    parent[0] = -1;
	    //depth[0] = 0;
	    BFS(0);
	 
	    int c = 0;  
	    for (int i=0; i<N; ++i)
	        if (parent[i] == -1 || heavy[parent[i]] != i)
	        {
	        	int len=0;
	        	//colorids.clear();
	            for (int k = i; k != -1; k = heavy[k])
	                {
	            	//	colorids.add(color[k]);
	            		chain[k] = c;
	            		head[k] = i;
	            		chainid[k]=len;
	            		len++;
	                }
	            chainlength[i]=len;
	            //Vector<Node> currseg =  new Vector<>();
	            //currseg.add(new Node());
	            Node start= new Node();
	            makesegtree(start,0,len-1);	            
	            c++;
	            segtree.add(start);
	        }
	}
	/*static int lca(int i, int j)
	{
	    while (chain[i] != chain[j])
	        if (depth[head[i]] > depth[head[j]])
	            i = parent[head[i]];
	        else
	            j = parent[head[j]];
	 
	    return depth[i] < depth[j] ? i : j;
	}*/
	 static int search(int i,int j,int k) {
		   /*while (chain[i] != chain[j])
		   {
			    Integer index=searchtreer(segtree.get(chain[i]), 0, 0, chainlength[head[i]]-1,  chainid[head[i]],chainid[i]);
			   if(index!=Integer.MAX_VALUE)
				   return index+1;
			     i = parent[head[i]];
		   }
		   Integer ind=searchtreer(segtree.get(chain[i]), 0, 0, chainlength[head[i]]-1,  chainid[j],chainid[i]);
		   if(ind!=Integer.MAX_VALUE)
			   return ind+1;
		   */Vector<Integer> listofblack = new Vector<>();
		   while (chain[j] != chain[k])
		   {    
			   int index=searchtreel(segtree.get(chain[k]), 0, chainlength[head[k]]-1,  chainid[head[k]],chainid[k]);
			   if(index!=Integer.MAX_VALUE)
				   listofblack.add(index);
			   k= parent[head[k]];
		   }
		   int ind=searchtreel(segtree.get(chain[k]), 0, chainlength[head[k]]-1,  chainid[j],chainid[k]);
			if(ind!=Integer.MAX_VALUE)
			   listofblack.add(ind);
		   if(listofblack.size()==0)
			   return -1;
		   return listofblack.get(listofblack.size()-1)+1;
	 }
}

public class Solution {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n,q = 0,loopi,loopj,a,b;
		Scanner input = new Scanner(System.in);
		//HLD data= new HLD();
		n=input.nextInt();
		q=input.nextInt();
		for(loopi=0;loopi<n;loopi++){
			HLD.adj.add(new Vector<Integer>());
	//		HLD.color[loopi]=false;
			HLD.heavy[loopi]=-1;
			HLD.parent[loopi]=-1;
			//HLD.depth[loopi]=0;
			HLD.chain[loopi]=-1;
			HLD.head[loopi]=-1;
		}
		for(loopi=0;loopi<n-1;loopi++){
			a=input.nextInt();
			b=input.nextInt();
			HLD.adj.get(a-1).add(b-1);
			HLD.adj.get(b-1).add(a-1);
		}
		
		//HLD.DFS(0);
		HLD.heavylight_DFS(n);
		//System.out.println("HI");//data.
		for(loopi=0;loopi<q;loopi++){
			//System.out.println("HI");
			a=input.nextInt();
			b=input.nextInt();
			if(a==0)
				HLD.update(HLD.segtree.get(HLD.chain[b-1]), 0, HLD.chainlength[HLD.head[b-1]]-1, HLD.chainid[b-1],b-1);
			else
			{
				//Integer lc=HLD.lca(a-1, b-1);
				int fid=HLD.search(a-1,a-1,b-1);
				System.out.println(fid);
			}
			
		}
	}

}
