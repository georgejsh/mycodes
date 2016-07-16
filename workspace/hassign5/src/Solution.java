import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.crypto.Data;

class Point implements Comparable<Point>{
	public Point(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}

	Integer x;
	Integer y;
	
	@Override
	public int compareTo(Point o) {
		// TODO Auto-generated method stub
		if(o.y>y)
			return -1;
		else if(o.y<y)
			return 1;
		if(o.x>x)
			return -1;
		else if(o.x<x)
			return 1;
		return 0;
	}
	
}

public class Solution {

	/**
	 * @param args
	 */
	static int leftorright(int x1,int y1,int x2,int y2,int x3,int y3)
	{
		double slope1,slope2;
		if(x2-x1==0 && x3-x2==0)
			return 0;//on-line
		if(x2-x1==0)
		{
			if(y2>y1)
			{
				if(x2<x3)
					return 0;//right
				else
					return 1;//left
			}
			else
			{
				if(x2<x3)
					return 1;//left
				else
					return 0;//right
			}
		}
		if(x3-x2==0)
		{
			if(y2<y3)
			{
				if(x1<x3)
					return 1;//left
				else
					return 0;//right
			}
			else
			{
				if(x1<x3)
					return 0;//right
				else
					return 1;//left
			}
				
		}
		slope1=(y2-y1)/((x2-x1)*1.0);
		slope2=(y3-y2)/((x3-x2)*1.0);
		Double mod1=slope1!=0?Math.abs(slope1)/slope1:1,mod2=slope2!=0 ?Math.abs(slope2)/slope2:1;
		mod1=mod1/mod2;
		if(mod1>0 && slope2>slope1)
			return 1;//left
		else if(mod1<0 && slope2<slope1)
			return 1;//left
		else
			return 0;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input 						= new Scanner(System.in);
		ArrayList<Point> data				= new ArrayList<Point>();
		Integer noofpoints				  	= input.nextInt();
		ArrayList<Point> Hulls 				= new ArrayList<Point>();
		Stack<Point> MyStack				= new Stack<Point>();
		for(Integer loopi=0;loopi<noofpoints;loopi++)
		{
			data.add(new Point(input.nextInt(),input.nextInt()));
		}
		Collections.sort(data);
		while(!data.isEmpty())
		{
			for(Integer loopi=0;loopi<data.size();loopi++)
			{
				Point curr= data.get(loopi);
				if(MyStack.size()<2)
					MyStack.push(curr);
				else
				{
					Point p1,p2;
					p2=MyStack.get(MyStack.size()-1);
					p1=MyStack.get(MyStack.size()-2);
					if(leftorright(p1.x, p1.y, p2.x, p2.y, curr.x, curr.y)==1) {
						MyStack.pop();
						loopi--;
					}
					else
						MyStack.push(curr);
				}
			}
			Hulls.addAll(MyStack);
			MyStack.clear();
			for(Integer loopi=data.size()-1;loopi>=0;loopi--)
			{
				Point curr= data.get(loopi);
				if(MyStack.size()<2)
					MyStack.push(curr);
				else
				{
					Point p1,p2;
					p2=MyStack.get(MyStack.size()-1);
					p1=MyStack.get(MyStack.size()-2);
					if(leftorright(p1.x, p1.y, p2.x, p2.y, curr.x, curr.y)==1) {
						MyStack.pop();
						loopi++;
					}
					else
						MyStack.push(curr);
				}
			}
			MyStack.remove(0);
			Hulls.remove(0);
			Hulls.removeAll(MyStack);
			Hulls.addAll(MyStack);
			MyStack.clear();
			//System.out.println(Hulls.size());
			for(Integer loopi=Hulls.size()-1;loopi>=0;loopi--)
			{
				System.out.println(Hulls.get(loopi).x+" "+Hulls.get(loopi).y);
			}
			data.removeAll(Hulls);
			Hulls.clear();
			System.out.println("zzz");
			if(data.size()==1)
			{
				System.out.println(data.get(0).x+" "+ data.get(0).y);
				System.out.println("zzz");
				data.clear();
			}
		}
	}

}
