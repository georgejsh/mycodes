#include<stdio.h>
int main(){
	int t;
	int n;
	int a[10001],b[10001];
	int i,j,k,l;
	for(scanf("%d",&t);t;t--){
		for(scanf("%d",&n),i=0;i<n;i++){
			scanf("%d",&a[i]);
		}
		for(i=n-1;i>0;i--)
			a[i]-=a[i-1];
		for(i=0;i<n;i++){
			scanf("%d",&b[i]);
		}
		for(i=0,j=0;i<n;i++){
			if(a[i]>=b[i])
				j++;
		}
		printf("%d\n",j);
	}
	return 0;
}