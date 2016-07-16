package circlecity;


import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;
public class Solution {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Vector<Long> xcor,ycor;
//		xcor=new Vector<>();
//		ycor= new Vector<>();
		Long n,loopj,loopi=0L,a,b,count,k,l;
		Scanner input = new Scanner(System.in);
		for(n=input.nextLong();loopi<n;loopi++) {
			a=input.nextLong();
			b=input.nextLong();
			count=0L;
			loopj= (long) Math.sqrt(a);
			if(loopj*loopj==a){
				count+=4;
				loopj--;
			}
				
			for(;loopj*loopj*2>a;loopj--) {
				k=a-loopj*loopj;
				l=(long) Math.sqrt(k);
				if(l*l==k)
					count+=8;
			}
			if(loopj*loopj*2==a)
				count+=4;
			if(count<=b)
				System.out.println("possible");
			else
				System.out.println("impossible");
	//		xcor.add(a);
	//		ycor.add(b);
		}
		
	}

}
