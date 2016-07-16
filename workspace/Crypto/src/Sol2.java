import java.util.BitSet;
import java.util.Scanner;
import java.util.Vector;

public class Sol2 {
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
			if(i!=0){
				if(nof1s(i&x)==nof1s(i))c^=1;
			}
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
		int n,v,inl,j,nonl=8;
		n=inp.nextInt();
		for(c=0;c<n;c++) {
			v=inp.nextInt();
			a.add(v);
		}
		Vector<Integer> b = new Vector<>();
		for(inl=1;inl<16;inl++) {
			b.removeAllElements();
			count=0;
			for(j=0;j<4;j++) 
				if(((1<<j)&inl)>0){
					b.add((int)1<<j);
				}
			for(c=0;c<16;c++) {
				if(fn(a,c)!=fn(b,c))
					count++;
			}

		//	System.out.println(fn(a,1));
			nonl=Math.min(nonl, count);
		}
		System.out.println(nonl);
	}

}
