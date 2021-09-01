import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class Part2Hashing {
    public static void main(String[] args) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("PaulReveresRide.txt");
        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);

        BufferedReader br = new BufferedReader(streamReader);

        Hashtable<Integer, String> wordsHashed = new Hashtable<Integer, String>();
        HashMap<String, Integer> hValues = new HashMap<String, Integer>();
        int C = 123;
        int m = 500;

        ArrayList<String> words = new ArrayList<String>();

        try {
            String st = "";
            while ((st = br.readLine()) != null) {
                for (String s : st.split("\\s+")) {
                    String formattedString = s.replaceAll("[^'\\-a-zA-Z]*", "");
                    words.add(formattedString);
                }
            }
        } catch (IOException i) {
            System.out.println("IOException");
        }

        //hash the words
        boolean tableFull = false;
        for (String s : words) {
            int hash = 0;
            for (Character c : s.toCharArray()) {
                hash = (hash * C + (int) (c)) % m;
            }

            //add to table
            int startHash = hash;
            while(wordsHashed.get(hash) != null){
                if(s.equals(wordsHashed.get(hash))) break;
                ++hash;
                if(startHash == hash){
                    tableFull = true;
                    break;
                }
                if(hash >= 1000){
                    hash = 0;
                }
            }

            if(tableFull) break;

            wordsHashed.put(hash, s);


            hValues.put(s, startHash);
        }


        //print the hash table
        for(int i = 0; i < 1000; ++i){
            String word = wordsHashed.get(i);
            String value = hValues.get(word) == null ? "null" : hValues.get(word).toString();
            System.out.println("Hash address: " + i + ", Hashed Word: " + word + ", Hash Value of Word: " + value);
        }
    }
}

