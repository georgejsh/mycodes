package euler;

import java.util.ArrayList;

public class p35 {
	static ArrayList<Integer> prime= new ArrayList<Integer>();
	public static Integer size=1000000;
	public static boolean check(int i){
		int j=i;
		int len=new Integer(i).toString().length();
		do{
			int l=i%10;
			i=i/10;
			l=l*(int)Math.pow(10,len-1);
			i=i+l;
			if(prime.get(i)!=0)
				return false;
		}while(j!=i);
		return true;
	}
	
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
		for(loopi=2;loopi<=size;loopi++){
			if(prime.get(loopi)!=0)
				continue;
			if(check(loopi))
				loopj++;
		}
		System.out.println(loopj);
	}

}
