#include<iostream>
#include<fstream>
#include<time.h>
#include<math.h>
using namespace std;

void assign(double** a, double** b, int n)
{
	int i,j;
	for(i=0;i<n;i++)
	{
		for(j=0;j<n;j++)
		{
			a[i][j]=b[i][j];
		}
	}
}

void pivot(double**, double**, int, int);
void gauss(double** a, double** b, int n)
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

void pivot(double** a, double** b, int n, int i)
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

void backsub(double** a, double* b, double* x, int n)
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

void inverse(double** a1, double** ainv, int n)
{
	double** a = new double*[n];
	for(int i=0;i<n;i++)
	{
		a[i]=new double[n];
	}
	assign(a,a1,n);
	double** id = new double*[n];
	for(int i=0;i<n;i++)
	{
		id[i]= new double[n];
		for(int j=0;j<n;j++)
		{
			id[i][j]=0;
		}
		id[i][i]=1;
	}
	gauss(a,id,n);
	for(int i=0;i<n;i++)
	{
		double* b = new double[n];
		double* x = new double[n];
		for(int j=0;j<n;j++)
		{
			b[j]=id[j][i];
		}
		backsub(a,b,x,n);
		for(int j=0;j<n;j++)
		{
			ainv[j][i]=x[j];
		}
		delete b,x;
	}
	delete id,a;
}

void defab(double** a, int n)
{
	for(int i=0;i<n;i++)
	{
		for(int j=0;j<n;j++)
		{
			a[i][j]=0;
		}
		a[i][i]=1;
	}
}

void defd(double** a, int n)
{
	for(int i=0;i<n;i++)
	{
		for(int j=0;j<n;j++)
		{
			a[i][j]=0;
		}
		a[i][i]=4;
	}
	for(int i=0;i<n-1;i++)
	{
		a[i][i+1]=1;
		a[i+1][i]=1;
	}
}

void multiply(double** a, double** b, double** c, int n)
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

void subtract(double** a, double** b, double** c, int n)
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

void ludecomp(double*** l, double*** u, double** ab, double** d, int n, int nb)
{
	int i;
	double** linv = new double*[nb];
	double** m = new double*[nb];
	for(i=0;i<nb;i++)
	{
		linv[i]= new double[nb];
		m[i]= new double[nb];
	}
	assign(l[0],d,nb);
	for(i=0;i<n-1;i++)
	{
		inverse(l[i],linv,nb);
		multiply(linv,ab,u[i],nb);
		multiply(ab,u[i],m,nb);
		subtract(d,m,l[i+1],nb);
	}
	delete linv,m;
}

void pivotl(double**, double**, double*, int, int);
void gaussl(double*** a, double*** b, double** c, int n, int nf)
{
	int i,j,k,p;
	double m;
	for(p=nf-1;p>0;p--)
	{
	for(i=n-1;i>0;i--)
	{
		pivotl(a[p],b[p-1],c[p],n,i);
		for(j=i-1;j>=0;j--)
		{
			m=a[p][j][i]/a[p][i][i];
			a[p][j][i]=0;
			for(k=i-1;k>=0;k--)
				a[p][j][k]-= m*a[p][i][k];
			for(k=n-1;k>=0;k--)
				b[p-1][j][k]-= m*b[p-1][i][k];
			c[p][j]-=m*c[p][i];
		}
	}
	}
	for(i=n-1;i>0;i--)
	{
		if(a[p][i-1][i]>a[p][i][i])
		{
			for(j=0;j<n;j++)
			{
				m=a[p][i][j];
				a[p][i][j]=a[p][i-1][j];
				a[p][i-1][j]=m;
			}
		}
		m=a[p][i-1][i]/a[p][i][i];
		a[p][i-1][i]=0;
		a[p][i-1][i-1]-= m*a[p][i][i-1];
		c[p][i-1]-=m*c[p][i];			
	}
}

void pivotl(double** a, double** b, double* c, int n, int i)
{
	int j,k,f=0;
	double max;
	max= a[i][i]; 
	for(k=i-1;k>=0;k--)
	{
		if(fabs(max)<fabs(a[k][i]))
		{
			max=a[k][i];
			j=k;
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
		max=c[i];
		c[i]=c[j];
		c[j]=max;
	}
}

void forwardsubl(double*** a, double*** b, double** y, double** x, int n, int nf)
{
	int i,j,p;
	double sum;
	x[0][0]=y[0][0]/a[0][0][0];
	for(i=1;i<n;i++)
	{
		sum=y[0][i]-x[0][i-1]*a[0][i][i-1];
		x[0][i]=sum/a[0][i][i];
	}
	
	for(p=1;p<nf;p++)
	{
	for(i=0;i<n;i++)
	{
		sum=y[p][i];
		for(j=0;j<i;j++)
		{
			sum-= x[p][j]*a[p][i][j]+x[p-1][n-1-j]*b[p-1][i][n-1-j];
		}
		sum-=x[p-1][i]*b[p-1][i][i];
		x[p][i]=sum/a[p][i][i];
	}
	}
}

void backsubu(double*** b, double** y, double** x, int n, int nf)
{
	int i,j,p;
	double sum;
	for(i=0;i<n;i++)
		x[nf-1][i]=y[nf-1][i];
	for(p=nf-2;p>=0;p--)
	{
	for(i=n-1;i>=0;i--)
	{
		sum=y[p][i];
		for(j=n-1;j>=0;j--)
		{
			sum-= x[p+1][j]*b[p][i][j];
		}
		x[p][i]=sum;
	}
	}
}
		
int main()
{
int i,j,k;
double soln[3][5];
clock_t t;
ofstream f;
f.open("q2.txt",ios::out|ios::trunc);
int n[3]={10,20,30};
int nb=5;
double** ab = new double*[nb];
double** d = new double*[nb];
for(i=0;i<nb;i++)
{
	ab[i]= new double[nb];
	d[i]= new double [nb];
}
defab(ab,nb);
defd(d,nb);
for(i=0;i<3;i++)
{
	double*** l = new double**[n[i]];
	double*** u = new double**[n[i]-1];
	double*** b = new double**[n[i]-1];
	double** f = new double*[n[i]];
	double** y = new double*[n[i]];
	double** x = new double*[n[i]];
	for(j=0;j<n[i];j++)
	{
		l[j]= new double*[nb];
		for(k=0;k<nb;k++)
			l[j][k]= new double[nb];
	}
	for(j=0;j<(n[i]-1);j++)
	{
		u[j]= new double*[nb];
		b[j]= new double*[nb];
		for(k=0;k<nb;k++)
		{
			u[j][k]= new double[nb];
			b[j][k]= new double[nb];
		}
	}
	for(j=0;j<n[i]-1;j++)
		defab(b[j],nb);
	for(j=0;j<n[i];j++)
	{
		f[j]= new double[nb];
		y[j]= new double[nb];
		x[j]= new double[nb];
	}
	for(j=0;j<n[i];j++)
	{
		for(k=1;k<nb-1;k++)
			f[j][k]=1/double(nb);
		f[j][0]=1;
		f[j][nb-1]=2;
	}
	
	ludecomp(l,u,ab,d,n[i],nb);
	gaussl(l,b,f,nb,n[i]);
	forwardsubl(l,b,f,y,nb,n[i]);
	backsubu(u,y,x,nb,n[i]);

	for(j=0;j<nb;j++)
	{
		soln[i][j]=x[n[i]/2-1][j];
	}
	
	delete l,u,b,f,x,y;
}
for(i=0;i<5;i++)
{
	f<<i+1<<"\t";
	for(j=0;j<3;j++)
		f<<soln[j][i]<<"\t";
	f<<"\n";
}
f.close();
return 0;
}
