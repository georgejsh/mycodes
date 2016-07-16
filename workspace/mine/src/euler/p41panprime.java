package euler;

import java.util.ArrayList;

public class p41panprime {
	public static boolean check(String s){
		int a[]= new int[10];
		
		int i;
		for(i=0;i<s.length();i++)
			a[s.charAt(i)-48]++;
		if(a[0]>0)
			return false;
		for(i=1;i<8;i++)
			if(a[i]!=1)
				return false;
		return true;
	}
	static ArrayList<Integer> prime= new ArrayList<Integer>();
	public static Integer size=10000000;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
		for(loopi=1;loopi<=size;loopi++){
			if(prime.get(loopi)!=0)
				continue;
			if(check(Integer.toString(loopi)))
				System.out.println(loopi);
		}
	}

}
