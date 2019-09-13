

import java.util.NoSuchElementException;  // allows the access of the NoSuchElementException to be used

public class Hotel {
    // 3 private attributes
    private String name;
    private Room[] roomArray;
    private Reservation[] reservationArray;
    
    
    // a public constructor that takes as input the name of the hotel, and an array of Rooms.
    // It initializes the Room array and COPIES the Room references from the input array to the Hotel array
    public Hotel(String nameOfHotel, Room[] arrayOfRooms) {
        this.name = nameOfHotel;
        this.roomArray = new Room[arrayOfRooms.length];
        for (int i=0; i<roomArray.length; i++) {
            this.roomArray[i] = arrayOfRooms[i];
        }
    }
    
    
    // a method that adds a reservation
    // a private method that takes a Reservation as input and adds it to the array of reservations of the hotel
    private void addReservation(Reservation info) {
        // since reservationArray is not initialized in constructor, must have this if statement to prevent
        // NullPointerException
        if (this.reservationArray == null) {
            Reservation[] a = new Reservation[1]; // set array to a size of 1
            a[0] = info;
            this.reservationArray = a;  // copy reference of array into the hotel reservation array
        } else {
            // create a new array with a size of 1 greater than orignial array storing reservations
            Reservation[] newReservationArray = new Reservation[this.reservationArray.length +1];
            // - copy all the old elements into the new array
            for(int i=0; i<this.reservationArray.length; i++) {
                newReservationArray[i] = this.reservationArray[i];
            }
            // - add the new extra element into the final position of new array
            newReservationArray[this.reservationArray.length] = info;
            // - update the attribute array so it has reference pointing to the newly created array
            this.reservationArray = newReservationArray;
        }
    }
    
    
    // a method that removes a reservation
    // a private method that takes 2 Strings as input: a name and a type of room
    private void removeReservation(String person, String typeOfRoom) {
        // throw exception if trying to remove a reservation when there is no reservation in the array
        if (this.reservationArray == null) {
            throw new NoSuchElementException("There is no such reservation");
        } else {
            // create an array of type Reservations representing the reservations left. It is 1 size smaller.
            Reservation[] reservationsLeft = new Reservation[this.reservationArray.length - 1];
            int index = 0;
            // run through all positions of original array to find index where the reservation is stored
            for (int i=0; i<this.reservationArray.length; i++) {
                // the reservation index is found by checking that the name and room type are the same
                if ((this.reservationArray[i].getName()).equals(person)) {
                    Room temp = this.reservationArray[i].getRoom(); // store the Room in temporary Room variable
                    if (temp.getType().equals(typeOfRoom)) {   // get the type of the room
                        index = i;
                        // copy the remaining reservations inside the new array created at the beginning
                        for(int j=0; j<reservationsLeft.length; j++) {
                            if(j<i) {  
                                // copy elements before the index
                                reservationsLeft[j] = this.reservationArray[j];
                            } else {
                                // copy elements after the index so basically the position where the reservation is has
                                // been skipped. i.e the new array does not have this reservation anymore
                                reservationsLeft[j] = this.reservationArray[j+1];
                            }
                        }
                        // update the attribute by copying reference from array we used above into reservationArray
                        this.reservationArray = reservationsLeft;
                        // update the roomArray with by changing one of the rooms of the correct type back to true
                        // i.e change room back to available
                        for (int j=0; j<this.roomArray.length; j++) {
                            if ((this.roomArray[j].getType()).equals(typeOfRoom)) {
                                this.roomArray[j].changeAvailability();
                                break;
                            }
                        }
                        // if the above process was successful, return so we do not go through the exception below
                        return;
                    } 
                } 
            }
            // this is for the case that there are no reservations made under the given name for the given type of room
            // It is thrown only after the for loop has run through the whole array and a reservation 
            // with the name and room type has not been found
            throw new NoSuchElementException("The reservation requested was not found");
        }
    }
      
    
    // a method that creates a reservation.
    // It takes as input 2 Strings: a name and a type
    public void createReservation(String name, String type) {
        // run through available rooms and if there is none of the type requested available, then print statement shown
        if (Room.findAvailableRoom(this.roomArray, type) == null) {
            System.out.println("Sorry " + name + ", there are currently no " + type + " rooms available");
        } else {   // there is a room available of the type requested 
            // store the found room in a variable of type Room
            Room vacantRoom = Room.findAvailableRoom(this.roomArray, type); 
            Reservation r = new Reservation(vacantRoom, name);  // create a reservation
            vacantRoom.changeAvailability();  // change the room's status to occupied 
            addReservation(r);   // update the reservationArray
            System.out.println("You have successfully reserved a " + type + " room under the name of " + name +
                               ". We look forward to having you at " + this.name + "!");
        }
    }
    
    
    // a method that cancels a reservation. It uses a try/catch block
    public void cancelReservation(String name, String roomType) {
        // if the try statement works then remove the reservation. If it does not work, i.e. there is no reservation of 
        // that name and room type then catch the exception that will be thrown from the removeReservation
        // method so that it does not cause the program to crash
        try {
            removeReservation(name, roomType);
            System.out.println(name + ", your reservation for a " + roomType + " room has been successfully cancelled.");
        }
        catch (NoSuchElementException e) {
            System.out.println("There's no reservation for a " + roomType + " room under the name of " + name);
        }
    }
    
    
    // a method that prints out a message letting the user know how much the person owes the hotel based
    // on all the reservations made under such name
    public void printInvoice(String name) {
        double invoice = 0.0; //local variables must be initialized before use because the are not initialized by default
        if (this.reservationArray == null) {   
            System.out.println(name + "'s invoice is of " + invoice);
            return;
        }
        for (int i=0; i<reservationArray.length; i++) {
            if ((reservationArray[i].getName()).equals(name)) {
                // add up the total price charged to the person of a certain name
                invoice = invoice + reservationArray[i].getRoom().getPrice();
            }
        }
        System.out.println(name + "'s invoice is of $" + invoice);
    }
    
    
    // a toString method
    public String toString() {
        // initialize 3 variables used to count the number of double, queen, and single rooms
        int availableDoubleRooms = 0;
        int availableQueenRooms = 0;
        int availableKingRooms = 0;
        for (int i=0; i<this.roomArray.length; i++) {
            if ((roomArray[i].getType()).equals("double")) {  // type of room is double
                if (roomArray[i].getAvailability() == true) { // room is available
                    availableDoubleRooms = availableDoubleRooms + 1;  // increase the counter by 1
                }
            }
            if ((roomArray[i].getType()).equals("queen")) {   // type of room is queen
                if (roomArray[i].getAvailability() == true) { // room is available
                    availableQueenRooms = availableQueenRooms + 1; // increase the counter by 1
                }
            }
            if ((roomArray[i].getType()).equals("king")) {     // type of room is king
                if (roomArray[i].getAvailability() == true) { // room is available
                    availableKingRooms = availableKingRooms + 1; // increase the counter by 1
                }
            }
        }
        // print the info of the hotel: number of double, queen, king rooms
        String s1 = "\nHere is the hotel info: \nHotel name: " + this.name + "\n";
        String s2 = "Available Rooms: " + availableDoubleRooms + " double, " + availableQueenRooms + " queen, "
            + availableKingRooms + " king";
        return s1 + s2;
    }
    
}

