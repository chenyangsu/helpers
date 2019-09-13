
public class Warehouse{

	protected Shelf[] storage;
	protected int nbShelves;
	public Box toShip;
	public UrgentBox toShipUrgently;
	static String problem = "problem encountered while performing the operation";
	static String noProblem = "operation was successfully carried out";
	
	public Warehouse(int n, int[] heights, int[] lengths){
		this.nbShelves = n;
		this.storage = new Shelf[n];
		for (int i = 0; i < n; i++){
			this.storage[i]= new Shelf(heights[i], lengths[i]);
		}
		this.toShip = null;
		this.toShipUrgently = null;
	}
	
	public String printShipping(){
		Box b = toShip;
		String result = "not urgent : ";
		while(b != null){
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n" + "should be already gone : ";
		b = toShipUrgently;
		while(b != null){
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n";
		return result;
	}
	
 	public String print(){
 		String result = "";
		for (int i = 0; i < nbShelves; i++){
			result += i + "-th shelf " + storage[i].print();
		}
		return result;
	}
	
 	public void clear(){
 		toShip = null;
 		toShipUrgently = null;
 		for (int i = 0; i < nbShelves ; i++){
 			storage[i].clear();
 		}
 	}
 	
 	/**
 	 * initiate the merge sort algorithm
 	 */
	public void sort(){
		mergeSort(0, nbShelves -1);
	}
	
	/**
	 * performs the induction step of the merge sort algorithm
	 * @param start
	 * @param end
	 */
	protected void mergeSort(int start, int end){
		//ADD YOUR CODE HERE
		if (start<end) {
			int mid = (start+end)/2;
			mergeSort(start,mid);
			mergeSort(mid+1,end);
			merge(start,mid,end);
		}
	}
	
	/**
	 * performs the merge part of the merge sort algorithm
	 * @param start
	 * @param mid
	 * @param end
	 */
	protected void merge(int start, int mid, int end){
		//ADD YOUR CODE HERE
		
		// this is the code from lect 9 but modified a bit
		int n1 = mid-start+1;
		int n2 = end-mid;
		int[] left = new int[n1];
		int[] right = new int[n2];
		for (int i=0; i<n1; i++) {
			left[i] = this.storage[start+i].height;
		}
		for (int j=0; j<n2; j++) {
			right[j] = this.storage[mid+j+1].height;
		}
		
		int i=0;
		int j=0;
		int k=start;
		
		while (i<n1 && j<n2) {
			if (left[i] <= right[j]) {
				this.storage[k].height = left[i];
				i= i+1;
			} else {
				this.storage[k].height = right[j];
				j=j+1;
			}
			k++;
		}
		// for the case when either of the two subarrays is empty so we need
		// to add all the elements of the remaining subarray into the full
		// array by itself
		while (i<n1) {
			this.storage[k].height = left[i];
			i++;
			k++;
		}
		while (j<n2) {
			this.storage[k].height = right[j];
			j++;
			k++;
		}
	}
	
	/**
	 * Adds a box is the smallest possible shelf where there is room available.
	 * Here we assume that there is at least one shelf (i.e. nbShelves >0)
	 * @param b
	 * @return problem or noProblem
	 */
	public String addBox (Box b){
		//ADD YOUR CODE HERE
		// if there is space in shelf then add the box 
		for (int i=0; i<nbShelves; i++) {
			if (this.storage[i].height >= b.height && 
					this.storage[i].availableLength >= b.length) {
				this.storage[i].addBox(b);
				return noProblem;
			}
		}
		return problem;
		
	}
	
	/**
	 * Adds a box to its corresponding shipping list and updates all the fields
	 * @param b
	 * @return problem or noProblem
	 */
	public String addToShip (Box b){
		//ADD YOUR CODE HERE
		
		if (b instanceof UrgentBox) { // comparing the classes
			if (toShipUrgently == null) {
				toShipUrgently =  (UrgentBox) b;
				b.previous = null;
				b.next = null;
			} else { //for case when list is not empty
				toShipUrgently.previous = b;
				b.next = toShipUrgently; // remember to link b back to the previous box
				toShipUrgently = (UrgentBox) b;
			}
			return noProblem;
		}
		else if (b instanceof Box) { // comparing the classes
			if (toShip == null) {
				toShip = b;
				b.previous = null;
				b.next = null;
			} else { // for case when list is not empty
				toShip.previous = b;
				b.next = toShip;
				toShip = b;
			}
			return noProblem;
		}
		return ""; // return random thing but this will not be used b/c the
		// two if and else if case covers all cases
		// this return must be here because the method requires a string to be returned
		
	}
	
	/**
	 * Find a box with the identifier (if it exists)
	 * Remove the box from its corresponding shelf
	 * Add it to its corresponding shipping list
	 * @param identifier
	 * @return problem or noProblem
	 */
	public String shipBox (String identifier){
		//ADD YOUR CODE HERE
		
		Box dummy = null;
		for (int i=0; i<nbShelves; i++) { //iterate as many times as there are shelves
			dummy = storage[i].firstBox; // set dummy to first box of each shelf 
			while (dummy != null) { // meaning we have not yet reached the end of the list
				if (dummy.id.equals(identifier)) {
					storage[i].removeBox(identifier);
					addToShip(dummy);
					return noProblem;
				}
				dummy = dummy.next; // important to remember to update the dummy 
			}
		}
		return problem;
	}
	
	/**
	 * if there is a better shelf for the box, moves the box to the optimal shelf.
	 * If there are none, do not do anything
	 * @param b
	 * @param position
	 */
	public void moveOneBox (Box b, int position){
		//ADD YOUR CODE HERE
		
		// since we know the shelves are sorted, we start at the smallest shelf
		// i.e. shelf 0 and once we get to a shelf that is bigger than the length 
		// of the box and taller, we can remove the box from its prior shelf and 
		// add it to this shelf we have found as it must be the smallest minimum shelf
		// possible since we started at the lowest bound of shelf 0.
		for (int i=0; i<position; i++) {
			if (storage[i].availableLength >= b.length 
					&& storage[i].height >= b.height) {
				storage[position].removeBox(b.id);
				this.storage[i].addBox(b);
				break;
			}
		}
	}
	
	/**
	 * reorganize the entire warehouse : start with smaller shelves and first box on each shelf.
	 */
	public void reorganize (){
		//ADD YOUR CODE HERE
		
		Box b = null;
		for (int i=0; i<nbShelves; i++) {
			b = storage[i].firstBox; // set to first box of each shelf		
			while (b != null && this.storage[i].firstBox != null) {
				// the method canMoves tells us if there is a smaller shelf
				// that the box can be moved to 
				if(b.id.equals(this.storage[i].firstBox.id) && canMove(b,i)) {
					moveOneBox(this.storage[i].firstBox,i);
	                if(this.storage[i].firstBox != null) {
	                    b= this.storage[i].firstBox;
	                } 
	            }
	            else {
	                moveOneBox(b,i);
	                b = b.next; // update b to be the first box for next iteration
	                // since moveOneBox removes a box so the next box is the firstBox on the shelf
	            }
			}
		}
	}
	
	//A helper method for figuring out if box can be moved
	//Returns true if box is movable, false otherwise
	public boolean canMove(Box b, int position) {
	    for(int i=0; i<position; i++){
	        if(this.storage[i].height >= b.height 
	        		&& this.storage[i].availableLength >= b.length) {
	            return true;
	        }
	  }
	  return false;
	 }
}







