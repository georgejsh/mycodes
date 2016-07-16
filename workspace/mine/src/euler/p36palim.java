package euler;

public class p36palim {
	public static boolean ch(int i){
		Integer ii=new Integer(i);
		String s=Integer.toBinaryString(i);
	    if(palim(ii.toString()) && palim(s))
	    	return true;
		return false;
	}
	public static boolean palim(String s){
		for(int j=0;j<s.length();j++){
	    	if(s.charAt(j)!=s.charAt(s.length()-j-1)){
	    		return false;
	    	}
		}
		return true;
	    
	}
	public static void main(String[] args) {
		int j=0;
		for(int i=1;i<1000000;i++){
			if(ch(i))
				j+=i;//System.out.println(i);
		}
		System.out.println(j);
	}
}
