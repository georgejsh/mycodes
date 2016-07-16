package euler;

import java.math.BigInteger;

public class fib25 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BigInteger prev= new BigInteger("0"),curr= new BigInteger("1");
		Integer loopi,loopj;
		for(loopi=0;;loopi++){
			if(curr.toString().length()>=1000)
				break;
			curr=prev.add(curr);
			prev=curr.subtract(prev);
		}
		System.out.println(loopi);
	}

}
