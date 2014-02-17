import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
* The class <code>Project3TreeSet</code> implements the Abstract Data Type 
* (ADT) <code>Project3SortedSet</code>. <code>Project3TreeSet></code> is a generic
* class and <code>Project3SortedSet</code> is an interface for which there
* are no duplicate elements and the elements are sorted.
*
* @author Mark A. Holliday
* @author Tim Sizemore
* @author Andrea Sloan
* @version 31 October 2011
*/
public class Project3TreeSet<E extends Comparable<? super E>> implements
    Project3SortedSet<E> {

    /** Inner class for a single node in the tree */
    private class BinaryNode {
        /** The data in the node */
        E element;           

        /** A pointer to the left child of the node */
        BinaryNode left; 

        /** A pointer to the left child of the node */
        BinaryNode right;

        /** A pointer to the parent node */
        BinaryNode parent;

        /** Parent pointer and child pointers are null in this constructor */
        BinaryNode(E element) {
            this(element, null, null, null); 
        }

        /** 
         * The constructor that has pointers to the child nodes and 
         * to the parent node 
         */
        BinaryNode(E element, BinaryNode lt, BinaryNode rt, BinaryNode pt) {
            this.element  = element; 
            left          = lt; 
            right         = rt; 
            parent        = pt; 
        }
    }  

    /** Inner class that specifies an Iterator object */
    private class Project3TreeSetIterator implements java.util.Iterator {
        /** 
         * <code>Integer</code> value that keeps track of the modifications made since the
         * Iterator was created.
         */
        private int        modCount;

        /**
         * <code>BinaryNode</code> representing the node containing the item 
         *  that is returned by a call to next().
         */
        private BinaryNode current;

        /** <code>Integer</code> value that keeps track of the number of 
         * modifications made on this set.
         */
        private int expectedModCount = modCount;

        /**
         * Creates an Iterator and sets the <code>modCount</code> to zero and
         * sets the <code>current</code> reference to null.
         *
         * @return the new Project3TreeSetIterator
         */
        public Project3TreeSetIterator() {
            modCount = 0;
            current  = null; 
        }

        /** 
         * Determines whether there is an element after the current one. 
         *
         * @return true if the element is followed by another element
         */
        public boolean hasNext() {
            return current.left != null || current.right != null;
        }

        /** 
         * Returns the current element then moves the <code>current</code> field 
         * pointer to point to the next <code>BinaryNode</code> in the set.
         *
         * @return the current element
         */
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            } 
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }

            /*E next  = current.element;
            //check to see if current has a left || right
            //traverse
            return next;*/
            return null;
        }

        /** Throws an <code>UnsupportedOpertaionException()</code>. */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /** <code>BinaryNode</code> that points to the head of the tree. */
    private BinaryNode root;

    /** <code>Integer</code> value that holds the size of the set. */
    private int        size;

    /** <code>Integer</code> value that keeps track of the modifications made
     * on this set. 
     */
    private int modCount;

    /** 
     * Creates an instance of <code>Project3TreeSet</code>, initializing the
     * <code>root</code> to null and the <code>size</code> to zero.
     */
    public Project3TreeSet() {
        root = null;
        size = 0;
    }

    /** 
     * Returns a String representation of the object; the elements are
	 * in represented in sorted order. 
     *
     * @return a String representation of the object.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<" + findMin());
        sb.append(", " + treeTraversal(root));
        sb.append(">");
        return sb.toString();
    } 

    /** 
     *  Private, recursive helper method that traverses through the tree and
     *  returns a String representation of the element of that 
     *  <code>BinaryNode</code>.
     *
     *  @param  node - the starting <code>BinaryNode</code> for the traversal
     *  @return the String representation of the element of the 
     *          <code>BinaryNode</code>
     *  @throws IllegalArgumentException if node references null
     *  @precondition The <code>root</code> cannot be null
     */
    private String treeTraversal(BinaryNode node) {
        String cursor;
        if (node == null) {
            throw new IllegalArgumentException();
        } else if (node.left == null && node.right == null) {
            cursor = node.element.toString();
        } else if (node.left == null && node.right != null) {
            cursor = node.element + treeTraversal(node.right);
        } else if (node.left != null && node.right == null) {
            cursor = treeTraversal(node.left) + node.right.element;
        } else {
            cursor = treeTraversal(node.left) + node.element + 
                treeTraversal(node.right);
        }
        return cursor;
    }

    /**
     * Private, helper method that finds the <code>BinaryNode</code> with the
     * least value in the left side of the tree.
     *
     * @return the element of the <code>BinaryNode</code> with the least value
     * @throws IllegalArgumentException if the <code>root</code> references 
     *         null
     * @precondition The <code>root</code> cannot be null
     */
    private E findMin() {
        BinaryNode current = root;
        if (current == null) {
            throw new IllegalArgumentException();
        }
        while (current.left != null) {
            current = current.left;
        }
        return current.element;
    }

    /** 
     * Returns true if this set contains the specified element.
     *
     * @note It may seem odd for the parameter not to be the generic
     * type but this was a decision by Joshua Block, the designer
     * of the Java Collection Framework, because of problems that
     * arise if the parameter is restricted to be the same type
     * as the generic type of the set.  
     * 
     * @param 0 element whose presence in this set is to be tested
     * @return boolean if this set contains the specified element
     * @throws if the argument is null
     * @precondition the argument cannot be null
     */
    public boolean contains(Object o) {
        if (o == null) {
            throw new IllegalArgumentException();
        }
        @SuppressWarnings("unchecked")
        E type = (E) o;
        return contain(type, root);
    } 

    /**
     * Private, recursive helper method that returns true if this set 
     * contains the specified element.
     *
     * @param  compare - the element to be searched for
     * @param  node    - the <code>BinaryNode</code> that will represent the root
     * @return boolean if this set contains the element
     */
    private boolean contain(E compare, BinaryNode node) {
        boolean contains = false;
        if (node == null) {
            contains = false;
        }

        int compares = compare.compareTo(node.element);
        if (compares == 0) {
            contains = true;
        } else if (compares < 0) {
            contain(compare, node.left);
        } else {
            contain(compare, node.right);
        }
        return contains;
    }

    /** 
     * Adds the specified element to this set if it is not already present. 
	 * The new element is placed in the proper order in the sorted set.
     * If this set already contains the element, the call leaves the 
     * set unchanged and returns false. 
     * This ensures that sets never contain duplicate elements.
     * @param e - element to be added to this set
     * @return true if this set did not already contain the specified element
     * @throws NullPointerException - if the argument is null
     */
    public boolean add(E e) {
        boolean added = false;
        if (e == null) {
            throw new NullPointerException();
        }
        if (!contains(e)) {
            root  = insert(e, root);
            added = true;
            size++;
        } 
        modCount++;
        return added;
    }  

    /**
     * Private, recursive helper method that inserts the specified value at the
     * appropriate place in the tree.
     *
     * @param value - The value to be added to this set
     * @param node  - The root of this set
     * @return node that was added to this set
     */
    private BinaryNode insert(E value, BinaryNode node) {
        if (node == null) {
            node = new BinaryNode(value);
        }

        int compares = value.compareTo(node.element);
        if (compares == 0) {
            ;
        } else if (compares < 0) {
            node.left = insert(value, node.left);
        } else {
            node.right = insert(value, node.right);
        }
        return node;
    }

    /** 
     * Removes the specified element from this set if it is present.
     *
     * @note It may seem odd for the parameter not to be the generic
     * type but this was a decision by Joshua Block, the designer
     * of the Java Collection Framework, because of problems that
     * arise if the parameter is restricted to be the same type
     * as the generic type of the set.
     *
     * @param o the object to be removed from this set, if present
     * @return true if this set contained the element.
     */
    public boolean remove(Object o) {
        //TODO
        modCount++;
        return false;
    } 
 

    /** Returns the number of elements in this set. 
    * @return int the number of elements in the set
    */
    public int size() {
        return size;
    } 

    /** Returns true if this set contains no elements.
    * @return true if the set has no elements
    */
    public boolean isEmpty() {
        boolean empty = false;
        if (size == 0) {
            empty = true;
        }
        return empty;
    } 

    /** Removes all of the elements from this set.
    */
    public void clear() {
        root = null;
        modCount++;
    } 

    /**
     * Returns an iterator over the elements in this set. The elements are
     * returned in the sorted order.
     * @return an iterator over the elements in this set
     */
    @SuppressWarnings("unchecked")
    public Iterator<E> iterator() {
        return new Project3TreeSetIterator();
    }

    /** Compares the specified object with this set for equality. 
     * Returns true if the specified object is also a set, the two sets have
     * the same size, every member of the specified set is contained in
     * this set, and both sets have the members in the same order.
     *
     * @note It may seem odd for the parameter not to be the generic
     * type but this was a decision by Joshua Block, the designer
     * of the Java Collection Framework, because of problems that
     * arise if the parameter is restricted to be the same type
     * as the generic type of the set.
     *
	 * @precondition - the parameter is a reference to a valid Project3TreeSet	
	 * object which implies that the elements are in sorted order and
	 * there are no duplicates
     * @param o - object to be compared for equality with this set
     * @return true if the specified object is equal to this set
     */
    public boolean equals(Object o) {
        boolean equals = ( this == o );
        boolean okaySoFar = true;
        if (!equals && o instanceof Project3TreeSet) {
            Project3TreeSet otherSet = (Project3TreeSet) o;
            //ensure this is a Set
            if (toString().equals(otherSet.toString())) {
                if (size == otherSet.size) {
                //check every element in otherSet is contained in this set
                //check every element is in the same order
                //solution: if (toString().equals(otherSet.toString()) 
                }
            }
            //do not know if the equals call to root is valid && does what i
            //want 
        } else {
            okaySoFar = false;
        }
        equals = okaySoFar;
        return equals;
    } 

}
