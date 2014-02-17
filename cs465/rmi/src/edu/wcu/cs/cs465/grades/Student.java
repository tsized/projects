import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Models a student and their grades.
 *
 * @author Tim Sizemore
 * @author Jordan Chapman
 * @version 12-3-12
 */
public class Student implements Serializable {
    private static final long serialVersionUID = 7526472295622776147L;
   
    /**
     * First name of the Student.
     */
    private String firstName;

    /**
     * Last name of the Student.
     */
    private String lastName;
    
    /**
     * List of Students grades.
     */
    private List<Integer> grades;

    /**
     * Creates a Student with the given first and last name.
     *
     * @param firstName the Student's first name
     * @param lastName  the Student's last name
     */
    public Student(String fistName, String lastName) {
        this.firstName = firstName;
        this.lastName  = lastName;
        grades         = new ArrayList<Integer>();
    }

    /**
     * Gets the Student's first name.
     *
     * @return the Student's first name
     */
    public String getFirst() {
        return firstName;
    }

    /**
     * Gets the Student's last name.
     *
     * @return the Student's last name
     */
    public String getLast() {
        return lastName;
    }

    /**
     * Gets the Student's grades.
     *
     * @return a list of all the Student's grades.
     */
    public List<Integer> getGrades() {
        return grades;
    }
}
