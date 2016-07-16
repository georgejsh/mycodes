package euler;

public class p33digit {
	public static boolean check(int i,int j){
		int a,b,c,d;
		a=i%10;
		b=(i/10)%10;
		c=j%10;
		d=(j/10)%10;
		double r=i*1.0/j;
		if(c==0 || d==0)
			return false;
		if((r==a*1.0/c && b==d)|| (r==a*1.0/d && b==c) || (r==b*1.0/c && a==d) || (r==b*1.0/d && a==c))
			return true;
		return false;
		
	}
	public static void main(String[] args) {
		int i,j;
		for(i=1;i<100;i++){
			for(j=1;j<100;j++){
				if(i==j)
					continue;
				if(check(i,j))
					System.out.println(i+" "+j);
			}
		}
		
	}
}
