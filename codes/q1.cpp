#include<iostream>
#include<fstream>
#include<time.h>
#include<math.h>
#include<vector>
using namespace std;

void pivot(vector<vector<double> >, vector<vector<double> >, int, int);
void gauss(vector<vector<double> > a, vector<vector<double> > b, int n)
{
	int i,j,k;
	double m;
	for(i=0;i<n-1;i++)
	{
		pivot(a,b,n,i);
		for(j=i+1;j<n;j++)
		{
			m=a[j][i]/a[i][i];
			a[j][i]=0;
			for(k=i+1;k<n;k++)
			{
				a[j][k]-= m*a[i][k];
			}
			for(k=0;k<n;k++)
			{
				b[j][k]-= m*b[i][k];
			}
		}
	}
}

void pivot(vector<vector<double> > a, vector<vector<double> > b, int n, int i)
{
	int j,k,f=0;
	double max;
	max= a[i][i]; 
	for(k=i;k<n-1;k++)
	{
		if(fabs(max)<fabs(a[k+1][i]))
		{
			max=a[k+1][i];
			j=k+1;
			f=1;
		}
	}

	if(f==1)
	{
		for(k=0;k<n;k++)
		{
			max=a[i][k];
			a[i][k]=a[j][k];
			a[j][k]=max;
			max=b[i][k];
			b[i][k]=b[j][k];
			b[j][k]=max;
		}
	}
}

void backsub(vector<vector<double> > a, vector<double> b, vector<double> x, int n)
{
	int i,j,k;
	double sum;
	for(i=n-1;i>=0;i--)
	{
		sum=b[i];
		for(j=n-1;j>i;j--)
		{
			sum-= x[j]*a[i][j];
		}
		x[i]=sum/a[i][i];
	}
}

void inverse(vector<vector<double> > a1, vector<vector<double> > ainv, int n)
{
	vector<double> line(50,0);
	vector<vector<double> > a=a1;
	vector<vector<double> > id(51,line);
	for(int i=0;i<n;i++)
	{
		id[i][i]=1;
	}
	gauss(a,id,n);
	for(int i=0;i<n;i++)
	{
		vector<double> b(50,0),x(50,0);
		for(int j=0;j<n;j++)
		{
			b[j]=id[j][i];
		}
		backsub(a,b,x,n);
		for(int j=0;j<n;j++)
		{
			ainv[j][i]=x[j];
		}
	}
}


void multiply(vector<vector<double> > a, vector<vector<double> > b, vector<vector<double> > c, int n)
{
	int i,j,k;
	double sum;
	for(i=0;i<n;i++)
	{
		for(j=0;j<n;j++)
		{
			sum=0.0;
			for(k=0;k<n;k++)
			{
				sum+=a[i][k]*b[k][j];
			}
			c[i][j]=sum;
		}
	}
}

void subtract(vector<vector<double> > a, vector<vector<double> > b, vector<vector<double> > c, int n)
{
	int i,j;
	for(i=0;i<n;i++)
	{
		for(j=0;j<n;j++)
		{
			c[i][j]=a[i][j]-b[i][j];
		}
	}
}

void ludecomp(vector<vector<double> > l, vector<vector<double> > u, vector<vector<double> > ab, vector<vector<double> > d, int n)
{
	int i;
	vector<double> line(50,0);
	vector<double> line2(50,0);
	vector<vector<double> > linv(50,line),m(50,line2);
	inverse(l,linv,n);
	multiply(linv,ab,u,n);
	multiply(ab,u,m,n);
	subtract(d,m,l,n);
}
		
int main()
{
int i,j,k;
clock_t t;
ofstream f;
f.open("q1.txt",ios::out|ios::trunc);
int n[3]={10,20,30};
int nb=5;
vector<double> line(51,0),line2(51,0);
vector<vector<double> > ab(50,line) ,d(50,line2);
for(int i=0;i<30;i++)
{
	ab[i][i]=1;
}

for(int i=0;i<30;i++)
{
	d[i][i]=4;
}
for(int i=0;i<29;i++)
{
	d[i][i+1]=1;
	d[i+1][i]=1;
}
for(i=0;i<3;i++)
{
	vector<double> line3(51,0),line4(51,0);
	vector<vector<double> > l(50,line3),u(50,line4);
	t=clock();
	ludecomp(l,u,ab,d,n[i]);
	t=clock()-t;
	f<<n[i]<<"\t"<<(double(t))/CLOCKS_PER_SEC<<"\n";
	cout<<n[i]<<"\t"<<(double(t))/CLOCKS_PER_SEC<<"\t"<<t<<"\n";
}
f.close();
return 0;
}
