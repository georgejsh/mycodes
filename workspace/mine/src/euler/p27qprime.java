package euler;

import java.util.ArrayList;

public class p27qprime {
	static ArrayList<Integer> prime= new ArrayList<Integer>();
	public static Integer size=100000;
	public static int findconpr(int a,int b){
		int i,j=0;
		for(i=0;i<100;i++){
			if(i*i+a*i+b<0)
				break;
			if(prime.get(i*i+a*i+b)!=0)
				break;
			j++;
		}
		return j;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer loopi,loopj;
		Long sum=0L;
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
		prime.set(0, 1);
		prime.set(1, 1);
		/*
		for(loopi=0;loopi<=size;loopi++){
			if(prime.get(loopi)!=0)
				continue;
			System.out.print(loopi+", ");
		}
		*/
		int i,j,k,l,m=0,a=1,b=1;
		for(i=-999;i<1000;i++)
			for(j=-999;j<1000;j++){
				k=findconpr(i, j);
				if(k>m){
					m=k;
					a=i;
					b=j;
				}
					
			}
		System.out.println(a+" "+b+" "+m+" "+a*b);
	}

}
