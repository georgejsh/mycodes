package euler;

public class p39rttri {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a[]= new int[1000];
		int i,j,k;
		for(i=1;i<1000;i++){
			for(j=1;j<1000;j++){
				for(k=1;k<1000;k++){
					if(i+j+k>=1000)
						break;
					if(i*i+j*j==k*k)
						a[i+j+k]++;
				}	
			}	
		}
		j=a[1];
		k=1;
		for(i=1;i<1000;i++){
			if(j<a[i]){
				j=a[i];
				k=i;
			}
				
		}
		System.out.println(k);
	}

}
