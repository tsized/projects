import java.rmi.NotBoundException;                                              
import java.util.List;
import java.rmi.RemoteException;                                                
import java.rmi.registry.LocateRegistry;                                        
import java.rmi.registry.Registry; 
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * A class which drives the client, reading input from the user and executing 
 * commands based on that input.
 *
 * @author Tim Sizemore
 * @author Jordan Chapman
 * @version 12-3-12
 */
public class ClientDriver {

    /** The choice for exiting the client */
    private static final int EXIT = 1;

    /** The choice for adding a student */
    private static final int ADD_STUDENT = 2;

    /** The choice for finding a student */
    private static final int FIND_STUDENT = 3;

    /** The choice for deleting a student */
    private static final int DELETE_STUDENT = 4;

    /** The choice for listing students */
    private static final int LIST_STUDENTS = 5;

    /** The choice for finding the mean of a student's grades */
    private static final int MEAN_STUDENT = 6;

    /** The choice for finding the median of a student's grades */
    private static final int MEDIAN_STUDENT = 7;

    /** The choice for finding the mode of a student's grades */
    private static final int MODE_STUDENT = 8;

    /** The GradeInterface we will use to do the actual work */
    private static GradeInterface gradeBook = null; 
    /** A Scanner for reading input */
    private static Scanner scanner;

    /** The first name of the current student */
    private static String firstName;

    /** The last name of the current student */
    private static String lastName;

    /**
     * The entry point of the program, takes a host and port and displays a 
     * list of options for the user. 
     *
     * @param args Expects a host and a port.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
           usage(); 
        }
        String host = args[0];
        int port = 0;
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex) {
           usage(); 
        }
        try {
            Client tester = new Client(host, port);
            gradeBook = tester.getGradeInterface();
        } catch (RemoteException ex) {
            System.out.println("Connection-related error");
        } catch (NotBoundException ex) {
            System.out.println("Port not bound");
        }

        int choice = 0;
        scanner = new Scanner(System.in);
        while (choice != EXIT) {
            printOptions();
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                System.out.println("Please enter a number from the list");
                scanner.nextLine();
                System.out.println("");
            }
            switch (choice) {
                case ADD_STUDENT:
                    addStudent();
                    break;
                case FIND_STUDENT:
                    findStudent();
                    break;
                case DELETE_STUDENT:
                    deleteStudent();
                    break;
                case LIST_STUDENTS:
                    listStudents();
                    break;
                case MEAN_STUDENT:
                    meanStudent();
                    break;
                case MEDIAN_STUDENT:
                    medianStudent();
                    break;
                case MODE_STUDENT:
                    modeStudent();
                    break;
                case EXIT:
                default:
                    break;
            }
        }
    }

    /**
     * Prints the commands that a user can do.
     */
    private static void printOptions() {
        System.out.println("+---------------------+");
        System.out.println("|(" + EXIT + ") Exit             |");
        System.out.println("|(" + ADD_STUDENT + ") Add student      |");
        System.out.println("|(" + FIND_STUDENT + ") Find student     |");
        System.out.println("|(" + DELETE_STUDENT + ") Delete student   |");
        System.out.println("|(" + LIST_STUDENTS + ") List students    |");
        System.out.println("|(" + MEAN_STUDENT + ") Student's mean   |");
        System.out.println("|(" + MEDIAN_STUDENT +") Student's median |");
        System.out.println("|(" + MODE_STUDENT + ") Student's mode   |");
        System.out.println("+---------------------+");
        System.out.println("Please Enter Selection:");
    }

    /**
     * Prompts for then adds a student and given grades to gradeBook.
     */
    private static void addStudent() {
        scanner.nextLine();
        enterName();
        System.out.println("Enter any number of space" + 
                            " seperated grades:");

        Student newStudent = new Student(firstName, lastName);

        Scanner gradeScanner = new Scanner(scanner.nextLine());
        while(gradeScanner.hasNextInt()) {
            int grade = gradeScanner.nextInt();
            newStudent.addGrade(grade);
        }
        System.out.println("");

        try {
            gradeBook.addStudent(newStudent);
        } catch (RemoteException ex) {
            System.out.println("Error adding student");
        }
    }

    /**
     * Prompts for then finds and prints a student in the gradebook. Prints a 
     * message if no student by the name is found.
     */
    private static void findStudent() {
        scanner.nextLine();
        enterName();
        System.out.println("");

        try {
            Student found = gradeBook.lookupStudent(firstName, lastName);
            if (found != null) {
                System.out.println(found);
            } else {
                System.out.println("Student not found");
            }
        } catch (RemoteException ex) {
            System.out.println("No student found");
        } 
        System.out.println("");
    }

    /**
     * Lists all current students in the gradebook.
     */
    private static void listStudents() {
        List<Student> listOfStudents = null;
        try {
            listOfStudents = gradeBook.getStudentList();
        } catch (RemoteException ex) {
            System.out.println("No students in the list");
        }
        System.out.println("");
        System.out.println("List of Students:");
        for (int i = 0;  i < listOfStudents.size(); i++) {
            System.out.println(listOfStudents.get(i));
        }
        System.out.println("");
    }

    /**
     * Prompts for then deletes the student if found. Prints a message if no
     * student by the supplied name is found.
     */
    private static void deleteStudent() {
        scanner.nextLine();
        enterName();
        System.out.println("");

        try {
            gradeBook.removeStudent(firstName, lastName);
        } catch (RemoteException ex) {
            System.out.println("Student not found");
        }
        System.out.println("");
    }

    /**
     * Prompts for a student then prints the mean of the grades of that student
     * if that student exists in our grade book. If there is no student by that
     * name in our gradebook we print a message.
     */
    private static void meanStudent() {
       scanner.nextLine();
       enterName();
        
       try {
           System.out.println(gradeBook.getMean(firstName, lastName));
       } catch (RemoteException ex) {
           System.out.println("Connection issue occured");
       } catch (NoSuchElementException ex) {
           System.out.println("Student not found");
       }
    }

    /**
     * Prompts for a student then prints the median of the grades of that student
     * if that student exists in our grade book. If there is no student by that
     * name in our gradebook we print a message.
     */
    private static void medianStudent() {
       scanner.nextLine();
       enterName();
        
       try {
           System.out.println(gradeBook.getMedian(firstName, lastName));
       } catch (RemoteException ex) {
           System.out.println("Connection issue occured");
       } catch (NoSuchElementException ex) {
           System.out.println("Student not found");
       } catch (ArithmeticException ex) {
           System.out.println("There are no grades for the student");
       }
    }

    /**
     * Prompts for a student then prints the mode of the grades of that student
     * if that student exists in our grade book. If there is no student by that
     * name in our gradebook we print a message.
     */
    private static void modeStudent() {
       scanner.nextLine();
       enterName();
        
       try {
           System.out.println(gradeBook.getMode(firstName, lastName));
       } catch (RemoteException ex) {
           System.out.println("Connection issue occured");
       } catch (NoSuchElementException ex) {
           System.out.println("Student not found");
       } catch (ArithmeticException ex) {
           System.out.println("There are no grades for the student");
       }
    }

    /**
     * Prompts for a student's name.
     */
    private static void enterName() {
        System.out.println("Enter student's first name:");
        firstName = scanner.nextLine();
        System.out.println("Enter student's last name:");
        lastName = scanner.nextLine();
    }

    /**
     * Prints the usage message then exits the program with error code 1.
     */
    private static void usage() {
        System.out.println("Usage: java ClientDriver <host>" +
                            " <port>\n");
        System.exit(1);
    }
}
