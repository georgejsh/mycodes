import java.util.ArrayList;
import java.util.Scanner;


public class Solution {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		ArrayList<Long> mylist =new ArrayList<Long>();
		Integer n,i,j,k,t;
		long res,l,r,tempres=2L,m;
		t=input.nextInt();
		mylist.add(1L);
		for(i=1;i<65;i++) {
			res=tempres;
			mylist.add(res);
			for(j=0;j<38;j++) {
				res=res*3;
				if(res>1000000000000000000L || res<0)
					break;
				mylist.add(res);
			}
			
			tempres=tempres*2;
		//	if(tempres>1000000000000000000L ||tempres<0)
		//		break;
		}
			
		for(i=0;i<t;i++)
		{
			l=input.nextLong();
			r=input.nextLong();
			k=0;
			for(j=0;j<mylist.size();j++) {
				m=mylist.get(j);
				if(m>=l && m<=r)
					k++;
			}
			System.out.println(k);
		}
			
				
		
	}

}
