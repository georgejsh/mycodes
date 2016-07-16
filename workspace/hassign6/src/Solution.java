import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

class Point implements Comparable<Point>{
	public Point(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}

	int x;
	int y;
	
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
	
};

public class Solution {
	static ArrayList<Point> Hulls 				= new ArrayList<Point>();
	static int distance(Point p1,Point p2) {
		return (p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y);
	}
	static int searchhull(int index,int low,int high,ArrayList<Point> H){
		if(low==high)
			return low;
		if((high-low)==1)
			{
				if(distance(H.get(low),Hulls.get(index))>=distance( H.get(high),Hulls.get(index)))
					return low;
				else 
					return high;
			}
		Integer mid=(high+low)/2;
		
		if((high-low)==2)
		{
			if(distance(H.get(mid),Hulls.get(index))>=distance(H.get(low),Hulls.get(index)) && distance(H.get(mid),Hulls.get(index))>=distance(H.get(high),Hulls.get(index)) )
				return mid;
			else if(distance(H.get(mid),Hulls.get(index))>=distance(H.get(low),Hulls.get(index)) && distance(H.get(mid),Hulls.get(index))<=distance(H.get(high),Hulls.get(index)) )
			    return high;
			else 
				return low;
		}
		if(distance(H.get(mid),Hulls.get(index))>distance(H.get(mid+1),Hulls.get(index))) 
			return searchhull(index, low, mid,H);
		else if(distance(H.get(mid),Hulls.get(index))<distance(H.get(mid+1),Hulls.get(index))) 
			return searchhull(index, mid+1, high,H);
		else
		{
			if(distance(Hulls.get(index),H.get(searchhull(index, low, mid-1,H))) > distance(H.get(searchhull(index, mid+1, high,H)),Hulls.get(index)))
				return searchhull(index, low, mid-1,H);
			else
				return searchhull(index, mid+1, high,H);
		}
		//return 0;
	}
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
		Stack<Point> MyStack				= new Stack<Point>();
		Point Resp1=null,Resp2 = null;
		for(Integer loopi=0;loopi<noofpoints;loopi++)
		{
			data.add(new Point(input.nextInt(),input.nextInt()));
		}
		Collections.sort(data);
		//while(!data.isEmpty())
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
			//MyStack.remove(0);
			//Hulls.remove(0);
			//Hulls.removeAll(MyStack);
			Hulls.addAll(MyStack);
			MyStack.clear();
			//System.out.println(Hulls.size());
			Resp1=Hulls.get(0);
			Resp2=Hulls.get(1);
			//Integer loopi=0;
			ArrayList<Point> temp=new ArrayList<Point>();
			for(Integer loopi=0;loopi<Hulls.size();loopi++)
			{
				temp.clear();
				//Point te;
				temp.addAll(Hulls.subList(loopi+1, Hulls.size()));
				temp.addAll(Hulls.subList(0, loopi));
				//System.out.println(Hulls.size()+" "+temp.size());
				//System.out.println(temp.get(searchhull(loopi, 0,Hulls.size()-2,temp)).x+" "+temp.get(searchhull(loopi, 0,Hulls.size()-2,temp)).y);
				Integer p=(searchhull(loopi, 0,Hulls.size()-2,temp));
				if(distance(temp.get(p),Hulls.get(loopi))>=distance(Resp1, Resp2)) {
					Resp1=Hulls.get(loopi);
					Resp2=temp.get(p);							
				}
				
			}
			if(Resp1.y<Resp2.y || (Resp1.y==Resp2.y && Resp1.x<Resp2.x)) {
				System.out.println(Resp1.x+" "+Resp1.y);
				System.out.println(Resp2.x+" "+Resp2.y);
					
			}
			else
			{
				System.out.println(Resp2.x+" "+Resp2.y);
				System.out.println(Resp1.x+" "+Resp1.y);
				
			}
			
		}
	}

}
