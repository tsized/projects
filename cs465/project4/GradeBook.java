import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

/**
 * Models a gradebook with multiple instances of Students.
 *
 * @author Tim Sizemore
 * @author Jordan Chapman
 * @version 12-3-12
 */
public class GradeBook implements GradeInterface {

    /** Holds multiple instances of Student. */
    private HashMap<String, Student> students;
    
    /**
     * Creates a new HashMap.
     */
    public GradeBook() {
        students = new HashMap<String, Student>();
    }

    /**
     * Adds a Student to the HashMap.
     *
     * @param student added to the HashMap
     */
    public void addStudent(Student student) {
        students.put(student.getFirst() + student.getLast(), student);
    }

    /**
     * Finds a Student from the HashMap.
     *
     * @param firstName Student's first name
     * @param lastName  Student's last name
     * @return          the Student, if he exists
     */
    public Student lookupStudent(String firstName, String lastName) {
        Student s = students.get(firstName + lastName);
        return s;
    }

    /**
     * Removes a Student from the HashMap.
     *
     * @param firstName Student's first name
     * @param lastName  Student's last name
     */
    public Student removeStudent(String firstName, String lastName) {
        return students.remove(firstName + lastName);
    }

    /**
     * Returns a List of Students.
     *
     * @return a List of Students
     */
    public List<Student> getStudentList() {
        return new ArrayList<Student>(students.values());
    }

    /**
     * Returns the mean of the Student's grades.
     *
     * @param firstName Student's first name
     * @param lastName  Student's last name
     * @return          the mean value of the Student's grades
     * @throws NoSuchElementException if the Student does not exist.
     * @throws ArithmeticException if there are no grades.
     */
    public double getMean(String firstName, String lastName) 
                             throws NoSuchElementException,ArithmeticException {
        List<Integer> grades = students.get(firstName + lastName).getGrades();
        double mean = 0.0;
        for (int i : grades) {
            mean += i;
        }
        return mean/grades.size();
    }

    /**
     * Returns the median of the Student's grades.
     *
     * @param firstName Student's first name
     * @param lastName  Student's last name
     * @return          the median value of the Student's grades
     * @throws NoSuchElementException if the Student does not exist.
     * @throws ArithmeticException if there are no grades.
     */
    public int getMedian(String firstName, String lastName) 
                             throws NoSuchElementException,ArithmeticException {
        List<Integer> grades = students.get(firstName + lastName).getGrades();
        Collections.sort(grades);
        return grades.get(grades.size() / 2);

    }

    /**
     * Returns the mode of the Student's grades.
     *
     * @param firstName Student's first name
     * @param lastName  Student's last name
     * @return          the mode value of the Student's grades
     * @throws NoSuchElementException if the Student does not exist.
     * @throws ArithmeticException if there are no grades.
     */
    public int getMode(String firstName, String lastName) 
                             throws NoSuchElementException,ArithmeticException {
        List<Integer> grades = students.get(firstName + lastName).getGrades();
        Collections.sort(grades);
        int maxValue = 0, maxCount = 0;

        for (int i = 0; i < grades.size(); ++i) {
            int count = 0;
            for (int j = 0; j < grades.size(); ++j) {
                if (grades.get(j) == grades.get(i)) ++count;
            }
            if (count > maxCount) {
                maxCount = count;
                maxValue = grades.get(i);
            }
        }
        return maxValue;
    }
}
