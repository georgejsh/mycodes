package testcases;

import java.math.BigInteger;
import java.util.Random;

public class Medium {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i;
		BigInteger three = new BigInteger("3");
		String s="((((a+b)*).(a.(a.a))).((a+b)*))";
		System.out.println(s);
		System.out.println(500);
		for(i=0;i<500;i++){
			BigInteger r;
			 Random rnd = new Random();
			 Boolean ch=rnd.nextBoolean();
			while(true){
				 int bits=(Math.abs(rnd.nextInt())%1000+100)%1000;
				 //System.out.println(bits);
			    r = new BigInteger(bits, rnd);
			    if(ch){
			    	if(r.toString(2).contains("000"))
			    		break;
			    }
			    else{
			    	if(!r.toString(2).contains("000"))
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
