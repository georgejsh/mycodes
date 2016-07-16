package euler;

import java.util.ArrayList;

public class p48primeap {
	static ArrayList<Integer> prime= new ArrayList<Integer>();
	public static Integer size=100000;
	public static boolean check(int i,int j,int k){
		if(prime.get(i)!=0 || prime.get(j)!=0 || prime.get(k)!=0 )
			return false;
		int a[]=new int [10];
		int b[]=new int [10];
		a[i%10]++;i=i/10;
		a[i%10]++;i=i/10;
		a[i%10]++;i=i/10;
		a[i%10]++;i=i/10;
		b[j%10]++;j=j/10;
		b[j%10]++;j=j/10;
		b[j%10]++;j=j/10;
		b[j%10]++;j=j/10;
		for(int ii=0;ii<10;ii++)
			if(a[ii]!=b[ii])
				return false;
		for(int ii=0;ii<10;ii++)
			b[ii]=0;
		b[k%10]++;k=k/10;
		b[k%10]++;k=k/10;
		b[k%10]++;k=k/10;
		b[k%10]++;k=k/10;
		for(int ii=0;ii<10;ii++)
			if(a[ii]!=b[ii])
				return false;
		return true;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i,j,k,l;
		Integer loopi,loopj;
		for(loopi=0;loopi<=size;loopi++){
			prime.add(0);
		}
		for(loopi=2;loopi<=size;loopi++) {
			if(prime.get(loopi)!=0)
				continue;
			//System.out.println(loopi);
			for(loopj=2;loopj*loopi<=size;loopj++){
				if(prime.get(loopj*loopi)!=0)
					continue;	
				prime.set(loopj*loopi,1);
			}
		}
		loopj=0;
		prime.set(1, 1);
		for(i=1000;i<10000;i++){
			for(j=1;j<9999;j++){
				if(i+j+j>=10000)
					continue;
				
					if(check(i,i+j,i+j+j))
					System.out.println(i+""+(i+j)+""+(i+j+j));
			}
		}
	}

}
