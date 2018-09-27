import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Q3 {

    public static void main(String args[]) throws IOException {

        FileReader fr = new FileReader(new File("98-0.txt"));
        BufferedReader br = new BufferedReader(fr);
        String text = "";
        String sz = null;
        while ((sz = br.readLine()) != null) {
            text = text.concat(sz);
        }
        String[] words = text.split(" ");
        int count = 0;
        for(String word : words)
            System.out.println('['+word+']');

        int WORD_COUNT = 3;
        ST<String, Integer> st = new ST<String, Integer>(WORD_COUNT);
        while (!StdIn.isEmpty()){
            // Build symbol table and count frequencies.
            String word = StdIn.readString();
            if (word.length() < WORD_COUNT) continue; // Ignore short keys.
            if (!st.contains(word)) st.put(word, 1);
            else st.put(word, st.get(word) + 1);
        }
        // Find a key with the highest frequency count.
        String max = "";
        st.put(max, 0);
        for (String word : st.keys())
            if (st.get(word) > st.get(max)) max = word;
        StdOut.println(max + " " + st.get(max));

        Map<String, Integer> map = new HashMap<>();
        for(String w : words) {
            Integer n = map.get(w);
            n = (n == null) ? 1 : ++n;
            map.put(w, n);
        }
    }
}

