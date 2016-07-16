package pre1;

import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;
public class Solution {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vector<Long> xcor,ycor;
		xcor=new Vector<>();
		ycor= new Vector<>();
		Long n,loopi=0L,a,b;
		Scanner input = new Scanner(System.in);
		for(n=input.nextLong();loopi<n;loopi++) {
			a=input.nextLong();
			b=input.nextLong();
			xcor.add(a);
			ycor.add(b);
		}
		Collections.sort(xcor);
		Collections.sort(ycor);
		System.out.println(xcor.get(0)*ycor.get(0));
	}

}
