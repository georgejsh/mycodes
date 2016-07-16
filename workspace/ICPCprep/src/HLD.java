import java.util.Vector;
class Node {
	Node left;
	Node right;
	Integer value;
	Integer index;
	Node() {
		left=null;
		right=null;
		value=0;
		index=0;
	}
}
public class HLD {
	static int V = 100000;
	static Vector<Vector<Integer>> segtree = new Vector<>();     // segtrees
	static Vector<Vector<Integer>> adj = new Vector<>();     // adjacency list
	static int parent[]=new int[V], heavy[] =new int[V];
	static int depth[]=new int[V], size[]=new int[V];  
	static int chain[]=new int[V], head[]=new int[V]; 
	static int chainid[]=new int[V], chainlength[]=new int[V];
	void DFS(int i)
	{
	    size[i] = 1;
	    for (int k=0; k<adj.get(i).size(); ++k)
	    {
	        int j = adj.get(i).get(k);
	        if (j == parent[i]) continue;
	 
	        parent[j] = i;
	        depth[j] = depth[i] + 1;
	 
	        DFS(j);
	 
	        size[i] += size[j];
	        if (heavy[i] == -1 || size[j] > size[heavy[i]]) 
	            heavy[i] = j;
	    }
	}
	void makesegtree(Vector<Node> segt,int index,int start,int end) {
		 if(start==end){
			 segt.get(index).value=start;
		 }
		 else {
			 segt.get(index).left=new Node();
			 segt.get(index).right=new Node();
			 segt.add(segt.get(index).left);
			 segt.get(index).left.index=index+1;
			 makesegtree(segt, index+1, start, (start+end)/2);
			 segt.add(segt.get(index).right);
			 segt.get(index).right.index=segt.size()-1;
			 makesegtree(segt, segt.size()-1, (start+end)/2+1,end);
			 segt.get(index).value=Math.min(segt.get(index).right.value, segt.get(index).left.value);
		 }
	 }
	Integer searchtree(Vector<Node> segt,int index,int start,int end,int qs,int qe) {
		if(start>qe || end>qs) 
			return Integer.MAX_VALUE;
		int mid=(start+end)/2;
		if(start==qs && end==qe)
			return segt.get(index).value;
		return Math.min(searchtree(segt, segt.get(index).left.index, start, mid, qs, qe),searchtree(segt, segt.get(index).right.index, mid+1, end, qs, qe));
	}
	Integer update(Vector<Node> segt,int index,int start,int end,int q) {
		if(!(start<=q && q<=end)) 
			return segt.get(index).value;
		int mid=(start+end)/2;
		if(start==q && end==q)
		{
			segt.get(index).value=q;
			return segt.get(index).value;
		}
		segt.get(index).value= Math.min(update(segt, segt.get(index).left.index, start, mid, q),update(segt, segt.get(index).right.index, mid+1, end, q));
		return segt.get(index).value;
	}
	void heavylight_DFS(int N)
	{
	    //memset(heavy, -1, sizeof(heavy));
	 
	    parent[0] = -1;
	    depth[0] = 0;
	    DFS(0);
	 
	    int c = 0;  
	    for (int i=0; i<N; ++i)
	        if (parent[i] == -1 || heavy[parent[i]] != i)
	        {
	        	int len=0;
	            for (int k = i; k != -1; k = heavy[k])
	                {
	            		chain[k] = c;
	            		head[k] = i;
	            		chainid[k]=len;
	            		len++;
	                }
	            chainlength[i]=len;
	            Vector<Node> currseg =  new Vector<>();
	            currseg.add(new Node());
	            makesegtree(currseg,0,0,len);	            
	            c++;
	            
	        }
	}
	int lca_1(int i, int j)
	{
	    while (chain[i] != chain[j])
	        if (depth[head[i]] > depth[head[j]])
	            i = parent[head[i]];
	        else
	            j = parent[head[j]];
	 
	    return depth[i] < depth[j] ? i : j;
	}
	 
}
