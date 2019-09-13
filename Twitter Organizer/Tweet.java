
import java.util.HashSet;
import java.util.ArrayList;
import java.io.*;

public class Tweet {
    
    private String userAccount;  // stores the user's ID
    private String date;         // stores the date the tweet was tweeted
    private String time;         // stores the time tweet was tweeted
    private String message;      // stores the tweeted message
    private static HashSet<String> stopWords;
    
    // constructor
    public Tweet(String userAccount, String date, String time, String message) {
        this.userAccount = userAccount;
        this.date = date;
        this.time = time;
        this.message = message;
    }
    
    // checkMessage - checks whether the tweet is valid or not
    public boolean checkMessage() {
        if (this.stopWords == null) {  // i.e HashSet has not been initialized yet
            throw new NullPointerException("Error checking the stopWords database: " + 
                                           "The file of stopWords has not been loaded yet");  
        }
        // the split method splits a String and stores the split parts into an array of type String
        String[] parts = this.message.split(" ");
        int numStopWords = 0;
        for (int i=0; i<parts.length; i++) {   // count the number of stopWords in the message
            if (isStopWord(parts[i]) == true) {
                numStopWords++;
            }
        }  
        // the size of the message is the total size minus the number of stopWords
        int messageSize = parts.length - numStopWords;  
        if (messageSize < 16 && messageSize > 0) {  // the message is between 0 and 15 words (inclusive)
            return true;
        } else {
            return false;
        }
    }
    
    // isStopWord helper method
    public static boolean isStopWord(String s) {
        ArrayList<String> list = new ArrayList<String>();
        // must use try/catch because FileIO is very error prone
        try {
            FileReader fr = new FileReader("stopWords.txt");
            BufferedReader br = new BufferedReader(fr);
            String currentLine = br.readLine();
            while (currentLine != null) {
                list.add(currentLine);   // store the stopWords from the file into an ArrayList
                currentLine = br.readLine();
            }
            br.close();
            fr.close();   
        } catch (IOException e) {
            System.out.println("There was an error accessing the file");
        }
        
        // declare and initialize my 4 Strings to empty String
        String comma = "";
        String period = "";
        String semicolon = "";
        String colon = "";
        for (int i=0; i<list.size(); i++) {
            comma = list.get(i) + ",";  // the comma String is the stopWord + a comma at the end
            period = list.get(i) + ".";  // the period String is the stopWord + a period at the end
            semicolon = list.get(i) + ";";
            colon = list.get(i) + ":";
            // if the String s (input) is the same as the stopWord with no punctuation at end, or with
            // the 4 types of punctuation at end, then return true. i.e. the input s is a stopWord
            if (s.equalsIgnoreCase(list.get(i)) || s.equalsIgnoreCase(comma) || s.equalsIgnoreCase(period) 
                    || s.equalsIgnoreCase(semicolon) || s.equalsIgnoreCase(colon)) {
                return true;
            }
            // reinitialize the 4 Strings back to empty String before start of next lop
            comma = "";
            period = "";
            semicolon = "";
            colon = "";
        }
        return false;  // i.e the input s is not a stopWord
        
        
    }
    
    
    // get methods
    public String getDate() {
        return this.date;
    }
    
    public String getTime() {
        return this.time;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public String getUserAccount() {
        return this.userAccount;
    }
    
    // toString method
    public String toString() {
        String newString = this.userAccount + "\t" + this.date + "\t" + this.time + "\t" + this.message;
        return newString;
    }
    
    
    // isBefore method - returns true if "this" Tweet was posted earlier than input Tweet
    public boolean isBefore(Tweet input) {
        String[] thisDateString = this.date.split("-");  // split the String attribute date into array of length 3
        String[] inputDateString = input.date.split("-"); // split input into array
        int[] thisDateInt = new int[3];  // declare the int array for "this"
        int[] inputDateInt = new int[3];  // declare the int array for the input 
        
        String[] thisTimeString = this.time.split(":");  // split the String attribute time into array
        String[] inputTimeString = input.time.split(":");
        int[] thisTimeInt = new int[3];  // declare the int array
        int[] inputTimeInt = new int[3];
        
        for (int i=0; i<thisDateString.length; i++) {
            // for the 3 Strings in each array, parse the values to ints and store into int array
            thisDateInt[i] = Integer.parseInt(thisDateString[i]);
            inputDateInt[i] = Integer.parseInt(inputDateString[i]);
            thisTimeInt[i] = Integer.parseInt(thisTimeString[i]);
            inputTimeInt[i] = Integer.parseInt(inputTimeString[i]);
        }
        
        // for thisDateInt: index 0 stores the year (i.e. 1998), index 1 stores the month (i.e. 10), 
        // index 2 stores the day(i.e 20)
        // for thisTimeInt: format is HH:MM:SS
        // i.e index 0 stores the hour, index 1 the minute, index 2 the seconds
        if (thisDateInt[0] < inputDateInt[0]) {  // comparing the date, specifically the year
            return true;  
        } else if (thisDateInt[0] == inputDateInt[0]) {  // same year
            if (thisDateInt[1] < inputDateInt[1]) {    // compare month
                return true;
            } else if (thisDateInt[1] > inputDateInt[1]) {
                return false;
            } else if (thisDateInt[1] == inputDateInt[1]) {  // same year and month
                if (thisDateInt[2] < inputDateInt[2]) {   // compare day
                    return true;
                } else if (thisDateInt[2] > inputDateInt[2]) {   
                    return false;
                } else if (thisDateInt[2] == inputDateInt[2]) {   // same year, month, and day
                    if (thisTimeInt[0] < inputTimeInt[0]) {  // compare time, specifically hour
                        return true;
                    } else if (thisTimeInt[0] > inputTimeInt[0]) {  
                        return false;
                    } else if (thisTimeInt[0] == inputTimeInt[0]) {  // same hour
                        if (thisTimeInt[1] < inputTimeInt[1]) {  // compare time, specifically minute
                            return true;
                        } else if (thisTimeInt[1] > inputTimeInt[1]) {  
                            return false;
                        } else if (thisTimeInt[1] == inputTimeInt[1]) { //same minute
                            if (thisTimeInt[2] < inputTimeInt[2]) {  // compare time, specifically seconds
                                return true;
                            } else if (thisTimeInt[2] > inputTimeInt[2]) {  
                                return false;
                            } else if (thisTimeInt[2] == inputTimeInt[2]) {  // posted at exact same time
                                return false;  // I decided to return false in this case
                            } 
                        } 
                    }                    
                }
            }
        }
        return false; // thisDateInt[0] > inputDateInt[0]   
        
    }
    
    // loadStopWords
    public static void loadStopWords(String fileName) {
        try {
            FileReader fr = new FileReader(fileName);   // fileName is stopWords.txt
            BufferedReader br = new BufferedReader(fr);
            HashSet<String> set = new HashSet<String>();
            String currentLine = br.readLine();
            while (currentLine != null) {
                set.add(currentLine);  // must use another HashSet to get values
                // cannot just initialize the attribute stopWords because it is null so will get NullPointerExc.
                currentLine = br.readLine();
            }
            stopWords = set;  // now stopWords will have same value as set
            br.close();
            fr.close();   
        } catch (IOException e) {
            System.out.println("There was an error during the loading of the stopWords file");
        } 
    }
    
    
}