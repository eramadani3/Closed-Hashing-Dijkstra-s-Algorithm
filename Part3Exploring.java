import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class Part3Exploring {
    public static void main(String[] args) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("PaulReveresRide.txt");
        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);

        BufferedReader br = new BufferedReader(streamReader);

        Hashtable<Integer, String> wordsHashed = new Hashtable<Integer, String>();

        HashMap<String, Integer> hValues = new HashMap<String, Integer>();

        ArrayList<HashData> data = new ArrayList<HashData>();

        //given constants
        int C = 123;
        int m = 500;

        ArrayList<String> words = new ArrayList<String>();

        try {
            String st = "";

            //initialize the arrayList of words by separating lines by spaces
            while ((st = br.readLine()) != null) {
                for (String s : st.split("\\s+")) {
                    //removes anything that's not an apostrophe, dash, or letter
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

            int startHash = hash;
            while(wordsHashed.get(hash) != null){
                if(s.equals(hValues.get(hash))) break;
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


            data.add(new HashData(hash, s, startHash));
        }


        
        int totalEmptyAdd = 0;
        int emptyAddStreak = 0;
        int highestEmptyAddStreak = 0;
        int nonEmptyAddStreak = 0;
        int highestNonEmptyAddStreak = 0;
        for(int i = 0; i < 1000; ++i){
            String word = wordsHashed.get(i);
            String value = hValues.get(word) == null ? "null" : hValues.get(word).toString();
            System.out.println("Hash address: " + i + ", Hashed Word: " + word + ", Hash Value of Word: " + value);

            if(value == "null"){
                nonEmptyAddStreak = 0;
                totalEmptyAdd++;
                emptyAddStreak++;
                highestEmptyAddStreak = emptyAddStreak > highestEmptyAddStreak ? emptyAddStreak : highestEmptyAddStreak;
            } else {
                emptyAddStreak = 0;
                nonEmptyAddStreak++;
                highestNonEmptyAddStreak = nonEmptyAddStreak > highestNonEmptyAddStreak ? nonEmptyAddStreak : highestNonEmptyAddStreak;
            }
        }

        //create a hash map to keep track of number of occurrences
        HashMap<Integer, Integer> reoccurringHashValues = new HashMap<Integer, Integer>();
        for(String key : hValues.keySet()){
            int hash = hValues.get(key);
            if(reoccurringHashValues.get(hash) == null){
                reoccurringHashValues.put(hash, 1);
            } else {
                int numOccurrences = reoccurringHashValues.get(hash);
                reoccurringHashValues.put(hash, numOccurrences + 1);
            }
        }

        //create an int to keep track of highest occurrences
        int mostOccurrences = 0;
        int mostOccurrencesHash = 0;
        for(Integer hash : reoccurringHashValues.keySet()){
            int value = reoccurringHashValues.get(hash);
            if (value > mostOccurrences) {
                mostOccurrences = value;
                mostOccurrencesHash = hash;
            }
        }

        //find the word placed farthest from its actual hash address and how far away it is
        HashData farthestWord = new HashData(0, "", 0);
        int farthestDistance = 0;
        for(HashData h : data){
            int distance = Math.abs(h.address - h.calculatedHash);
            //account for wrapping
            if(distance >= 500){
                distance = 1000 - distance;
            }
            if(distance > farthestDistance){
                farthestWord = h;
                farthestDistance = distance;
            }
        }


        System.out.println("Number of empty addresses: " + totalEmptyAdd);
        System.out.println("Longest empty streak: " + highestEmptyAddStreak);
        System.out.println("Longest non-empty streak: " + highestNonEmptyAddStreak);
        System.out.println("Hash address that has the most disctinct words: " + mostOccurrencesHash + " with " + mostOccurrences + " distinct words.");
        System.out.println(farthestWord.word + " is placed furthest from its address in the table, being " + Math.abs(farthestWord.calculatedHash - farthestWord.address) + " spaces away from its original hash of " + farthestWord.calculatedHash);
    }


    public static class HashData{
        public int address;
        public String word;
        public int calculatedHash;

        HashData(int address, String word, int calculatedHash){
            this.address = address;
            this.word = word;
            this.calculatedHash = calculatedHash;
        }
    }

}



