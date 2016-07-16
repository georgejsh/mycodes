package euler;

import java.util.ArrayList;

public class p46goldbach {

	static ArrayList<Integer> prime= new ArrayList<Integer>();
	public static Integer size=100000;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Boolean> ch= new ArrayList<>();
		Integer loopi,loopj;
		for(loopi=0;loopi<=size;loopi++){
			prime.add(0);
			ch.add(false);
		}
		for(loopi=2;loopi<=size;loopi++) {
			if(prime.get(loopi)!=0)
				continue;
			//System.out.println(loopi);
			for(loopj=2;loopj*loopi<=size;loopj++){
				if(prime.get(loopj*loopi)!=0)
					continue;	
				prime.set(loopj*loopi,1);
			}
		}
		loopj=0;
		prime.set(1, 1);
		for(loopi=1;loopi<=size;loopi++){
			if(prime.get(loopi)!=0)
				continue;
			for(loopj=1;loopj<10000;loopj++)
			if(loopi+loopj*loopj*2<size){
				ch.set(loopi+loopj*loopj*2, true);
			}
		}
		for(loopi=5;loopi<size;loopi++){
			if(prime.get(loopi)==0)
				continue;
			if(ch.get(loopi) || loopi%2==0)
				continue;
			System.out.println(loopi);
			break;
	}
	}

}
