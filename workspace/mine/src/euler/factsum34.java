package euler;

import java.util.ArrayList;

public class factsum34 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer loopi,loopj,tsum;
		long rsum=0;
		String temp;
		ArrayList<Integer> pow=new ArrayList<>();
		pow.add(1);
		for(loopi=1;loopi<10;loopi++)
			pow.add(pow.get(loopi-1)*loopi);
		for(loopi=3;loopi<5000000;loopi++){
			temp=loopi.toString();
			//System.out.println(temp);
			tsum=0;
			for(loopj=0;loopj<temp.length();loopj++){
				tsum+=pow.get(temp.charAt(loopj)-48);
			}
			if(tsum.equals(loopi))
				rsum+=loopi;
		}
		System.out.println(rsum);
	}

}
