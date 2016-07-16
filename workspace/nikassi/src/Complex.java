

public  class Complex{
	public double real;
	public double imaginary;
	public Complex(double real,double imaginary)
	{
		this.real = real;
		this.imaginary = imaginary ;
	}
	public Double modulus()
	{
		return (real*real + imaginary*imaginary);
	}
}
