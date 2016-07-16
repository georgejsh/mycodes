package euler;

import java.util.ArrayList;
import java.util.Scanner;

public class triangular1867 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Integer> prev= new ArrayList<>(),curr= new ArrayList<>();
		Integer loopi=1,loopj;
		Scanner input= new Scanner(System.in);
		prev.add(0);
		for(loopi=1;loopi<=100;loopi++)
		{
			curr.clear();
			for(loopj=0;loopj<loopi;loopj++) {
				if(loopj==0){
					curr.add(prev.get(loopj)+input.nextInt());
				}
				else if(loopj==loopi-1) {
					curr.add(prev.get(loopj-1)+input.nextInt());
				}
				else {
					curr.add(Math.max(prev.get(loopj),prev.get(loopj-1))+input.nextInt());;
				}
			}
			prev.clear();
			prev.addAll(curr);
		}
		loopj=0;
		for(loopi=0;loopi<100;loopi++) {
			if(prev.get(loopi)>prev.get(loopj))
				loopj=loopi;
		}
		System.out.println(prev.get(loopj));
	}

}
