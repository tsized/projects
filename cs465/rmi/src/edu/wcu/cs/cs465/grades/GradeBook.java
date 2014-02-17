import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
public class GradeBook implements GradeInterface {
    private HashMap<String, Student> students;
    
    public GradeBook() {
        students = new HashMap<String, Student>();
    }

    public void addStudent(Student student) {
        students.put(student.getFirst() + student.getLast(), 
                            new Student(student.getFirst(), student.getLast()));
    }

    public Student lookupStudent(String firstName, String lastName) {
        return students.get(firstName + lastName);
    }

    public Student removeStudent(String firstName, String lastName) {
        return students.remove(firstName + lastName);
    }

    public List<Student> getStudentList() {
        return new ArrayList<Student>(students.values());
    }

    public double getMean(String firstName, String lastName) {
        List<Integer> grades = students.get(firstName + lastName).getGrades();
        double mean = 0.0;
        for (int i : grades) {
            mean += i;
        }
        return mean/grades.size();
    }

    public int getMedian(String firstName, String lastName) {
        List<Integer> grades = students.get(firstName + lastName).getGrades();
        Collections.sort(grades);
        return grades.get(grades.size() / 2);

    }

    public int getMode(String firstName, String lastName) {
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
