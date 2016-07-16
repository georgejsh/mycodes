import java.util.Comparator;


public class ComplexNumber implements Comparable<ComplexNumber>{
	
	double real;
	double imaginary;
	
	/**
	 * 
	 */
	public ComplexNumber() {
		// TODO Auto-generated constructor stub
		real=imaginary=0.0;
	}
	
	public ComplexNumber(Double r,Double i) {
		// TODO Auto-generated constructor stub
		real=r;
		imaginary=i;
	}
	public int compareTo(ComplexNumber arg0) {
		// TODO Auto-generated method stub
		if(arg0.real*arg0.real+arg0.imaginary*arg0.imaginary!=real*real+imaginary*imaginary)
			 return Double.compare( real*real+imaginary*imaginary,arg0.real*arg0.real+arg0.imaginary*arg0.imaginary);
			else if(arg0.real!=real)
				return Double.compare(real, arg0.real);
			else
				return Double.compare(imaginary,arg0.imaginary);
		
	}	
}
