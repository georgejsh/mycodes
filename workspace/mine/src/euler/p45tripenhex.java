package euler;

import java.util.ArrayList;
import java.util.Collections;

public class p45tripenhex {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i,j;
		ArrayList<Long> a = new ArrayList<>();
		ArrayList<Long> b = new ArrayList<>();
		ArrayList<Long> c = new ArrayList<>();
		//a.add(0);
		for(i=1;i<200001;i++){
			a.add(0L);
			b.add(0L);
			c.add(0L);
			a.set(i-1, (i*1L*(i+1))/2);
			b.set(i-1, (i*1L*(3*i-1)*1L)/2);
			c.set(i-1, i*1L*(2*i-1));
		}
		for(i=0;i<200000;i++){
			if(Collections.binarySearch(a, c.get(i))<0)
				continue;
			if(Collections.binarySearch(b, c.get(i))<0)
				continue;
			System.out.println(c.get(i));
		}
	}

}
