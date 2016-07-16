#include<stdio.h>
int distance(int x1,int y1,int x2,int y2)
{
 return abs(x1-x2)+abs(y1-y2);
}
int checkp(int x,int y,int pa[4][3],int n)
{
	int i;
	for(i=0;i<n;i++)
	{
		switch(pa[i][2])
		{
			case 0:if(x<=pa[i][0]	&& y>=pa[i][1])
				return 1;	//NW
				break;
			case 1:if(x>=pa[i][0]	&& y>=pa[i][1])
				return 1;	//NE	
				break;
			case 2:if(x<=pa[i][0]	&& y<=pa[i][1])
				return 1;	//SW	
				break;
			case 3:if(x>=pa[i][0]	&& y<=pa[i][1])
				return 1;	//SE
				break;
		}
	}
	return 0;
}
int check(int x,int y,int pa[4][3],int n)
{
	int i;
	for(i=0;i<n;i++)
		if(pa[i][0]==x && pa[i][1]==y)
			return 1;
	return 0;
}
int p[500001][2];
int main()
{
	int i,j,k,t,n,res[4][2],count;
	int temp[4][3],tempi;
	int mmxy[4],finalres[4][2],fresi=0;
	int minxi,maxxi,minyi,maxyi,tempx,tempy;
	for(scanf("%d",&t);t;t--)
	{
		mmxy[0]=mmxy[1]=mmxy[2]=mmxy[3]=0;
		for(scanf("%d",&n),i=0;i<n;i++)
		{
			scanf("%d%d",&p[i][0],&p[i][1]);
		}
		for(i=0;i<n;i++)
		{
			if(p[i][0]<p[mmxy[0]][0] || (p[i][0]==p[mmxy[0]][0] && p[i][1]<=p[mmxy[0]][1]) )
				mmxy[0]=i;
			if(p[i][0]>p[mmxy[1]][0] || (p[i][0]==p[mmxy[1]][0] && p[i][1]>=p[mmxy[1]][1]) )
				mmxy[1]=i;
			if(p[i][0]<p[mmxy[2]][0] || (p[i][0]==p[mmxy[2]][0] && p[i][1]>=p[mmxy[2]][1]) )
				mmxy[2]=i;
			if(p[i][0]>p[mmxy[3]][0] || (p[i][0]==p[mmxy[3]][0] && p[i][1]<=p[mmxy[3]][1]) )
				mmxy[3]=i;
						
			//if(p[i][1]<p[mmxy[2]][1])
			//	mmxy[2]=i;
			//if(p[i][1]>p[mmxy[3]][1])
			//	mmxy[3]=i;
		}
		/*for(i=0;i<4;i++)
			printf("%d ",mmxy[i]);*/
		fresi=0;
		if(distance(p[mmxy[1]][0],p[mmxy[1]][1],p[mmxy[0]][0],p[mmxy[0]][1])>distance(p[mmxy[2]][0],p[mmxy[2]][1],p[mmxy[3]][0],p[mmxy[3]][1]))
		{
			mmxy[0]=mmxy[2];
			mmxy[1]=mmxy[3];
		}		
		for(count=1,k=0;count<(1<<7);count++)
		{
			tempi=0;

			for(i=1;i<=2;i++)
			{
				if((count&(1<<(i*3-1)))==0)
					continue;
				if(check(p[mmxy[i-1]][0],p[mmxy[i-1]][1],temp,tempi))
					continue;
				//printf("b");
				res[tempi][0]=mmxy[i-1];
				temp[tempi][0]=p[mmxy[i-1]][0];
				temp[tempi][1]=p[mmxy[i-1]][1];
				temp[tempi][2]=(count&(3<<(i*3-3)))>>(i*3-3);
				res[tempi][1]=temp[tempi][2];
				tempi++;
			}
			if(tempi==0 || tempi>2)
			 continue;
			/*printf("z%dz",tempi);
			for(k=0;k<tempi;k++)
			{
				printf("%d %d %d",temp[k][0],temp[k][1],temp[k][2]);
				if(temp[k][2]==0)
					printf("NW ");
				if(temp[k][2]==1)
					printf("NE ");
				if(temp[k][2]==2)
					printf("SW ");
				if(temp[k][2]==3)
					printf("SE ");
			}
			printf("\n");*/
			for(i=0;i<n;i++)
			{
				if(!checkp(p[i][0],p[i][1],temp,tempi))
					break;
			}
			if(i==n)
			{
				//printf("OK");
				if(fresi==0 || fresi>tempi)
				{
					fresi=tempi;
					for(k=0;k<tempi;k++)
						finalres[k][0]=res[k][0],finalres[k][1]=res[k][1];
				}
				k=1;
				//break;
			}			
		}
		
			printf("%d\n",fresi);
			for(k=0;k<fresi;k++)
			{
				printf("%d ",finalres[k][0]+1);
				if(finalres[k][1]==0)
					printf("NW\n");
				if(finalres[k][1]==1)
					printf("NE\n");
				if(finalres[k][1]==2)
					printf("SW\n");
				if(finalres[k][1]==3)
					printf("SE\n");
			}			
		

	}
	return 0;
}
