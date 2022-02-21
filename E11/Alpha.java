import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Alpha { 

    static HashSet<String> words = new HashSet<>();
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            Scanner tokens = new Scanner(in.nextLine());
            while (tokens.hasNext()) {
                String token = tokens.next();
                // System.out.println(token);
                // System.out.println(isWord(token));
                if (isWord(token)) { 
                    // System.out.println(getWord(token));
                    words.add(getWord(token));
                }
            }
        }
        in.close();

        ArrayList<String> wordsSorted = new ArrayList<>(words);
        Collections.sort(wordsSorted);
        
        for (String word : wordsSorted) {
            System.out.println(word);
        }
    }

    static boolean isWord(String token) {
        Pattern pattern = Pattern.compile("[\"]?([A-Z]|[a-z])[a-z]*[\']?[a-z]*[\"]?[\\,\\.\\:\\;\\?\\!]?");
        Matcher match = pattern.matcher(token);
        return(match.matches());        
    }

    static String getWord(String token) {
        Pattern pattern = Pattern.compile("([A-Z]|[a-z])[a-z]*[\']?[a-z]*");
        Matcher match = pattern.matcher(token);
        // String s = "";
        // while (match.find()) {
        //     s = match.group();
        //     // s now contains "BAR"
        // }
        match.find();
        
        return(match.group().toLowerCase());
    }
}