package euler;

import java.math.BigInteger;

public class selfpower48 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer loopi;
		String t;
		BigInteger res=BigInteger.ZERO;
		for(loopi=1;loopi<=1000;loopi++) {
			res=res.add((new BigInteger(loopi+"")).pow(loopi));
		}
		t=res.toString();
		for(loopi=t.length()-11;loopi<t.length();loopi++) {
			System.out.print(t.charAt(loopi));
		}
	}

}
