package euler;

public class p43substdiv {
	static int a[];
	public static boolean findnext(){
		int i=9;
		/*while(!set(i)){
			i--;
			if(i<0)
				return false;
		}*/
		for(i=9;i>=1;i--)
			if(a[i]>a[i-1]){
				return set(i-1);
			}
		if(i==0)
			return false;
		return true;
	}
	public static boolean set(int i){
		int b[]=new int[11];
		int j;
		for(j=0;j<i;j++)
			b[a[j]]++;
		for(j=a[i]+1;j<=9;j++)
			if(b[j]==0){
				a[i]=j;
				b[j]++;
				break;
			}
		if(j>9)
			return false;
		i++;
		for(j=0;j<=9;j++)
			if(b[j]==0){
				a[i]=j;
				b[j]++;
				i++;
			}
		return true;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i,j=0;
		a=new int[10];
		long sum=0,r;
		a[0]=1;a[1]=0;a[2]=2;a[3]=3;a[4]=4;a[5]=5;a[6]=6;a[7]=7;a[8]=8;a[9]=9;
		while(findnext()){
			/*for(i=0;i<10;i++)
				System.out.print(a[i]+" ");
				System.out.println();
				j++;
				*/
			if((a[1]*100+a[2]*10+a[3])%2!=0)
				continue;
			if((a[2]*100+a[3]*10+a[4])%3!=0)
				continue;
			if((a[3]*100+a[4]*10+a[5])%5!=0)
				continue;
			if((a[4]*100+a[5]*10+a[6])%7!=0)
				continue;
			if((a[5]*100+a[6]*10+a[7])%11!=0)
				continue;
			if((a[6]*100+a[7]*10+a[8])%13!=0)
				continue;
			if((a[7]*100+a[8]*10+a[9])%17!=0)
				continue;
			r=0;
			for(i=0;i<10;i++)
				r=r*10+a[i];
			sum+=r;
			//System.out.print(a[i]+" ");
				//System.out.println();
				
			
		}
		System.out.println(sum);
	}

}
