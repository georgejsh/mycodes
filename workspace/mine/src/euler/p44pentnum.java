package euler;

import java.util.ArrayList;
import java.util.Collections;

public class p44pentnum {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i,j;
		ArrayList<Integer> a = new ArrayList<>();
		//a.add(0);
		for(i=1;i<20001;i++){
			a.add(0);
			a.set(i-1, (i*(3*i-1))/2);
		}
		int l=999999999,k;
		for(i=0;i<20000;i++)
			for(j=i+1;j<20000;j++){
				 k=a.get(j)+a.get(i);
				if(Collections.binarySearch(a, k)<0)
					continue;
				k=a.get(j)-a.get(i);
				if(Collections.binarySearch(a, k)<0)
					continue;
				l=Math.min(l, k);
				System.out.println(k);
				//break;
			}
		System.out.println(l);
	}

}
