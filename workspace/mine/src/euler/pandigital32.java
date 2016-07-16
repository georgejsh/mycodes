package euler;

import java.util.HashSet;

public class pandigital32 {
	public static boolean check(int i,int j,long k){
		int a[]= new int[10];
		while(i>0){
			a[i%10]++;
			i/=10;
		}
		while(j>0){
			a[j%10]++;
			j/=10;
		}
		while(k>0){
			a[ (int) (k%10)]++;
			k/=10;
		}
		if(a[0]>0)
			return false;
		for(i=1;i<10;i++)
			if(a[i]!=1)
				return false;
		return true;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i,j;
		long sum=0,r;
		HashSet<Long> st= new HashSet<>();
		for(i=0;i<10000;i++)
			for(j=0;j<10000;j++){
				r=i*1L*j;
				if(r>999999999)
					break;
				if(check(i,j,r))
					if(!st.contains(r)){
						System.out.println(r+"a");sum+=r;
						st.add(r);
					}
				//break;
			}
		System.out.println(sum);
	}

}
