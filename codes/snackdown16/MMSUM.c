#include<stdio.h>
#include<math.h>
int n;
long long int a[100001];
long long int b[100001];
long long int c[100001];
long long int max(long long int a,long long int b){
	return a>b?a:b;
}
/*long long int mmsum(int i,int j,long long int sum){
	long long int res=sum,fres=sum;
	if(i>1){
		if(a[i-1]<0)
			fres=max(res,res+b[i-2]);
	}
	if(j+2<n){
		if(a[j+1]<0)
			fres=max(fres,res+b[j+2]);
	}
	return fres;
}*/
int main(){
	long long int maxhere,maxh;
	long long int maxsum;
	int i;
	long long int j,k;
	int t,starti;
	for(scanf("%d",&t);t;t--){
		scanf("%d",&n);
		for(i=0;i<n;i++){
			scanf("%lld",&a[i]);
			b[i]=c[i]=0;
		}
		if(n==1){
			printf("%lld\n",a[0]);
			continue;
		}
		maxhere=a[0];
		b[0]=maxhere;
		for(i=1;i<n;i++){
			maxhere=max(a[i],a[i]+maxhere);
			b[i]=maxhere;
		}
		maxhere=a[n-1];
		c[n-1]=maxhere;
		for(i=n-2;i>=0;i--){
			maxhere=max(a[i],a[i]+maxhere);
			c[i]=maxhere;
		}
		maxsum=a[0];
		for(i=0;i<n;i++){	
			if(a[i]<0){
				j=0,k=0;
				if(i>0)
					j=b[i-1];
				if(i<n-1)
					k=c[i+1];
				maxsum=max(maxsum,j+k);
			}
			
			j=b[i];k=c[i];
			maxsum=max(k,max(maxsum,j));
			
		}
/*		for(i=0;i<n;){
			j=i;
			k=0;
			while(a[j]>0){
				k+=a[j];
				j++;
			}
			b[i]=k;
			if(i!=j){
				b[j-1]=k;
				i=j;
			}
			else
				i++;
		}
		starti=0;
		maxhere=a[0];
		//printf("%lld ",maxhere);
		maxh=mmsum(0,0,maxhere);
		//printf("%lld\n",maxh);
		maxsum=maxh;
		for(i=1;i<n;i++){
			maxhere=a[i]+maxhere;
			if(a[i]>maxhere){
				maxhere=a[i];
				starti=i;
			}		
		//	printf("%lld ",maxhere);
			maxh=mmsum(starti,i,maxhere);
		//	printf("%lld ",maxh);
			if(maxsum<maxh){
				maxsum=maxh;
			}
	//		printf("%lld\n",maxsum);
		}
		starti=n-1;
		maxhere=a[n-1];
	//	printf("%lld ",maxhere);
		maxh=mmsum(n-1,n-1,maxhere);
	//	printf("%lld\n",maxh);
		maxsum=maxh;
		for(i=n-2;i>=0;i--){
			maxhere=a[i]+maxhere;
			if(a[i]>maxhere){
				maxhere=a[i];
				starti=i;
			}
	//		printf("%lld ",maxhere);
			maxh=mmsum(i,starti,maxhere);
	//		printf("%lld ",maxh);
			if(maxsum<maxh){
				maxsum=maxh;
			}
	//		printf("%lld\n",maxsum);
		}
	//	re=maxsum;
*/		printf("%lld\n",maxsum);
	}
	return 0;
}
