package euler;

import java.math.BigInteger;

public class power16 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BigInteger res= new BigInteger("1");
		String r;
		Integer loopi,sum=0;
		for(loopi=1;loopi<=1000;loopi++){
			res=res.multiply(new BigInteger("2"));
		}
		r=res.toString();
		for(loopi=0;loopi<r.length();loopi++){
			sum+=r.charAt(loopi)-48;
		}
		System.out.println(sum);
	}

}
