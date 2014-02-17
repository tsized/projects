import java.util.Iterator;
import java.lang.Comparable;
/**
* The interface specifies a subset of the <code>Set</code>
* interface that is part of the Java Collections Framework.
* The javadoc is my modification (mainly simplification) of
* the javadoc for the real <code>Set</code> interface.
*
* @author Mark A. Holliday
* @version 18 March 2011
*/
public interface Project3Set<E>
{
    /** Returns true if this set contains the specified element.
    *
    * @note It may seem odd for the parameter not to be the generic
    * type but this was a decision by Joshua Bloch, the designer
    * of the Java Collection Framework, because of problems that
    * arise if the parameter is restricted to be the same type
    * as the generic type of the set.  
    * 
    * @param o element whose presence in this set is to be tested
    * @return boolean if this set contains the specified element
    * @throws if the argument is null
    * @precondition the argument cannot be null
    */
    public boolean contains(Object o);

    /** Adds the specified element to this set if it is not already present. 
    * If this set already contains the element, the call leaves the 
    * set unchanged and returns false. 
    * This ensures that sets never contain duplicate elements.
    * @param e - element to be added to this set
    * @return true if this set did not already contain the specified element
    * @throws NullPointerException - if the argument is null
    */
    public boolean add(E e);

    /** Removes the specified element from this set if it is present.
    *
    * @note It may seem odd for the parameter not to be the generic
    * type but this was a decision by Joshua Bloch, the designer
    * of the Java Collection Framework, because of problems that
    * arise if the parameter is restricted to be the same type
    * as the generic type of the set.
    *
    * @param o the object to be removed from this set, if present
    * @return true if this set contained the element.
    */
    public boolean remove(Object o);

    /** Returns the number of elements in this set. 
    * @return int the number of elements in the set
    */
    public int size();

    /** Returns true if this set contains no elements.
    * @return true if the set has no elements
    */
    public boolean isEmpty();

    /** Removes all of the elements from this set.
    */
    public void clear();

    /** Returns an iterator over the elements in this set. The elements are returned in 
    * no particular order 
    * @return an iterator over the elements in this set
    */
    public Iterator<E> iterator();

    /** Compares the specified object with this set for equality. 
    * Returns true if the specified object is also a set, the two sets have the same size, 
    * and every member of the specified set is contained in this set.
    *
    * @note It may seem odd for the parameter not to be the generic
    * type but this was a decision by Joshua Bloch, the designer
    * of the Java Collection Framework, because of problems that
    * arise if the parameter is restricted to be the same type
    * as the generic type of the set.
    *
    * @param o - object to be compared for equality with this set
    * @return true if the specified object is equal to this set
    */
    public boolean equals(Object o);

}

