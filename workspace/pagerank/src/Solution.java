
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

import org.la4j.Matrix;
import org.la4j.matrix.sparse.CCSMatrix;
//used for the sorting the pageranks 

class RankNode implements Comparable<RankNode>{
	Double rank;
	Integer id;
	public RankNode(Double r,Integer i) {
		rank=r;
		id=i;
	}
	@Override
	public int compareTo(RankNode o) {
		// TODO Auto-generated method stub
		return -rank.compareTo(o.rank);
	}
}
public class Solution {

	/**
	 * @param args
	 */
	
	static double precision=0.000001;
	static Integer size=10000;
	//checking the required precision
	
	public static boolean checkprecision(org.la4j.Vector prev,org.la4j.Vector curr) {
		for(Integer loopi=0;loopi<prev.length();loopi++) {
			if(precision<Math.abs(prev.get(loopi)-curr.get(loopi))) {
				return false;
			}
		}
		return true;	
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Matrix dataMatrix = new CCSMatrix(size,size);
		Vector<Integer> startedgeList 	=new Vector<Integer>(); //used to store edge list
		Vector<Integer> endedgeList 	=new Vector<Integer>(); 
		Vector<Integer> degree			=new Vector<Integer>();//used to calculate the out degree of each node
		double[] unistab				=new double[size];		//finding stabilising vector
		Vector<RankNode> result			=new Vector<RankNode>(); //stores result
		org.la4j.Vector 				stabnxt,stabcurr;		//used to search the stabilising vector
		Scanner input					= new Scanner(System.in);
		Integer fromnode,tonode;								
		for(Integer i=0;i<size;i++){						//Initialising the uniform vector
			degree.add(0);
			unistab[i]	=1.0/7115;
		}
		while(input.hasNext()) {
			fromnode	=input.nextInt();
			tonode		=input.nextInt();
			startedgeList.add(fromnode);
			endedgeList.add(tonode);
			degree.set(fromnode, degree.get(fromnode)+1);		//calculating the degree of each vertex
		}
		for(Integer i=0;i<startedgeList.size();i++) {
			fromnode	=startedgeList.get(i);
			tonode		=endedgeList.get(i);
			dataMatrix.set(tonode,fromnode,1.0/degree.get(fromnode));	//setting the matrix with contribution
		}
		//searching the stabilising vector;
		
		stabcurr	=	org.la4j.Vector.fromArray(unistab);
		// Pagerank(next) = PageRank(curr)*Trasition*.85+.15
		
		stabnxt		=	dataMatrix.copy().multiply(stabcurr).multiply(0.85).add(org.la4j.Vector.constant(size, 0.15));
		while(!checkprecision(stabcurr, stabnxt))		//until 
		{	
			stabcurr	=stabnxt;
			stabnxt		=dataMatrix.copy().multiply(stabcurr).multiply(0.85).add(org.la4j.Vector.constant(size, 0.15));
		}
		
		//sorting the list
		
		for(Integer loopi=0;loopi<size;loopi++) {
			result.add(new  RankNode(stabnxt.get(loopi),loopi));
			System.out.print(stabnxt.get(loopi)+" ");
		}
		Collections.sort(result);
		for(Integer loopi=0;loopi<10;loopi++)
			System.out.println(result.get(loopi).id);
		System.out.println();
		input.close();
	}

}
