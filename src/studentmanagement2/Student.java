package studentmanagement2;

import studentmanagement2.SortingHandler.SortableData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Student extends SearchableClass {

    String studentID;

    String name;

    List<String> modules = new ArrayList<String>();

    HashMap<String, Integer> taskMarks = new HashMap<String, Integer>();

    public Student() {

    }

    public Student(String args) {
        studentID = MyReader.getArgument(args, 0, String.class);
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
        modules.add(module.getID());
    }

    @Override
    public String getID() {
        return studentID;
    }

    public int getTaskMark(String taskID) {

        if (taskMarks.containsKey(taskID)) {
            return taskMarks.get(taskID);
        }
        return -1;
    }

    public SortableData getMarkSortableData(Module module) {
        String seperator = "\t";
        String s = module.ModuleID + seperator + studentID + seperator + name + seperator + seperator;
        float weightedValue = 0;
        for (String taskID : module.taskIds) {
            AssessmentTask task = StudentManagement2.school.getTask(taskID);
            if (task != null) {
                int mark = getTaskMark(taskID);
                if (mark >= 0) {
                    float percent = (float) mark / task.fullMarks * 100;
                    weightedValue += percent * module.getWeightedRatio(taskID);
                    s += (int) percent + seperator;
                } else {
                    s += "No Data    ";
                }
            } else {
                Debug.LogError("Task with ID " + taskID + " was not found!");
            }
        }
        s += weightedValue;
        SortableData sd = new SortableData(s, (int) weightedValue);
        return sd;
    }
}
