package euler;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;

public class p29distprimes {
	public static int countdiv(int n){
		int i,j=0;
		for(i=1;i<n;i++)
			if(n%i==0)
				j+=i;
		return j;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashSet<BigInteger> test= new HashSet<>();
		for(int i=2;i<=100;i++)
			for(int j=2;j<=100;j++){
				BigInteger a= new  BigInteger(Integer.toString(i));
				//BigInteger b= new  BigInteger(Integer.toString(j));
				a=a.pow(j);
				test.add(a);
			}
		System.out.println(test.size());
	}

}
