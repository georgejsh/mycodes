package testcases;

import java.math.BigInteger;
import java.util.Random;

public class Medium3 {
	public static boolean check(String s){
			int i,j=0;
			for(i=0;i<s.length();i++)
				if(s.charAt(i)=='0')
					j++;
			if(j<2)
				return false;
			return !s.contains("00");
					
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i;
		BigInteger three = new BigInteger("3");
		String s="((((((b*).a).b).(b*)).(a+(a.(b.(b*)))))*)";
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
			    	if(check(r.toString(2)))
			    		break;
			    }
			    else{
			    	if(!check(r.toString(2)))
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
