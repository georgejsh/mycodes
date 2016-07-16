

import java.util.Comparator;

public  class KeyCompare implements Comparator<Complex>{
	public int compare(Complex c1,Complex c2)
	{
		return c1.modulus().compareTo(c2.modulus());
	}
	}
