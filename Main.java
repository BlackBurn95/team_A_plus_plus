
import java.io.*;
import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.Double.parseDouble;
import static java.lang.String.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                                            //(new FileReader("input.in"));
        StringBuilder out = new StringBuilder();
        StringTokenizer tk;
        Reader.init(System.in);
        //PrintWriter pw = new PrintWriter("output.out", "UTF-8");

    }
    
}

class Tools {

    public static boolean next_permutation(int[] p, int len) {
        int a = len - 2;
        while (a >= 0 && p[a] >= p[a + 1]) {
            a--;
        }

        if (a == -1) {
            return false;
        }

        int b = len - 1;
        while (p[b] <= p[a]) {
            b--;
        }

        p[a] += p[b];
        p[b] = p[a] - p[b];
        p[a] -= p[b];

        for (int i = a + 1, j = len - 1; i < j; i++, j--) {
            p[i] += p[j];
            p[j] = p[i] - p[j];
            p[i] -= p[j];
        }

        return true;
    }
    
    public static int lower_bound(Comparable[] arr, Comparable key) {
        int len = arr.length;
        int lo = 0;
        int hi = len-1;
        int mid = (lo + hi)/2;
        while (true) {
            int cmp = arr[mid].compareTo(key);
            if (cmp == 0 || cmp > 0) {
                hi = mid-1;
                if (hi < lo)
                    return mid;
            } else {
                lo = mid+1;
                if (hi < lo)
                    return mid<len-1?mid+1:-1;
            }
            mid = (lo + hi)/2;
        }
    }

    public static int upper_bound(Comparable[] arr, Comparable key) {
        int len = arr.length;
        int lo = 0;
        int hi = len-1;
        int mid = (lo + hi)/2;
        while (true) {
            int cmp = arr[mid].compareTo(key);
            if (cmp == 0 || cmp < 0) {
                lo = mid+1;
                if (hi < lo)
                    return mid<len-1?mid+1:-1;
            } else {
                hi = mid-1;
                if (hi < lo)
                    return mid;
            }
            mid = (lo + hi)/2;
        }
    }

}

class Reader {

    static BufferedReader reader;
    static StringTokenizer tokenizer;

    static void init(InputStream input) throws UnsupportedEncodingException {
        reader = new BufferedReader(
                new InputStreamReader(input, "UTF-8"));
        tokenizer = new StringTokenizer("");
    }

    static String next() throws IOException {
        while (!tokenizer.hasMoreTokens()) {
            //TODO add check for eof if necessary
            tokenizer = new StringTokenizer(
                    reader.readLine());
        }
        return tokenizer.nextToken();
    }

    static String nextLine() throws IOException {
        return reader.readLine();
    }

    static int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    static double nextDouble() throws IOException {
        return Double.parseDouble(next());
    }

    static long nextLong() throws IOException {
        return Long.parseLong(next());
    }
}

