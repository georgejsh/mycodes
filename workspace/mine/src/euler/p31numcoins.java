package euler;

public class p31numcoins {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a[]={1,2,5,10,20,50,100,200};
		long b[]= new long[201];
		b[0]=1;
		for(int j=0;j<a.length;j++){
			for(int i=1;i<=200;i++)
			{
				if(i-a[j]>=0)
					b[i]+=b[i-a[j]];
			}
		}
		System.out.println(b[200]);
	}

}
