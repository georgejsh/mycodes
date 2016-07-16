package euler;

import java.util.HashMap;
import java.util.Vector;

public class collatz14 {
	static int count;
	public static void coll(int n) {
		count++;
		if(n==1) return;
		if(n%2==0)  coll(n/2);
		else coll(3*n+1);
	}
	public static void main(String args[]) {
	 int loopi,res=0,ans = 0;
	 Long n;
	 HashMap<Long,Integer> path =new HashMap<>();
	 for(loopi=1;loopi<100000000;loopi++){
		// if(loopi==113383 || loopi==134379 ||loopi==138367)
		//	 continue;
		 count=0;
		 path.clear();
		 //coll(loopi);
		 n=(long) loopi;
		 while(true) {
			 count++;
	//		 path.put(n,1);
			 if(n==1)
				 break;
			 if(n%2==0)
				 n=n/2;
			 else
				 n=3*n+1;
	//		 if(path.get(n)!=null)
			 {
	//			 System.out.println("inf"+loopi);
	//			 break;
			 }
		 }
		 if(count>res)
		 {
			 res=count;ans=loopi;
		 }
		 //System.out.println(loopi);
	 }
	 System.out.println(ans);
 }
}
