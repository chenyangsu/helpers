
import java.util.Scanner;
import java.util.Random;

public class BookingSystem {
    
    private static String[] typeOfRooms = {"double","queen","king"};
    private static Random r = new Random(123);
    
    //returns a random String from the above array. 
    private static String getRandomType(){
        int index = r.nextInt(typeOfRooms.length);
        return typeOfRooms[index];
    }
    //returns a random number of rooms between 5 and 50.
    private static int getRandomNumberOfRooms(){
        return r.nextInt(50)+1;
    }

    
    
    
    public static void main(String[] args){
        
        System.out.println("Welcome to the COMP 202 booking system");
        System.out.println("Please enter the name of the hotel you would like to book");
        Scanner read = new Scanner(System.in);  
        String hotelName = read.nextLine();   // get the input of the hotel name from user
        
        // create the hotel
        int numOfRooms = getRandomNumberOfRooms();  // generate a random number of rooms (method is from provided code)
        Room[] rooms = new Room[numOfRooms];  // create an array of type Room with size generated randomly 
        for (int i=0; i<rooms.length; i++) {  
            rooms[i] = new Room(getRandomType()); // initialize each room randomly to a one of the three types of rooms
        }
        Hotel hotel = new Hotel(hotelName, rooms);   // create the hotel                 
        
        printMenu(hotelName);
        int num = read.nextInt();
        while (num <=5 || num > 5) {
            if (num == 1) {   // Make a reservation
                read.nextLine();   // clear the buffer before getting new input because may read an int from user
                                   // then read String after which will cause Scanner to act weirdly. 
                System.out.print("Please enter your name: ");
                String name = read.nextLine();  // get the name as input from user
                System.out.print("What type of room would you like to reserve? ");
                String roomType = read.nextLine();  // get the room type as input from user
                hotel.createReservation(name, roomType);  // create the reservation
            } else if (num == 2) {  // Cancel a reservation 
                System.out.print("Please enter the name you used to make the reservation: ");
                read.nextLine();   // clear the buffer
                String personName = read.nextLine();
                System.out.print("What type of room did you reserve? ");
                String reservedRoom = read.nextLine();
                hotel.cancelReservation(personName, reservedRoom);  // cancel the reservation
            } else if (num == 3) {  // See an invoice
                read.nextLine(); // clear the buffer
                System.out.print("Please enter your name: ");
                String inputName = read.nextLine();
                hotel.printInvoice(inputName);  // show invoice
            } else if (num == 4) { // See hotel info
                String info = hotel.toString();
                System.out.println(info);  // show info of the hotel i.e. how many rooms of each type 
            } else if (num == 5) { // Exit the Booking System
                System.out.println("It was a pleasure doing business with you!");
                return;
            }
            printMenu(hotelName);  // print the menu again so user can select another option
            num = read.nextInt();  // get another input from user 
        }
    }
    
    //helper method to print the menu
    private static void printMenu(String hotelName) {
        System.out.println();
        System.out.println("**********************************************************************");
        System.out.println("Welcome to " + hotelName + ". Choose one of the following options:");
        System.out.println("1) Make a reservation");
        System.out.println("2) Cancel a reservation");
        System.out.println("3) See an invoice");
        System.out.println("4) See hotel info");
        System.out.println("5) Exit the Booking System");
        System.out.println("**********************************************************************");
    }
    
    
}