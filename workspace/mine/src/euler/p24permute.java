package euler;

public class p24permute {
	static int  b[]= new int[11];
	
	public static int findnext(int i){
		int j=0;
		while(i>0){
			if(b[j]==0)
				i--;
		j++;
		}
		return j-1;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i;
		for(i=0;i<10;i++)
			b[i]=0;
		int  a[]= new int[11];
		a[1]=1;
		for(i=2;i<11;i++)
				a[i]=a[i-1]*i;
		int j=1000000;
		for(i=2;i<11;i++)
			System.out.print(a[i]+" ");
		System.out.println();
		i=9;
		while(j>0){
			int k=findnext(j/a[i]+1),l=j/a[i]+1;
			j=j%a[i];
			if(j==0 && i!=1){
				k=findnext(l-1);
				b[k]=1;
				System.out.print(k);
				for(j=9;j>=0;j--)
					if(b[j]!=1)
						System.out.print(j);
				break;
			}
			b[k]=1;
			System.out.print(k);
			i--;
		}
	}

}
