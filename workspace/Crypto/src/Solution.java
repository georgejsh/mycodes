import java.util.BitSet;
import java.util.Scanner;
import java.util.Vector;

public class Solution {
	public static int nof1s(int x) {
			int i=0;
			while(x!=0) {
				if(x%2==1)
					i++;
				x=x/2;
			}
			return i;
	}
	public static int fn(Vector<Integer> a,int x) {
		int c=0;
		for(int i:a) {
			if(i!=0)
				c^=nof1s(i&x)%2;
			else
				c^=1;
		}
		return c;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int c,count=0;
		Vector<Integer> a=new Vector<>();
		Scanner inp= new Scanner(System.in);
		int n,v;
		n=inp.nextInt();
		for(c=0;c<n;c++) {
			v=inp.nextInt();
			a.add(v);
		}
		for(c=0;c<16;c++) {
			if(fn(a,c)==0)
				count++;
		}
		if(count!=8)
			System.out.println("Not Balanced\n");
		else
			System.out.println("Balanced\n");
	}

}
