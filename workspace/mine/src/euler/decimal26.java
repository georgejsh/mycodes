package euler;

import java.util.HashMap;

public class decimal26 {
	public static int findreclength(int n) {
		int i;
		int j=1,k;
		HashMap<Integer, Integer> test=new HashMap<>();
		for(i=1;;i++){
			test.put(j, 1);
			while(j<n)
				j*=10;
			j=j%n;
			if(test.get(j)!=null || j==0)
				break;
		}
		return i;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer loopi,res=0,temp,resi = 0;
		for(loopi=2;loopi<=1000;loopi++) {
		//	System.out.println(loopi);
			temp=findreclength(loopi);
			if(temp>res){
				res=temp;
				resi=loopi;
			}
		//	System.out.println(temp);
		}
		System.out.println(resi);
	}

}
