#include<stdio.h>
#include<iostream>
#include<vector>
#include<map>
#include <algorithm>
#include <numeric>
#include <cstdio>
#include <cstdlib>
#include <cctype>
#include <cassert>
#include <sstream>
#include <string>
#include <set>
#include <stack>
#include <queue>

#include <cmath>
#include <complex>
using namespace std;
class edge{
 public:
 int a,b,c;
 bool flag;
 int ord;
 bool operator < (const edge& e) const{
   return e.c>c;
 }
};
int setp[100001];
int getp(int i){
 if(setp[i]==i)
  return i;
 return getp(setp[i]);
}
void merge(int i,int j){
  i=getp(i);
  setp[i]=getp(j);
}
vector<edge> el;
const int V = 100001;
vector<int> adj[V];     // adjacency list
int parent[V], heavy[V];
int depth[V], size[V];  
int chain[V], head[V];  
int chainl[V],chainpos[V];
map<long long int,int> parre;
//Where chain[u] is u's chain number and head[u] is the node closest to root in u's chain.
vector<vector<int> > segtreelist;
void DFS(int i)
{
    size[i] = 1;
    for (int k=0; k<adj[i].size(); ++k)
    {
        int j = adj[i][k];
        if (j == parent[i]) continue;
 
        parent[j] = i;
        depth[j] = depth[i] + 1;
 
        DFS(j);
 
        size[i] += size[j];
        if (heavy[i] == -1 || size[j] > size[heavy[i]]) 
            heavy[i] = j;
    }
}
 
void heavylight_DFS(int N)
{
//    memset(heavy, -1, sizeof(heavy));
    for (int i=0; i<N; ++i)
      heavy[i]=-1;
    parent[0] = -1;
    depth[0] = 0;
    DFS(0);
 
    int c = 0;  
    for (int i=0; i<N; ++i)
        if (parent[i] == -1 || heavy[parent[i]] != i)
        {
	    vector<int> vi;
	    segtreelist.push_back(vi);
	    int j=0;
            for (int k = i; k != -1; k = heavy[k])
                chain[k] = c, head[k] = i,chainpos[k]=j,j++,segtreelist[c].push_back(-1),segtreelist[c].push_back(-1),segtreelist[c].push_back(-1),segtreelist[c].push_back(-1);
            chainl[c]=j-1;
	    c++;
        }
}
int q[V], *qf, *qb; // BFS queue
 
void heavylight_BFS(int N)
{
    qf = qb = q;
    parent[0] = -1;
    depth[0] = 0;
    *qb++ = 0;
    while (qf < qb)
        for (int i=*qf++, k=0; k<adj[i].size(); ++k)
        {
            int j = adj[i][k];
            if (j == parent[i]) continue;
            parent[j] = i;
            depth[j] = depth[i] + 1;
            *qb++ = j;
        }
 	for (int i=0; i<N; ++i)
      		heavy[i]=-1,size[i]=0;
    
//    memset(size, 0, sizeof(size));
  //  memset(heavy, -1, sizeof(heavy));
    for (int k=N-1; k>0; --k)   
    {
        int j = q[k], i = parent[q[k]];
        size[j]++;
        size[i] += size[j];
        if (heavy[i] == -1 || size[j] > size[heavy[i]])
            heavy[i] = j;
    }
 
    int c = 0;
    for (int i=0; i<N; ++i)
        if (parent[i] == -1 || heavy[parent[i]] != i)
        {
         	vector<int> vi;
	    segtreelist.push_back(vi);
	    int j=0;
            for (int k = i; k != -1; k = heavy[k])
                chain[k] = c, head[k] = i,chainpos[k]=j,j++,segtreelist[c].push_back(-1),segtreelist[c].push_back(-1),segtreelist[c].push_back(-1),segtreelist[c].push_back(-1);
            chainl[c]=j-1;
	       c++;
        }
}

void update(int chainid,int pos,int l,int r,int i,int j,int val){
//	cout<<"update"<<chainid<<" "<<pos<<" "<<l<<" "<<r<<" "<<i<<" "<<j<<" "<<val<<endl;
	if(r<0)
		return;	
	if(l==i && j==r){
		if(segtreelist[chainid][pos]==-1)
			segtreelist[chainid][pos]=val;
		return;
	}
	if(r<i || j<l)
		return;
	if(segtreelist[chainid][pos]!=-1)
		return;
	int mid=(l+r)/2;
	if(i<=mid && mid<=j){
		update(chainid,2*pos,l,mid,i,mid,val);
		if(mid<j)
			update(chainid,2*pos+1,mid+1,r,mid+1,j,val);
	}	
	else if(j<mid)
		update(chainid,2*pos,l,mid,i,j,val);
	else
		update(chainid,2*pos+1,mid+1,r,i,j,val);
}
int get(int chainid,int pos,int l,int r,int i){
	//cout<<chainid<<" "<<pos<<" "<<l<<" "<<r<<" "<<i<<endl;
	if(l==i && i==r){
		return segtreelist[chainid][pos];
	}
	int val=-1,mid=(l+r)/2;
	if(segtreelist[chainid][pos]!=-1)
		val=segtreelist[chainid][pos];
	if(i<=mid){
		int x=get(chainid,2*pos,l,mid,i);
	//	cout<<val<<"x"<<x<<endl;
		if(val!=-1 && x==-1)
			return val;
		else if(val!=-1)
			return min(val,x);
		else
			return x;
	}	
	else{
		int x=get(chainid,2*pos+1,mid+1,r,i);
	//	cout<<val<<"x"<<x<<endl;
		if(val!=-1 && x==-1)
			return val;
		else if(val!=-1)
			return min(val,x);
		else
			return x;
	}
}
void lca(int i, int j,int val)
{
//cout<<"LCA\n";
    while (chain[i] != chain[j]){
// cout<<i<<" "<<j<<endl;
       if (depth[head[i]] > depth[head[j]]){
	    update(chain[i],1,0,chainl[chain[i]]-1,0,chainpos[i]-1,val);
	    if(parre.find(parent[head[i]]*100000L+head[i])==parre.end())
		parre[parent[head[i]]*100000L+head[i]]=val;
            i = parent[head[i]];
	}
        else{
	    update(chain[j],1,0,chainl[chain[j]]-1,0,chainpos[j]-1,val);
	    if(parre.find(parent[head[j]]*100000L+head[j])==parre.end())
		parre[parent[head[j]]*100000L+head[j]]=val;
            j = parent[head[j]];
	}
    }
	if(depth[i] < depth[j])
	    update(chain[j],1,0,chainl[chain[j]]-1,chainpos[i],chainpos[j]-1,val);
	else
    		update(chain[j],1,0,chainl[chain[j]]-1,chainpos[j],chainpos[i]-1,val);
    //return depth[i] < depth[j] ? i : j;
}
void look_inside(int N) {
	int i;
 
	printf("\n");
	printf("HEAVY: \n");
	printf("(i, j): i----(heavy edge)----j\n\n");
 
	for (i = 0; i < N; i++)
		printf("(%d, %d)\n", i, heavy[i]);
	
	printf("\n");
	printf("CHAIN: \n");
	printf("(i, j): Node i is in group (heavy-path group) number j\n\n");
	
	for (i = 0; i < N; i++)
		printf("(%d, %d)\n", i, chain[i]);
 
	printf("\n");
	printf("HEAD: \n");
	printf("(i, j): Node i goes up all the way to the highest node (j) which is in the same group\n\n");
 
	for (i = 0; i < N; i++)
		printf("(%d, %d)\n", i, head[i]);
}

int main(){
 int t,n,e;
 int i,j,k,a,b,c;
 long long int l;
 long long int pr[200001];
 for(scanf("%d",&t);t;t--){
	segtreelist.clear();
	 scanf("%d%d",&n,&e);
	el.clear();
	for(i=0;i<=n;i++){
		setp[i]=i;
		adj[i].clear();
	}
	 for(i=0;i<e;i++){
	   edge ed;
 	   scanf("%d%d%d",&ed.a,&ed.b,&ed.c);
	   ed.ord=i;
	   ed.flag=false;
	   el.push_back(ed);
	 }
	sort(el.begin(),el.end());
	l=0;
	for(vector<edge>::iterator ee=el.begin();ee!=el.end();ee++){
		if(getp(ee->a)==getp(ee->b))
			continue;
//		cout<<ee->a<<" "<<ee->b<<" "<<ee->c<<endl;
		merge(ee->a,ee->b);
		l+=ee->c;
		ee->flag=true;
		adj[ee->a-1].push_back(ee->b-1);
		adj[ee->b-1].push_back(ee->a-1);
	}
	//cout<<"OK"<<endl;
	heavylight_BFS(n);
//	look_inside(n);
	for(vector<edge>::iterator ee=el.begin();ee!=el.end();ee++){
		if(!ee->flag){
			pr[ee->ord]=l;
			lca(ee->a-1,ee->b-1,ee->c);
		}
	}	
	for(vector<edge>::iterator ee=el.begin();ee!=el.end();ee++){
		ee->a--;ee->b--;
		if(ee->flag){
			if(chain[ee->a]!=chain[ee->b]){
				if(parre.find(ee->a*100000L+ee->b)!=parre.end())
					pr[ee->ord]=parre[ee->a*100000L+ee->b]+l-ee->c;
				else if(parre.find(ee->b*100000L+ee->a)!=parre.end())
					pr[ee->ord]=parre[ee->b*100000L+ee->a]+l-ee->c;
				else
					pr[ee->ord]=-1;
			}
			else if(chain[ee->a]==chain[ee->b]){
				if(chainpos[ee->a]<chainpos[ee->b])
					pr[ee->ord]=get(chain[ee->a],1,0,chainl[chain[ee->a]]-1,chainpos[ee->a])+l-ee->c;			
				else
					pr[ee->ord]=get(chain[ee->a],1,0,chainl[chain[ee->a]]-1,chainpos[ee->b])+l-ee->c;
			}
			
		}
	}parre.clear();
	 for(i=0;i<e;i++){
		if(pr[i]>=l)
			printf("%lld\n",pr[i]);
		else
			printf("-1\n");
	}
 }
 return 0;
}

