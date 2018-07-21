//package au.edu.rmit.csit.aa;

import java.util.Stack;


/**
 * Singly linked list.
 * 
 * @author jkcchan
 */
public class SimpleLinkedList<T extends Object>
{
	/** Reference to head node. */
    protected Node<T> head;

    /** Length of list. */
    protected int size;


    public SimpleLinkedList() {
        head = null;
        size = 0;
    } // end of SimpleList()

    public int getSize() { return this.size;}

    /**
     * Add a new value to the start of the list.
     * 
     * @param newValue Value to add to list.
     */
    public void add(T newValue) {
        Node newNode = new Node<T>(newValue);
             
        // If head is empty, then list is empty and head reference need to be initialised.
        if (head == null) {
            head = newNode;
        }
        // otherwise, add node to the head of list.
        else {
            newNode.setNext(head);
            head = newNode;
            
        }
        
        size++;
    } // end of add()


    /**
     * Add value (and corresponding node) at position 'index'.  Indices start at 0.
     * 
     * @param index Position in list to add new value to.
     * @param newValue Value to add to list.
     * 
     * @throws IndexOutOfBoundsException In index are out of bounds.
     */
    public void add(int index, T newValue) throws IndexOutOfBoundsException {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Supplied index is invalid.");
        }

        Node<T> newNode = new Node<T>(newValue);

        if (head == null) {
            head = newNode;
        }
        // list is not empty
        else {
            Node<T> currNode = head;
            for (int i = 0; i < index-1; ++i) {
                currNode = currNode.getNext();
            }

            newNode.setNext(currNode.getNext());
            currNode.setNext(newNode);            
        }

        size += 1;
    } // end of add()


    /**
     * Returns the value stored in node at position 'index' of list.
     *  
     * @param index Position in list to get new value for.
     * @return Value of element at specified position in list.
     * 
     * @throws IndexOutOfBoundsException In index are out of bounds.
     */
    public T get(int index) throws IndexOutOfBoundsException {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Supplied index is invalid.");
        }

        Node<T> currNode = head;
        for (int i = 0; i < index; ++i) {
            currNode = currNode.getNext();
        }

        return currNode.getValue();
    }
    
    
    /**
     * Returns the value stored in node at position 'index' of list.
     * 
     * @param value Value to search for.
     * @return True if value is in list, otherwise false.
     */
    public boolean search(T value) {
        Node currNode = head;
        for (int i = 0; i < size; ++i) {
        	if (currNode.getValue() == value) {
        		return true;
        	}
            currNode = currNode.getNext();
        }

        return false;
    } // end of search()


    public Node search(int index, boolean dummy)
    {
        Node node = head;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                return node;
            }
            node = node.next;
        }

        return null;
    }


    /**
     * Delete given value from list (delete first instance found).
     *   
     * @param value Value to remove.
     * @return True if deletion was successful, otherwise false.
     */
    public boolean remove(T value) {
        // YOUR IMPLEMENTATION
    	if (size == 0) {
    		return false;
    	}
    	
    	
        Node<T> currNode = head;
        Node<T> prevNode = null;

        // check if value is head node
        if (currNode.getValue() == value) {
            head = currNode.getNext();
            size--;
            return true;
        }

        prevNode = currNode;
        currNode = currNode.getNext();

        while (currNode != null) {
            if (currNode.getValue() == value) {
                prevNode.setNext(currNode.getNext());
                currNode = null;
                size--;
                return true;
            }
            prevNode = currNode;
            currNode = currNode.getNext();
        }		


        return false;
    } // end of delete()


    /**
     * Delete value (and corresponding node) at position 'index'.  Indices start at 0.
     * 
     * @param index Position in list to get new value for.
     * @param dummy Dummy variable, serves no use apart from distinguishing overloaded methods.
     * @return Value of node that was deleted.
     */
    public T remove(int index, boolean dummy) throws IndexOutOfBoundsException {
        // YOUR IMPLEMENTATION
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Supplied index is invalid.");
        }

        Node<T> currNode = head;
        Node<T> prevNode = null;
        
        T value;
        // deleting head
        if (index == 0) {
            value = currNode.getValue();
            head = currNode.getNext();
        }
        else {
            for (int i = 0; i < index; ++i) {
                prevNode = currNode;
                currNode = currNode.getNext();
            }

            value = currNode.getValue();
            prevNode.setNext(currNode.getNext());
            currNode = null;
        }

        size--;
        
        return value;
    } // end of delete()

    /**
     * Print the list in head to tail.
     */
    public void print() {
        System.out.println(toString());
    } // end of print()
    
    
    
    /**
     * Print the list from tail to head.
     */
    public void reversePrint() {
        // use a stack
        Stack<T> stack = new Stack<T>();
        Node<T> currNode = head;
        while (currNode != null) {
            stack.add(currNode.getValue());
            currNode = currNode.getNext();
        }        
        

        while (!stack.empty()) {
            System.out.print(stack.pop() + " ");
        }
        
        System.out.println("");
    } // end of reversePrint()
        
	
    /**
     * @return String representation of the list.
     */
    public String toString() {
        Node currNode = head;

        StringBuffer str = new StringBuffer();

        while (currNode != null) {
            str.append(currNode.getValue() + " ");
            currNode = currNode.getNext();
        }

        return str.toString();
    } // end of getString();
	
	
	
    /**
     * Node type, inner private class.
     */
    private class Node<T>
    {
        /** Stored value of node. */
        protected T value;
        /** Reference to next node. */
        protected Node<T> next;

        public Node(T value) {
            this.value = value;
            next = null;
        }

        public T getValue() {
            return value;
        }


        public Node<T> getNext() {
            return next;
        }


        public void setValue(T value) {
            this.value = value;
        }


        public void setNext(Node next) {
            this.next = next;
        }
    } // end of inner class Node
		
} // end of class SimpleList


