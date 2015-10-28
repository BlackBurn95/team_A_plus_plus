class Alignment {
    
    static int[][]computeMatchAndAlignment(String a,String b) {
        int[][]match=new int[a.length()+1][b.length()+1];
        int i,j;
        for(j=1;j<=b.length();j++)
            match[0][j]=-j;
        for(i=1;i<=a.length();i++) {
            match[i][0]=-i;
            for(j=1;j<=b.length();j++)
                match[i][j]=Math.max(match[i-1][j-1]+score(a.charAt(i-1),b.charAt(j-1)), Math.max(match[i-1][j]-1, match[i][j-1]-1));
        }
        return match;
    }
    
    static int computeLCS(String a,String b) {
        //Longest Common Subsequence
        int[][]match=new int[a.length()+1][b.length()+1];
        int i,j;
        for(i=1;i<=a.length();i++)
            for(j=1;j<=b.length();j++)
                match[i][j]=Math.max(match[i-1][j-1]+LCS_Score(a.charAt(i-1),b.charAt(j-1)), Math.max(match[i-1][j], match[i][j-1]));
        return match[a.length()][b.length()];
    }
    
    static int score(char c1,char c2) {
        return c1==c2?2:-1;
    }
    
    static int LCS_Score(char c1,char c2) {
        return c1==c2?1:-1000000000;
    }
    
}