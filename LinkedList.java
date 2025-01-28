/**
 * Represents a list of Nodes. 
 */
public class LinkedList {
	
	private Node first; // pointer to the first element of this list
	private Node last;  // pointer to the last element of this list
	private int size;   // number of elements in this list
	
	/**
	 * Constructs a new list.
	 */ 
	public LinkedList () {
		first = null;
		last = null;
		size = 0;
	}
	
	/**
	 * Gets the first node of the list
	 * @return The first node of the list.
	 */		
	public Node getFirst() {
		return this.first;
	}

	/**
	 * Gets the last node of the list
	 * @return The last node of the list.
	 */		
	public Node getLast() {
		return this.last;
	}
	
	/**
	 * Gets the current size of the list
	 * @return The size of the list.
	 */		
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Gets the node located at the given index in this list. 
	 * 
	 * @param index
	 *        the index of the node to retrieve, between 0 and size
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 * @return the node at the given index
	 */		
	public Node getNode(int index) {
		if (index < 0 || index >= this.size) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		// If index = last index, returns the last node
		if (index == size - 1){
			return last;
		}
		// Iterate over the list and returns the node at the given index
		Node current = first;
		int i = 0;
		while(i < index){
			current = current.next;
			i++;
		}
		return current;
	}
	
	/**
	 * Creates a new Node object that points to the given memory block, 
	 * and inserts the node at the given index in this list.
	 * <p>
	 * If the given index is 0, the new node becomes the first node in this list.
	 * <p>
	 * If the given index equals the list's size, the new node becomes the last 
	 * node in this list.
     * <p>
	 * The method implementation is optimized, as follows: if the given 
	 * index is either 0 or the list's size, the addition time is O(1). 
	 * 
	 * @param block
	 *        the memory block to be inserted into the list
	 * @param index
	 *        the index before which the memory block should be inserted
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 */
	public void add(int index, MemoryBlock block) {
		if (index < 0 || index > this.size) {
			throw new IllegalArgumentException("illegal index " + index);
		}
		if (index == 0) {
			this.addFirst(block);
			return;
		}
		if (index == this.size) {
			this.addLast(block);
			return;
		}
		Node insert = new Node(block);
		Node prev = null;
		Node current = this.first;
		for (int i = 0; i < index; i++) {
			prev = current;
			current = current.next;
		}
		prev.next = insert;
		insert.next = current;
		this.size++;
	}

	/**
	 * Creates a new node that points to the given memory block, and adds it
	 * to the end of this list (the node will become the list's last element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addLast(MemoryBlock block) {
		Node newNode = new Node(block);
		if (this.size == 0) {
			this.first = newNode;
			this.last = newNode;
		} else {
			this.last.next = newNode;
			this.last = newNode;
		}
		this.size++;
	}
	
	/**
	 * Creates a new node that points to the given memory block, and adds it 
	 * to the beginning of this list (the node will become the list's first element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addFirst(MemoryBlock block) {
		Node n = new Node(block); // Create the new node
		n.next = this.first;      // Link it to the current first node
		this.first = n;           // Update `first` to the new node
		if (this.size == 0) {     // If the list was empty, update `last` as well
			this.last = n;
		}
		this.size++;              // Increment size
	}

	/**
	 * Gets the memory block located at the given index in this list.
	 * 
	 * @param index
	 *        the index of the retrieved memory block
	 * @return the memory block at the given index
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public MemoryBlock getBlock(int index) {
		if (index < 0 || index >= this.size) {
			throw new IllegalArgumentException("index must be between 0 and size");
			}
		Node cur = this.first;
		int i = 0;
		while (i < index){
			cur = cur.next;
			i++;
		}
		return cur.block;
	}	

	/**
	 * Gets the index of the node pointing to the given memory block.
	 * 
	 * @param block
	 *        the given memory block
	 * @return the index of the block, or -1 if the block is not in this list
	 */
	public int indexOf(MemoryBlock block) {
		Node cur = this.first;
		for(int i = 0; i < this.size; i++){
			if (cur.block.equals(block)){
				return i;
			}
			cur = cur.next;
		}
		return -1;
	}

	/**
	 * Removes the given node from this list.	
	 * 
	 * @param node
	 *        the node that will be removed from this list
	 */
	public void remove(Node node) {
		// Special case: removing the first node
		if (this.first.equals(node)) {
			this.first = this.first.next;
			if (this.first == null) { // If the list becomes empty
				this.last = null;
			}
			this.size--;
			return;
		}
 
		// General case: find and remove the node
		Node prev = null;
		Node cur = this.first;
		while (cur != null) {
			if (cur.equals(node)) {
				prev.next = cur.next;
				if (cur == this.last) { // Update last if removing the last node
					this.last = prev;
				}
				this.size--;
				return;
			}
			prev = cur;
			cur = cur.next;
		}
	}

	/**
	 * Removes from this list the node which is located at the given index.
	 * 
	 * @param index the location of the node that has to be removed.
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public void remove(int index) {
		if (index < 0 || index >= this.size) {
			throw new IllegalArgumentException("illegal index " + index);
			}
		remove(getNode(index));
	}

	/**
	 * Removes from this list the node pointing to the given memory block.
	 * 
	 * @param block the memory block that should be removed from the list
	 * @throws IllegalArgumentException
	 *         if the given memory block is not in this list
	 */
	public void remove(MemoryBlock block) {
		if (indexOf(block) == -1){
			throw new IllegalArgumentException("index must be between 0 and size");
		}
		remove(indexOf(block));
	}	

	/**
	 * Returns an iterator over this list, starting with the first element.
	 */
	public ListIterator iterator(){
		return new ListIterator(first);
	}
	
	/**
	 * A textual representation of this list, for debugging.
	 */
	public String toString() {
		String s = "";

		Node current = first;

		while (current != null) {

		s = s + current.block + " ";

		current = current.next;
		}
		return s;
	}
}