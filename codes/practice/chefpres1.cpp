#include<iostream>
#include<vector>
#include<map>
#include<algorithm>
#include<queue>
#include<iterator>
using namespace std;
int dis[10001];
vector<int>  adj[10001];
//vector<int> path;
//map<int,int> searchv;
//custom_priority_queue<int,vector<int>, std::greater<int> > q;
vector<int> f;
void dfsf(int r){
 int i;
 for(i=0;i<adj[r].size();i++){
   if(dis[adj[r][i]]==-1){
	dis[adj[r][i]]=dis[r]+1;
  	dfsf(adj[r][i]);
  }	
 }
}
int minv,mini;
int dfss(int r,int p,int par,int m){
 int i;
 m=min(m,dis[r]);
//cout<<"test"<<r<<" "<<m<<endl;
 if(f[r]==p && minv<=m){
   if((minv==m && mini>r) || minv<m){
    mini=r;
    minv=m;
   }
  }
 for(i=0;i<adj[r].size();i++){
	if(adj[r][i]==par)
		continue;
//        q.push(dis[adj[r][i]]);
//	path.push_back(i);
  	dfss(adj[r][i],p,r,m);
//	path.pop_back();
//	q.remove(dis[adj[r][i]]);
 }
}
int main()
{
 int n,k;
 int Q,i,j,l,a,b,B,p;
 cin>>n>>k;
 cin>>B;
 for(i=0;i<n-1;i++){
   cin>>a>>b;
    dis[i]=-1;
   adj[a].push_back(b);  
   adj[b].push_back(a);  
 }
 dis[n]=dis[n-1]=-1;
 dis[B]=0;
 dfsf(B);
 f.push_back(0);
 for(i=0;i<n;i++){
  cin>>a;
  f.push_back(a);
 }
 cin>>Q;
/* for(i=1;i<=n;i++){
  cout<<dis[i]<<" ";
 }
 cout<<endl;
 */for(i=0;i<Q;i++){
  cin>>a>>p;
  minv=-1;
  mini=100000;
//  q.clear();
 // path.clear();
 // q.push(dis[a]);
  dfss(a,p,-1,99999999);
  if(minv==-1)
   cout<<-1<<endl;
  else
   cout<<mini<<endl;
 // path.push_back(a);
 // q.remove(dis[a]);	
 }
 return 0;
}
