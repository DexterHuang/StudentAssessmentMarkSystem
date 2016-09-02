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

    @Override
    public String toString() {
        return ModuleID + " " + ModuleName;
    }

    @Override
    public String getID() {
        return ModuleID;
    }
}
