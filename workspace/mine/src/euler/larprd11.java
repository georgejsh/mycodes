package euler;

import java.util.Scanner;
import java.util.Vector;

public class larprd11 {
	static int [][] aa;
	public static int maxij(int i,int j){
		int a=0,b=0,c=0,d=0;
		if(j<17)
			d=aa[i][j]*aa[i][j+1]*aa[i][j+2]*aa[i][j+3];
		if(i<17 && j<17)
			c=aa[i][j]*aa[i+1][j+1]*aa[i+2][j+2]*aa[i+3][j+3];
		if(i<17)
			b=aa[i][j]*aa[i+1][j]*aa[i+2][j]*aa[i+3][j];
		if(j>3 && i<17)
			a=aa[i][j]*aa[i+1][j-1]*aa[i+2][j-2]*aa[i+3][j-3];
		a=Math.max(a,Math.max(b, Math.max(c, d)));
		return a;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		aa= new int [20][20];
		Scanner inp= new Scanner(System.in);
		for(int i=0;i<20;i++)
			for(int j=0;j<20;j++)
				aa[i][j]=inp.nextInt();
		int k=0;
		for(int i=0;i<20;i++)
			for(int j=0;j<20;j++)
				k=Math.max(k,maxij(i, j));
		System.out.println(k);
	}

}
