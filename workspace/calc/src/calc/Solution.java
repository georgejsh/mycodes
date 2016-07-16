package calc;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Solution {

	/**
	 * @param args
	 */
	public static Integer isprimeposs(int num,ArrayList<Integer> keys){
		Integer i,j;
		String numstring=Integer.toString(num);
		for(i=0;i<numstring.length();i++){
			if(keys.get(numstring.charAt(i)-48)==0)
				return Integer.MAX_VALUE;
		}
		return numstring.length()+1;
	}
	public static boolean checkprime(int num) {
		if(num<=size)
			return prime.get(num)==0;
		else
		{
			for(Integer loopi=2;loopi*loopi<=num;loopi++)
			//	if(prime.get(loopi)==0)
					if(num%loopi==0)
					return false;
			return true;
		}
	}
	public static ArrayList<Integer> finddivs(int num){
		ArrayList<Integer> prevdivsors =new ArrayList<>();
		ArrayList<Integer> currdivsors =new ArrayList<>();
/*		if(num<=size){
			Integer loopj,loopi=num,loopl;
			while(loopi!=1){
				loopj=powerofprime.get(loopi);
				currdivsors.clear();
				currdivsors.addAll(prevdivsors);
				loopl=lowprime.get(loopi);
				while(loopj>0) {
					currdivsors.add(loopl);
					for(Integer loopk:prevdivsors){
						//if(checkpossible(loopk*loopl)!=Integer.MAX_VALUE)
							currdivsors.add(loopk*loopl);
					}
					loopj--;
					loopl*=lowprime.get(loopi);
				}
				prevdivsors.clear();
				prevdivsors.addAll(currdivsors);
				loopi=remaining.get(loopi);
			}
	 		
		}
		else {*/
			for(Integer loopi=2;loopi*loopi<=num;loopi++)
				if(num%loopi==0) {
					currdivsors.add(loopi);
					currdivsors.add(num/loopi);
				}
		//}
		return currdivsors;
	}
	public static int checkpossible(int num) {
		if(result.get(num)!=-1)
			return result.get(num);
		if(checkprime(num))		{
			result.set(num,isprimeposs(num, keysleft));
			return result.get(num);
		}
		Integer best=isprimeposs(num, keysleft),tempbest = 0,tempbest2=0;
 		if(best!=Integer.MAX_VALUE)
 			return best;
		ArrayList<Integer> currdivsors= finddivs(num);
 		//Collections.sort(currdivsors);
 		for(Integer divs:currdivsors){
 			if(isprimeposs(divs, keysleft)==Integer.MAX_VALUE)
 				continue;
 			tempbest=checkpossible(divs);
 			if(tempbest==Integer.MAX_VALUE )
 				continue;
 			tempbest2=checkpossible(num/divs);
 			if(tempbest2==Integer.MAX_VALUE)
 				continue;
 			tempbest+=tempbest2;
 			if(tempbest<best)
 				best=tempbest;
 		}
 		result.set(num, best);
		return best;
	}
	static ArrayList<Integer> prime= new ArrayList<Integer>();
	//static ArrayList<Integer> lowprime =new ArrayList<Integer>();
	//static ArrayList<Integer> powerofprime = new ArrayList<Integer>();
	//static ArrayList<Integer> remaining = new ArrayList<Integer>();
	static ArrayList<Integer> keysleft = new ArrayList<Integer>();
	static ArrayList<Integer> result =new ArrayList<Integer>(); 
	public static Integer size=1000;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner input	= new Scanner(System.in);
		Integer loopi,loopj;
		Long sum=0L;
		/*Scanner in = null;
		try {
			in = new Scanner(new File("out.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(loopi=0;loopi<=size;loopi++){
			prime.add(in.nextInt());
		}
		for(loopi=0;loopi<=size;loopi++){
			lowprime.add(in.nextInt());
		}
		for(loopi=0;loopi<=size;loopi++){
			powerofprime.add(in.nextInt());
		}
		for(loopi=0;loopi<=size;loopi++){
			remaining.add(in.nextInt());

			result.add(-1);
		}*/
		for(loopi=0;loopi<=size;loopi++){
			prime.add(0);
	//		lowprime.add(0);
	//		powerofprime.add(0);
	//		remaining.add(0);
		}
		for(loopi=0;loopi<=1000000;loopi++){
			result.add(-1);
		}
		for(loopi=2;loopi<=size;loopi++) {
			if(prime.get(loopi)!=0)
				continue;
			//System.out.println(loopi);
	//		lowprime.set(loopi, loopi);
	//		powerofprime.set(loopi,1);
	//		remaining.set(loopi,1);
			for(loopj=2;loopj*loopi<=size;loopj++){
				if(prime.get(loopj*loopi)!=0)
					continue;	
				prime.set(loopj*loopi,1);
	//			lowprime.set(loopj*loopi, loopi);
			}
		}
/*		for(loopi=2;loopi<=size;loopi++) {
			if(prime.get(loopi)==0)
				continue;
			if(lowprime.get(loopi)==lowprime.get(loopi/lowprime.get(loopi))) {
				powerofprime.set(loopi,powerofprime.get(loopi/lowprime.get(loopi))+1);
				remaining.set(loopi,remaining.get(loopi/lowprime.get(loopi)));
			}
			else{
				powerofprime.set(loopi,1);
				remaining.set(loopi,loopi/lowprime.get(loopi));
			}
		}*//*
		for(loopi=2;loopi<=20;loopi++) {
			System.out.print(prime.get(loopi)+" ");
			System.out.print(lowprime.get(loopi)+" ");
			System.out.print(powerofprime.get(loopi)+" ");
			System.out.print(remaining.get(loopi));
			System.out.println();
		}*/
		for(loopi=0;loopi<10;loopi++){
			keysleft.add(input.nextInt());
		}
		Integer limit= input.nextInt(),value=checkpossible(limit);
		if(value==Integer.MAX_VALUE)
		System.out.println("Impossible");
		else
			System.out.println(value);
	}

}