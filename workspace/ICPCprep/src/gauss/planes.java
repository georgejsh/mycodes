package gauss;

import java.util.Scanner;

public class planes {
	static int n=3;
	static double a[][]= new double[4][5];
	 
	static void forwardSubstitution() {
	    int i, j, k, max;
	    double t;
	    for (i = 0; i < n; ++i) { 
	        max = i;
	        for (j = i + 1; j < n; ++j)
	            if (a[j][i] > a[max][i]) 
	                max = j;
	         if(a[max][i]==0)
	        	 continue;
	        for (j = 0; j < n + 1; ++j) { //n+1 to n+2
	            t = a[max][j];
	            a[max][j] = a[i][j];
	            a[i][j] = t;
	        }
	         
	        for (j = n; j >= i; --j)
	            for (k = i + 1; k < n; ++k)
	                a[k][j] -= a[k][i]/a[i][i] * a[i][j];
	 
	/*      for (k = 0; k < n; ++k) {
	            for (j = 0; j < n + 1; ++j)
	                printf("%.2f\t", a[k][j]);
	            printf("\n");
	        }*/
	    }
	}
	 
	static void reverseElimination() {
	    int i, j;
	    for (i = n - 1; i >= 0; --i) {
	    	if(a[i][i]==0) { 
	    		a[i][n]=1;
	    		continue;
	    	}
	    	else {
	    		a[i][n] = a[i][n] / a[i][i];
	           a[i][i] = 1;
	    	}
	        for (j = i - 1; j >= 0; --j) {
	            a[j][n] -= a[j][i] * a[i][n];
	            a[j][i] = 0;
	        }
	    }
	}
	 
	static void gauss() {
	    int i, j;
	 
	    forwardSubstitution();
	    reverseElimination();
	     
	    for (i = 0; i < n; ++i) {
	        for (j = 0; j < n + 1; ++j)  // edited n+1 to n+2
	            System.out.print( a[i][j]+" ");
	        System.out.println();
	    }
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new  Scanner(System.in);
		Integer t=input.nextInt(); 
		for(Integer loopi=0;loopi<t;loopi++) {
			for(Integer loopj=0;loopj<4;loopj++){
				a[loopj][0]=input.nextInt();
				a[loopj][1]=input.nextInt();
				a[loopj][2]=input.nextInt();
				a[loopj][3]=1.0;
				//a[loopj][4]=0;				
			}
			gauss();
			double sum=0;
			double coff[]= new double[4];
			coff[0]=a[0][3]*a[1][1]*a[2][2];
			coff[1]=a[0][0]*a[1][3]*a[2][2];
			coff[2]=a[0][0]*a[1][1]*a[2][3];
			coff[3]=a[0][0]*a[1][1]*a[2][2];
			for(Integer loopj=0;loopj<3;loopj++){
				sum+=coff[loopj]*a[loopj][3];
				System.out.print(coff[loopj]+" ");
			}
			
			if(sum==-coff[3])
				System.out.println("YES");
			else
				System.out.println("NO");
		}
	}

}
