package euler;

import java.util.Date;

public class date19 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Date d = new Date(1901, 1, 1);
		//d.getDay()
		//d.setMonth(1);
		int c=0;
		for(int i=1;i<101;i++)
			for(int j=0;j<12;j++){
				Date d=new Date(i, j, 1);
				if(d.getDay()==0)
					c++;
			}
		System.out.println(c);
	}

}
