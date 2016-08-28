package studentmanagement2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Student extends SearchableClass {

    String studentID;

    String name;

    List<Module> modules = new ArrayList<Module>();

    HashMap<String, Integer> taskMarks = new HashMap<String, Integer>();

    public Student() {

    }

    public Student(String args) {
        studentID = MyReader.getArgument(args, 0, Integer.class);
        name = MyReader.getArgument(args, 1, String.class);
    }

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
    }

    @Override
    public String toString() {
        return studentID + " " + name;
    }

    public boolean tookModule(Module module) {
        return modules.contains(module);
    }

    void takeModule(Module module) {
        modules.add(module);
    }

    @Override
    public String getID() {
        return studentID;
    }
}
