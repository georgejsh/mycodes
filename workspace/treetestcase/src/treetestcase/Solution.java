package treetestcase;

import java.util.Random;
import java.util.Vector;

public class Solution {
	static int N=100000;
	static int Q=400000;
	static int degree=50000;
	static Vector<Integer> unvisited= new Vector<>();
	public static Integer randvalue(){
		Random val = new Random();
		Integer index= val.nextInt(unvisited.size());
		Integer value=unvisited.get(index);
		unvisited.remove((int)index);
		return value;
	}
	public static void dfs(int n){
			Integer loopj,loopi=0;
			Vector<Integer> neigh= new Vector<>();
			for(loopj=0;loopj<N;loopj++)
			{
					for(loopi=0;loopi<degree;loopi++) {
					if(unvisited.isEmpty()) {
						return;
					}
					Integer nextneigh=randvalue();
					System.out.println((n+1)+" "+ (nextneigh+1));
					neigh.add(nextneigh);
					//dfs(nextneigh);
				}
			}
			//for(Integer nextneigh:neigh) {
		//		dfs(nextneigh);
		//	}
	}
	public static void randq(){
		Random val = new Random();
		for(Integer loopi=0;loopi<Q;loopi++) {
			System.out.println(val.nextInt(2)+" "+ (val.nextInt(N)+1));
		}
			
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer loopi;
		for(loopi=0;loopi<N;loopi++)
			unvisited.add(loopi);
		System.out.println(N+" "+ Q);
		unvisited.remove(0);
		dfs(0);
		randq();
	}

}
