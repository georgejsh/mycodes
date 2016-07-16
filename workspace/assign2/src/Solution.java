import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeMap;


public class Solution {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TreeMap<ComplexNumber, Integer> myTreemap=new TreeMap<ComplexNumber, Integer>();
		Scanner input = new Scanner(System.in);
		Double a,b;
		Integer c;
		while(true)
		{
			a=input.nextDouble();
			b=input.nextDouble();
			c=input.nextInt();
			if(a==0.0 && b==0.0 && c==0)
				break;
			myTreemap.put(new ComplexNumber(a,b),c);
		}
		Iterator<Integer> myCollection=myTreemap.values().iterator();
		//System.out.println(myTreemap.size());
		for(;myCollection.hasNext();)
		{
			System.out.println(myCollection.next());
		}
	}

}
