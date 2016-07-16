package euler;

public class p38pandig {
	public static boolean check(String s){
		int a[]= new int[10];
		
		int i;
		for(i=0;i<s.length();i++)
			a[s.charAt(i)-48]++;
		if(a[0]>0)
			return false;
		for(i=1;i<10;i++)
			if(a[i]!=1)
				return false;
		return true;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = new String();
		for(int i=0;i<100000;i++){
			s="";
			for(int j=1;j<=5;j++){
				s+=Integer.toString(i*j);
				if(check(s))
					System.out.println(s+" "+i);
			}
		}
	}

}
