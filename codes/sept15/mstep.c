#include<stdio.h>
#include<math.h>
int distance(int x1,int y1,int x2,int y2)
{
 return abs(x1-x2)+abs(y1-y2);
}
int main()
{	
	int x,y,z;
	int n,t,i,j;	
	int a[250000][2],ai,sum;
	for(scanf("%d",&t);t;t--)
	{
		sum=ai=0;
		for(scanf("%d",&n),i=0;i<n;i++)
		{
			for(j=0;j<n;j++)
			{
				scanf("%d",&z);
				a[z-1][0]=i,a[z-1][1]=j;
			}
		}
		for(i=1;i<n*n;i++)
		{
			sum+=distance(a[i][0],a[i][1],a[i-1][0],a[i-1][1]);
		}
		printf("%d\n",sum);
	}
	return 0;
}
