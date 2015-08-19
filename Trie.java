class Trie {
    
    public Trie[]child;
    public boolean isLeaf;
    private static char start;
    private static int length,maxLength,minLength;
    private int minIndex,maxIndex;
    private char current;
    private static String str;
    
    public Trie(char s,char e) {
        start=(char)Math.min(s, e);
        minLength=length=Math.max(s, e)-start+1;
        minIndex=length-1;
        child=new Trie[length];
    }
    
    public Trie() {
        child=new Trie[length];
        minIndex=length-1;
        minLength=length;
    }
    
    public void add(String str) {
        checkAndInsert(str,0);
    }
    
    public void add(char[]str) {
        checkAndInsert(str,0);
    }
    
    private void checkAndInsert(String str,int index) {
        if(index==str.length()) {
            isLeaf=true;
            maxLength=Math.max(maxLength, str.length());
            minLength=Math.min(minLength, str.length());
            return;
        }
        if(child[str.charAt(index)-start]==null) {
            child[str.charAt(index)-start]=new Trie();
            minIndex=Math.min(minIndex, str.charAt(index)-start);
            maxIndex=Math.max(maxIndex, str.charAt(index)-start);
            child[str.charAt(index)-start].current=str.charAt(index);
        }
        child[str.charAt(index)-start].checkAndInsert(str, index+1);
    }
    
    private void checkAndInsert(char[]str,int index) {
        if(index==str.length) {
            isLeaf=true;
            maxLength=Math.max(maxLength, str.length);
            minLength=Math.min(minLength, str.length);
            return;
        }
        if(child[str[index]-start]==null) {
            child[str[index]-start]=new Trie();
            minIndex=Math.min(minIndex, str[index]-start);
            maxIndex=Math.max(maxIndex, str[index]-start);
            child[str[index]-start].current=str[index];
        }
        child[str[index]-start].checkAndInsert(str, index+1);
    }
    
    public boolean isExistWord(String str) {
        return checkWorld(str,0);
    }
    
    public boolean isExistWord(char[]str) {
        return checkWorld(str,0);
    }
    
    private boolean checkWorld(String str,int index) {
        if(index==str.length())
            return isLeaf;
        if(child[str.charAt(index)-start]==null)
            return false;
        return child[str.charAt(index)-start].checkWorld(str, index+1);
    }
    
    private boolean checkWorld(char[]str,int index) {
        if(index==str.length)
            return isLeaf;
        if(child[str[index]-start]==null)
            return false;
        return child[str[index]-start].checkWorld(str, index+1);
    }
    
    public boolean isExistPrefix(String str) {
        return checkPrefix(str,0);
    }
    
    public boolean isExistPrefix(char[]str) {
        return checkPrefix(str,0);
    }
    
    private boolean checkPrefix(String str,int index) {
        if(index==str.length())
            return true;
        if(child[str.charAt(index)-start]==null)
            return false;
        return child[str.charAt(index)-start].checkPrefix(str, index+1);
    }
    
    private boolean checkPrefix(char[]str,int index) {
        if(index==str.length)
            return true;
        if(child[str[index]-start]==null)
            return false;
        return child[str[index]-start].checkPrefix(str, index+1);
    }
    
    public String getMinimumString() {
        str="";
        return minimumString(child[minIndex]);
    }
    
    private String minimumString(Trie t) {
        str+=t.current;
        if(t.isLeaf) return str;
        return minimumString(t.child[t.minIndex]);
    }
    
    public String getMaximumString() {
        str="";
        return maximumString(child[maxIndex]);
    }
    
    private String maximumString(Trie t) {
        str+=t.current;
        if(t.child[t.maxIndex]==null) return str;
        return maximumString(t.child[t.maxIndex]);
    }
    
    public int getMinimumLength() {
        return minLength;
    }
    
    public int getMaximumLength() {
        return maxLength;
    }
    
}
