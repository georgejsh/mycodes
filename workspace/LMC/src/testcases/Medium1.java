package testcases;

import java.math.BigInteger;
import java.util.Random;

public class Medium1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i;
		BigInteger three = new BigInteger("3");
		for(i=0;i<500;i++){
			BigInteger r;
			 Random rnd = new Random();
			 while(true){
				 int bits=(Math.abs(rnd.nextInt())%1000+100)%1000;
				 //System.out.println(bits);
			    r = new BigInteger(bits, rnd);
			    if(rnd.nextBoolean()){
			    	if(r.mod(three).intValue()==0)
			    		break;
			    }
			    else{
			    	if(r.mod(three).intValue()!=0)
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
