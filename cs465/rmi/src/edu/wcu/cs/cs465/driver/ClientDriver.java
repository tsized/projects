import java.rmi.NotBoundException;                                              
import java.rmi.RemoteException;                                                
import java.util.ArrayList;
import java.util.Scanner;

public class ClientDriver {
    private static final int EXIT = 1;
    private static final int ADD_STUDENT = 2;
    private static final int FIND_STUDENT = 3;
    private static final int DELETE_STUDENT = 4;
    private static final int LIST_STUDENTS = 5;
    private static final int MEAN_STUDENT = 6;
    private static final int MEDIAN_STUDENT = 7;
    private static final int MODE_STUDENT = 8;
   
    // so much error checking, NOT done.
    public static void main(String[] args) {
        if (args.length != 2) {
           usage(); 
        }
        String host = args[0];
        int port = 0;
        int choice = ADD_STUDENT;
        Scanner scanner = new Scanner(System.in);
        Student selectedStudent = null;
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex) {
           usage(); 
        }
        
        while(choice != EXIT) {
            boolean keepGoing = true;
            System.out.println("(" + EXIT + ") Exit");
            System.out.println("(" + ADD_STUDENT + ") Add student");
            System.out.println("(" + FIND_STUDENT + ") Find student");
            System.out.println("(" + LIST_STUDENTS + ") List students");

            choice = scanner.nextInt();
            switch(choice) {
                case EXIT: 
                    keepGoing = false;
                    break;
                case ADD_STUDENT:
                    System.out.println("Enter <student_first> " +
                                       "<student_lastname>"); 
                    String line[] = scanner.nextLine().split(" ");
                    String firstName = line[0];
                    String lastName = line[1];
                    System.out.println("Enter any number of  space" + 
                                        " seperated grades");
                    //add student
                    while(scanner.hasNext()) {
                        int grade = scanner.nextInt();
                        //add grade to student
                    }
                    break;
                case FIND_STUDENT:
                    System.out.println("Enter <student_first> " +
                                       "<student_lastname>"); 
                    String in[] = scanner.nextLine().split(" ");
                    firstName = in[0];
                    lastName = in[1];
                    //TODO find the student
                    Student found = null;
                    if (found == null) {
                        System.out.println("No student found");
                    } else {
                        System.out.println("Student " + firstName + " " + 
                                            lastName + " selected.");
                        selectedStudent = found;
                    }
                    break;
                case LIST_STUDENTS:
                    //TODO Get the list of students
                    ArrayList<Student> students = new ArrayList<Student>();
                    for (int i = 0; i < students.size(); ++i) {
                        System.out.println("" + i + " " + students.get(i));
                    }
                    System.out.println("Enter number of student to select");
                    int index = scanner.nextInt();
                    while (index < 0 || index > students.size()) {
                        System.out.println("Incorrect number, try again");
                        index = scanner.nextInt();
                    }
                    selectedStudent = students.get(index);
                    System.out.println("Student " + selectedStudent + 
                                       "selected.");
                    break;
                default:
                    System.out.println("Incorrect selection!");
                    keepGoing = false;
                    break;
            } 

            if (choice != EXIT && keepGoing &&selectedStudent != null) {
                while(keepGoing) {
                    System.out.println("(" + EXIT + ") Exit");
                    System.out.println("(" + DELETE_STUDENT + 
                                        ") Delete student");
                    System.out.println("(" + MEAN_STUDENT +
                                        ") Student's mean grade");
                    System.out.println("(" + MEDIAN_STUDENT +
                                        ") Student's median grade");
                    System.out.println("(" + MODE_STUDENT + 
                                        ") Student's mode grade");
                    choice = scanner.nextInt();
                    keepGoing = false;
                    switch (choice) {
                        case EXIT:
                            break;
                        case DELETE_STUDENT:
                            //delete selected student
                            break;
                        case MEAN_STUDENT:
                            double mean = -1;
                            System.out.println("Mean for " + selectedStudent +
                                                mean);
                            break;
                        case MEDIAN_STUDENT:
                            //Get calculated median
                            int median = -1;
                            System.out.println("Median for " + selectedStudent +
                                                median);
                        case MODE_STUDENT:
                            int mode = -1;
                            System.out.println("Mode for " + selectedStudent +
                                                mode);
                            break;
                        default:
                            System.out.println("Incorrect selection!");
                            keepGoing = true;
                            break;
                    }
                }
            }
        }


        try {
            //Client tester = new Client(args[0]);
            //tester.go();
        } catch (Exception e) {
            System.out.println("Bad to ignore exceptions!");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    private static void usage() {
        System.out.println("Usage: java ClientDriver " +
            "<host> <port>\n");
        System.exit(1);
    }

}
