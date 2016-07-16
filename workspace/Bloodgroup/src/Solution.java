import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Solution {
	public static void main(String args[]){
		Scanner input=new Scanner(System.in);
		Integer noofpairs=input.nextInt();
		HashMap<Integer,Integer> donermap = new HashMap<Integer, Integer>();
		HashMap<Integer,Integer> recmap = new HashMap<Integer, Integer>();
		donermap.put(0, 0);
		donermap.put(1, 0);
		donermap.put(2, 0);
		donermap.put(3, 0);
		recmap.put(0, 0);
		recmap.put(1, 0);
		recmap.put(2, 0);
		recmap.put(3, 0);
		Integer loopi,maxop=0;String res,don;
		for(loopi=0;loopi<noofpairs;loopi++){
			don=input.next();
			res=input.next();
			if(res.charAt(0)=='A' && res.length()==1)
			{
				recmap.put(0, recmap.get(0)+1);
			}
			else if(res.charAt(0)=='B')
			{
				recmap.put(1, recmap.get(1)+1);
			}
			else if(res.charAt(0)=='A' && res.length()==2)
			{
				recmap.put(3, recmap.get(3)+1);
			}
			else
			{
				recmap.put(2, recmap.get(2)+1);
			}
			if(don.charAt(0)=='A' && don.length()==1)
			{
				donermap.put(0, donermap.get(0)+1);
			}
			else if(don.charAt(0)=='B')
			{
				donermap.put(1, donermap.get(1)+1);
			}
			else if(don.charAt(0)=='A' && don.length()==2)
			{
				donermap.put(3, donermap.get(3)+1);
			}
			else
			{
				donermap.put(2, donermap.get(2)+1);
			}
		}
		Integer loopj=Math.min(donermap.get(3),recmap.get(3));
		donermap.put(3, donermap.get(3)-loopj);
		recmap.put(3, recmap.get(3)-loopj);
		maxop+=loopj;
		loopj=Math.min(donermap.get(0),recmap.get(0));
		donermap.put(0, donermap.get(0)-loopj);
		recmap.put(0, recmap.get(0)-loopj);
		maxop+=loopj;
		loopj=Math.min(donermap.get(0),recmap.get(3));
		donermap.put(0, donermap.get(0)-loopj);
		recmap.put(3, recmap.get(3)-loopj);
		maxop+=loopj;
		loopj=Math.min(donermap.get(1),recmap.get(1));
		donermap.put(1, donermap.get(1)-loopj);
		recmap.put(1, recmap.get(1)-loopj);
		maxop+=loopj;
		loopj=Math.min(donermap.get(1),recmap.get(3));
		donermap.put(1, donermap.get(1)-loopj);
		recmap.put(3, recmap.get(3)-loopj);
		maxop+=loopj;
		loopj=Math.min(donermap.get(2),recmap.get(0));
		donermap.put(2, donermap.get(2)-loopj);
		recmap.put(0, recmap.get(0)-loopj);
		maxop+=loopj;
		loopj=Math.min(donermap.get(2),recmap.get(1));
		donermap.put(2, donermap.get(2)-loopj);
		recmap.put(1, recmap.get(1)-loopj);
		maxop+=loopj;
		loopj=Math.min(donermap.get(2),recmap.get(2));
		donermap.put(2, donermap.get(2)-loopj);
		recmap.put(2, recmap.get(2)-loopj);
		maxop+=loopj;
		loopj=Math.min(donermap.get(2),recmap.get(3));
		donermap.put(2, donermap.get(2)-loopj);
		recmap.put(3, recmap.get(3)-loopj);
		maxop+=loopj;
		System.out.println(maxop);
	}
}
