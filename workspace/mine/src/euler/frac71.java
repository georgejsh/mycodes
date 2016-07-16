package euler;

import java.util.ArrayList;
import java.util.Collections;

public class frac71 {
	public static int gcd(int a,int b) {
		
		if(b>a)
			return gcd(b,a);
		if(a%b==0)
			return b;
		return gcd(b,a%b);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer loopi,loopj,resi=7,resj=2;
		ArrayList<Double> val = new ArrayList<>();
		double lim=3.0/7;
		System.out.println(lim);
		for(loopi=8;loopi<=1000000;loopi++) {
			loopj=(int) Math.floor(3.0/7*loopi);
			//for(loopj=1;loopj<loopi;loopj++)
			{
				if(loopj*1.0/loopi<lim){
					if(gcd(loopj,loopi)==1){
						if(loopj*1.0/loopi>resj*1.0/resi) {
							resi=loopi;
							resj=loopj;
						}
						
					}
						//val.add(loopj*1.0/loopi);
				}else {
				//		break;
				}
			}
		}
		//Collections.sort(val);
		System.out.println(resi+" "+resj+" "+resj*1.0/resi);
	}

}