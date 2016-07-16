package euler;

public class amicable21 {
	public static int countdiv(int n){
		int i,j=0;
		for(i=1;i<n;i++)
			if(n%i==0)
				j+=i;
		return j;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i,j=0,k;
		for(i=1;i<10000;i++){
			k=countdiv(i);
			if(countdiv(k)==i && k!=i)
				j+=i;
		}
		System.out.println(j);
	}

}
