import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Hashtable;

public class PaulRevere {
    public static void main(String[] args) {
        ClassLoader cLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = cLoader.getResourceAsStream("PaulReveresRide.txt");
        InputStreamReader sreader = new InputStreamReader(is, StandardCharsets.UTF_8);

        BufferedReader buffer = new BufferedReader(sreader);

        Hashtable<Integer, String> wordsHashed = new Hashtable<Integer, String>();

        int C = 123;
        int m = 500;

        ArrayList<String> words = new ArrayList<>();

        try{
            String string = "";
            while((string = buffer.readLine()) != null){
                for(String s : string.split("\\s+")){
                    String newString = s.replaceAll("[^'\\\\-a-zA-Z]*", "");
                    words.add(newString);
                }
            }
        } catch (IOException i){
            System.out.println("IOException");
        }
        boolean full = false;
        for(String s : words){
            int hash = 0;
            for(Character ch: s.toCharArray()){
                hash = (hash * C + (int) (ch)) % m;
            }

            int start = hash;
            while(wordsHashed.get(hash) != null){
                hash++;
                if(start == hash){
                    full = true;
                    break;
                }
                if(hash >= 1000){
                    hash = 0;
                }
            }
            if(full){
                break;
            }
            System.out.println("The word " + s + " was added with the hash: " + hash);
            wordsHashed.put(hash,s);
        }

    }
}
