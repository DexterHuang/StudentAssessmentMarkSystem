package studentmanagement2;

import java.util.ArrayList;
import java.util.List;

public class Module extends SearchableClass {

    String ModuleID;

    String ModuleName;

    List<String> taskIds = new ArrayList<String>();

    public Module(String id, String name) {
        this.ModuleID = id;
        this.ModuleName = name;
        taskIds.add("Null");
        taskIds.add("Null");
        taskIds.add("Null");
    }

    public Module() {

    }

    public float getWeightedRatio(String taskID) {
        if (taskIds.contains(taskID)) {
            float total = 0;
            AssessmentTask mainTask = StudentManagement2.school.getTask(taskID);
            for (String id : taskIds) {
                AssessmentTask task = StudentManagement2.school.getTask(id);
                if (task != null) {
                    total += task.weight;
                }
            }
            return (float) mainTask.weight / total;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return ModuleID + " " + ModuleName;
    }

    @Override
    public String getID() {
        return ModuleID;
    }
}
