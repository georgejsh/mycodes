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
		Integer i,j=0;
		while(num>0){
			i=num%10;
			if(keys.get(i)==0)
				return Integer.MAX_VALUE;
			num=num/10;
			j++;
		}
		/*String numstring=Integer.toString(num);
		for(i=0;i<numstring.length();i++){
			if(keys.get(numstring.charAt(i)-48)==0)
				return Integer.MAX_VALUE;
		}*/
		return j+1;
	}
	public static int checkpossible(int num) {
		if(result[num]!=-1)
			return result[num];
		if(prime[num]==0)
		{
			result[num]=isprimeposs(num, keysleft);
			return result[num];
		}
		Integer best=isprimeposs(num, keysleft),tempbest = 0,tempbest2=0;
 		if(best!=Integer.MAX_VALUE)
 			return best;
		Integer loopj,loopi=num,loopl;
		//ArrayList<Integer> prevdivsors =new ArrayList<>();
		//ArrayList<Integer> currdivsors =new ArrayList<>();
 		int [] prevdivsors= new int[size+1];
 		int [] currdivsors= new int[size+1];
		int [] t;
		int currl=0,curlp=0,temp;
		
		while(loopi!=1){
			loopj=powerofprime[loopi];
			currl=0;
			//currdivsors.clear();
			//currdivsors.addAll(prevdivsors);
			//t=currdivsors;
			//currdivsors=prevdivsors;
			//prevdivsors=t;
			for(Integer loopk=0;loopk<curlp;loopk++){
				//if(checkpossible(loopk*loopl)!=Integer.MAX_VALUE)
				currdivsors[currl++]=prevdivsors[loopk];
			}
			loopl=lowprime[loopi];
			while(loopj>0) {
				currdivsors[currl++]=loopl;
				for(Integer loopk=0;loopk<curlp;loopk++){
					//if(checkpossible(loopk*loopl)!=Integer.MAX_VALUE)
					currdivsors[currl++]=prevdivsors[loopk];	
					currdivsors[currl++]=prevdivsors[loopk]*loopl;
				}
				loopj--;
				loopl*=lowprime[loopi];
			}
			t=prevdivsors;
			prevdivsors=currdivsors;
			currdivsors=t;
			curlp=currl;
			
			//prevdivsors.clear();
			//prevdivsors.addAll(currdivsors);
			loopi=remaining[loopi];
		}
 		//Collections.sort(currdivsors);
 		
 		int sqnum=(int)Math.sqrt(num);
 		for(Integer divs=0;divs<curlp;divs++){
 			if(isprimeposs(prevdivsors[divs],keysleft)==Integer.MAX_VALUE)
 				continue;
 			//tempbest=checkpossible(prevdivsors[divs]);
 			//if(tempbest==Integer.MAX_VALUE )
 			//	continue;
 			tempbest2=checkpossible(num/prevdivsors[divs]);
 			if(tempbest2==Integer.MAX_VALUE)
 				continue;
 			tempbest+=tempbest2;
 			if(tempbest<best)
 				best=tempbest;
 			break;
 		}
 		result[num]= best;
		return best;
	}
	public static Integer size=1000000;
	static int[] prime =new int[size+1];
	static int[] lowprime =new int[size+1];
	static int[] powerofprime =new int[size+1];
	static int[] remaining =new int[size+1];
	static int[] result =new int[size+1];
//	static ArrayList<Integer> prime= new ArrayList<Integer>();
//	static ArrayList<Integer> lowprime =new ArrayList<Integer>();
//	static ArrayList<Integer> powerofprime = new ArrayList<Integer>();
//	static ArrayList<Integer> remaining = new ArrayList<Integer>();
	static ArrayList<Integer> keysleft = new ArrayList<Integer>();
//	static ArrayList<Integer> result =new ArrayList<Integer>(); 
	
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
			//prime.add(0);
			//lowprime.add(0);
		//	powerofprime.add(0);
		//	remaining.add(0);
			result[loopi]=-1;
		}
		for(loopi=2;loopi<=size;loopi++) {
			if(prime[loopi]!=0)
				continue;
			//System.out.println(loopi);
			lowprime[loopi]= loopi;
			powerofprime[loopi]=1;
			remaining[loopi]=1;
			for(loopj=2;loopj*loopi<=size;loopj++){
				if(prime[loopj*loopi]!=0)
					continue;	
				prime[loopj*loopi]=1;
				lowprime[loopj*loopi]=loopi;
				if(loopj%loopi==0) {
					powerofprime[loopi*loopj]=powerofprime[loopj]+1;
					remaining[loopi*loopj]=remaining[loopj];
				}
				else{
					powerofprime[loopi*loopj]=1;
					remaining[loopi*loopj]=loopj;
				}
			}
		}
	/*	for(loopi=2;loopi<=size;loopi++) {
			if(prime[loopi]==0)
				continue;
		if(lowprime[loopi]==lowprime[loopi/lowprime[loopi]]) {
				powerofprime[loopi]=powerofprime[loopi/lowprime[loopi]]+1;
				remaining[loopi]=remaining[loopi/lowprime[loopi]];
			}
			else{
				powerofprime[loopi]=1;
				remaining[loopi]=loopi/lowprime[loopi];
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