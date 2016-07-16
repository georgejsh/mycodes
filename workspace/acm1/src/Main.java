import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;

public class Main {

	/**
	 * @param args
	 */
	public static int seclar(int a,int b,int c) {
		if((a<=b && b<=c )|| (c<=b && b<=a) ) {
			return 1;
		}
		if((a<=c && c<=b )|| (b<=c && c<=a) ) {
			return 2;
		}
		if((c<=a && a<=b )|| (b<=a && a<=c) ) {
			return 0;
		}
		return -1;
	}
	static ArrayList<Integer> setarray = new ArrayList<Integer>();
	public static int find(int a) {
		if(a==setarray.get(a))
			return a;
		return find(setarray.get(a));
	}
	public static void merge(Integer a,Integer b) {
		int parx=find(a);
		int pary=find(b);
		if(parx==pary)
			return;
		setarray.set(parx, pary);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Integer> data = new ArrayList<Integer>();
		HashSet<Integer> testdata= new HashSet<Integer>();
		Integer n,t,loopi,loopj,loopk,loopl;
		Double result;
		Input in	 = new Input(System.in);
		for(t=in.nextInt();t>0;t--) {
			n=in.nextInt();
			setarray.clear();
			for(loopi=0;loopi<n;loopi++) {
				setarray.add(loopi);
			}
			data.clear();
			for(loopi=0;loopi<n;loopi++) {
				data.add(in.nextInt());
				if(data.get(loopi)!=-1)
				merge(loopi,data.get(loopi));
			}
			testdata.clear();
			Integer setcount=0;
			for(loopi=0;loopi<n;loopi++) {
					testdata.add(find(loopi));
					if(data.get(loopi)==-1)
							setcount++;
			}
			//System.out.println(setcount+" "+testdata.size());
			setcount--;
			Integer m=setcount+1,neg=setcount;
			setcount=testdata.size();
			loopj=0;
			Long res=0L;
			/*for(loopi=1;loopj<=neg;loopi=loopi*m/loopj){
				res+=loopi*setcount;
				setcount--;
				m--;
				loopj++;
				System.out.println(res);
			}*/
			result=setcount-neg/2.0;//res*1.0/Math.pow(2.0, neg);
			System.out.println(result);
		}
	}

}
class Input
{
 
    private InputStream stream;
    private byte[] buf = new byte[10000000];
    private int curChar;
    private int numChars;
 
    public Input(InputStream stream) {
        this.stream = stream;
    }
 
    public int read() {
        if (numChars == -1)
            throw new InputMismatchException();
        if (curChar >= numChars) {
            curChar = 0;
            try {
                numChars = stream.read(buf);
            } catch (IOException e) {
                throw new InputMismatchException();
            }
            if (numChars <= 0)
                return -1;
        }
        return buf[curChar++];
    }
 
    public int nextInt() {
        int c = read();
        while (isSpaceChar(c))
            c = read();
        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = read();
        }
        int res = 0;
        do {
            if (c < 48 || c > 57)
                throw new InputMismatchException();
            res =(res<<3)+(res<<1) + (c & 15);
            c = read();
        } while (!isSpaceChar(c));
        return res * sgn;
    }
 
    public long nextLong() {
        int c = read();
        while (isSpaceChar(c))
            c = read();
        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = read();
        }
        long res = 0;
        do {
            if (c < 48 || c > 57)
                throw new InputMismatchException();
            res =(res<<3)+(res<<1) + (c & 15);
            c = read();
        } while (!isSpaceChar(c));
        return res * sgn;
    }
    public char nextChar() {
        int c = read();
        return (char) c;
    }
    public String next() {
        int c = read();
        while (isSpaceChar(c))
            c = read();
        StringBuilder res = new StringBuilder();
        do {
            res.appendCodePoint(c);
            c = read();
        } while (!isSpaceChar(c));
        return res.toString();
    }
 
    public String nextLine() {
        int c = read();
        //while (c != '\n' && c != '\r' && c != '\t' && c != -1)
        //c = read();
        StringBuilder res = new StringBuilder();
        do {
            res.appendCodePoint(c);
            c = read();
        } while (c != '\n' && c != '\r' && c != '\t' && c != -1);
        return res.toString();
    }
 
    public static boolean isSpaceChar(int c) {
        return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
    }    
}  
 
 
class Output{
    public PrintWriter out;
    Output(OutputStream outStream){
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outStream)));
    }
    public void flush() {
		 out.flush();
	}
    public void close() throws Exception {
        out.flush();
        out.close();
    }
    public void println(String i) throws Exception {
        out.println(i);
    }
 
    public void print(String i) throws Exception {
        out.print(i+" ");
    }
    public void println(char[] i) throws Exception {
        out.println(i);
    }
 
    public void print(char[] i) throws Exception {
        out.print(i+" ");
    }
 
    public void println() throws Exception {
        out.print("\n");
    }
 
    public void print(int i) throws Exception {
        out.print(i+" ");
    }
 
    public void println(int i) throws Exception {
        out.println(i);
    }
 
    public void print(long i) throws Exception {
        out.print(i+" ");
    }
 
    public void println(long i) throws Exception {
        out.println(i);
    }
    public void print(double i) throws Exception {
        out.print(i+" ");
    }
 
    public void println(double i) throws Exception {
        out.println(i);
    }
    public void print(char i) throws Exception {
        out.print(i+" ");
    }
 
    public void println(char i) throws Exception {
        out.println(i);
    }
    public void print(Object i) throws Exception {
        out.print(i+" ");
    }
 
    public void println(Object i) throws Exception {
        out.println(i);
    }
} 