package euler;
import java.util.Scanner;

class Node{
    int max;
    int min;
    int sum;
    int rangeStart;
    int rangeEnd;
    public Node(int maxx ,int minn,int summ, int index1,int index2){
        
        max=maxx;
        min=minn;
        sum=summ;
        rangeStart=index1;
        rangeEnd=index2;
       
    }
}
public class TEST {
	public static int n;
	public static int []arr;
    public static Node[] nodeArray;
   
	
	public static Node buildTree(int i,int start,int end){
		if(start==end){
			
			Node a=new Node(arr[start], arr[start], arr[start], start, end);
			nodeArray[i]=a;
			return a;
						
		}
		int mid=(start+end)/2;
		Node n1=buildTree(2*i, start, mid);
		Node n2=buildTree(2*i+1, mid+1, end);
		int min,max,sum=0;
		if(n1.min<n2.min)
			min=n1.min;
		else
			min=n2.min;
		if(n1.max>n2.max)
			max=n1.max;
		else
			max=n2.max;
		sum=n1.sum+n2.sum;
		Node res=new Node(max,min,sum,start,end);
		nodeArray[i]=res;
		return res;
	}
	
	public static int getRangeSum(int gvnStrt,int gvnEnd,int queryStart,int queryEnd,int index){
		
		if(gvnStrt>=queryStart&&gvnEnd<=queryEnd){
			return nodeArray[index].sum;
		}
		
		if(gvnEnd<queryStart||gvnStrt>queryEnd){
			return 0;
		}
		int mid=(gvnEnd+gvnStrt)/2;
		return (getRangeSum(gvnStrt, mid, queryStart, queryEnd, 2*index)+getRangeSum(mid+1, gvnEnd, queryStart, queryEnd, 2*index+1));
		
		
	}

	public static int getRangeMin(int gvnStrt,int gvnEnd,int queryStart,int queryEnd,int index){
		
		if(gvnStrt>=queryStart&&gvnEnd<=queryEnd){
			return nodeArray[index].min;
		}
		
		if(gvnEnd<queryStart||gvnStrt>queryEnd){
			return Integer.MAX_VALUE;
		}
		int mid=(gvnEnd+gvnStrt)/2;
		int min1=getRangeMin(gvnStrt, mid, queryStart, queryEnd, 2*index);
		int min2=getRangeMin(mid+1, gvnEnd, queryStart, queryEnd, 2*index+1);
		if(min1<min2)
			return min1;
		else
			return min2;
		
	}
	
	public static int getRangeMax(int gvnStrt,int gvnEnd,int queryStart,int queryEnd,int index){
		
		if(gvnStrt>=queryStart&&gvnEnd<=queryEnd){
			return nodeArray[index].max;
		}
		
		if(gvnEnd<queryStart||gvnStrt>queryEnd){
			return Integer.MIN_VALUE;
		}
		int mid=(gvnEnd+gvnStrt)/2;
		int max1=getRangeMax(gvnStrt, mid, queryStart, queryEnd, 2*index);
		int max2=getRangeMax(mid+1, gvnEnd, queryStart, queryEnd, 2*index+1);
		if(max1>max2)
			return max1;
		else
			return max2;
		
	}
	public static Node doUpdate(int gvnStrt,int gvnEnd,int gvnIndex, int newVal,int index,int diff){
		int min,max;
		if(gvnStrt==gvnIndex&&gvnEnd==gvnIndex){
			nodeArray[index].min=newVal;
			nodeArray[index].max=newVal;
			nodeArray[index].sum=newVal;
			return nodeArray[index];
		}
		
		if(gvnIndex<gvnStrt||gvnIndex>gvnEnd){
			return nodeArray[index];
		}
		int mid=(gvnEnd+gvnStrt)/2;
		Node n1=doUpdate(gvnStrt, mid, gvnIndex,newVal, 2*index,diff );
		Node n2=doUpdate( mid+1,gvnEnd, gvnIndex,newVal, 2*index + 1,diff);
		if(n1.min<n2.min)
			min=n1.min;
		else
			min=n2.min;
		
		nodeArray[index].min=min;
		if(n1.max<n2.max)
			max=n2.max;
		else
			max=n1.max;
		
		nodeArray[index].max=max;
		nodeArray[index].sum=nodeArray[index].sum+diff;
		return nodeArray[index];
	}
	
	
    public static void main(String[] args) {
       
    	
        Scanner sc=new Scanner(System.in);
        n=sc.nextInt();
        int m=sc.nextInt();
        arr=new int [n+1];
        nodeArray=new Node[4*n];
       
       
       
       
        for(int i=1;i<n+1;i++){
            arr[i]=sc.nextInt();
           
        }
       
        buildTree(1,1,n);
    
        for(int i=0;i<m;i++){
            String s=sc.next();
            if(s.equals("MAX")){
                int a= sc.nextInt();
                int b=sc.nextInt();
                int max=getRangeMax(1,n,a+1,b+1,1);
                System.out.println(max);
            }
            else if(s.equals("MIN")){
                int a= sc.nextInt();
                int b=sc.nextInt();
                int min=getRangeMin(1,n,a+1,b+1,1);
                System.out.println(min);
            }
            else if(s.equals("SUM")){
                int a= sc.nextInt();
                int b=sc.nextInt();
                int sum=getRangeSum(1,n,a+1,b+1,1);
                System.out.println(sum);

            }
            else if(s.equals("UPDATE")){
                int a= sc.nextInt();
                int num=sc.nextInt();
                int diff=num-arr[a+1];
                doUpdate(1,n,a+1,num,1,diff);

            }
        }
        sc.close();
    }

}