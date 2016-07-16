#include<stdio.h>
#include<math.h>
int main()
{
	int t;
	double m,p,res;
	for(scanf("%d",&t);t;t--)
	{
		scanf("%lf%lf",&m,&p);
		//if(p<0.5)
		//	res=.5*1000000000;
		//else
			res=(1-pow(-p,m))/(1+p)*1000000000;
		printf("%lf %lf\n",res,1000000000-res);
	}
}
