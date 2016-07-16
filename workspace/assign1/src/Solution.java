
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class Solution {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		Stack<Integer> myStack=new Stack<Integer>();
		LinkedList<Integer> myLinkList=new LinkedList<Integer>();
		HashSet<Integer> myHashSet= new HashSet<Integer>();
		String command;
		Integer currentDatatype=0;
		while(true)
			{
				command=input.next();
				switch(command.charAt(0))
				{
				case 'p':myStack.push(input.nextInt());
					break;
				case 'o':try
					{
					System.out.println(myStack.pop());
					}catch(EmptyStackException stackemptyexp) {
						System.out.println("e");
					}
					break;
				case 'l': if(currentDatatype==0)
							System.out.println(myStack.size());
						else if(currentDatatype==1)
							System.out.println(myLinkList.size());
						else
							System.out.println(myHashSet.size());
					break;
				case 'x':
						return;
				case 'q':while(!myStack.empty())
						{
							myLinkList.addLast(myStack.pop());
						}			
						currentDatatype=1;
					break;
				case 'a':if(currentDatatype==1)
							myLinkList.addLast(input.nextInt());
						else
							myHashSet.add(input.nextInt());
					break;
				case 'd':try
						{
							if(currentDatatype==1)
								System.out.println(myLinkList.pop());
							else
								myHashSet.remove(input.nextInt());
						}catch(Exception exp) {
							System.out.println("e");
						}
					break;
				case 's': myHashSet.addAll(myLinkList);
						currentDatatype=2;
							break;
				
			}
		//System.out.print(input.nextLine());
		}
	}

}
