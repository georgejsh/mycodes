package euler;

public class integerrighttri39 {
	public static int gcd(int a,int b) {
		
		if(b>a)
			return gcd(b,a);
		if(a%b==0)
			return b;
		return gcd(b,a%b);
	}
	public static void integerrttri(int limit) {
		int lim2=limit/2,m,n,k,d,a,b,c;
		int loopi;
		for(loopi=2;loopi*loopi<=lim2;loopi++){
			if(lim2%loopi!=0)
				continue;
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
