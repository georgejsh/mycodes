import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.Comparator;

class Point implements Comparator<Point>,Comparable<Point>{
int x;
int y;
double polarAngle;
double radius;
public Point(int x, int y) {
	super();
	this.x = x;
	this.y = y;
}


@Override
public int compareTo(Point p1){
	if(this.getPolarAngle()>p1.getPolarAngle()) return 1;
	if(this.getPolarAngle()==p1.getPolarAngle()){
		if(this.radius>p1.radius) return 1;
		else return -1;
	}
	return -1;
}
@Override
public int compare(Point p1, Point p2){
	if(p1.getPolarAngle()>p2.getPolarAngle()) return 1;
	if(p1.getPolarAngle()==p2.getPolarAngle()){
			if(p1.radius>p2.radius) return 1;
			else return -1;
	}
	return -1;
}
public int getX() {
	return x;
}
public void setX(int x) {
	this.x = x;
}
public int getY() {
	return y;
}
public void setY(int y) {
	this.y = y;
}
public double getPolarAngle() {
	return polarAngle;
}
public void setPolarAngle(double polarAngle) {
	this.polarAngle = polarAngle;
}

public double getRadius() {
	return radius;
}
public void setRadius(double radius) {
	this.radius = radius;
}
};


public  class Solution {
	public static int counterClockWise(Point p1, Point p2 ,Point p3){
		int p1x = p1.getX();
		int p1y = p1.getY();
		int p2x = p2.getX();
		int p2y = p2.getY();
		int p3x = p3.getX();
		int p3y = p3.getY();
		return (p2x-p1x)*(p3y-p1y)-(p2y-p1y)*(p3x-p1x);
	}
	
	public static Point stacknextTop(Stack<Point> st){
		Point pp = st.pop();
		Point rr = st.pop();
		st.push(rr);
		st.push(pp);
		return rr;
	}
	public static Point findMinandMakeFirst(ArrayList<Point> list){
		Point min = list.get(0);
		int minIndex =0;
		for(int i=1;i<list.size();i++){
			Point pt = list.get(i);
			if(pt.getY()<min.getY()) {min = pt; minIndex = i;list.remove(minIndex);list.add(0, min);}
			else if(pt.getY()==min.getY()) {
				if(pt.getX()<min.getX()){ min =pt; minIndex =i;list.remove(minIndex);list.add(0, min);}
			}
			}
		
		return min;
		}
	public static void setPolarAngleandSort(ArrayList<Point> list){
		
		Point min = findMinandMakeFirst(list);
		list.remove(0);
		for(Point pt : list){
			double polarAngle = Math.atan2(pt.getY()-min.getY(), pt.getX()-min.getX());
			double XX = pt.getX()- min.getX();
			double YY = pt.getY()-min.getY();
			double radius = XX*XX + YY*YY;
			pt.setPolarAngle(polarAngle);
			pt.setRadius(radius);
		}
		Collections.sort(list);
		list.add(0, min);
	}
	
	public static void printReverseStack(Stack<Point> st){
		Stack<Point> tempStack = new Stack<Point>();
		while(!st.isEmpty()){
			Point ppp = st.pop();
	    	tempStack.push(ppp);
	 	    }
		while(!tempStack.isEmpty()){
	    	Point pon = tempStack.pop();
	    	
	    	System.out.println(pon.getX()+" "+ pon.getY());
	    }
	}
	
	public static int distance(Point p1, Point p2){
		int m = (p1.x-p2.x)*(p1.x-p2.x);
		int n=  (p1.y-p2.y)*(p1.y-p2.y);
		 return m+n;
	}
	public static int binarySearch(ArrayList<Point>list,Point p){
		int index1=0;
		int index2=list.size()-1;
		
		while(index2-index1>1){
			
		int mid = (index1+index2)/2;
		//System.out.println(mid);
		int m = distance(list.get(mid),p);
		int n = distance(list.get(mid+1),p);
		int r = distance(list.get(mid-1),p);
		//System.out.println(index1+"r "+mid+"m "+index2+"n ");
		//System.out.println();
        if(m>r)
		 index1=mid;
        else
        	index2=mid-1;
		/*if(m>=n&& m>=r)	return mid;
        if(r<=m&&m<=n) { index1=mid+1;}
        else if(r>=m&&m>=n) 
        	{ index2=mid-1;}
		
		else
		{
			//{ index1=mid-1;}
			if(distance(p, list.get(index1))>distance(p, list.get(index2))) index2= mid+1;
			else index1=mid-1;
			//if(r>n) return mid-1;
			//else return mid+1;
			//if(m<n && m<r)
			//	System.out.println("Y");
			//System.out.println(index1+"r "+mid+"m "+index2+"zn ");
			//System.out.println(list.get(mid).x+" "+list.get(mid).y+" "+p.x+" "+p.y);
			//System.out.println(r+"r "+m+"m "+n+"zn ");
			//break;	
		}*/
		}
		if(index1==index2) return index1;
		if(index2==index1+1) {
			if(distance(p, list.get(index1))>=distance(p, list.get(index2))) return index1;
			else return index2;
		}
		return index1;
	}
    //ArrayList<Point> pointsList = new ArrayList<Point>();
	public static void main(String[] args) {
		ArrayList<Point> pointsList = new ArrayList<Point>();
		Stack<Point> stack = new Stack<Point>();
		
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
						
		for(int i=1;i<=n;i++){
			
			int x = scan.nextInt();
			int y = scan.nextInt();
			
			Point p = new Point(x, y);
			pointsList.add(p);
			
			}
		
    setPolarAngleandSort(pointsList);	    
		
    
    
    
    
    
    //Scan algorithm 
    
    if(pointsList.size()>3){
    stack.push(pointsList.get(0));
    stack.push(pointsList.get(1));
    stack.push(pointsList.get(2));
    for(int i=3;i<pointsList.size();i++){
    	   	
    	while(counterClockWise(stacknextTop(stack),stack.peek(),pointsList.get(i))<0){
      		stack.pop();
      		if(stack.size()==1) break;
     	}
    	stack.push(pointsList.get(i));
    }
    }
    else {
    	for(Point pint : pointsList){
    		stack.push(pint);
    	}
    }
	int x1=0,x2=0,y1=0,y2 = 0;
    
     
    	
    	//Points are there in the stack;
    	//Stack<Point> tempStack
    	ArrayList<Point> list = new ArrayList<Point>();
    	while(!stack.isEmpty()){
			Point ppp = stack.pop();
			//System.out.println(ppp.getX()+" "+ppp.getY());
	    	list.add(ppp);
	 	    }
      
    	int maxDistance = 0;
    	  int ind1=0;int ind2=0;
    	
    	for(int i=0;i<list.size();i++){
    		//System.out.println(list.get(i).x+" "+list.get(i).y);
    		//System.out.println(i);
    		List<Point> tempList1 =new ArrayList<Point>();
    		List<Point> tempList2 =new ArrayList<Point>();
    		ArrayList<Point> tempList3 =new ArrayList<Point>();
    		tempList1 = list.subList(0, i);
    		tempList2 = list.subList(i+1, list.size());
    		tempList3.addAll(tempList2);
    		tempList3.addAll(tempList1);
    		int index = binarySearch(tempList3,list.get(i));
    		Point temp = tempList3.get(index);
    		if(distance(temp,list.get(i))>maxDistance){
    		int index1;
    		x1=list.get(i).x;
    				x2=temp.x;
    				y1=list.get(i).y;
    				y2=temp.y;
    		//if(index>list.size()-i-1){ index1=index-(list.size()-i-1)-1;}
    		//else{index1= index+i;}
    		// ind1=i;ind2=index1;
    		}
    		
    	}
    	// x1=list.get(ind1).getX();
    	// x2=list.get(ind2).getX();
    //	 y1=list.get(ind1).getY();
    //	 y2=list.get(ind2).getY();
    	if(y1<y2){
    		System.out.println(x1+" "+y1);
    		System.out.println(x2+" "+y2);
    	}
    	else if(y2<y1) {
    		System.out.println(x2+" "+y2);
    		System.out.println(x1+" "+y1);
    		}
    	
    	else{
    		if(x1<x2){
        		System.out.println(x1+" "+y1);
        		System.out.println(x2+" "+y2);
        	}
        	else  {
        		System.out.println(x2+" "+y2);
        		System.out.println(x1+" "+y1);
        		}
    	}
    	
    }
         
    }

