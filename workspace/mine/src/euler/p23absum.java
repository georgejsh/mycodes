package euler;

import java.util.Vector;

public class p23absum {
	public static long countdiv(int n){
		int i;
		long  j=0;
		for(i=1;i<n;i++)
			if(n%i==0)
				j+=i;
		return j;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vector<Integer> li=new Vector<>();
		Vector<Integer> l=new Vector<>();
		int i,j=0,k=0;
		for(i=1;i<=28123;i++){
			long m=countdiv(i);
			if(m>i){
				li.addElement(i);
			}
		}
		for(i=0;i<=28123;i++){
			l.add(0);
		}
		for(i=0;i<li.size();i++){
			//if(li.get(i)<=28123)
				//l.set(li.get(i), 1);
			for(j=0;j<=i;j++){
				if(li.get(i)+li.get(j)<=28123)
					l.set(li.get(i)+li.get(j), 1);
			}
		}
		for(i=0;i<=28123;i++){
			if(l.get(i)==0){
				k+=i;//System.out.print(i+" ");
			}
		}
		System.out.println(k);
	}

}
