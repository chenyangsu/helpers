

public class Shelf {
	
	protected int height;
	protected int availableLength;
	protected int totalLength;
	protected Box firstBox;
	protected Box lastBox;

	public Shelf(int height, int totalLength){
		this.height = height;
		this.availableLength = totalLength;
		this.totalLength = totalLength;
		this.firstBox = null;
		this.lastBox = null;
	}
	
	protected void clear(){
		availableLength = totalLength;
		firstBox = null;
		lastBox = null;
	}
	
	public String print(){
		String result = "( " + height + " - " + availableLength + " ) : ";
		Box b = firstBox;
		while(b != null){
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n";
		return result;
	}
	
	/**
	 * Adds a box on the shelf. Here we assume that the box fits in height and length on the shelf.
	 * @param b
	 */
	public void addBox(Box b){
		//ADD YOUR CODE HERE
		if (lastBox == null && firstBox == null) { //list is empty
			lastBox = firstBox = b; //set both pointers to b
			b.previous = null;
			b.next = null;
			
		}
		else {
			lastBox.next = b; //add b to end
			b.previous = lastBox; // connect b to previous box
			b.next = null;  // because b is last box
			lastBox = b; // set lastbox to b
		}
		availableLength = availableLength - b.length;  // update length of shelf
	}
	
	/**
	 * If the box with the identifier is on the shelf, remove the box from the shelf and return that box.
	 * If not, do not do anything to the Shelf and return null.
	 * @param identifier
	 * @return
	 */
	public Box removeBox(String identifier){
		//ADD YOUR CODE HERE
		
		//there are 4 cases to look at.
		// this was quite tricky to do and may be the harderst method
		Box b = firstBox;
		//note the cases outside this while loop are the cases when the box is
		//not found on the shelf so we return null
		while (b != null && lastBox != null && firstBox != null) {
			//for case when there is only one box on shelf and it
			//is the one we want to remove
			// note this must go at the start before the case for 
			// the box being the first box OR the box being the last box
			if (firstBox.id.equals(identifier) && lastBox.id.equals(identifier)) {
				Box found = firstBox;
				firstBox = null;
				lastBox = null;
				availableLength = availableLength + found.length; // update length
				found.previous = null;
				found.next = null;
				return found;
			}
			//for case when box is the first one
			if (firstBox.id.equals(identifier)) {
				Box found = firstBox;
				firstBox = firstBox.next;
				firstBox.previous = null;
				availableLength = availableLength + found.length;
				found.previous = null;
				found.next = null;
				return found;
			}
			// for case when box is the last one on shelf
			if (lastBox.id.equals(identifier)) {
				Box found = lastBox;
				lastBox = lastBox.previous;
				lastBox.next = null;
				availableLength = availableLength + found.length;
				found.previous = null;
				found.next =null;
				return found;
			}
			//for case when the box is in middle
			if (b.id.equals(identifier)) {
				Box found = b;
				// link the boxes to each other
				b.previous.next = b.next;
				b.next.previous = b.previous;
				availableLength = availableLength + found.length;
				found.previous=null;
				found.next = null;
				return found;
			}
			
			b=b.next;	
		}
		return null;
			
	}
	
}
