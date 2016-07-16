#include<iostream>
#include<vector>
#include<map>
#include<algorithm>
#include<queue>
#include<iterator>
using namespace std;
template<class T,
    class Container = std::vector<T>,
    class Compare = std::greater<typename Container::value_type>
>
class custom_priority_queue : public std::priority_queue<T, std::vector<T>,std::greater<typename Container::value_type> >
{
  public:

      bool remove(const T& value) {
        typename std::vector<T>::iterator it= std::find(this->c.begin(), this->c.end(), value);
        if (it != this->c.end()) {
            this->c.erase(it);
            std::make_heap(this->c.begin(), this->c.end(), this->comp);
            return true;
       }
       else
	 {
        return false;
       }
 }
};
map<int,int> dis;
map<int,vector<int> > adj;
//vector<int> path;
map<int,int> searchv;
custom_priority_queue<int,vector<int>, std::greater<int> > q;
vector<int> f;
void dfsf(int r){
 int i;
 for(i=0;i<adj[r].size();i++){
   if(dis.find(adj[r][i])==dis.end()){
	dis[adj[r][i]]=dis[r]+1;
  	dfsf(adj[r][i]);
  }	
 }
}
int minv,mini;
int dfss(int r,int p,int par){
 int i;
//cout<<"test"<<r<<" "<<q.top()<<endl;
 if(f[r]==p && minv<=q.top()){
   if((minv==q.top() && mini>r) || minv<q.top()){
    mini=r;
    minv=q.top();
   }
  }
 for(i=0;i<adj[r].size();i++){
	if(adj[r][i]==par)
		continue;
        q.push(dis[adj[r][i]]);
//	path.push_back(i);
  	dfss(adj[r][i],p,r);
//	path.pop_back();
	q.remove(dis[adj[r][i]]);
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
   adj[a].push_back(b);  
   adj[b].push_back(a);  
 }
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
  q.push(dis[a]);
  dfss(a,p,-1);
  if(minv==-1)
   cout<<-1<<endl;
  else
   cout<<mini<<endl;
 // path.push_back(a);
  q.remove(dis[a]);	
 }
 return 0;
}
