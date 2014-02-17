import java.lang.ArithmeticException;
import java.rmi.RemoteException;
import java.rmi.Remote;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Shared interference used by the GradeBook client and server.
 *
 * @author Dr. Kreahling
 * @version 12/3/12
 */
public interface GradeInterface extends Remote {

    /**
     * Adds a student to the gradebook.
     *
     * @param contact          the new student to be added
     * @throws RemoteException when a communication-related error occurs.
     */
    public void addStudent(Student contact) throws RemoteException;

    /**
     * Returns the information of the given Student.
     *
     * @param firstName        the Student's first name
     * @param lastName         the Student's last name
     * @return                 the information for the desired Student
     * @throws RemoteException when a communication-related error occurs.
     */
    public Student lookupStudent(String firstName, String lastName)
                                                         throws RemoteException;
    
    /**
     * Removes the information of the given Student from the gradebook.
     *
     * @param firstName        the Student's first name
     * @param lastName         the Student's last name
     * @return                 the information for the desired Student
     * @throws RemoteException when a communication-related error occurs.
     */
    public Student removeStudent(String firstName, String lastName)
                                                         throws RemoteException;
    
    /**
     * Returns a list of all Student's.
     *
     * @return                  a list of all Students in the gradebook
     * @throws RemoteExeception when a communication-related error occurs.
     */
    public List<Student> getStudentList() throws RemoteException;

    /**
     * Returns the mean value of the given Student's grades.
     *
     * @param firstName               the Student's first name
     * @param lastName                the Student's last name
     * @return                        the mean value of the Student's grades
     * @throws NoSuchElementException if the Student cannot be found.
     * @throws RemoteException        when a communication-related error occurs.
     */
    public double getMean(String firstName, String lastName)
                                 throws RemoteException, NoSuchElementException;
    
    /**
     * Returns the mean value of the given Student's grades.
     *
     * @param firstName               the Student's first name
     * @param lastName                the Student's last name
     * @return                        the median value of the Student's grades
     * @throws ArithmeticException    if the Student exists but there are no 
     *                                grades
     * @throws NoSuchElementException if the Student cannot be found.
     * @throws RemoteException        when a communication-related error occurs.
     *
     */
    public int getMedian(String firstName, String lastName)
            throws RemoteException, NoSuchElementException, ArithmeticException;

    /**
     * Returns the mean value of the given Student's grades.
     *
     * @param firstName               the Student's first name
     * @param lastName                the Student's last name
     * @return                        the mean value of the Student's grades
     * @throws ArithmeticException    if the Student exists but there are no 
     *                                grades
     * @throws NoSuchElementException if the Student cannot be found.
     * @throws RemoteException        when a communication-related error occurs.
     *
     */
    public int getMode(String firstName, String lastName)
            throws RemoteException, NoSuchElementException, ArithmeticException;
}
