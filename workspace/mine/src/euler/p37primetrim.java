package euler;

import java.util.ArrayList;

public class p37primetrim {
	static ArrayList<Integer> prime= new ArrayList<Integer>();
	public static Integer size=1000000;
	public static boolean check(int i){
		int j=i;
		int len=new Integer(i).toString().length();
		do{
			//j=j*10+i%10;
			i=i/10;
			//l=l*;
			//i=i+l;
			if(prime.get(i)!=0)
				return false;
		}while(i>0);
		do{
			j=j%(int)Math.pow(10,len-1);
			if(prime.get(j)!=0)
				return false;
			len--;
			//l=l*(int)Math.pow(10,len-1);
			//i=i+l;
		}while(j>0);
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
		prime.set(1, 1);
		for(loopi=10;loopi<=size;loopi++){
			if(prime.get(loopi)!=0)
				continue;
			if(check(loopi)){
				//System.out.println(loopi);
				loopj+=loopi;
			}
		}
		System.out.println(loopj);
	}

}
