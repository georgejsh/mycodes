package acm1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArrayList<Integer> data = new ArrayList<Integer>();
		ArrayList<Integer> tempdata = new ArrayList<Integer>();
		
		Integer n,t,loopi,loopj,loopk,loopl;
		Input in = new Input(System.in);
		for(t=in.nextInt();t>0;t--) {
			n=in.nextInt();
			for(loopi=0;loopi<n;loopi++) {
				data.add(in.nextInt());
			}
			for(loopi=0,loopj=n-1,loopk=loopl=-1;loopi<=loopj;loopi++) {
				if(loopk==-1) {
					tempdata.add(data.get(loopi));
					loopk=loopl=0;
					loopi++;
				}
				else if(data.get(loopi)<=tempdata.get(loopk) && data.get(loopj)<=tempdata.get(loopk)) {
					if(data.get(loopi)<=data.get(loopj)) {
						tempdata.add(data.get(loopi));
						loopl++;
						loopi++;
					}
					else {
						tempdata.add(data.get(loopj));
						loopl++;
						loopj--;
					}
				}
				else if(data.get(loopi)>=tempdata.get(loopl) && data.get(loopj)>=tempdata.get(loopl)) {
					if(data.get(loopi)<=data.get(loopj)) {
						tempdata.add(data.get(loopj));
						loopl++;
						loopj--;
					}
					else {
						tempdata.add(data.get(loopi));
						loopl++;
						loopi++;
					}
				}
				else if(data.get(loopi)<=tempdata.get(loopk) ) {
					if(data.get(loopi)<data.get(loopj)) {
						tempdata.add(data.get(loopj));
						loopl++;
						loopj--;
					}
					else {
						tempdata.add(data.get(loopi));
						loopl++;
						loopi++;
					}
				} 
			}
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