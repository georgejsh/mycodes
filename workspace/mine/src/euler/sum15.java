package euler;

import java.math.BigInteger;
import java.util.Scanner;

public class sum15 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BigInteger num= new BigInteger("0");
		Scanner input= new Scanner(System.in);
		Integer loopi;
		String inpnum;
		for(loopi=0;loopi<100;loopi++) {
			inpnum=input.next();
			num=num.add(new BigInteger(inpnum));
			//System.out.println(num.toString());
		}
		//System.out.println(num.toString());
		for(loopi=0;loopi<10;loopi++){
			System.out.print(num.toString().charAt(loopi));
		}
	}

}
