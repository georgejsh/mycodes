#include<stdio.h>
int a[26];
int min(int a,int b){
	return a>b?b:a;
}
int findval(int i,int val){
	int b,j;
	b=0;
	for(j=i;j<26;j++){
		if(a[j]>val)
			b+=(a[j]-val);
	}
	return b;
}
int main(){
	char s[100005];
	int i,j,k,b,c,n,t,lari;
	for(scanf("%d",&t);t;t--){
		scanf("%s %d",s,&k);
//		printf("%s\n",s);
		for(j=0;j<26;j++)
			a[j]=0;
		for(j=0;s[j]!='\0';j++){
			a[s[j]-'a']++;
		}
		for(i=0;i<26;i++)
			for(j=0;j<25;j++){
				if(a[j]>a[j+1]){
					b=a[j];
					a[j]=a[j+1];
					a[j+1]=b;
				}
			}
		b=1000000,c=0;
		for(j=0;j<26;j++)
			if(a[j]!=0){
				b=min(b,c+findval(j+1,a[j]+k));
				c+=a[j];
			}
		/*	for(j=0;j<26;j++)
			printf("%d ",a[j]);
		printf("\n");	*/	
		//printf("%c ",lari+'a');
		printf("%d\n",b);
	}
	return 0;
}
