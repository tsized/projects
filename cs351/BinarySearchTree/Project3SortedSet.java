import java.util.Iterator;
import java.lang.Comparable;
/**
* The interface specifies a subset of the <code>SortedSet</code>
* interface that is part of the Java Collections Framework.
* It is a modification I developed that does not include any of the 
* additional methods specified in the <code>SortedSet</code> interface.
* Instead it just has different javadoc for the <code>iterator</code>
* and <code>equals</code> methods that require the values to be in
* sorted order.
*
* @author Mark A. Holliday
* @version 18 March 2011
*/
public interface Project3SortedSet<E> extends Project3Set<E>
{

    /** Returns an iterator over the elements in this set. 
	* The elements are returned in the order in which they are sorted.
    * @return an iterator over the elements in this set
    */
    public Iterator<E> iterator();

    /** Compares the specified object with this set for equality. 
    * Returns true if the specified object is also a set, the two sets have the same size, 
    * every member of the specified set is contained in this set, and
	* the elements in the two sets are in the same sorted order.
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

