
public class Room {
    // 3 private attributes
    private String type;
    private double price;
    private boolean availability;
    
    
    // constructor that takes as input the type of the room and uses it to initialize the 3 attributes 
    public Room(String typeOfRoom) {
        String room = typeOfRoom;
        // throw exception if the room type is not valid
        if (room.equals("double") == false && room.equals("queen") == false && room.equals("king") == false) {
            throw new IllegalArgumentException("No room of that type can be created");
        }
        this.type = typeOfRoom;   // intialize the attribute
        if (room.equals("double")) {   // NOTE: for String must use .equals() to compare NOT ==
            this.price = 90.0;         // set price for different room types
        } else if (room.equals("queen")) {
            this.price = 110.0;
        } else if (room.equals("king")) {
            this.price = 150.0;
        }
        this.availability = true;
    }
    
    
    // get method which returns the type of the room
    public String getType() {
        return this.type;
    }
    
    
    // get method which returns the price of the room
    public double getPrice() {
        return this.price;
    }
    
    
    // get method that returns the availability of the room
    public boolean getAvailability() {
        return this.availability;
    }
    
    
    // method which takes no input and sets the value stored in the availability attribute to the opposite of the
    // one currently there
    public boolean changeAvailability() {
        if (this.availability == true) {
            this.availability = false;
        } else {
            this.availability = true;
        }
        return this.availability;
    }
    
    
    // a method which takes as input an array of Rooms as well as a String indicating the room type. It returns 
    // the first available room in the array of indicated type. If no such room exists, return null
    public static Room findAvailableRoom(Room[] a, String roomType) {
        for (int i=0; i<a.length; i++) {   // return the room that is of the correct type AND is available
            if (a[i].type.equals(roomType) && a[i].availability == true) {
                return a[i];
            }
        }
        return null;   // return null after iterating through the array and not returning a room
    }
    
    
}