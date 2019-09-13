public class ISBN {

    public static void main(String[] args) {
 
        //Declaring the variable to represent the ISBN number
        int n = Integer.parseInt(args[0]);

        
        
        // typecast to int is used to round the value down to the nearest integer
        
        // this gives the nearest integer value of the 5th digit from the right
        int d5 = (int) (n * 0.001);
        
        // this gives the nearest integer value of the 4th digit from the right
        int d4 = (int) ((n - 1000*d5) * 0.01);
        // this gives the nearest integer value of the 3rd digit from the right
        int d3 = (int) ((n - 1000*d5 - 100*d4) *0.1);
        // this gives the nearest integer value of the 2nd digit from the right
        int d2 = (n-1000*d5-100*d4-10*d3);
        
        // this gives the sum of the value of the 4 digits (not including d1) 
        int sum = 5*d5 + 4*d4 + 3*d3 + 2*d2;
        int remainder = sum%11;
        int d1 = 11 - remainder;
        
        // prints 0 if value of d1 is greater than 10. For example, if d1 = 11.
        boolean z = d1 > 10;
        if (z) {
        System.out.println("0");
        }
        
        // prints X if d1 is equal to 10
        else if (z = d1 == 10) {
        System.out.println("X");    
        }
        
        // prints the value of d1 if the above two conditions are not met
        else {
        System.out.println(d1);    
        }
        
        

    }

}
