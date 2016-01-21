String str = in.readLine();
char [] A = str.toCharArray();
int n = str.length();

int begin = 0;
int mxLen = 1;
                
dp = new boolean[n][n];
for(i=0; i<n; i++)
    dp[i][i] = true;
                
for(i=0; i<n-1; i++) {
   if(A[i] == A[i+1]) {
      dp[i][i+1] = true;
      being = i;
      mxLen = 2;
   }
}
                                
for(len=3; len<=n; len++) {
   for(i=0; i<=n-len; i++) {
       j = i+len-1;
       if(A[i] == A[j] && dp[i+1][j-1]) {
	  dp[i][j] = true;
          begin = i;
          mxLen = len;
       }
    }
}
                
System.out.println(str.substring(i,mxLen));
                
