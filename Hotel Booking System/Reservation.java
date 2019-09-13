

public class Reservation {
    // 2 private attributes
    private String name;
    private Room roomReserved;
    
    // a constructor that takes as input a Room and the name under which the reservation is made
    public Reservation(Room r, String person) {
        this.name = person;
        this.roomReserved = r;
    }
    
    // get method that returns the name under which the reservation is made
    public String getName() {
        return this.name;
    }
    
    // get method which returns the Rooms that has been reserved
    public Room getRoom() {   // normally the get method would have the format getAttribute where Attribute is the 
                              // name of the attribute, but this does not always have to be the case
        return this.roomReserved;
    }
    
}