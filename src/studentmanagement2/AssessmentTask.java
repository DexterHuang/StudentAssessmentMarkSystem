package studentmanagement2;

public class AssessmentTask extends SearchableClass {

    String id;

    String title;

    float weight;

    int fullMarks;

    String description;

    public AssessmentTask(String id, String title, float weight, int fullMarks, String description) {
        this.id = id;
        this.title = title;
        this.weight = weight;
        this.fullMarks = fullMarks;
        this.description = description;
    }

    public AssessmentTask() {

    }

    @Override
    public String toString() {
        return id + " " + description;
    }

    @Override
    public String getID() {
        return id;
    }
}
