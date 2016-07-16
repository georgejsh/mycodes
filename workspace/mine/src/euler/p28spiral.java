package euler;

public class p28spiral {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int j,i,l=1,k,n=2;
		long m=1;
		for(i=0;i<500;i++){
			for(j=0;j<4;j++){
				l+=n;
				m+=l;
			}
			n+=2;
		}
		System.out.println(m);
	}

}
