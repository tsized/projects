import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

/**
 * This class tests the <code>Project3TreeSet</code> class.
 * Its tests for the <code>iterator</code> and <code>toString</code>
 * methods check that the elements are in sorted order.
 *
 * @author Dr. Mark A. Holliday
 * @author Ben Casses
 * @version 6 October 2011
 */

public class Project3Tester {
    /** The number of tests that have passed. */
    private int passes;

    /** The number of tests that have failed. */
    private int failures;

    /**
     * A method that initializes the two counters (passes and failures)
     * and calls helper methods to run the tests.
     */
    public void runTests() {
        passes   = 0;
        failures = 0;

        System.out.println("Testing of class TreeSet");
     
        this.testConstructor();
        this.testContains();
        this.testAdd();
        this.testRemove();
        this.testSize();
        this.testIsEmpty();
        this.testClear();
        this.testIterator();
		this.testToString();
        this.testEquals();

        System.out.println("Tests executed:    " + (passes + failures));
        System.out.println("     Successful:   " + passes);
        System.out.println("     Unsuccessful: " + failures);
    }

    /**
     * A helper method that contains the common aspects of conducting a test.
     * The first argument involves calling the method being tested and comparing
     * its return value with the expected value. This is a boolean expression
     * that evaluates to true only if the method being tested returned the
     * expected value. If that boolean value is true, then counter of passed
     * tests is incremented; otherwise the counter of failed tests is
     * incremented.  A string is constructed and displayed indicating whether
     * the test passed or failed and the expected and actual return values from
     * the method being tested. 
     *
     * @note The type of the third parameter is Object to handle the case when
     * the third argument is a method call that returns a null value. 
     *
     * @param condition The result of the boolean expression which compares the
     * return value of the tested method with its expected value. Thus, if the
     * result is true, the test is passed and otherwise the test fails.
     * @param expected A string describing the expected return value of the
     *  tested method.
     * @param actual A string describing the actual return value of the tested
     * method. The corresponding argument is a call to the method being tested.
     */
    private void test(boolean condition, String  expected, Object actual) {
        String msg = "EXPECTED: " + expected + "\n\tACTUAL: " + (String) actual;
        if (condition) {
            msg = msg + "\tPASSED";
            passes++;
        } else {
            msg = msg + "\tFAILED";
            failures++;
        }
        System.out.println(msg);
    }

    /**
     * Test the assumptions made upon creation of a new TreeSet
     */
    private void testConstructor() {

        Project3TreeSet<String> p = new Project3TreeSet<String>();

        test( ( 0 == p.size() ), "constructor: initial size is 0", "" + p.size() );

        test( p.toString().equals("<>"), "constructor: new Set String is empty",
                p.toString() );

        test( p.isEmpty(), "constructor: new set isEmpty is true", "" + p.isEmpty() );

    }

    /**
     * Test the contains method
     */
    private void testContains() {

        Project3TreeSet<String> p = new Project3TreeSet<String>();

        test( !p.contains( "foo" ), "contains: Contains is false in an empty Set",
                ""+p.contains( "foo" ) );

        p.add( "foo" );

        test( p.contains( "foo" ), "contains: Contains is true when element is added",
                ""+p.contains( "foo" ) );
        test( !p.contains( "oof" ), "contains: Contains is false for another element",
                ""+p.contains( "oof" ) );

        p.remove( "foo" );

        test( !p.contains( "foo" ), "contains: Contains is now false after " + 
                "element has been removed", "" + p.contains( "foo" ) );

        p.add( "foo" );

        test( p.contains( "foo" ), "contains: Contains is true when element is re-added",
                ""+p.contains( "foo" ) );

        p.add( "oof" );
        p.add( "ofo" );

        test( p.contains( "foo" ), "contains: Contains is still true after other " +
               "elements are added", ""+p.contains( "foo" ) );

        p.clear();

        test( !p.contains( "foo" ), "contains: Contains is false after clear",
               ""+p.contains( "foo" ) );

        boolean exceptionThrown = false;
        try {
            p.contains( null );
        } catch ( NullPointerException npe ) {
            exceptionThrown = true;
        }
        test( exceptionThrown, "contains: Cannot check contains for \"null\"",
                ""+exceptionThrown );

    }

    /**
     *  Tester for add method.  Also tested within other tests.
     */
    private void testAdd() {

        Project3TreeSet<String> p = new Project3TreeSet<String>();

        boolean returnVal = p.add( "foo" );
        test( returnVal, "add:  can add something valid", ""+returnVal );

        returnVal = p.add( "foo" );
        test( !returnVal, "add:  can't add it again", ""+returnVal );

        returnVal = p.add( "oof" );
        returnVal = p.add( "ofo" );

        returnVal = p.add( "foo" );
        test( !returnVal, "add:  still can't add it again after other adds",
                ""+returnVal );

        boolean exceptionThrown = false;
        try {
            p.add( null );
        } catch ( NullPointerException npe ) {
            exceptionThrown = true;
        }
        test( exceptionThrown, "add:  Exception thrown adding \"null\"",
                ""+exceptionThrown );

    }

    /**
     * Test the remove method.
     */
    private void testRemove() {

        Project3TreeSet<Integer> p = new Project3TreeSet<Integer>();

        test( !p.remove(4), "remove: fail to remove from an empty set",
            ""+p.remove(4) );

        p.add(4);

        boolean returnVal = p.remove(4);
        test( returnVal, "remove: valid removal after added", ""+returnVal );

  		returnVal = p.remove(4);
        test( !returnVal, "remove: item was actually removed", "" + !returnVal );

        test( !p.remove(4), "remove: fail to remove second time", ""+p.remove(4) );

        p.add(8);
        p.add(-4);

        test( !p.remove(4), "remove: fail to remove after other adds", ""+p.remove(4) );

		returnVal = p.remove(-4);
        test( returnVal, "remove: valid removal after other adds", "" + returnVal );

        boolean exceptionThrown = false;
        try {
            p.remove( null );
        } catch ( NullPointerException npe ) {
            exceptionThrown = true;
        }
        test( exceptionThrown, "remove: Exception thrown removing \"null\"",
                ""+exceptionThrown );

    }

    /**
     * Test the size method.
     */
    private void testSize() {

        Project3TreeSet<Integer> p = new Project3TreeSet<Integer>();

        test( 0 == p.size(), "size: initial size is zero", ""+p.size() );

        p.add(4);

        test( 1 == p.size(), "size: add one, size is changed", ""+p.size() );

        p.add(4);

        test( 1 == p.size(), "size: add failure, size is unchanged", ""+p.size() );

        p.add(2);
        p.add(3);
        p.add(-88);

        test( 4 == p.size(), "size: added three more, size kept up", ""+p.size() );

        p.remove(3);

        test( 3 == p.size(), "size: removed one, size kept up", ""+p.size() );

        p.remove(99);

        test( 3 == p.size(), "size: invalid remove, size unchanged", ""+p.size() );

        p.remove(2);
        p.remove(-88);
        p.remove(4);

        test( 0 == p.size(), "size: removed all, size is zero", ""+p.size() );

        p.add(4);

        test( 1 == p.size(), "size: add one, size is changed again", ""+p.size() );

        p.clear();

        test( 0 == p.size(), "size: after clear, size is zero", ""+p.size() );

    }

    /**
     * Test the isEmpty method.
     */
    private void testIsEmpty() {
    
        Project3TreeSet<String> p = new Project3TreeSet<String>();

        test( p.isEmpty(), "isEmpty: A new set is empty", ""+p.isEmpty() );

        p.add( "a" );

        test( !p.isEmpty(), "isEmpty: not empty after adding stuff", ""+p.isEmpty() );

        p.add( "b" );
        p.add( "c" );
        p.remove( "a" );
        p.remove( "c" );
        p.remove( "b" );

        test( p.isEmpty(), "isEmpty: empty after all items removed", ""+p.isEmpty() );

        p.add( "b" );
        p.add( "c" );
        p.clear();

        test( p.isEmpty(), "isEmpty: empty after clear", ""+p.isEmpty() );
    
    }

    /**
     * Test the Clear method.
     */
    private void testClear() {

        Project3TreeSet<Integer> p = new Project3TreeSet<Integer>();

        p.clear();

        test( p.isEmpty(), "clear: empty after clear", ""+p.isEmpty() );
        test( 0 == p.size(), "clear: size 0 after clear", ""+p.size() );
        test( p.toString().equals("<>"), "clear: toString is \"<>\" after clear",
                p.toString() );

        p.add(3);
        p.add(7);
        p.clear();

        test( p.isEmpty(), "clear: empty after clear", ""+p.isEmpty() );
        test( 0 == p.size(), "clear: size 0 after clear", ""+p.size() );
        test( p.toString().equals("<>"), "clear: toString is \"<>\" after clear",
                p.toString() );

    }

    /**
     * Test the iterator Method.
     */
    private void testIterator() {

        Project3TreeSet<String> p = new Project3TreeSet<String>();

        Iterator<String> i = p.iterator();

        test( !i.hasNext(), "iterator: Iterator over empty has no next", ""+i.hasNext() );

        boolean exceptionThrown = false;
        try {
            i.next();
        } catch ( NoSuchElementException nse ) {
            exceptionThrown = true;
        }
        test( exceptionThrown, "iterator: next on empty iterator throws exception",
                ""+exceptionThrown );

        exceptionThrown = false;
        try {
            i.remove();
        } catch ( UnsupportedOperationException ise ) {
            exceptionThrown = true;
        }
        test( exceptionThrown, "iterator: iterator remove operation throws exception",
                ""+exceptionThrown );

        p.add( "cc" );
        p.add( "bb" );
        p.add( "aa" );
		p.add("dd");
        test( p.toString().equals( "<aa, bb, cc, dd>" ), 
			"iterator: looks like \"<aa, bb, cc, dd>\"", p.toString() );
        i = p.iterator();
    
        test( i.hasNext(), "iterator: has next is valid with 4 elements",
            ""+i.hasNext() );
        String catchNext = i.next();
        test( catchNext.equals( "aa" ) , "iterator: expected return from first call" +
                " to next is aa", catchNext );
        catchNext = i.next();
        test( catchNext.equals( "bb" ) , "iterator: expected return from 2nd call" +
                " to next is bb", catchNext);
        catchNext = i.next();
        test( catchNext.equals( "cc" ), "iterator: expected return from 3rd call" +
                " to next is cc", catchNext );
        catchNext = i.next();
        test( catchNext.equals( "dd" ), "iterator: expected return from 4th call" +
                " to next is dd", catchNext );

        exceptionThrown = false;
        try {
            i.next();
        } catch ( NoSuchElementException nse) {
            exceptionThrown = true;
        }
        test( exceptionThrown, "iterator: exhausted iterator throws exception",
                ""+exceptionThrown );


        test( p.toString().equals( "<aa, bb, cc, dd>" ), 
			"iterator: looks like \"<aa, bb, cc, dd>\"", p.toString() );
        i = p.iterator();
        test( i.hasNext(), "iterator: has next is valid with 4 elements",
            ""+i.hasNext() );
        catchNext = i.next();
        test( catchNext.equals( "aa" ) , "iterator: expected return from first call" +
                " to next is aa", catchNext );
		p.add("ee");

        exceptionThrown = false;
        try {
            i.next();
        } catch ( ConcurrentModificationException nse) {
            exceptionThrown = true;
        }
        test( exceptionThrown, "iterator: concurrent modification exception",
                ""+exceptionThrown );

    }

    /**
     * Test the toString method.
     */
    private void testToString() {

        Project3TreeSet<String> p = new Project3TreeSet<String>();

        test( p.toString().equals( "<>" ), "toString: new Set looks like \"<>\"",
                p.toString() );

        p.add("a");

        test( p.toString().equals( "<a>" ), "toString: one element looks like \"<a>\"",
                p.toString() );

        p.add("b");

        test( p.toString().equals( "<a, b>" ),
                "toString: two elements looks like \"<a, b>\"",
                p.toString() );

        p.remove("a");

        test( p.toString().equals( "<b>" ), "toString: one element, looks like \"<b>\"",
                p.toString() );

        p.remove("b");

        test( p.toString().equals( "<>" ), "toString: empty again", p.toString() );

        p.add("d");
        p.add("c");
		p.add("a");
		p.add("b");
        test( p.toString().equals( "<a, b, c, d>" ), 
			"toString: looks like \"<a, b, c, d>\"", p.toString() );
        p.clear();

        test( p.toString().equals( "<>" ), "toString: clear is empty", p.toString() );

    }

    /**
     * Test the equals method.
     */
    private void testEquals() {

        Project3TreeSet<String> p1 = new Project3TreeSet<String>();
        Project3TreeSet<String> p2 = new Project3TreeSet<String>();
        Project3TreeSet<Integer> i = new Project3TreeSet<Integer>();

        test( p1.equals(p2), "equals: two empty sets of like type are equal",
                ""+p1.equals(p2) );

        test( p1.equals(p1), "equals: self equality, empty",
                ""+p1.equals(p1) );

        p1.add("a");
        test( p1.equals(p1), "equals: self equality, populated",
                ""+p1.equals(p1) );
        test( !p1.equals(p2), "equals: unequal sets", ""+p1.equals(p2) );
        test( !p2.equals(p1), "equals: unequal sets", ""+p2.equals(p1) );

        p2.add("b");
        test( !p1.equals(p2), "equals: equal size, wrong elements", ""+p1.equals(p2) );
        test( !p2.equals(p1), "equals: equal size, wrong elements", ""+p2.equals(p1) );

        p2.add("a");
        p1.add("b");
        test( p1.equals(p2), "equals: equalized", ""+p1.equals(p2) );
        test( p2.equals(p1), "equals: equalized", ""+p2.equals(p1) );

        p1.remove("A");
        test( p1.equals(p2), "equals: invalid remove, still equal", ""+p1.equals(p2) );

        p1.remove("a");
        p2.remove("a");
        test( p1.equals(p2), "equals: valid remove, still equal", ""+p1.equals(p2) );

        p1.clear();
        p2.clear();
        test( p1.equals(p2), "equals: clear, still equal", ""+p1.equals(p2) );

    }

    /**
     * Provides the entry point for this application.  This method creates
     * an instance of this class and executes the
     * runTests() method.
     *
     * @param args Unused.
     */
    public static void main(String[] args) {
        Project3Tester tester = new Project3Tester();
        tester.runTests();
    }
}
