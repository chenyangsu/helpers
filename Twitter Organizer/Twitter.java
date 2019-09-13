

import java.util.ArrayList;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public class Twitter {
    
    private ArrayList<Tweet> tweets;
    
    // Twitter constructor
    public Twitter() {
        this.tweets = new ArrayList<Tweet>();
    }
    
    
    // loadDB - must be non-static as a static method has no access to instance variables
    public void loadDB(String fileName) {       
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String currentLine = br.readLine();
            while (currentLine != null) {
                String[] parts = currentLine.split("\t");  // separate the String by its tab characters
                // use the 4 elements split from the String to initialize the 4 attributes of Tweet object
                Tweet t = new Tweet(parts[0], parts[1], parts[2], parts[3]);
                if (t.checkMessage()) {  //if the Tweet is valid (btwn 0 and 15 words), add it to ArrayList of Tweets
                    this.tweets.add(t);
                }
                currentLine = br.readLine();
            }
            // System.out.println(tweets); //for checking that the correct tweets (58 of them) are printed
            sortTwitter(); // sort the Tweets
            br.close();
            fr.close();
        }
        catch (IOException e) {
            System.out.println("There was an error in accessing the file");
        }
    }
    
    // sortTwitter - sorts the Tweets by the date/time: earliest to latest
    public void sortTwitter() {
        for (int i=0; i<this.tweets.size(); i++) {
            for (int j= i+1; j<this.tweets.size(); j++) { // start j at the Tweet right after i
                if (this.tweets.get(j).isBefore(this.tweets.get(i))) {
                    Tweet temp = this.tweets.get(i);  // store the tweet at position i in a temp Tweet
                    this.tweets.set(i, tweets.get(j));  // set the tweet at i as the tweet at position j
                    this.tweets.set(j, temp);  // set the tweet as position j as the one originally in i
                    // i.e. tweets posted at a later time but located at an earlier position in the list
                    // are moved to the back of the ArrayList
                    // by being swapped with an earlier tweet that was originally in a position after it
                }
            }
            
        }
    }
    
    //getSizeTwitter 
    public int getSizeTwitter() {
        return this.tweets.size();
    }
    
    // getTweet
    public Tweet getTweet(int index) {
        return this.tweets.get(index);
    }
    
    //printDB
    public String printDB() {
        String s = "";
        for (int i=0; i<this.tweets.size(); i++) {  // use toString method from Tweet class 
            // as it already has the 4 attributes sorted into 4 columns using tab characters
            s = s + this.tweets.get(i).toString() + "\n";
        }
        return s;
    }
    
    // rangeTweets - returns a new ArrayList of Tweets containing tweets posted between the time of 
    // tweet1 and tweet2. There is no guarantee that tweet1 is before tweet2
    public ArrayList<Tweet> rangeTweets(Tweet tweet1, Tweet tweet2) {
        int lower = 0;
        int upper = 0;
        // 2 possibilities: tweet1 is before tweet2 OR tweet2 is before tweet1
        if (tweet1.isBefore(tweet2)) {
            for (int i=0; i<this.tweets.size(); i++) {
                if (this.tweets.get(i).getDate().equals(tweet1.getDate()) 
                        && this.tweets.get(i).getTime().equals(tweet1.getTime())) {
                    lower = i;                                              // get the index where tweet 1 is
                }
            }
            for (int i=0; i<this.tweets.size(); i++) {
                if (this.tweets.get(i).getDate().equals(tweet2.getDate()) 
                        && this.tweets.get(i).getTime().equals(tweet2.getTime())) {
                    upper = i;                                              // get index where tweet 2 is
                }
            }
            // tweet2 is before tweet1
        } else if (tweet2.isBefore(tweet1)) {
            for (int i=0; i<this.tweets.size(); i++) {
                if (this.tweets.get(i).getDate().equals(tweet2.getDate()) 
                        && this.tweets.get(i).getTime().equals(tweet2.getTime())) {
                    lower = i;                                             // get the index where tweet 2 is
                }
            }
            for (int i=0; i<this.tweets.size(); i++) {
                if (this.tweets.get(i).getDate().equals(tweet1.getDate()) 
                        && this.tweets.get(i).getTime().equals(tweet1.getTime())) {
                    upper = i;                                              // get the index where tweet 1 is
                }
            }
        }
        ArrayList<Tweet> newList = new ArrayList<Tweet>();
        for (int i=lower; i<=upper; i++) {  // store the tweets between the two indexes (inclusive) into an ArrayList
            newList.add(this.tweets.get(i));  // add each Tweet to the ArrayList
        }
        return newList;
        
    }
    
    
    // saveDB - gets one parameter, the name of the file, and writes in the file
    public void saveDB(String fileName) {
        try {
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(printDB());    // write the database into a new file        
            bw.close();
            fw.close();
            
        } catch (IOException e) {
            System.out.println("An error occurred while saving");
        }
    }
    
    // trendingTopic
    // returns the word (that is not a stop word) that is the most frequent in the tweets from the data base
    // If a word appears more than once in a tweet message it is only counted once
    public String trendingTopic() {
        ArrayList<String> list = new ArrayList<String>();
        HashMap<String, Integer> map = new HashMap<String, Integer>();      
        for (int i=0; i<this.tweets.size(); i++) {
            
            HashSet<String> set = new HashSet<String>();  // new HashMap at the start of each loop (i.e. new message)
            // split the Tweet message and store into an array
            String[] arr = getTweet(i).getMessage().split(" "); 
            
            for (int j=0; j<arr.length; j++) {
                // going through the array's elements (the elements are storing each word of the tweet) 
                // change each element into lowercase 
                String allLowerCase = arr[j].toLowerCase();  // store the result into a String
                arr[j] = allLowerCase;                // set the element in array to the lowerCase String
                
                // now store the words that are not stop words into a Hashset
                // first we need to get rid of the punctuation present at the end of similar words
                if (!Tweet.isStopWord(arr[j])) {  // only words that are not stop words will get added to the 
                                                  // HashSet at the end. As those elements in the array
                    // containing words that are stop words do not go through this if statement and thus
                    // are not stored in the HashSet in the end which is similar to deleting them from the array
                    char c = arr[j].charAt(arr[j].length()-1);  // store the last character of word into a char
                    if (c==',' || c=='.' || c==';' || c==':') {  
                    // if the char is a punctuation remove it by reconstructing the String from scratch and 
                    // not adding the last char in (i.e. the punctuation)
                        String editedWord = "";  // start with an empty String
                        for (int k=0; k<arr[j].length()-1; k++) {  // iterate to 1 less than the original String
                            // length. This guarantees that the last char (the punctuation) is left out of the
                            // new String word. e.g word = "hi," so iterate from 0 to length 3 minus 1 = 2
                            // therefore only loop twice: for index 0 and index 1 to form "hi"
                            editedWord += arr[j].charAt(k);  // form the String by adding each char
                        }
                        arr[j] = editedWord;  // store the new word without the punctuation back to its array 
                        // in its original position
                    }
                    // now add the word into the HashSet
                    set.add(arr[j]);  // iterating through j will add all the words of one Tweet message
                    // into the HashSet. 
                }
            }
            // iterate through the HashSet which contains the words (with no repeated words) of one Tweet message
            // and update the HashMap with the HashSet String values
            for (String word: set) {
                if (!map.containsKey(word)) {  // if the word is not a key yet create a new key for that word
                                               // and give it value 1
                    map.put(word,1);  
                } else {  // if the key already exists
                    int value = map.get(word); // get the value associated with the key
                    map.put(word,value+1);  // increase the value associated with the key by 1
                }
            }          
        }
        
        System.out.println(map);  // Testing: for seeing the full map. i.e. each word and the number
        // of times it occurs in the database
        
        // now we need find out which word is most frequently mentioned
        // first we take a guess value of 1 as each word in the database must be mentioned at least once
        int guess = 1;
        String trendingWord = "";
        // keySet(): returns a Set view of the keys contained in this map
        for (String key: map.keySet()) {  // loop through the keys
            if (map.get(key) > guess) {  // if the value associated to the key is greater that our guess
                // then update our guess with this new value
                guess = map.get(key);
                trendingWord = key;  // also update the word with the key value
            }
        }
        return trendingWord; // after iterating through the map, the word must now be the most popular
    }
      
    
    public static void main(String[] args) {
        // with this method of commenting, I can test the code by just adding // to /*
        // and turn example code back into a comment by getting rid of the // I added initially
        
        // Example 1       
        /*
        Twitter example = new Twitter();
        example.loadDB("tweets.txt");
        //*/
        
        // Example 2
        /*
        Twitter example = new Twitter();
        Tweet.loadStopWords("stopWords.txt");
        example.loadDB("tweets.txt");
        System.out.println("The number of tweets is: " + example.getSizeTwitter());
        //*/
        
        // Example 3
        /*
        Twitter example = new Twitter();
        Tweet.loadStopWords("stopWords.txt");
        example.loadDB("tweets.txt");
        System.out.println(example.printDB());
        //*/
        
        //Example 4
        /*
        Twitter example = new Twitter();
        Tweet.loadStopWords("stopWords.txt");
        example.loadDB("tweets.txt");
        System.out.println(example.rangeTweets(example.getTweet(4), example.getTweet(2)));
        //*/
        
        // Example 5
        ///*
        Twitter example = new Twitter();
        Tweet.loadStopWords("stopWords.txt");
        example.loadDB("tweets.txt");
        System.out.println(example.trendingTopic());
        //*/
        
        
    }
    
}