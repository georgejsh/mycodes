package testcases;

import java.math.BigInteger;
import java.util.Random;

public class Medium2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i;
		BigInteger three = new BigInteger("3");
		String s="((((a+b).((a+b).((a+b).(a+b))))*)+(((a+b).((a+b).((a+b).((a+b).((a+b).(a+b))))))*))";
		System.out.println(s);
		System.out.println(1000);
		for(i=0;i<1000;i++){
			BigInteger r;
			 Random rnd = new Random();
			 while(true){
				 int bits=(Math.abs(rnd.nextInt())%10000+5000)%10000;
				 //System.out.println(bits);
			    r = new BigInteger(bits, rnd);
			    if(rnd.nextBoolean()){
			    	if(r.bitLength()%4==0 || r.bitLength()%6==0)
			    		break;
			    }
			    else{
			    	if(!(r.bitLength()%4==0 || r.bitLength()%6==0))
				    		break;
			    }
			 }
			    String o=r.toString(2);
			    o=o.replace("0", "a");
			    o=o.replace("1", "b"); 
			    System.out.println(o);
		}
	}

}
