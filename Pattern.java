class Pattern {
    
    public static ArrayList<Integer>KMP(String str,String pat) {
        int n=str.length(),m=pat.length();
        int[]longestPrefix=computePrefix(pat);
        ArrayList<Integer>kmp=new ArrayList<Integer>();
        for(int i=0,k=0;i<n;i++) {
            while(k>0&&pat.charAt(k)!=str.charAt(i))
                k=longestPrefix[k-1];
            if(pat.charAt(k)==str.charAt(i))
                k++;
            if(k==m) {
                kmp.add(i-m+1);
                k=longestPrefix[k-1];
            }
        }
        return kmp;
    }
    
    public static int[]computePrefix(String pat) {
        int m=pat.length();
        int[]longestPrefix=new int[m];
        for(int i=1,k=0;i<m;i++) {
            while(k>0&&pat.charAt(k)!=pat.charAt(i))
                k=longestPrefix[k-1];
            if(pat.charAt(k)==pat.charAt(i))
                longestPrefix[i]=++k;
            else longestPrefix[i]=k;
        }
        return longestPrefix;
    }
    
}
