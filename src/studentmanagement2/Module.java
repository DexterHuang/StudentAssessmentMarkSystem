package studentmanagement2;

public class Module extends SearchableClass {

    int ModuleID;

    String ModuleName;

    int maxPopulation;

    int population = 0;

    int semester;

    public Module(int ModuleID, String ModuleName, int maxPopulation, int semester) {
        this.ModuleID = ModuleID;
        this.ModuleName = ModuleName;
        this.maxPopulation = maxPopulation;
        this.semester = semester;
    }

    public Boolean isFull() {
        return population >= maxPopulation;
    }

    public Boolean isOfferedInSemester(int semester) {
        return this.semester == semester;
    }

    @Override
    public String toString() {
        return ModuleID + " " + ModuleName + " " + semester + " " + population + " " + maxPopulation;
    }

    @Override
    public int getID() {
        return ModuleID;
    }
}
