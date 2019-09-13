public class JudgeScore {
    public static void main(String[] args) {
 
        //Declaring the variables for storing the judges scores.
        int judge1, judge2, judge3, judge4;
        judge1 = Integer.valueOf(args[0]);
        judge2 = Integer.valueOf(args[1]);
        judge3 = Integer.valueOf(args[2]);
        judge4 = Integer.valueOf(args[3]);
  
              
        
        /* The 24 different combinations for the size of the values of judge1, judge2, judge3, 
         * and judge4 are displayed below.
         * NOTE: a typecast to double is used in the print statement so that a value such as 
         * 2.5 will give 2.5 and not be rounded down to 2.
         * NOTE: else if is started on a new line instead of right after the closed curly brace
         * of the previous if statement for better readability of the code.
         */
        
        
        // the 6 combinations for judge 4 being the largest value
        if ((judge1 <= judge2) && (judge2 <= judge3) && (judge3 <= judge4)) {
            System.out.println((double) (judge2 + judge3)/2);      
        } 
        else if ((judge2 <= judge1) && (judge1 <= judge3) && (judge3 <= judge4)) {
            System.out.println((double) (judge1 + judge3)/2);
        }
        else if ((judge1 <= judge3) && (judge3 <= judge2) && (judge2 <= judge4)) {
            System.out.println((double) (judge2 + judge3)/2);
        }
        else if ((judge3 <= judge1) && (judge1 <= judge2) && (judge2 <= judge4)) {
            System.out.println((double) (judge1 + judge2)/2);
        }
        else if ((judge2 <= judge3) && (judge3 <= judge1) && (judge1 <= judge4)) {
            System.out.println((double) (judge1 + judge3)/2);
        }
        else if ((judge3 <= judge2) && (judge2 <= judge1) && (judge1 <= judge4)) {
            System.out.println((double) (judge1 + judge2)/2);
        }
        
        // the 6 combinations for judge3 being the largest value
        else if ((judge1 <= judge2) && (judge2 <= judge4) && (judge4 <= judge3)) {
         System.out.println((double) (judge2 + judge4)/2);   
        }
        else if ((judge2 <= judge1) && (judge1 <= judge4) && (judge4 <= judge3)) {
            System.out.println((double) (judge1 + judge4)/2);
        }
        else if ((judge1 <= judge4) && (judge4 <= judge2) && (judge2 <= judge3)) {
            System.out.println((double) (judge4 + judge2)/2);
        }
        else if ((judge4 <= judge1) && (judge1 <= judge2) && (judge2 <= judge3)) {
            System.out.println((double) (judge1 + judge2)/2);
        }
        else if ((judge2 <= judge4) && (judge4 <= judge1) && (judge1 <= judge3)) {
            System.out.println((double) (judge1 + judge4)/2);
        }
        else if ((judge4 <= judge2) && (judge2 <= judge1) && (judge1 <= judge3)) {
         System.out.println((double) (judge1 + judge2)/2);   
        }
        
        // the 6 combinations for judge2 being the largest value
        else if ((judge1 <= judge4) && (judge4 <= judge3) && (judge3 <= judge2)) {
            System.out.println((double) (judge3 + judge4)/2);
        }
        else if ((judge4 <= judge1) && (judge1 <= judge3) && (judge3 <= judge2)) {
            System.out.println((double) (judge1 + judge3)/2);
        }
        else if ((judge1 <= judge3) && (judge3 <= judge4) && (judge4 <= judge2)) {
            System.out.println((double) (judge3 + judge4)/2);
        }
        else if ((judge3 <= judge1) && (judge1 <= judge4) && (judge4 <= judge2)) {
            System.out.println((double) (judge1 + judge4)/2);
        }
        else if ((judge4 <= judge3) && (judge3 <= judge1) && (judge1 <= judge2)) {
            System.out.println((double) (judge1 + judge3)/2);
        }
        else if ((judge3 <= judge4) && (judge4 <= judge1) && (judge1 <= judge2)) {
            System.out.println((double) (judge1 + judge4)/2);
        }
        
        // the 6 combinations for judge1 being the largest value
        else if ((judge4 <= judge2) && (judge2 <= judge3) && (judge3 <= judge1)) {
            System.out.println((double) (judge2 + judge3)/2);
        }
        else if ((judge2 <= judge4) && (judge4 <= judge3) && (judge3 <= judge1)) {
            System.out.println((double) (judge3 + judge4)/2);
        }
        else if ((judge4 <= judge3) && (judge3 <= judge2) && (judge2 <= judge1)) {
            System.out.println((double) (judge3 + judge2)/2);
        }
        else if ((judge3 <= judge4) && (judge4 <= judge2) && (judge2 <= judge1)) {
            System.out.println((double) (judge2 + judge4)/2);
        }
        else if ((judge2 <= judge3) && (judge3 <= judge4) && (judge4 <= judge1)) {
            System.out.println((double) (judge3 + judge4)/2);
        }
        else if ((judge3 <= judge2) && (judge2 <= judge4) && (judge4 <= judge1)) {
            System.out.println((double) (judge2 + judge4)/2);
        }       
        
  
  
    }
}
