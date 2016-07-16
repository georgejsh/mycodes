import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
class Node {
	int i, j, k;
	Node(int i, int j, int k) {
		this.i = i;
		this.j = j;
		this.k = k;
	}
	public int hashCode() {
		// System.out.println("In hashcode");
		int hashcode = 0;
		hashcode = i <<14 + j * 16 + k;
		//hashcode += item.hashCode();
		return hashcode;
	}

	public boolean equals(Object obj) {
		//System.out.println("In equals");
		if (obj instanceof Node) {
			Node pp = (Node) obj;
			return (i == pp.i && j == pp.j && k == pp.k);
		} else {
			return false;
		}
	}
}
public class Solution {
	static int n, m, t;
	static char s[][];
	static long a[][];

	static long getnoofX(int r1, int c1, int r2, int c2) // this function return the number of X in the input submatrix
	{
		return a[r2][c2] - a[r1 - 1][c2] - a[r2][c1 - 1] + a[r1 - 1][c1 - 1];
	}
	static void merge(int a[], int b[], int c[], int order) {
		int i = 0, j = 0, k = 0;
	//	System.out.println(1);
		while (a[i] != -1 && b[j] != -1 && k < 17) {
			if (a[i] > b[j] || (order == -1 && a[i] < b[j])) {
				c[k] = a[i];
				k++;
				i++;
			} else {
				c[k] = b[j];
				k++;
				j++;
			}
		//	System.out.println("c"+c[k-1]);
		}
		while (a[i] != -1 && k < 17) {
			c[k] = a[i];
			k++;
			i++;
		}
		while (b[j] != -1 && k < 17) {
			c[k] = b[j];
			k++;
			j++;
		}
		c[k] = -1;
	}
	static long findDanger(int r1, int c1, int r2, int c2) {
		//write your code here
		int i, j = 0, k = 0, l=0;
		//System.out.println(r1 + " " + c1 + r2 + c2);
		long tempX = 0, Cextra = 0, tX = 0, tempXCount=0;
		if (c1 == c2) {
			//for (l = 0; l < n; l++)
			{
				j = 0;
				for (i = l; i <= n; i++)
				{
					j += s[i][c1] - '0';
					if (j == t) k++;
				}
			}
	//		System.out.println(r1 + " " + c1 + r2 + c2);
//System.out.println("k" + k);
			return k;
		} else {
			int tempc1[][], tempc2[][], Tres[], Tresp[],sttemp[];
			int mid = (c1 + c2) / 2;
			tempc1 = new int[2505][17];
			tempc2 = new int[2505][17];
			Tres = new int[18];
			Tresp = new int[18];
			HashMap < Node, Long > temp = new HashMap < Node, Long > ();
			tX = findDanger(r1, c1, r2, mid) + findDanger(r1, mid + 1, r2, c2);
			 	for (i = 0; i < n; i++) {
				for (j = mid, k = 0; j >= c1; j--) {
					if (s[i][j] == '1' && k < 17) tempc1[i][k++] = j;
					else if (k == 16) {
						tempc1[i][k] = -1;
						break;
					}
				}
				tempc1[i][k] = -1;
			}
			for (i = 0; i < n; i++) {
				for (j = mid + 1, k = 0; j <= c2; j++) {
					if (s[i][j] == '1' && k <= 16) tempc2[i][k++] = j;
					else if (k == 16) {
						tempc2[i][k] = -1;
						break;
					}
				}
				tempc2[i][k] = -1;
			}
			for (i = 0; i < n; i++) {
				for (j = i; j < n; j++) {
				//	System.out.println("1aaa");
					if (j > i) {
						merge(Tres, tempc1[j], Tresp, 1);
						sttemp=Tresp;
						Tresp=Tres;
						Tres=sttemp;
					} else {
						for(k=0;tempc1[j][k]!=-1;k++)
						Tres[k] = tempc1[j][k];
						Tres[k]=-1;
					}
					
					//if(Tres[0]!=-1)
					// Tresp[0]=
					//for (k = 1; k < 17 && Tres[k]!-=1 ; k++) {
					//	if(Tres[k]!=Tresp[k=1])
					//}
					
					for (k = 0,l=0; k < 16; ) 
					{
			//			System.out.println("k"+Tres[k]);
						if(Tres[k]==-1) {
							if(k==0) {
								tempX = k;
								tempXCount = mid-c1+1 ;	
							}
							else {
								tempX = k;
								tempXCount = Tres[k-1]-c1+1;	
							}
					//		System.out.println(i + " " + j + " " + k + "ed "+Tres[k]+ " " + tempX + " " +  tempXCount);
							temp.put(new Node(i, j, (int) tempX), tempXCount * 1L);
							break;
						}
						l=k;
						
						while(Tres[k]==Tres[l])
						 l++;
						if (k > 0 && Tres[k] != -1) {
							tempX = k;
							tempXCount = Tres[k-1]-Tres[k]  ;
						} else if (k == 0 && Tres[k] != -1) {
							tempX = k;
							tempXCount = mid-Tres[k];
						}
					//	System.out.println(i + " " + j + " " + k+" " + l + "d "+Tres[l]+ " " + tempX + " " +  tempXCount);
						temp.put(new Node(i, j, (int) tempX), tempXCount * 1L);
						k=l;
						
					}
					
				}
			}
			/*for (i = 0; i < n; i++) {
				for (j = i; j < n; j++) {
					for (k = 0; k < 16; k++) {
						if (temp.get(new Node(i, j, k)) != null) System.out.println(i + " " + j + " " + k + "v " + temp.get(new Node(i, j, k)));
					}
				}
			}*/
		//	return 0;
			for (i = 0; i < n; i++) {
				for (j = i; j < n; j++) {
					if (j > i) {
						merge(Tres, tempc2[j], Tresp, -1);
						sttemp=Tresp;
						Tresp=Tres;
						Tres=sttemp;
					} else {
						for(k=0;tempc2[j][k]!=-1;k++)
						Tres[k] = tempc2[j][k];
						Tres[k]=-1;
					}
					for (k = 0,l=0; k < 16; )
					{
						if(Tres[k]==-1) {
							if(k==0) {
								tempX = k;
								tempXCount = c2-mid;	
							}
							else {
								tempX = k;
								tempXCount = c2-Tres[k-1]+1;	
							}
				//			System.out.println(i + " " + j + " " + k + "ed "+Tres[k]+ " " + tempX + " " +  tempXCount);
				//			if ((t - (int) tempX) >= 0 && temp.get(new Node(i, j, t - (int) tempX)) != null) 
				//				Cextra += (tempXCount) * (temp.get(new Node(i, j, t - (int) tempX)));
				//			System.out.println("Cex" + Cextra);
							break;
						}
						l=k;
						
					//	while(Tres[k]==Tres[l])
					//	 l++;
						if (k > 0 && Tres[k] != -1) {
							tempX = k;
							tempXCount = Tres[k]-Tres[k-1]  ;
						} else if (k == 0 && Tres[k] != -1) {
							tempX = k;
							tempXCount = Tres[k]-mid-1;
						}
				//		System.out.println(i + " " + j + " " + k + "d "+Tres[l]+ " " + tempX + " " +  tempXCount);
				//		if ((t - (int) tempX) >= 0 && temp.get(new Node(i, j, t - (int) tempX)) != null) 
				//			Cextra += (tempXCount) * (temp.get(new Node(i, j, t - (int) tempX)));
//System.out.println("Cex" + Cextra);
						k=l;
						
					}
					//Tresp=Tres;
					
				}
			}
	//		System.out.println(r1 + " " + c1 + r2 + c2);
	//		System.out.println("v" + Cextra + tX);
			return Cextra + tX;
		}
		//System.out.println(r1+" "+c1+r2+c2);
	}

	public static void main(String args[]) throws IOException {
		s = new char[1005][1005];
		a = new long[1005][1005];
		Scanner scanner = new Scanner(System. in );
		n = scanner.nextInt();
		m = scanner.nextInt();
		t = scanner.nextInt();
		int i, j, k = 0;
		
		for (i = 0; i < n; i++) {
			String str = scanner.next();
			j = 0;
			while (j < m) {
				//str = scanner.next();
				if (str.charAt(j) == 'X') {
					if (k == 0) s[i][j] = '1';
					else s[j][i] = '1';
				} else {
					if (k == 0) s[i][j] = '0';
					else s[j][i] = '0';
				}
				j++;
			}

		}
		for (i = 1; i <= n; i++) //store the solution
		{
			for (j = 1; j <= m; j++) {

				a[i][j] = a[i - 1][j] + a[i][j - 1] - a[i - 1][j - 1] + s[i - 1][j - 1] - '0';
				//					System.out.println(a[i][j]);
			}
		}

		System.out.println(findDanger(0, 0, n - 1, m - 1));

	}
}